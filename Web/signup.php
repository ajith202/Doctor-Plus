<html>
    <head>
        <title>Doctor Plus</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/style.css" rel="stylesheet" type="text/css"/>
        <script src="js/jquery-1.11.1.min.js" type="text/javascript"></script>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>
        <script src="js/custom_js.js" type="text/javascript"></script>
        <script type="text/javascript">
            var $map, $autocomplete, $marker, latitude, longitude, address;

            function initialize() {
                var auto_complete_input = document.getElementById('place_autocomplete');
                var auto_complete_options = {
//                    types: ['geocode'],
                    componentRestrictions: {'country': 'ind'}
                };
                var lanlng = new google.maps.LatLng(10.5276754899999, 76.2165849);
                var map_options = {
                    zoom: 5,
                    center: lanlng,
                    mapTypeId: google.maps.MapTypeId.HYBRID,
                    disableDefaultUI: true
                };
                var map_loader = document.getElementById('map_view');
                $autocomplete = new google.maps.places.Autocomplete(auto_complete_input, auto_complete_options);
                $map = new google.maps.Map(map_loader, map_options);
                $marker = new google.maps.Marker({
                    position: lanlng,
                    map: $map,
                    draggable: true,
                    animation: google.maps.Animation.DROP,
                    title: 'Click me',
                    visible: false
                });
                google.maps.event.addListener($autocomplete, "place_changed", getSelectedplace);
                google.maps.event.addListener($marker, "dragend", function (event) {
                    latitude = this.getPosition().lat();
                    longitude = this.getPosition().lng()
                });
            }
            function getSelectedplace() {
                var place = $autocomplete.getPlace();
                if (!place.geometry) {
                    alert("Your Searched place not found!.Plase use the makers to point to your location to get the GeoCordinates");
                    return;
                } else {
                    var location = place.geometry.location;
                    latitude = place.geometry.location.lat();
                    longitude = place.geometry.location.lng();
                    $map.panTo(location);
                    $map.setZoom(16);
                    $marker.setPosition(location);
                    $marker.setVisible(true);
                    $marker.setAnimation(google.maps.Animation.BOUNCE);
                    console.log(place);
                    alert("Plase Select your exact position.\n1.Drag the marker to your location\n2.Use mouse to scroll and pan arround the map\n3.Locate your position and Drop the marker there");
                    address = place.formatted_address; // gets the formated address of the selected place from places object
                }
            }

            $(document).ready(function () {
                initialize();
                $('.select_location').focus(function () {
                    var location_input = $(this);
                    var $modal = $('#locationSelectModal');
                    var lan_input = $('input[name=latitude]');
                    var lng_input = $('input[name=longitude]');
                    var $modal_submit = $modal.find('button.save_location');
                    $modal.modal('show').on('shown.bs.modal', function () {
                        google.maps.event.trigger($map, "resize");
                    });
                    $modal_submit.click(function () {
                        location_input.val(address);
                        lan_input.val(latitude);
                        lng_input.val(longitude);
                        $modal.modal('hide');
                    });
                });


            });
        </script>
    </head>
    <body style="overflow-y: auto;">
        <div class="container">
            <div class="navigation_head">
                <img class="img-responsive logo" src="images/logo.png">
            </div>
            <div class="content_wraper" align="center">
                <form class="signup_form" action="actions.php?action=signup" method="post" enctype="multipart/form-data">

                    <div class="row">
                        <div class="col-xs-12 col-sm-12 visible-sm visible-xs">
                            <div class="input-group">
                                <div class="text-center logo_add_btn" >
                                    <h5 class="text-logo-please">Logo Please</h5>
                                </div>
                            </div>
                        </div>
                        <div class="pull-left hidden-xs hidden-sm">
                            <div class="input-group">
                                <div class="text-center logo_add_btn" >
                                    <h5 class="text-logo-please">Logo</h5>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="account_type" />
                        <input type="file" name="logo" accept="image/jpeg" />
                        <div class="">
                            <div class="input-group">
                                <div class="input-group-addon">Name</div>
                                <input type="text" name="name" class="form-control" placeholder="Enter Name of Bussiness/Organization" required />
                            </div>
                            <div class="input-group">
                                <div class="input-group-addon">E-mail</div>
                                <input type="email" name="email" class="form-control" placeholder="Email" required />
                            </div>
                            <div class="input-group">
                                <div class="input-group-addon">Password</div>
                                <input type="password" name="password" class="form-control" placeholder="Password" required />
                            </div>
                            <div class="input-group">
                                <div class="input-group-addon">Confirm Password</div>
                                <input type="password" name="re-password" class="form-control" placeholder="Confirm Password" required />
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-6">
                            <div class="input-group">
                                <div class="input-group-addon">Website</div>
                                <input type="text" name="website" class="form-control" placeholder="Website Address" required>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="input-group">
                                <div class="input-group-addon">Phone</div>
                                <input type="text" name="phone" class="form-control" placeholder="Phone number" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <div class="input-group-addon">Location</div>
                                <input type="text" name="location" class="form-control select_location" placeholder="Location" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-6">
                            <div class="input-group">
                                <div class="input-group-addon">Latitude</div>
                                <input type="text" class="form-control" name="latitude" required readonly />
                            </div>
                        </div>
                        <div class="col-xs-6">
                            <div class="input-group">
                                <div class="input-group-addon">Longitude</div>
                                <input type="text" class="form-control" name="longitude" required readonly />
                            </div>
                        </div>
                    </div>
                   
                    <div class="row">
                        <div class="col-xs-12">
                            <input type="submit" 
                                   class="form-control ok_btn text-center" 
                                   value="Create Account" />
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- -----------------modals----------------->
        <div class="modal fade" id="locationSelectModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <input type="text" id="place_autocomplete" placeholder="Enter Your Location name [ Eg: Mala,kerala ]" autocomplete="on">
                    </div>
                    <div class="modal-body">
                        <div class="map_view" id="map_view"></div>
                    </div>
                    <div class="modal-footer">
                        <button class="save_location">Use this Location</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="signupCategoryChooseModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4>Register Account As</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row" data-iRandom="on">
                            <div class="col-md-6 col-xs-6">
                                <button class="signup_option_item" data-value="hospital">Hospital</button>
                            </div>
                            <div class="col-md-6 col-xs-6">
                                <button class="signup_option_item" data-value="clinic">Clinic</button>
                            </div>
                            <div class="col-md-6 col-xs-6">
                                <button class="signup_option_item" data-value="blood_bank">Blood Bank</button>
                            </div>
                            <div class="col-md-6 col-xs-6">
                                <button  class="signup_option_item" data-value="medical_shop">Medical Shop</button>
                            </div>
                        </div>
                    </div>   
                </div>
            </div>
        </div>
        <!--------------------------------------------------->
    </body>
</html>
