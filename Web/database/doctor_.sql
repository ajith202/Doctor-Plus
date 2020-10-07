-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 03, 2015 at 02:59 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `doctor+`
--

-- --------------------------------------------------------

--
-- Table structure for table `blood_bank`
--

CREATE TABLE IF NOT EXISTS `blood_bank` (
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `blood_bank`
--

INSERT INTO `blood_bank` (`id`, `name`, `email`, `password`, `website`, `phone`, `location`, `latitude`, `longitude`, `logo`) VALUES
(13, 'Government Hospital Mala', 'govthospmala@gmail.com', '12345', 'http://govtmala.com', '9961599207', 'Mala, Kerala, India', '10.240086789711787', '76.27343580489355', 'images/accounts/hospital/Government_Hospital_Mala.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE IF NOT EXISTS `booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `hospital_id` int(11) NOT NULL,
  `doctor_id` int(11) NOT NULL,
  `time` varchar(255) NOT NULL,
  `session` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id`, `user_id`, `hospital_id`, `doctor_id`, `time`, `session`, `date`, `status`) VALUES
(1, 3, 1, 12, '10:15:00 - 10:45:00', 'Morning', '2015-05-04', 0);

-- --------------------------------------------------------

--
-- Table structure for table `clinic`
--

CREATE TABLE IF NOT EXISTS `clinic` (
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `clinic`
--

INSERT INTO `clinic` (`id`, `name`, `email`, `password`, `website`, `phone`, `location`, `latitude`, `longitude`, `logo`) VALUES
(1, 'Hari Clinic', 'hari@clinic,com', 'HARI', 'www.hariclinic.com', '9874563214', 'Haripad, Kerala 690514, India', '9.289875037444629', '76.4398552512207', 'images/accounts/hospital/5509185605c20Hari_Clinic.5509185605c1ajpg');

-- --------------------------------------------------------

--
-- Table structure for table `departments`
--

CREATE TABLE IF NOT EXISTS `departments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=40 ;

--
-- Dumping data for table `departments`
--

INSERT INTO `departments` (`id`, `name`) VALUES
(1, 'Accident and emergency'),
(2, 'Anaesthetics'),
(3, 'Cardiology'),
(4, 'Chaplaincy'),
(5, 'Critical care'),
(6, 'Diagnostic imaging'),
(7, 'Discharge lounge'),
(8, 'Ear nose and throat '),
(9, 'Elderly services'),
(10, 'Gastroenterology'),
(11, 'General surgery'),
(12, 'Gynaecology'),
(13, 'Haematology'),
(14, 'Maternity '),
(15, 'Microbiology'),
(16, 'Neonatal unit'),
(17, 'Nephrology'),
(18, 'Neurology'),
(19, 'Nutrition and dietetics'),
(20, 'Obstetrics and gynaecology'),
(21, 'Occupational therapy'),
(22, 'Oncology'),
(23, 'Ophthalmology'),
(24, 'Orthopaedics'),
(25, 'Pain management '),
(26, 'Physiotherapy'),
(27, 'Radiotherapy'),
(28, 'Renal unit'),
(29, 'Rheumatology'),
(30, 'Sexual health '),
(31, 'Urology'),
(32, 'Family Medicine'),
(33, 'Nuclear Medicine'),
(34, 'Paediatric surgeon'),
(35, 'Stress Management'),
(36, 'Leprologists'),
(37, 'Plastic surgeon'),
(38, 'General Practice'),
(39, 'Physician');

-- --------------------------------------------------------

--
-- Table structure for table `doctor`
--

CREATE TABLE IF NOT EXISTS `doctor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `hospital_id` int(11) NOT NULL,
  `qualification` varchar(255) NOT NULL,
  `department` varchar(255) NOT NULL,
  `am_from` time NOT NULL,
  `am_to` time NOT NULL,
  `pm_from` time NOT NULL,
  `pm_to` time NOT NULL,
  `photo` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `doctor`
--

INSERT INTO `doctor` (`id`, `name`, `hospital_id`, `qualification`, `department`, `am_from`, `am_to`, `pm_from`, `pm_to`, `photo`) VALUES
(12, 'Avinash', 1, 'MBBS', 'Sexual health ', '10:15:00', '12:00:00', '14:00:00', '16:30:00', 'images/accounts/doctor/15539d3d92b2feAvinash.jpg'),
(13, 'Harikrishna V.S', 1, 'MBBD,MD', 'Physician', '00:00:00', '00:00:00', '13:30:00', '15:45:00', 'images/accounts/doctor/15539e6f1ecde2Harikrishna V.S.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `hospital`
--

CREATE TABLE IF NOT EXISTS `hospital` (
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `hospital`
--

INSERT INTO `hospital` (`id`, `name`, `email`, `password`, `website`, `phone`, `location`, `latitude`, `longitude`, `logo`) VALUES
(1, 'Thomson Medical Centre ', 'thomsonmedicalcentre@gmail.com', 'thomson', 'http://www.tmcmala.com', '0480-2894477,9526008881,9497250455', 'Mala, Kerala, India', '10.241470742152128', '76.26270263038862', 'images/accounts/hospital/Thomson_Medical_Centre_.jpg'),
(3, 'MOTHER Hospital and Research Centre', 'motherhospitalthrissur@gmail.com', '12345', 'http://motherhospitalthrissur.org/', '0487-2434100', 'Thrissur Kanjani Vadanappally Road, Thrissur, Kerala 680003, India', '10.519163454315335', '76.18507812764733', 'images/accounts/hospital/MOTHER_Hospital_and_Research_Centre.jpg'),
(4, 'WESTFORT HOSPITAL ', 'hitechhospital@gmail.com', '12345', 'http://www.westforthospitalgroup.com/', '0487-2388999', 'State Highway 69, Puzhakkal, Punkunnam, Thrissur, Kerala 680002, India', '10.536449484212591', '76.19700903439343', 'images/accounts/hospital/WESTFORT_HOSPITAL_.jpg'),
(5, 'Sun Medical and Research Centre', 'hearthospital@trichurheart.com', '12345', 'http://sunmedicalcentre.com/', '91 - 487 - 2433101', 'Sakthan Thampuran Nagar, Anchery, Thrissur, Kerala, India', '10.512651970952094', '76.21448589063789', 'images/accounts/hospital/Sun_Medical_and_Research_Centre.jpg'),
(6, 'Elite Mission Hospital', 'info@elitemissionhospital.com', '12345', 'http://www.elitemissionhospital.com/contactus', ' 0487- 2436100,0487- 2426266 ,0487- 2423358, 0487 2441504', 'Koorkenchery, Thrissur, Kerala, India', '10.502169493307793', '76.21102743348229', 'images/accounts/hospital/Elite_Mission_Hospital.jpg'),
(7, 'Amala Cancer Hospital and Research Centre', 'amalacan@md3.vsnl.net.in', '12345', 'http://www.amalaims.org', '+919946322288 ', 'Amalanagar, Thrissur, Kerala, India', '10.561335534575358', '76.16729189912803', 'images/accounts/hospital/Amala_Cancer_Hospital_and_Research_Centre.jpg'),
(8, 'Jubilee Mission Medical College & Research Institute', 'jmmctcr@gmail.com', '12345', 'http://www.jubileemissionmedicalcollege.org/', '0091-487-2432200,0091-487-2461000,0091-487-2462000,0091-8593951000', 'Thrissur Mannamangalam Road, Mahatma Nagar, Nellikunnu, Thrissur, Kerala 680006, India', '10.520523984946859', '76.22742802446749', 'images/accounts/hospital/Jubilee_Mission_Medical_College_&_Research_Institute.jpg'),
(9, 'CRAFT Hospital & Research Centre,', 'drashraf@craftivf.com', '12345', 'http://craftivf.com/', '+91480-2800200, 2812345, 2808808', 'Kodungallur Eriyad Rd, Chandapura, Pettumma, Kodungallur, Kerala 680668, India', '10.234218916870889', '76.19349653802874', 'images/accounts/hospital/CRAFT_Hospital_&_Research_Centre,.png'),
(10, 'Irinjalakuda Co-operative Hospital', 'info@cooperativehospital.com', '12345', 'http://www.thrissurkerala.com/', '+91 480 2822779, 2831310', 'Kombara West Rd, Irinjalakuda, Kerala 680121, India', '10.33277331630174', '76.21677695690005', 'images/accounts/hospital/Irinjalakuda_Co-operative_Hospital.png'),
(11, 'St. James Hospital', 'info@stjameshospital.in', '12345', 'http://www.stjameshospital.in/', '0480-2710271, 2710571', 'St James Hospital Rd, Chalakudy, Kerala 680307, India', '10.314821945059725', '76.33511397857364', 'images/accounts/hospital/St._James_Hospital.png'),
(12, 'Dhanya Mission Hospital', 'dhanyamissionhospital@gmail.com', '12345', 'http://www.dhanyamissionhospital.com/', '0480-2713271, 0480-2713299', 'Old Highway, Potta, Kerala 680722, India', '10.330894602189053', '76.32429646627043', 'images/accounts/hospital/Dhanya_Mission_Hospital.png'),
(13, 'Government Hospital Mala', 'govthospmala@gmail.com', '12345', 'http://govtmala.com', '9961599207', 'Mala, Kerala, India', '10.240086789711787', '76.27343580489355', 'images/accounts/hospital/Government_Hospital_Mala.jpg'),
(14, 'Sree Narayana Guru Medical College', 'mail@domain.com', 'sndp', 'www.websitename.com', '9874563214,0457-8541115', 'Chalakka, Kunnukara, Kerala 683578, India', '10.156945529235658', '76.28188044072272', 'images/accounts/hospital/Sree_Narayana_Guru_Medical_College.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `medical_shop`
--

CREATE TABLE IF NOT EXISTS `medical_shop` (
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `medical_shop`
--

INSERT INTO `medical_shop` (`id`, `name`, `email`, `password`, `website`, `phone`, `location`, `latitude`, `longitude`, `logo`) VALUES
(1, 'Thomson Medical Centre ', 'thomsonmedicalcentre@gmail.com', 'thomson', 'http://www.tmcmala.com', '0480-2894477,9526008881,9497250455', 'Mala, Kerala, India', '10.241470742152128', '76.26270263038862', 'images/accounts/hospital/Thomson_Medical_Centre_.jpg'),
(3, 'MOTHER Hospital and Research Centre', 'motherhospitalthrissur@gmail.com', '12345', 'http://motherhospitalthrissur.org/', '0487-2434100', 'Thrissur Kanjani Vadanappally Road, Thrissur, Kerala 680003, India', '10.519163454315335', '76.18507812764733', 'images/accounts/hospital/MOTHER_Hospital_and_Research_Centre.jpg'),
(4, 'WESTFORT HOSPITAL ', 'hitechhospital@gmail.com', '12345', 'http://www.westforthospitalgroup.com/', '0487-2388999', 'State Highway 69, Puzhakkal, Punkunnam, Thrissur, Kerala 680002, India', '10.536449484212591', '76.19700903439343', 'images/accounts/hospital/WESTFORT_HOSPITAL_.jpg'),
(5, 'Sun Medical and Research Centre', 'hearthospital@trichurheart.com', '12345', 'http://sunmedicalcentre.com/', '91 - 487 - 2433101', 'Sakthan Thampuran Nagar, Anchery, Thrissur, Kerala, India', '10.512651970952094', '76.21448589063789', 'images/accounts/hospital/Sun_Medical_and_Research_Centre.jpg'),
(6, 'Elite Mission Hospital', 'info@elitemissionhospital.com', '12345', 'http://www.elitemissionhospital.com/contactus', ' 0487- 2436100,0487- 2426266 ,0487- 2423358, 0487 2441504', 'Koorkenchery, Thrissur, Kerala, India', '10.502169493307793', '76.21102743348229', 'images/accounts/hospital/Elite_Mission_Hospital.jpg'),
(7, 'Amala Cancer Hospital and Research Centre', 'amalacan@md3.vsnl.net.in', '12345', 'http://www.amalaims.org', '+919946322288 ', 'Amalanagar, Thrissur, Kerala, India', '10.561335534575358', '76.16729189912803', 'images/accounts/hospital/Amala_Cancer_Hospital_and_Research_Centre.jpg'),
(8, 'Jubilee Mission Medical College & Research Institute', 'jmmctcr@gmail.com', '12345', 'http://www.jubileemissionmedicalcollege.org/', '0091-487-2432200,0091-487-2461000,0091-487-2462000,0091-8593951000', 'Thrissur Mannamangalam Road, Mahatma Nagar, Nellikunnu, Thrissur, Kerala 680006, India', '10.520523984946859', '76.22742802446749', 'images/accounts/hospital/Jubilee_Mission_Medical_College_&_Research_Institute.jpg'),
(9, 'CRAFT Hospital & Research Centre,', 'drashraf@craftivf.com', '12345', 'http://craftivf.com/', '+91480-2800200, 2812345, 2808808', 'Kodungallur Eriyad Rd, Chandapura, Pettumma, Kodungallur, Kerala 680668, India', '10.234218916870889', '76.19349653802874', 'images/accounts/hospital/CRAFT_Hospital_&_Research_Centre,.png'),
(10, 'Irinjalakuda Co-operative Hospital', 'info@cooperativehospital.com', '12345', 'http://www.thrissurkerala.com/', '+91 480 2822779, 2831310', 'Kombara West Rd, Irinjalakuda, Kerala 680121, India', '10.33277331630174', '76.21677695690005', 'images/accounts/hospital/Irinjalakuda_Co-operative_Hospital.png'),
(11, 'St. James Hospital', 'info@stjameshospital.in', '12345', 'http://www.stjameshospital.in/', '0480-2710271, 2710571', 'St James Hospital Rd, Chalakudy, Kerala 680307, India', '10.314821945059725', '76.33511397857364', 'images/accounts/hospital/St._James_Hospital.png'),
(12, 'Dhanya Mission Hospital', 'dhanyamissionhospital@gmail.com', '12345', 'http://www.dhanyamissionhospital.com/', '0480-2713271, 0480-2713299', 'Old Highway, Potta, Kerala 680722, India', '10.330894602189053', '76.32429646627043', 'images/accounts/hospital/Dhanya_Mission_Hospital.png'),
(13, 'Government Hospital Mala', 'govthospmala@gmail.com', '12345', 'http://govtmala.com', '9961599207', 'Mala, Kerala, India', '10.240086789711787', '76.27343580489355', 'images/accounts/hospital/Government_Hospital_Mala.jpg'),
(14, 'Sree Narayana Guru Medical College', 'mail@domain.com', 'sndp', 'www.websitename.com', '9874563214,0457-8541115', 'Chalakka, Kunnukara, Kerala 683578, India', '10.156945529235658', '76.28188044072272', 'images/accounts/hospital/Sree_Narayana_Guru_Medical_College.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `email`, `password`, `gender`, `dob`, `address`, `phone`, `avatar`) VALUES
(3, 'prasanth', 'nvpdigital@gmail.com', 'sree', 'male', '0000-00-00', 'zgshshhs,hshshsbs,vzhsgsbs,6dgsgsg', '49794', 'images/accounts/user/nvpdigital.jpg'),
(4, 'vijaykumar', 'vjy410@gmail.com', 'vjy', 'male', '0000-00-00', ',,,vijaykumar', '', 'images/accounts/user/vjy410.jpg');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
