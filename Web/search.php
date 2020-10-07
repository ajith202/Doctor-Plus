<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Google Map</title>
        <link href="css/style.css" rel="stylesheet" type="text/css"/>
        <script src="js/jquery-1.11.1.min.js" type="text/javascript"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>
        <script src="js/custom_js.js" type="text/javascript"></script>
        <style type="text/css">
            
            html,body{
                width: 100%;
                height: 100%;
                padding: 0;
                margin: 0;
                position: relative;
                overflow:  hidden;
            }
            .map_view{
                width: 100%;
                height: 100%;

            }
            .input{
                width: 100%;
                padding: 20px;

                position: absolute;
                top: 0;

                z-index: 9999;
            }
            .input input[type=text]{
                padding: 10px;
                width: 300px;

            }
            .view_controls{
                float: right;
                margin-right: 40px;
            }
            .view_controls div{
                display: inline-block;
                margin: 10px 5px;
                -moz-box-shadow:inset 0px 1px 0px 0px #ffffff;
                -webkit-box-shadow:inset 0px 1px 0px 0px #ffffff;
                box-shadow:inset 0px 1px 0px 0px #ffffff;
                background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #ffffff), color-stop(1, #f6f6f6));
                background:-moz-linear-gradient(top, #ffffff 5%, #f6f6f6 100%);
                background:-webkit-linear-gradient(top, #ffffff 5%, #f6f6f6 100%);
                background:-o-linear-gradient(top, #ffffff 5%, #f6f6f6 100%);
                background:-ms-linear-gradient(top, #ffffff 5%, #f6f6f6 100%);
                background:linear-gradient(to bottom, #ffffff 5%, #f6f6f6 100%);
                filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#f6f6f6',GradientType=0);
                background-color:#ffffff;
                -moz-border-radius:6px;
                -webkit-border-radius:6px;
                border-radius:2px;
                text-transform: capitalize;
                cursor:pointer;
                color:#666666;
                font-size: 12px;
                padding:6px 10px;
                text-decoration:none;
            }
        </style>
        <script type="text/javascript">
            var $autocomplete, $map, $marker, latitude, longitude;
            var result_markers = [], result_circles = [];
            function initialize() {
                var auto_complete_input = document.getElementById('googlePlaceAutoCompler');
                var auto_complete_options = {
                    types: ['geocode'],
                    componentRestrictions: {'country': 'ind'}
                };
                var lanlng = new google.maps.LatLng(10.527641599999999, 76.2144349);
                var map_options = {
                    zoom: 10,
                    center: lanlng,
                    mapTypeId: google.maps.MapTypeId.ROADMAP,
                    disableDefaultUI: true
                };
                var map_loader = document.getElementById('googleMap');
                $autocomplete = new google.maps.places.Autocomplete(auto_complete_input, auto_complete_options);
                google.maps.event.addListener($autocomplete, 'place_changed', getSelectedAddress);
                $map = new google.maps.Map(map_loader, map_options);
                $marker = new google.maps.Marker({
                    position: lanlng,
                    map: $map,
//                    draggable: true,
                    animation: google.maps.Animation.DROP,
                    title: 'Am here',
                    icon : 'images/my_position.png'
                    
                });
                google.maps.event.addListener($marker, 'dragend', function (event) {
                    console.log("Lat :" + this.getPosition().lat());
                    console.log("Lng :" + this.getPosition().lng());

                });
                google.maps.event.addListener($marker, 'click', function (event) {
                    $map.setMapTypeId(google.maps.MapTypeId.HYBRID);
                });
            }
            function getSelectedAddress() {

                var place = $autocomplete.getPlace();

                if (!place.geometry) {
                    alert("No Location Found !");
                    return;
                } else {
                    var location = place.geometry.location;
                    latitude = place.geometry.location.lat();
                    longitude = place.geometry.location.lng();
                    $map.panTo(location);
                    $map.setZoom(12);
                    $marker.setPosition(place.geometry.location);
                    $marker.setAnimation(google.maps.Animation.DROP);
                    searchNearby();
                }
            }


            google.maps.event.addDomListener(window, "load", initialize);

            function searchNearby() {
                deleteMarkers();
                deleteCircles();
                $.post("actions.php?action=location_search", {
                    "latitude": latitude,
                    "longitude": longitude
                }, function (data) {
                    var result = data;
                    var result_obj = JSON.parse(result);
                    for (var i = 0; i < result_obj.length; i++) {
                        var latLng = new google.maps.LatLng(result_obj[i].latitude, result_obj[i].longitude);
                        var distance = getDistance(latitude, result_obj[i].latitude, longitude, result_obj[i].longitude);
                        var marker = new google.maps.Marker({
                            position: latLng,
                            map: $map,
                            animation: google.maps.Animation.DROP,
                            title : distance+"KM from you position"
                        });
                    
                        result_markers.push(marker);
                    }
                });
                var place_circle = new google.maps.Circle({
                    center: new google.maps.LatLng(latitude, longitude),
                    radius: 20000,
                    strokeColor: "#ecf0f1",
                    strokeOpacity: 0.8,
                    strokeWeight: 2,
                    fillColor: "#0000ff",
                    fillOpacity: 0.1
                });
                place_circle.setMap($map);
                result_circles.push(place_circle);

            }
   
            function getDistance(lat1, lat2, lon1, lon2, unit) {
                // convert coordinates to radians        
                var radlat1 = Math.PI * lat1 / 180;
                var radlat2 = Math.PI * lat2 / 180;
                var radlon1 = Math.PI * lon1 / 180;
                var radlon2 = Math.PI * lon2 / 180;
                // find the differences between the coordinates
                var theta = lon1 - lon2;
                var radtheta = Math.PI * theta / 180;
                // here's the heavy lifting
                var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
                dist = Math.acos(dist)
                dist = dist * 180 / Math.PI
                dist = dist * 60 * 1.1515
                dist = dist * 1.609344
                dist = Math.round(dist * 1000) / 1000;
                return dist
            }

            function deleteMarkers() {
                //Loop through all the markers and remove
                for (var i = 0; i < result_markers.length; i++) {
                    result_markers[i].setMap(null);
                }
                result_markers = [];
            }
            function deleteCircles() {

                //Loop through all the markers and remove
                for (var i = 0; i < result_circles.length; i++) {
                    result_circles[i].setMap(null);
                }
                result_circles = [];
            }
        </script>
    </head>
    <body>
        <div class="map_view" id="googleMap"></div>
        <div class="input">
            <input id="googlePlaceAutoCompler" type="text" autocomplete="on" placeholder="Enter Your Position" />
        </div>
    </body>
</html>
