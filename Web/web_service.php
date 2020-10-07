<?php

include './DbConnection/class.DbConnection.php';
$db = new DbConnection();

if (!empty($_REQUEST)) {
    $action = getRequestData('action');
    if ($action == "user_signup") {
        $name = getRequestData('name');
        $email = getRequestData('email');
        $password = getRequestData('password');
        $gender = getRequestData('gender');
        $dob = getRequestData('dob');
        $place = getRequestData('place');
        $city = getRequestData('city');
        $district = getRequestData('district');
        $state = getRequestData('name');
        $phone = getRequestData('phone');
        $avatar = $_REQUEST['avatar'];
        $address = "$place,$city,$district,$state";
        list($email_name, $host_name) = explode("@", $email);
        $avatar_save_path = "images/accounts/user/" . $email_name . ".jpg";
        imagecompress(base64_to_jpeg($avatar, "images/temp/tmp.jpg"), $avatar_save_path, 100);
        $response = $db->setUserData($name, $email, $password, $gender, $dob, $address, $phone, $avatar_save_path);
        echo $response;
        if ($response) {
            echo true;
        } else {
            echo false;
        }
    }
    if ($action == "login") {
        $username = getRequestData('username');
        $password = getRequestData('password');
        $result = $db->userLogin($username, $password, "user");
        if (!empty($result)) {
            echo json_encode($result[0]);
        } else {
            echo "error";
        }
    }
    if ($action == "search") {
        $search_type = getRequestData('type');
        $key = getRequestData('key');
        $latitude = getRequestData('latitude');
        $longitude = getRequestData('longitude');
        $results_array = array();
        $search_result;
        if ($key != "") {
//            $Address = urlencode($key);
//             $geocode=file_get_contents('http://maps.google.com/maps/api/geocode/json?address='.$Address.'&sensor=false') or die("url not loading :-(");
//             $output= json_decode($geocode);
//        $latitude = $output->results[0]->geometry->location->lat;
//        $longitude = $output->results[0]->geometry->location->lng;
            $search_result = $db->getData("SELECT *,0 as distance FROM $search_type where location like '%$key%'");
        } else {
            $search_result = $db->getNearByLocations($search_type, $latitude, $longitude);
        }

        if (!empty($search_result)) {
            echo json_encode(base64imageReplaceInArray($search_result, "logo"));
        } else {
            echo "null";
        }
    }

    if ($action == "hospital_doctor_get") {
        $id = getRequestData('id');
        $doctors_data = $db->getDoctorDataByHospitalId($id);
        if (!empty($doctors_data)) {
            echo json_encode(base64imageReplaceInArray($doctors_data, "photo"));
        } else {
            echo "null";
        }
    }
    if ($action == "hospital_get_by_id") {
        $id = getRequestData('id');
        $doctors_data = $db->getHospitalDataById($id);
        if (!empty($doctors_data)) {
            echo json_encode(base64imageReplaceInArray($doctors_data, "logo"));
        } else {
            echo "error";
        }
    }
    if ($action == "clinic_get_by_id") {
        $id = getRequestData('id');
        $doctors_data = $db->getClinicDataById($id);
        if (!empty($doctors_data)) {
            echo json_encode(base64imageReplaceInArray($doctors_data, "logo"));
        } else {
            echo "error";
        }
    }
    if ($action == "blood_get_by_id") {
        $id = getRequestData('id');
        $doctors_data = $db->getBloodBankDataById($id);
        if (!empty($doctors_data)) {
            echo json_encode(base64imageReplaceInArray($doctors_data, "logo"));
        } else {
            echo "error";
        }
    }
    if ($action == "medical_get_by_id") {
        $id = getRequestData('id');
        $doctors_data = $db->getMedicalShopDataById($id);
        if (!empty($doctors_data)) {
            echo json_encode(base64imageReplaceInArray($doctors_data, "logo"));
        } else {
            echo "error";
        }
    }

    if ($action == "booking") {
        $hospital_id = getRequestData("hospital_id");
        $doctor_id = getRequestData("doctor_id");
        $user_id = getRequestData("user_id");
        $session = getRequestData("session");
        $date = getRequestData("date");
        $hopital_data = $db->getHospitalDataById($hospital_id);
        $doctor_data = $db->getDoctorDataById($doctor_id);
        $user_data = $db->getUserDataById($user_id);
        $hopital_data = $hopital_data[0];
        $doctor_data = $doctor_data[0];
        $user_data = $user_data[0];
        $doctor_am_from = $doctor_data['am_from'];
        $doctor_am_to = $doctor_data['am_to'];
        $doctor_pm_from = $doctor_data['pm_from'];
        $doctor_pm_to = $doctor_data['pm_to'];
        $doctor_am_duration = getTimeDiffrence($doctor_am_from, $doctor_am_to);
        $doctor_pm_duration = getTimeDiffrence($doctor_pm_from, $doctor_pm_to);
        if ($session == "Morning") {
            if ($doctor_am_duration == 0) {
                echo "Not available";
                exit();
            } else {
                $doctor_am_duration_minites = timeToMinute($doctor_am_duration);
                $alowed_patients = (($doctor_am_duration_minites * 3) / 30);
                $booking_data = $db->getAppoinmets($hospital_id, $doctor_id, $session, $date);
                if (inMultiArrayCheck($user_id, $booking_data)) {
                    echo "Apponment Already Taken !";
                } else {
                    $current_bookings = sizeof($booking_data);
                    if ($current_bookings < $alowed_patients) {
                        $x = intval($current_bookings / 3);
                        $selectedTime = $doctor_am_from;
                        $startTime = strtotime("+" . (30 * $x) . " minutes", strtotime($selectedTime));
                        $startTime = date('h:i:s', $startTime);
                        $endTime = strtotime("+" . ((30 * $x) + 30) . " minutes", strtotime($selectedTime));
                        $endTime = date('h:i:s', $endTime);
                        if (strtotime($doctor_am_to) - strtotime($endTime) < 0) {
                            $endTime = $doctor_am_to;
                        }
                        $time = "$startTime - $endTime";
                        $db->addAppoinment($user_id, $hospital_id, $doctor_id, $session, $time, $date);
                        echo "Appoinment Taken between $startTime to $endTime";
                    } else {
                        echo "Closed";
                    }
                }
            }
        }
        if ($session == "Evening") {
            if ($doctor_pm_duration == 0) {
                echo "Not available";
                exit();
            } else {
                $doctor_pm_duration_minites = timeToMinute($doctor_pm_duration);
                $alowed_patients = (($doctor_pm_duration_minites * 3) / 30);
                $booking_data = $db->getAppoinmets($hospital_id, $doctor_id, $session, $date);
                if (inMultiArrayCheck($user_id, $booking_data)) {
                    echo "Apponment Already Taken !";
                } else {
                    $current_bookings = sizeof($booking_data);
                    if ($current_bookings < $alowed_patients) {
                        $x = intval($current_bookings / 3);
                        $selectedTime = $doctor_pm_from;
                        $startTime = strtotime("+" . (30 * $x) . " minutes", strtotime($selectedTime));
                        $startTime = date('h:i:s', $startTime);
                        $endTime = strtotime("+" . ((30 * $x) + 30) . " minutes", strtotime($selectedTime));
                        $endTime = date('h:i:s', $endTime);
                        if (strtotime($doctor_pm_to) - strtotime($endTime) < 0) {
                            $endTime = $doctor_am_to;
                        }
                        $time = "$startTime - $endTime";
                        $db->addAppoinment($user_id, $hospital_id, $doctor_id, $session, $time, $date);
                        echo "Appoinment Taken between $startTime to $endTime";
                    } else {
                        echo "Closed";
                    }
                }
            }
        }
    }

    if ($action == "get_bookings") {
        $total_bookings = array();
        $user_id = getRequestData("user_id");
        $booking_data = $db->getAppoinmetsByUserId($user_id);
        if (!empty($booking_data)) {
            foreach ($booking_data as $bookings) {
                $hospital_id = $bookings['hospital_id'];
                $doctor_id = $bookings['doctor_id'];
                $hospital_data = $db->getHospitalDataById($hospital_id);
                $doctor_data = $db->getDoctorDataById($doctor_id);
                $bookings['hospital_logo'] = $hospital_data[0]['logo'];
                $bookings['hospital_name'] = $hospital_data[0]['name'];
                $bookings['doctor_name'] = $doctor_data[0]['name'];
                $total_bookings[] = $bookings;
            }
            $total_bookings = base64imageReplaceInArray($total_bookings, "hospital_logo");
            echo json_encode($total_bookings);
        } else {
            echo "null";
        }
        ;
    }
}

//echo "AM = " . timeToMinute($doctor_am_duration) . " Minutes";
//echo "<br>PM = " . $doctor_pm_duration . "---" . timeToMinute($doctor_pm_duration) . " Minutes";

function getTimeDiffrence($from, $to) {
    $db = new DbConnection();
    $duration_result = $db->getData("SELECT TIMEDIFF('$to','$from') as timediff");
    $duration = $duration_result[0]['timediff'];
    return $duration;
}

function timeToMinute($time) {
    list($hrs, $min, $sec) = explode(":", $time);
    $minute = ($hrs * 60) + $min;
    return $minute;
}

function inMultiArrayCheck($key, $multi_array) {
    foreach ($multi_array as $array) {
        if (in_array($key, $array)) {
            return true;
            exit();
        }
    }
}

function base64imageReplaceInArray($array, $field) {
    $array_size = sizeof($array) - 1; // size of the array (-1)
    for ($i = 0; $i <= $array_size; $i++) {
        $array[$i][$field] = base64_encode(file_get_contents($array[$i][$field]));
        if ($i == $array_size) {
            // showFinalResult($array);
            return $array;
        }
    }
}

function showFinalResult($array) {
//               echo json_encode($result);
    print_r($result);
}

function getRequestData($field_name) {
    return mysql_real_escape_string($_REQUEST[$field_name]);
}

function base64_to_jpeg($base64_string, $output_file) {
    $ifp = fopen($output_file, "wb");
    fwrite($ifp, base64_decode($base64_string));
    fclose($ifp);
    return $output_file;
}

function imagecompress($source, $destination, $quality) {
    $info = getimagesize($source);
    if ($info['mime'] == 'image/jpeg')
        $image = imagecreatefromjpeg($source);
    elseif ($info['mime'] == 'image/gif')
        $image = imagecreatefromgif($source);
    elseif ($info['mime'] == 'image/png')
        $image = imagecreatefrompng($source);
    imagejpeg($image, $destination, $quality);
    unlink($source);
    return $destination;
}

?>