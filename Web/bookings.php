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
$date = date('Y-m-d');
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
        <script type="text/javascript">
            $(document).ready(function () {
                $('.ok').click(function (e) {
                   if(!confirm("Click Yes to Confirm Action !"))
                    e.preventDefault();
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
                    <h4>Bookings Today</h4>
                    <table class="table">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Doctor</th>
                            <th>Session</th>
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
                            if ($booking_date == $date && $bookings['status'] == "0") {
                                ?>
                                <tr>
                                    <th>#<?php echo $booking_id; ?></th>
                                    <td><?php echo ucfirst($user_data['name']); ?></td>
                                    <td><?php echo ucfirst($doctor_data['name']); ?></td>
                                    <td><?php echo ucfirst($bookings['session']); ?></td>
                                    <td><?php echo $booking_time; ?></td>
                                    <td> 
        <!--                                        <a href="actions.php?action=remove_booking&id=<?php echo $booking_id; ?>"><span class="glyphicon glyphicon-remove pull-right booking_controls remove"></span></a>-->
                                        <a href="actions.php?action=ok_booking&id=<?php echo $booking_id; ?>"> <span class="glyphicon glyphicon-ok pull-right booking_controls ok"></span>  </a>
                                    </td>
                                </tr>
                                <?php
                            }
                        }
                        ?>
                    </table>
                </div>
            </div>
        </div>


    </body>
</html>
