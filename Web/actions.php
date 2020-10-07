<?php

@session_start();
require './DbConnection/class.DbConnection.php';
$db = new DbConnection();
if (!empty($_REQUEST)) {
    $action = getFormData("action");

    if ($action == "login") {
        $username = getFormData("email");
        $password = getFormData("password");
        $hospital_login = $db->userLogin($username, $password, "hospital");
        $clinic_login = $db->userLogin($username, $password, "clinic");
        $medical_login = $db->userLogin($username, $password, "medical_shop");
        $blood_login = $db->userLogin($username, $password, "blood_bank");
        if (!empty($hospital_login)) {
            $hospital_login = $hospital_login[0];
            $_SESSION['loged_id'] = $hospital_login['id'];
            $_SESSION['loged_email'] = $hospital_login['email'];
            $_SESSION['loged_type'] = "hospital";
            header("Location:hospital.php");
        } else if (!empty($clinic_login)) {
            $clinic_login = $clinic_login[0];
            $_SESSION['loged_id'] = $clinic_login['id'];
             $_SESSION['loged_email'] = $clinic_login['email'];
            $_SESSION['loged_type'] = "clinic";
            header("Location:clinic.php");
        } else if (!empty($medical_login)) {
            $medical_login = $medical_login[0];
            $_SESSION['loged_id'] = $medical_login['id'];
             $_SESSION['loged_email'] = $medical_login['email'];
            $_SESSION['loged_type'] = "medical_shop";
            header("Location:medical.php");
        } else if (!empty($blood_login)) {
            $blood_login = $blood_login[0];
            $_SESSION['loged_id'] = $blood_login['id'];
             $_SESSION['loged_email'] = $blood_login['email'];
            $_SESSION['loged_type'] = "clinic";
            header("Location:blood.php");
        } else {
            echo '<script>';
            echo 'window.alert("Login Failed/nUsername or Password Error !");';
            echo 'window.location = "index.php";';
            echo '</script>';
        }
        exit();

        $_SESSION['LOGED_EMAIL'] = $username;
        header("Location:hospital.php");
    }
    if ($action == "signup") {
        $account_type = getFormData("account_type");
        $name = getFormData("name");
        $email = getFormData("email");
        $password = getFormData("password");
        $website = getFormData("website");
        $phone = getFormData("phone");
        $location = getFormData("location");
        $latitude = getFormData("latitude");
        $longitude = getFormData("longitude");
        $logo = $_FILES['logo'];
        $logo_name = $logo['name'];
        $logo_tmp_path = $logo['tmp_name'];
        list($logo_name, $logo_extn) = explode(".", $logo_name);
        $logo_name = (str_ireplace(" ", "_", $name)) . "." . uniqid() . $logo_extn;
        if ($account_type == "hospital") {
            $logo_save_path = "images/accounts/hospital/" . uniqid() . $logo_name;
            $result = $db->setHospitalData($name, $email, $password, $website, $phone, $location, $latitude, $longitude, $logo_save_path);
        }
        if ($account_type == "clinic") {
            $logo_save_path = "images/accounts/clinic/" . uniqid() . $logo_name;
            $result = $db->setClinicData($name, $email, $password, $website, $phone, $location, $latitude, $longitude, $logo_save_path);
        }
        if ($account_type == "blood_bank") {
            $logo_save_path = "images/accounts/hospital/" . uniqid() . $logo_name;
            $result = $db->setBloodBankData($name, $email, $password, $website, $phone, $location, $latitude, $longitude, $logo_save_path);
        }
        if ($account_type == "medical_shop") {
            $logo_save_path = "images/accounts/hospital/" . uniqid() . $logo_name;
            $result = $db->setMedicalShopData($name, $email, $password, $website, $phone, $location, $latitude, $longitude, $logo_save_path);
        }


        if ($result) {
            move_uploaded_file($logo_tmp_path, $logo_save_path)or die("Image upload Failed!");
            $_SESSION['crop'] = "on";
            $_SESSION['LOGED_EMAIL'] = $email;
            header("Location:crop.php?image=" . urlencode($logo_save_path) . "&aspect=5.5&to=$account_type");
        }
    }
    if ($action == "doctor_add") {
        $name = getFormData("name");
        $qualification = getFormData("qualification");
        $department = getFormData("department");
        $am_from = getFormData("am_from");
        $am_to = getFormData("am_to");
        $pm_from = getFormData("pm_from");
        $pm_to = getFormData("pm_to");
        $LOGED_EMAIL = $_SESSION['LOGED_EMAIL'];
        $hospital_id = getFormData("hospital_id");
        $photo = @$_FILES['photo'] or die(errorAlert("Error in Photo upploading plz uppload an image of size less than 2Mb", "hospital.php"));
        if (!empty($photo)) {
            $photo_save_path = "images/accounts/doctor/$hospital_id" . uniqid() . $name . ".jpg";
            move_uploaded_file($photo['tmp_name'], $photo_save_path)or die("Image upload Failed!");
            $db->setDoctorData($name, $hospital_id, $qualification, $department, $am_from, $am_to, $pm_from, $pm_to, $photo_save_path);
            $_SESSION['crop'] = "on";
            header("Location:crop.php?image=" . urlencode($photo_save_path) . "&aspect=5.5&to=hospital");
        }
    }

    if ($action == "location_search") {
        $latitude = $_REQUEST['latitude'];
        $longitude = $_REQUEST['longitude'];
        $search_result = $db->getNearByLocations("hospital", $latitude, $longitude);
        echo json_encode($search_result);
    }
    if ($action == "doctor_delete") {
        $id = getFormData("id");
        $doctor_data = $db->getDoctorDataById($id);
        $doctor_data = $doctor_data[0];
        $db->deleteDoctorData($id);
        @unlink($doctor_data['photo']);
        errorAlert("Doctor deleted !", "hospital.php");
    }
    if($action == "ok_booking"){
        $id = $_GET['id'];
        $db->okAppoinmetsById($id);
        header("Location:bookings.php");
    }
}

function getFormData($name) {
    return @mysql_real_escape_string($_REQUEST[$name]);
}

function errorAlert($msg, $url) {
    echo '<script>window.alert("' . $msg . '");';
    echo 'window.location="' . $url . '"';
    echo '</script>';
    exit();
}
?>

