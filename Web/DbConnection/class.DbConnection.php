<?php

class DbConnection {

    public function __construct() {
//         @mysql_connect("localhost", "cmindia_doctor", "cm_doctor+123") or die(mysql_error());
//         @mysql_select_db("cmindia_doctor") or die(mysql_error());
        @mysql_connect("localhost", "root", "") or die(mysql_error());
         @mysql_select_db("doctor+") or die(mysql_error());
    }

    public function getData($query) {
        $data = array();
        $result = mysql_query($query);
        while ($rs = mysql_fetch_assoc($result)) {
            $data[] = $rs;
        }
        return $data;
    }
    public function setData($query) {
        $result = mysql_query($query) or die(mysql_error());
        if ($result)
            return true;
        else
            return false;
    }

    public function userLogin($username, $password, $from) {
        $query = "SELECT * FROM $from where email = '" . $username . "' and `password` = '" . $password . "'";
        return $this->getData($query);
    }

    public function setHospitalData($name, $email, $password, $website, $phone, $location, $latitude, $longitude, $logo) {
        $verification_code = sprintf("%06d", mt_rand(1, 999999));
        $query = "INSERT INTO hospital set name = '$name',email = '$email',password = '$password',website = '$website',phone = '$phone',location = '$location',latitude = '$latitude',longitude = '$longitude',logo='$logo'";
        return $this->setData($query);
    }

    public function getHospitalDataByEmail($email) {
        $query = "SELECT * FROM hospital where email = '" . $email . "'";
        return $this->getData($query);
    }

    public function getHospitalDataById($id) {
        $query = "SELECT * FROM hospital where id = '" . $id . "'";
        return $this->getData($query);
    }
    public function setClinicData($name, $email, $password, $website, $phone, $location, $latitude, $longitude, $logo) {
        $verification_code = sprintf("%06d", mt_rand(1, 999999));
        $query = "INSERT INTO clinic set name = '$name',email = '$email',password = '$password',website = '$website',phone = '$phone',location = '$location',latitude = '$latitude',longitude = '$longitude',logo='$logo'";
        return $this->setData($query);
    }

    public function getClinicByEmail($email) {
        $query = "SELECT * FROM clinic where email = '" . $email . "'";
        return $this->getData($query);
    }

    public function getClinicDataById($id) {
        $query = "SELECT * FROM clinic where id = '" . $id . "'";
        return $this->getData($query);
    }
       public function setBloodBankData($name, $email, $password, $website, $phone, $location, $latitude, $longitude, $logo) {
        $verification_code = sprintf("%06d", mt_rand(1, 999999));
        $query = "INSERT INTO blood_bank set name = '$name',email = '$email',password = '$password',website = '$website',phone = '$phone',location = '$location',latitude = '$latitude',longitude = '$longitude',logo='$logo'";
        return $this->setData($query);
    }

    public function getBloodBankByEmail($email) {
        $query = "SELECT * FROM blood_bank where email = '" . $email . "'";
        return $this->getData($query);
    }

    public function getBloodBankDataById($id) {
        $query = "SELECT * FROM blood_bank where id = '" . $id . "'";
        return $this->getData($query);
    }
       public function setMedicalShopData($name, $email, $password, $website, $phone, $location, $latitude, $longitude, $logo) {
        $verification_code = sprintf("%06d", mt_rand(1, 999999));
        $query = "INSERT INTO blood_bank set name = '$name',email = '$email',password = '$password',website = '$website',phone = '$phone',location = '$location',latitude = '$latitude',longitude = '$longitude',logo='$logo'";
        return $this->setData($query);
    }

    public function getMedicalShopByEmail($email) {
        $query = "SELECT * FROM blood_bank where email = '" . $email . "'";
        return $this->getData($query);
    }

    public function getMedicalShopDataById($id) {
        $query = "SELECT * FROM medical_shop where id = '" . $id . "'";
        return $this->getData($query);
    }
    

    public function getNearByLocations($from, $latitude, $longitude) {
        $query = sprintf("SELECT *, ( 3959 * acos( cos( radians('%s') ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians('%s') ) + sin( radians('%s') ) * sin( radians( latitude ) ) ) ) AS distance FROM $from HAVING distance < '%s' ORDER BY distance ASC", mysql_real_escape_string($latitude), mysql_real_escape_string($longitude), mysql_real_escape_string($latitude), mysql_real_escape_string(100000));

        //6.213712
//         ORDER BY distance LIMIT 0 , 20
        return $this->getData($query);
    }

    public function setUserData($name, $email, $password, $gender, $dob, $address, $phone, $avatar) {
        $query = "INSERT INTO user set name = '$name', email = '$email', password = '$password', gender = '$gender', dob = '$dob', address = '$address' , phone = '$phone', avatar = '$avatar'";
        return $this->setData($query);
    }
     public function getUserDataById($id) {
        $query = "SELECT * FROM user where id='$id'";
        return $this->getData($query);
    }
    public function setDoctorData($name, $hospital_id,$qualification, $department, $am_from, $am_to, $pm_from, $pm_to, $photo) {
        $query = "INSERT INTO doctor set name = '$name',hospital_id = '$hospital_id', qualification = '$qualification', department = '$department', am_from = '$am_from', am_to = '$am_to', pm_from = '$pm_from' , pm_to = '$pm_to', photo = '$photo'";
        return $this->setData($query);
    }
    public function getDoctorData() {
        $query = "SELECT * FROM doctor";
        return $this->getData($query);
    }
    public function getDoctorDataById($id) {
        $query = "SELECT * FROM doctor where id='$id'";
        return $this->getData($query);
    }
     public function getDoctorDataByHospitalId($id) {
        $query = "SELECT * FROM doctor where hospital_id='$id'";
        return $this->getData($query);
    }
    public function deleteDoctorData($id) {
        $query = "DELETE FROM doctor where id='$id'";
        return $this->setData($query);
    }
    public function getDepartments() {
        $query = "SELECT * FROM departments";
        return $this->getData($query);
    }
    public function addAppoinment($user_id,$hospital_id,$doctor_id,$session,$time,$date) {
        $query = "INSERT INTO booking set `user_id` = '$user_id', `hospital_id` = '$hospital_id', `doctor_id` = '$doctor_id', `session` = '$session', time = '$time', `date` = '$date'";
        return $this->setData($query);
    }
    public function getAppoinmets($hospital_id,$doctor_id,$session,$date) {
        $query = "SELECT * FROM booking  where `hospital_id` = '$hospital_id' and `doctor_id` = '$doctor_id' and `session` = '$session' and `date` = '$date'";
        return $this->getData($query);
    }
    public function getAppoinmetsByUserId($user_id) {
        $query = "SELECT * FROM booking  where `user_id` = '$user_id' ORDER BY date ASC";
        return $this->getData($query);
    }
    public function getAppoinmetsByHospitalId($hospital_id) {
        $query = "SELECT * FROM booking  where `hospital_id` = '$hospital_id'";
        return $this->getData($query);
    }
    public function deleteAppoinmetsById($id) {
        $query = "DELETE FROM booking  where `id` = '$id'";
        return $this->setData($query);
    }
    public function okAppoinmetsById($id) {
        $query = "UPDATE  booking set status = '1' where `id` = '$id'";
        return $this->setData($query);
    }
    

    protected static final function createDbTables() {
        mysql_query("CREATE TABLE IF NOT EXISTS `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) NOT NULL,
                        `email` varchar(255) NOT NULL,
                        `password` varchar(255) NOT NULL,
                        `website` varchar(255) NOT NULL,
                        `phone` varchar(255) NOT NULL,
                        `location` text NOT NULL,
                        `latitude` text NOT NULL,
                        `longitude` text NOT NULL,
                        `logo` text NOT NULL,
                        PRIMARY KEY (`id`)
                      );");
        mysql_query("CREATE TABLE IF NOT EXISTS `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) NOT NULL,
                        `email` varchar(255) NOT NULL,
                        `password` varchar(255) NOT NULL,
                        `gender` varchar(255) NOT NULL,
                        `dob` date NOT NULL,
                        `address` text NOT NULL,
                        `phone` varchar(255) NOT NULL,
                        `avatar` text NOT NULL,
                        PRIMARY KEY (`id`)
                      );");
    }

}

?>