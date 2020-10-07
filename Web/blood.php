<?php
@session_start();
include './DbConnection/class.DbConnection.php';
$db = new DbConnection();
if (empty($_SESSION['loged_email'])) {
    header("Location:index.php");
}
$LOGED_EMAIL = $_SESSION['loged_email'];

$blood_data = $db->getBloodBankByEmail($LOGED_EMAIL);
$blood_data = $blood_data[0];
$blood_id = $blood_data['id'];
$blood_name = $blood_data['name'];
$blood_email = $blood_data['email'];
$blood_phone = $blood_data['phone'];
$blood_logo = $blood_data['logo'];
$blood_website = $blood_data['website'];
$blood_location = $blood_data['location'];
$blood_latitude = $blood_data['latitude'];
$blood_longitude = $blood_data['longitude'];
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
                  
                </div>
            </div>
        </div>
        <div class="container">
            <div class="content_wraper">
                <div class="profile_con">
                    <img src="<?php echo $blood_logo ?>" class="pull-left" width="100">
                    <h4><?php echo $blood_name; ?></h4>
                </div>
                <div class="doctors_con">
                    <h4>Details</h4>
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
                                <div class="text-center logo_add_btn" style="background: url('<?php echo $blood_logo; ?>')">
                                    <h5 class="text-logo-please">Logo</h5>
                                </div>
                            </div>
                        </div>
                        <input disabled="" type="hidden" name="account_type" />
                        <input disabled="" type="file" name="logo" accept="image/jpeg" />
                        <div class="">
                            <div class="input-group">
                                <div class="input-group-addon">Name</div>
                                <input disabled="" type="text" name="name" value="<?php echo $blood_name; ?>" class="form-control" placeholder="Enter Name of Bussiness/Organization" required />
                            </div>
                            <div class="input-group">
                                <div class="input-group-addon">E-mail</div>
                                <input disabled="" type="email" name="email" value="<?php echo $blood_email; ?>" class="form-control" placeholder="Email" required />
                            </div>
                            <div class="input-group">
                                <div class="input-group-addon">Password</div>
                                <input disabled="" type="password" name="password" value="" class="form-control" placeholder="Password" required />
                            </div>
                            <div class="input-group">
                                <div class="input-group-addon">Confirm Password</div>
                                <input disabled="" type="password" name="re-password" class="form-control" placeholder="Confirm Password" required />
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-6">
                            <div class="input-group">
                                <div class="input-group-addon">Website</div>
                                <input disabled="" type="text" value="<?php echo $blood_website; ?>" name="website" class="form-control" placeholder="Website Address" required>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="input-group">
                                <div class="input-group-addon">Phone</div>
                                <input disabled="" type="text" name="phone" value="<?php echo $blood_phone; ?>" class="form-control" placeholder="Phone number" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <div class="input-group-addon">Location</div>
                                <input disabled="" type="text" name="location" value="<?php echo $blood_location; ?>" class="form-control select_location" placeholder="Location" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-6">
                            <div class="input-group">
                                <div class="input-group-addon">Latitude</div>
                                <input disabled="" type="text" class="form-control" value="<?php echo $blood_latitude; ?>" name="latitude" required readonly />
                            </div>
                        </div>
                        <div class="col-xs-6">
                            <div class="input-group">
                                <div class="input-group-addon">Longitude</div>
                                <input disabled="" type="text" class="form-control" value="<?php echo $blood_longitude; ?>" name="longitude" required readonly />
                            </div>
                        </div>
                    </div>
                   
                    <div class="row">
                       
                    </div>
                </form>
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
                                <input disabled="" type="text" name="name" class="form-control" placeholder="Doctor name" />
                                <input disabled="" type="hidden" name="blood_id" value="<?php echo $blood_id; ?>"/>
                            </div>
                            <div class="input-group">
                                <div class="input-group-addon">Qualification</div>
                                <input disabled="" type="text" name="qualification" class="form-control" placeholder="Qualification" />
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
                                <input disabled="" type="file" name="photo" accept="image/jpeg" class="form-control" placeholder="Choose a File" />
                            </div>
                            <div class="input-group">
                                <div class="input-group-addon">Moring From</div>
                                <input disabled="" type="time" name="am_from" class="form-control" placeholder="From" value="10:15" />
                                <div class="input-group-addon">Moring From</div>
                                <input disabled="" type="time" name="am_to" class="form-control" placeholder="From" value="10:15" />
                            </div>                        
                            <div class="input-group">
                                <div class="input-group-addon">Evening From</div>
                                <input disabled="" type="time" name="pm_from" class="form-control" placeholder="From" value="10:15" />
                                <div class="input-group-addon">Evening To</div>
                                <input disabled="" type="time" name="pm_to" class="form-control" placeholder="From" value="10:15" />
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
