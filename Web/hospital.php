<?php
@session_start();
include './DbConnection/class.DbConnection.php';
$db = new DbConnection();
if (empty($_SESSION['loged_email'])) {
    header("Location:index.php");
}
$LOGED_EMAIL = $_SESSION['loged_email'];
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
                    <a href="logout.php"><span class="glyphicon glyphicon-log-out tTip" data- title="Logout"></span></a>
                    <a href="bookings.php"><span class="glyphicon glyphicon-edit tTip" title="View booking"></span></a>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="content_wraper">
                <div class="profile_con">
                    <img src="<?php echo $hospital_logo ?>" class="pull-left" width="100">
                    <h4><?php echo $hospital_name; ?></h4>
                </div>
                <div class="doctors_con">
                    <h4>Doctors<span class=" pull-right glyphicon glyphicon tTip glyphicon-plus doctor_add_btn" data-toggle="modal" data-target="#doctorAddModel" data-placment="down" title="Cick to add Doctor"></span></h4>
                    <div class="row">
                        <?php
                        $doctor_data = $db->getDoctorDataByHospitalId($hospital_id);

                        foreach ($doctor_data as $doctor) {
                            $doctor_name = ucwords($doctor['name']);
                            $doctor_photo = $doctor['photo'];
                            $doctor_id = $doctor['id'];
                            $doctor_qualification = $doctor['qualification'];
                            $doctor_depaertmanet = ucwords($doctor['department']);
                            $doctor_am_from = $doctor['am_from'];
                            $doctor_am_to = $doctor['am_to'];
                            $doctor_pm_from = $doctor['pm_from'];
                            $doctor_pm_to = $doctor['pm_to'];
                            ?>
                            <div class="col-lg-3 col-md-3 col-sm-4 col-xs-6 " align="center">
                                <div class="doctors_list_item">
                                    <img class="doc_photo" src="<?php echo $doctor_photo; ?>" />
                                    <h5 class="doc_name"><?php echo $doctor_name; ?></h5>
                                    <h5 class="doc_qualification"><?php echo $doctor_qualification; ?></h5>
                                    <h5 class="doc_department"><?php echo $doctor_depaertmanet; ?></h5>
                                    <a href="actions.php?action=doctor_delete&id=<?php echo $doctor_id; ?>" class="pull-right">delete</a>
                                </div>
                            </div>
                            <?php
                        }
                        ?>
                    </div>
                </div>
            </div>
        </div>

        <!-- -----------------modals----------------->
        <div class="modal fade" id="doctorAddModel" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form class="doctor_add_form" method="post" action="actions.php?action=doctor_add" enctype="multipart/form-data">
                        <div class="modal-header">
                            Add Doctor
                        </div>
                        <div class="modal-body">
                            <div class="input-group">
                                <div class="input-group-addon">Doctor name</div>
                                <input type="text" name="name" class="form-control" placeholder="Doctor name" />
                                <input type="hidden" name="hospital_id" value="<?php echo $hospital_id; ?>"/>
                            </div>
                            <div class="input-group">
                                <div class="input-group-addon">Qualification</div>
                                <input type="text" name="qualification" class="form-control" placeholder="Qualification" />
                            </div>
                            <div class="input-group">
                                <div class="input-group-addon">Department</div>
                                <select name="department" class="form-control">
                                    <?php
                                    $department_data = $db->getDepartments();
                                    foreach ($department_data as $department) {
                                        $department_name = $department['name'];
                                        ?>
                                        <option value="<?php echo $department_name; ?>"><?php echo $department_name; ?></option>
                                        <?php
                                    }
                                    ?>
                                </select>
                            </div>
                            <div class="input-group">
                                <div class="input-group-addon">Photo</div>
                                <input type="file" name="photo" accept="image/jpeg" class="form-control" placeholder="Choose a File" />
                            </div>
                            <div class="input-group">
                                <div class="input-group-addon">Moring From</div>
                                <input type="time" name="am_from" class="form-control" placeholder="From" value="10:15" />
                                <div class="input-group-addon">Moring From</div>
                                <input type="time" name="am_to" class="form-control" placeholder="From" value="10:15" />
                            </div>                        
                            <div class="input-group">
                                <div class="input-group-addon">Evening From</div>
                                <input type="time" name="pm_from" class="form-control" placeholder="From" value="10:15" />
                                <div class="input-group-addon">Evening To</div>
                                <input type="time" name="pm_to" class="form-control" placeholder="From" value="10:15" />
                            </div> 

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Add Doctor</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!--------------------------------------------------->
    </body>
</html>
