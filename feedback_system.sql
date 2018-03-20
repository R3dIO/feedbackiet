-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 01, 2017 at 09:13 AM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `feedback_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `class_table`
--

CREATE TABLE `class_table` (
  `id` bigint(20) NOT NULL,
  `department_id` bigint(20) NOT NULL COMMENT 'IT , CS, Civil',
  `year` int(11) NOT NULL COMMENT 'First Year, Second Year, Third Year, Fourth Year',
  `section` char(5) DEFAULT NULL COMMENT 'A or B',
  `faculty_id` bigint(20) DEFAULT NULL COMMENT 'Class Teacher ID'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `class_table`
--

INSERT INTO `class_table` (`id`, `department_id`, `year`, `section`, `faculty_id`) VALUES
(1, 1, 1, 'A', 6),
(2, 2, 1, 'B', 1),
(3, 4, 2, 'A', 1),
(4, 1, 2, 'B', NULL),
(5, 1, 3, 'A', 6);

-- --------------------------------------------------------

--
-- Table structure for table `course_table`
--

CREATE TABLE `course_table` (
  `id` bigint(20) NOT NULL,
  `course_code` varchar(15) NOT NULL,
  `course_name` varchar(100) NOT NULL,
  `duration` int(11) NOT NULL COMMENT 'Duration of the course in years ... (BE=> 4 , BSC=> 3)'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `course_table`
--

INSERT INTO `course_table` (`id`, `course_code`, `course_name`, `duration`) VALUES
(1, 'ME', 'Master of Engineering', 1),
(2, 'BE', 'Bachelor of Engineering', 2),
(3, 'MSC', 'Master of Science', 3),
(4, 'MTECH', 'Master of Tech', 4),
(10, 'MBA', 'Master of bussiness', 3);

-- --------------------------------------------------------

--
-- Table structure for table `csf_table`
--

CREATE TABLE `csf_table` (
  `id` bigint(20) NOT NULL,
  `class_id` bigint(20) NOT NULL,
  `subject_id` bigint(20) NOT NULL,
  `faculty_id` bigint(20) NOT NULL,
  `session_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `csf_table`
--

INSERT INTO `csf_table` (`id`, `class_id`, `subject_id`, `faculty_id`, `session_id`) VALUES
(73, 2, 1, 1, 1),
(74, 2, 1, 5, 1),
(71, 2, 3, 1, 1),
(72, 2, 3, 6, 1),
(62, 5, 1, 1, 1),
(64, 5, 1, 5, 1),
(65, 5, 1, 6, 1),
(68, 5, 2, 1, 1),
(63, 5, 2, 6, 1),
(70, 5, 3, 1, 1),
(69, 5, 3, 5, 1),
(67, 5, 3, 6, 1);

-- --------------------------------------------------------

--
-- Table structure for table `department_table`
--

CREATE TABLE `department_table` (
  `id` bigint(20) NOT NULL,
  `faculty_id` bigint(20) DEFAULT NULL,
  `department_code` varchar(10) NOT NULL,
  `department_name` varchar(100) NOT NULL,
  `course_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `department_table`
--

INSERT INTO `department_table` (`id`, `faculty_id`, `department_code`, `department_name`, `course_id`) VALUES
(1, 1, 'IT', 'Information Technology', 1),
(2, 6, 'CS', 'Computer Science', 3),
(3, 1, 'ME', 'Mech', 3),
(4, 1, 'CV', 'Civil', 10);

-- --------------------------------------------------------

--
-- Table structure for table `faculty_table`
--

CREATE TABLE `faculty_table` (
  `id` bigint(20) NOT NULL,
  `title` varchar(15) DEFAULT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `designation` varchar(50) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `faculty_table`
--

INSERT INTO `faculty_table` (`id`, `title`, `first_name`, `last_name`, `designation`, `email`, `phone`, `department_id`) VALUES
(1, 'Dr', 'Sapan', 'Tanted', 'Assistant', 'sapantanted99@gmail.com', '9039833963', 1),
(5, 'Dr', 'SapanHello', 'Tanted', 'Hello', 'sapantanted90@gmail.com', '9039833963', 1),
(6, 'Mr', 'Naman', 'Jain', 'Hll', 'Saan', 'sflskj', 2);

-- --------------------------------------------------------

--
-- Table structure for table `feedback_table`
--

CREATE TABLE `feedback_table` (
  `id` bigint(20) NOT NULL,
  `csf_id` bigint(20) NOT NULL,
  `timestamp` int(11) NOT NULL,
  `ip_address` varchar(50) DEFAULT NULL,
  `attribute_1` int(11) NOT NULL,
  `attribute_2` int(11) NOT NULL,
  `attribute_3` int(11) NOT NULL,
  `attribute_4` int(11) NOT NULL,
  `attribute_5` int(11) NOT NULL,
  `attribute_6` int(11) NOT NULL,
  `attribute_7` int(11) NOT NULL,
  `attribute_8` int(11) NOT NULL,
  `attribute_9` int(11) NOT NULL,
  `attribute_10` int(11) NOT NULL,
  `attribute_11` int(11) NOT NULL,
  `attribute_12` int(11) NOT NULL,
  `attribute_13` int(11) NOT NULL,
  `attribute_14` int(11) NOT NULL,
  `attribute_15` int(11) NOT NULL,
  `comment_1` varchar(300) NOT NULL,
  `comment_2` varchar(300) NOT NULL,
  `comment_3` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `login_table`
--

CREATE TABLE `login_table` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `type` varchar(50) NOT NULL,
  `faculty_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login_table`
--

INSERT INTO `login_table` (`username`, `password`, `type`, `faculty_id`) VALUES
('sapan', 'sapan', 'admin', 1);

-- --------------------------------------------------------

--
-- Table structure for table `scheduled_feedback_table`
--

CREATE TABLE `scheduled_feedback_table` (
  `id` bigint(20) NOT NULL,
  `start_time` int(11) NOT NULL,
  `end_time` int(11) NOT NULL,
  `password` varchar(50) NOT NULL,
  `class_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `scheduled_feedback_table`
--

INSERT INTO `scheduled_feedback_table` (`id`, `start_time`, `end_time`, `password`, `class_id`) VALUES
(110, 1490953500, 1490955300, 'aa', 1);

-- --------------------------------------------------------

--
-- Table structure for table `scheduler_table`
--

CREATE TABLE `scheduler_table` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `scheduler_table`
--

INSERT INTO `scheduler_table` (`username`, `password`, `name`) VALUES
('sapan', 'sapan', 'Sapan');

-- --------------------------------------------------------

--
-- Table structure for table `schema_table`
--

CREATE TABLE `schema_table` (
  `class_id` bigint(20) NOT NULL,
  `subject_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `schema_table`
--

INSERT INTO `schema_table` (`class_id`, `subject_id`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(1, 2),
(3, 2),
(4, 2),
(5, 2),
(1, 3),
(2, 3),
(3, 3),
(4, 3),
(5, 3);

-- --------------------------------------------------------

--
-- Table structure for table `session_table`
--

CREATE TABLE `session_table` (
  `id` bigint(20) NOT NULL,
  `from_year` int(11) NOT NULL,
  `to_year` int(11) NOT NULL,
  `sem_type` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `session_table`
--

INSERT INTO `session_table` (`id`, `from_year`, `to_year`, `sem_type`) VALUES
(1, 2016, 2017, 'E'),
(2, 2016, 2017, 'O');

-- --------------------------------------------------------

--
-- Table structure for table `subject_table`
--

CREATE TABLE `subject_table` (
  `id` bigint(20) NOT NULL,
  `subject_code` varchar(20) NOT NULL,
  `subject_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `subject_table`
--

INSERT INTO `subject_table` (`id`, `subject_code`, `subject_name`) VALUES
(1, '3IT545', 'MATHS'),
(2, '4IT454', 'Physics'),
(3, '3IIT1231', 'Subject Something');

-- --------------------------------------------------------

--
-- Table structure for table `variable_table`
--

CREATE TABLE `variable_table` (
  `name` varchar(50) NOT NULL,
  `value` varchar(300) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `variable_table`
--

INSERT INTO `variable_table` (`name`, `value`) VALUES
('feedback_timeslot', '30'),
('current_session_id', '1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `class_table`
--
ALTER TABLE `class_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `department_id` (`department_id`,`year`,`section`),
  ADD KEY `department_id_col_2` (`department_id`),
  ADD KEY `faculty_id_col_2` (`faculty_id`);

--
-- Indexes for table `course_table`
--
ALTER TABLE `course_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `degree_code_col` (`course_code`);

--
-- Indexes for table `csf_table`
--
ALTER TABLE `csf_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `class_id` (`class_id`,`subject_id`,`faculty_id`,`session_id`),
  ADD KEY `class_id_col_2` (`class_id`),
  ADD KEY `subjecy_id_col_2` (`subject_id`),
  ADD KEY `faculty_id_col_2` (`faculty_id`),
  ADD KEY `session_id_col_2` (`session_id`);

--
-- Indexes for table `department_table`
--
ALTER TABLE `department_table`
  ADD PRIMARY KEY (`id`),
  ADD KEY `faculty_id_col_2` (`faculty_id`),
  ADD KEY `department_couse_id_idx` (`course_id`);

--
-- Indexes for table `faculty_table`
--
ALTER TABLE `faculty_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email_col` (`email`),
  ADD KEY `id_col` (`id`),
  ADD KEY `facult_department_id_idx` (`department_id`);

--
-- Indexes for table `feedback_table`
--
ALTER TABLE `feedback_table`
  ADD PRIMARY KEY (`id`),
  ADD KEY `feedback_csf_id_idx` (`csf_id`);

--
-- Indexes for table `login_table`
--
ALTER TABLE `login_table`
  ADD PRIMARY KEY (`username`,`faculty_id`),
  ADD UNIQUE KEY `faculty_id_UNIQUE` (`faculty_id`),
  ADD KEY `login_faculty_id_idx` (`faculty_id`);

--
-- Indexes for table `scheduled_feedback_table`
--
ALTER TABLE `scheduled_feedback_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `class_id_UNIQUE` (`class_id`);

--
-- Indexes for table `scheduler_table`
--
ALTER TABLE `scheduler_table`
  ADD PRIMARY KEY (`username`,`name`);

--
-- Indexes for table `schema_table`
--
ALTER TABLE `schema_table`
  ADD PRIMARY KEY (`class_id`,`subject_id`),
  ADD KEY `schema_subject_id_idx` (`subject_id`);

--
-- Indexes for table `session_table`
--
ALTER TABLE `session_table`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `subject_table`
--
ALTER TABLE `subject_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `subject_code_UNIQUE` (`subject_code`);

--
-- Indexes for table `variable_table`
--
ALTER TABLE `variable_table`
  ADD PRIMARY KEY (`name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `class_table`
--
ALTER TABLE `class_table`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `course_table`
--
ALTER TABLE `course_table`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `csf_table`
--
ALTER TABLE `csf_table`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=75;
--
-- AUTO_INCREMENT for table `department_table`
--
ALTER TABLE `department_table`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `faculty_table`
--
ALTER TABLE `faculty_table`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `feedback_table`
--
ALTER TABLE `feedback_table`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `scheduled_feedback_table`
--
ALTER TABLE `scheduled_feedback_table`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;
--
-- AUTO_INCREMENT for table `session_table`
--
ALTER TABLE `session_table`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `subject_table`
--
ALTER TABLE `subject_table`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `class_table`
--
ALTER TABLE `class_table`
  ADD CONSTRAINT `class_department_id` FOREIGN KEY (`department_id`) REFERENCES `department_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `class_faculty_id` FOREIGN KEY (`faculty_id`) REFERENCES `faculty_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `csf_table`
--
ALTER TABLE `csf_table`
  ADD CONSTRAINT `csf_class_id` FOREIGN KEY (`class_id`) REFERENCES `class_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `csf_faculty_id` FOREIGN KEY (`faculty_id`) REFERENCES `faculty_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `csf_session_id` FOREIGN KEY (`session_id`) REFERENCES `session_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `csf_subject_id` FOREIGN KEY (`subject_id`) REFERENCES `subject_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `department_table`
--
ALTER TABLE `department_table`
  ADD CONSTRAINT `department_couse_id` FOREIGN KEY (`course_id`) REFERENCES `course_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `department_faculty_id` FOREIGN KEY (`faculty_id`) REFERENCES `faculty_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `faculty_table`
--
ALTER TABLE `faculty_table`
  ADD CONSTRAINT `facult_department_id` FOREIGN KEY (`department_id`) REFERENCES `department_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `feedback_table`
--
ALTER TABLE `feedback_table`
  ADD CONSTRAINT `feedback_csf_id` FOREIGN KEY (`csf_id`) REFERENCES `csf_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `login_table`
--
ALTER TABLE `login_table`
  ADD CONSTRAINT `login_faculty_id` FOREIGN KEY (`faculty_id`) REFERENCES `faculty_table` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `scheduled_feedback_table`
--
ALTER TABLE `scheduled_feedback_table`
  ADD CONSTRAINT `schedule_feedback_class_id` FOREIGN KEY (`class_id`) REFERENCES `class_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `schema_table`
--
ALTER TABLE `schema_table`
  ADD CONSTRAINT `schema_class_id` FOREIGN KEY (`class_id`) REFERENCES `class_table` (`id`),
  ADD CONSTRAINT `schema_subject_id` FOREIGN KEY (`subject_id`) REFERENCES `subject_table` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
