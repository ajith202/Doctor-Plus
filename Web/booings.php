<?php
@session_start();
include './DbConnection/class.DbConnection.php';
$db = new DbConnection();
if (empty($_SESSION['LOGED_EMAIL'])) {
    header("Location:index.php");
}
$LOGED_EMAIL = $_SESSION['LOGED_EMAIL'];
$hospital_data = $db->getHospitalDataByEmail($LOGED_EMAIL);
$hospital_data = $hospital_data[0];
$hospital_id = $hospital_data['id'];
$hospital_name = $hospital_data['name'];
$hospital_phone = $hospital_data['phone'];
$hospital_logo = $hospital_data['logo'];
?>
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
        <script src="js/custom_js.js" type="text/javascript"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>
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
                <img class="img-responsive logo pull-left" src="images/logo.png">
                <div class="account-controls pull-right">
                    <a href="#"><span class="glyphicon glyphicon-log-out tTip" data- title="Logout"></span></a>
                    <a href="#"><span class="glyphicon glyphicon-cog tTip" title="Settings"></span></a>
                    <a href="#"><span class="glyphicon glyphicon-edit tTip" title="View booking"></span></a>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="content_wraper">
                <div class="profile_con bookings_table">
                    <img src="<?php echo $hospital_logo ?>" class="pull-left" width="100">
                    <h4><?php echo $hospital_name; ?></h4>
                </div>
                <div class="doctors_con">
                    <h4>Bookings</h4>
                    <table class="table">
                        <tr>
                            <th>Date</th>
                            <th>Name</th>
                            <th>Doctor</th>
                            <th>Time</th>
                            <th></th>
                        </tr>
                        <?php
                        $bookings_data = $db->getAppoinmetsByHospitalId($hospital_id);
                        foreach ($bookings_data as $bookings) {
                            $booking_id = $bookings['id'];
                            $booking_hospital_id = $bookings['hospital_id'];
                            $booking_doctor_id = $bookings['doctor_id'];
                            $booking_user_id = $bookings['user_id'];
                            $booking_date = $bookings['date'];
                            $booking_time = $bookings['time'];
                            $booking_session = $bookings['session'];
                            $doctor_data = $db->getDoctorDataById($booking_doctor_id);
                            $user_data = $db->getUserDataById($booking_user_id);
                            $doctor_data = $doctor_data[0];
                            $user_data = $user_data[0];
                            ?>
                            <tr>
                                <th><?php echo $booking_date;?></th>
                                <td><?php echo ucfirst($user_data['name']); ?></td>
                                <td><?php echo ucfirst($doctor_data['name']); ?></td>
                                <td><?php echo $booking_time; ?></td>
                                <td> 
                                    <span class="glyphicon glyphicon-remove pull-right booking_controls remove"></span>
                                    <span class="glyphicon glyphicon-ok pull-right booking_controls ok"></span>  
                                </td>
                            </tr>
                            <?php
                        }
                        ?>
                    </table>
                </div>
            </div>
        </div>


    </body>
</html>
