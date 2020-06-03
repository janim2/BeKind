-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 09, 2019 at 11:17 PM
-- Server version: 10.3.14-MariaDB
-- PHP Version: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id2958837_bekind`
--
CREATE DATABASE IF NOT EXISTS `id2958837_bekind` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `id2958837_bekind`;

-- --------------------------------------------------------

--
-- Table structure for table `Deleted`
--

CREATE TABLE `Deleted` (
  `Id` int(11) NOT NULL,
  `UserID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `ProfileImage` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `Firstname` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `Lastname` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `Phone_number` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `Gender` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Home_Address` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(5000) COLLATE utf8_unicode_ci NOT NULL,
  `Addate` date NOT NULL,
  `Addtime` time NOT NULL,
  `DeletedDate` date NOT NULL,
  `DeletedTime` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `Deleted`
--

INSERT INTO `Deleted` (`Id`, `UserID`, `ProfileImage`, `Firstname`, `Lastname`, `Phone_number`, `Gender`, `Email`, `Home_Address`, `Password`, `Addate`, `Addtime`, `DeletedDate`, `DeletedTime`) VALUES
(2, 'Bk245096Sa', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3W8UNYzNPaE38vzgzX_aebexbn94j3tKC-0MbMjmE5G30TdE7', 'Jesse', 'Anim', '0268977129', '-', 'iamjesse75@gmail.com', 'KNUST, Kumasi, Ghana', '$6$rounds=1000$IamGoingToBeKind$McKD4bxTsnSIQs3LQFAEOdaMrqJ1jvhRdW/B6rYImGW6T6rv5G/yrPNx3/.JFPiHH1CbD/y4oar87WEeSUJ5x.', '2019-04-24', '10:02:14', '2019-04-26', '02:36:28'),
(3, 'Bk192521Sa', 'nothing', 'Jesse', 'Anim', '0268977129', '-', 'iamjesse75@gmail.com', 'Knust, Kumasi, Ghana', '$6$rounds=1000$IamGoingToBeKind$McKD4bxTsnSIQs3LQFAEOdaMrqJ1jvhRdW/B6rYImGW6T6rv5G/yrPNx3/.JFPiHH1CbD/y4oar87WEeSUJ5x.', '2019-04-26', '03:07:24', '2019-04-26', '03:15:58');

-- --------------------------------------------------------

--
-- Table structure for table `Donations`
--

CREATE TABLE `Donations` (
  `Id` int(11) NOT NULL,
  `UserId` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `OrgId` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `MaterialId` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `OrgName` text COLLATE utf8_unicode_ci NOT NULL,
  `OrgImage` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `Addate` date NOT NULL,
  `Addtime` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `Donations`
--

INSERT INTO `Donations` (`Id`, `UserId`, `OrgId`, `MaterialId`, `OrgName`, `OrgImage`, `Addate`, `Addtime`) VALUES
(8, '?', '?', '?', '?', '?', '2019-04-29', '01:23:10');

-- --------------------------------------------------------

--
-- Table structure for table `Login_time`
--

CREATE TABLE `Login_time` (
  `Id` int(11) NOT NULL,
  `UserId` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `LastLoginDate` date NOT NULL,
  `LastLoginTime` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `Login_time`
--

INSERT INTO `Login_time` (`Id`, `UserId`, `LastLoginDate`, `LastLoginTime`) VALUES
(8, 'Bk245096Sa', '2019-04-24', '10:02:33'),
(9, 'Bk245096Sa', '2019-04-25', '10:03:05'),
(10, 'Bk941641Sa', '2019-04-25', '04:39:29'),
(11, 'Bk941641Sa', '2019-04-25', '04:40:59'),
(12, 'Bk941641Sa', '2019-04-25', '04:42:17'),
(13, 'Bk149121Sa', '2019-04-25', '05:26:22'),
(14, 'Bk677957Sa', '2019-04-25', '05:57:11'),
(15, 'Bk309010Sa', '2019-04-25', '05:57:59'),
(16, 'Bk245096Sa', '2019-04-26', '07:47:40'),
(17, 'Bk309010Sa', '2019-04-26', '12:50:59'),
(18, 'Bk245096Sa', '2019-04-26', '02:07:08'),
(19, 'Bk192521Sa', '2019-04-26', '03:14:48'),
(20, 'Bk192521Sa', '2019-04-26', '03:15:19'),
(21, 'Bk156058Sa', '2019-04-26', '03:19:45'),
(22, 'Bk156058Sa', '2019-04-26', '03:21:35'),
(23, 'Bk156058Sa', '2019-04-26', '04:57:41'),
(24, 'Bk156058Sa', '2019-04-28', '12:12:59'),
(25, 'Bk309010Sa', '2019-04-29', '02:08:57'),
(26, 'Bk806167Sa', '2019-04-30', '06:12:21'),
(27, 'Bk806167Sa', '2019-04-30', '06:18:41'),
(28, 'Bk156058Sa', '2019-05-05', '11:25:40');

-- --------------------------------------------------------

--
-- Table structure for table `MaterialDonations`
--

CREATE TABLE `MaterialDonations` (
  `Id` int(11) NOT NULL,
  `UserId` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `MaterialId` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `OrgId` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `MaterialImage` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `MaterialDescription` varchar(2000) COLLATE utf8_unicode_ci NOT NULL,
  `MaterialWorth` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Addate` date NOT NULL,
  `Addtime` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `MaterialDonations`
--

INSERT INTO `MaterialDonations` (`Id`, `UserId`, `MaterialId`, `OrgId`, `MaterialImage`, `MaterialDescription`, `MaterialWorth`, `Addate`, `Addtime`) VALUES
(1, '?', '?', '?', '?', '?', '?', '2019-04-27', '07:26:34'),
(2, 'Bk156058Sa', 'BeKindMa193393', 'BeKind0001Org', 'BeKindMa193393.jpg', 'toyz', '500', '2019-04-27', '07:27:15'),
(3, 'Bk156058Sa', 'BeKindMa874615', 'BeKind0001Org', 'BeKindMa874615.jpg', 'toyz', '500', '2019-04-27', '07:29:52'),
(4, 'Bk156058Sa', 'BeKindMa1168264', 'BeKind0001Org', 'BeKindMa1168264.jpg', 'toyz', '500', '2019-04-27', '07:33:49'),
(5, 'Bk156058Sa', 'BeKindMa361902', 'BeKind0001Org', 'BeKindMa361902.jpg', 'toyz', '500', '2019-04-27', '07:36:47'),
(6, 'Bk156058Sa', 'BeKindMa1943751', 'BeKind0001Org', 'BeKindMa1943751.jpg', 'girlfriend', '180000000000', '2019-04-27', '07:49:22'),
(7, 'Bk156058Sa', 'BeKindMa1148172', 'BeKind0001Org', 'BeKindMa1148172.jpg', 'Girlfriend', '180000000000000', '2019-04-27', '07:57:52'),
(8, 'Bk156058Sa', 'BeKindMa916602', 'BeKind0002Org', 'BeKindMa916602.jpg', 'Android Phone - 1', '500.00', '2019-04-29', '12:26:18');

-- --------------------------------------------------------

--
-- Table structure for table `Notifications`
--

CREATE TABLE `Notifications` (
  `Id` int(11) NOT NULL,
  `NotifyID` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NotifyImage` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `NotifySubject` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `NotifyMessage` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `Addate` date NOT NULL,
  `Addtime` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `Notifications`
--

INSERT INTO `Notifications` (`Id`, `NotifyID`, `NotifyImage`, `NotifySubject`, `NotifyMessage`, `Addate`, `Addtime`) VALUES
(15, 'Bk245096Sa', 'Re', 'Welcome to BeKind', 'Hi,Jesse Thank you for joining this family that is oriented towards helping all individuals\n                with any need for help. Our services are one in a million. Free free to contact in times of worry.', '2019-04-24', '10:02:14'),
(16, 'Bk941641Sa', 'Re', 'Welcome to BeKind', 'Hi,Willis Thank you for joining this family that is oriented towards helping all individuals\n                with any need for help. Our services are one in a million. Free free to contact in times of worry.', '2019-04-25', '04:39:01'),
(17, 'Bk149121Sa', 'Re', 'Welcome to BeKind', 'Hi,Dominic Thank you for joining this family that is oriented towards helping all individuals\n                with any need for help. Our services are one in a million. Free free to contact in times of worry.', '2019-04-25', '05:25:30'),
(18, 'Bk677957Sa', 'Re', 'Welcome to BeKind', 'Hi,Nadia Thank you for joining this family that is oriented towards helping all individuals\n                with any need for help. Our services are one in a million. Free free to contact in times of worry.', '2019-04-25', '05:56:17'),
(19, 'Bk309010Sa', 'Re', 'Welcome to BeKind', 'Hi,Emmanuel Thank you for joining this family that is oriented towards helping all individuals\n                with any need for help. Our services are one in a million. Free free to contact in times of worry.', '2019-04-25', '05:57:16'),
(20, 'Bk192521Sa', 'Re', 'Welcome to BeKind', 'Hi,Jesse Thank you for joining this family that is oriented towards helping all individuals\n                    with any need for help. Our services are one in a million. Free free to contact in times of worry.', '2019-04-26', '03:07:24'),
(21, 'Bk156058Sa', 'Re', 'Welcome to BeKind', 'Hi,Jesse Thank you for joining this family that is oriented towards helping all individuals\n                    with any need for help. Our services are one in a million. Free free to contact in times of worry.', '2019-04-26', '03:17:17'),
(23, 'Bk156058Sa', 'UpSF', 'Update Failed', 'Hi,Jesse Update of profile failed. Please try again later and of the problem persists do not hesitate to contact us for assistance.', '2019-04-26', '04:27:27'),
(24, 'Bk156058Sa', 'UpS', 'Update Successful', 'Hi,Alberto You have successfully updated your profile details. Keep doing so regularly so that it would be easy for us to contact you when we need to. Keep on doing good and Have a nice day', '2019-04-26', '06:04:14'),
(30, 'Bk156058Sa', 'DoS', 'Donation Successful', 'Hi, WoW. Did you hear the load shout of joy of a child. You have just helped this child to get food today. Your donation has been received by us and is being processed so that it goes to the right destination as soon as possible. All processes would be communicated to you. Keep it up. One point has been added to you. ', '2019-04-27', '07:57:52'),
(31, 'Bk156058Sa', 'Ss', 'Service Donation Received', 'Hi, You have offered to render your services. We really appreciate that. Due procedure would be followed to make this dream possible. Please stand by for further detials.', '2019-04-27', '09:31:54'),
(33, 'Bk156058Sa', 'UpS', 'Update Successful', 'Hi,Jonathan You have successfully updated your profile details. Keep doing so regularly so that it would be easy for us to contact you when we need to. Keep on doing good and Have a nice day', '2019-04-28', '12:14:14'),
(35, 'Bk156058Sa', 'DoS', 'Donation Successful', 'Hi, WoW. Did you hear the load shout of joy of a child. You have just helped this child to get food today. Your donation has been received by us and is being processed so that it goes to the right destination as soon as possible. All processes would be communicated to you. Keep it up. One point has been added to you. ', '2019-04-29', '12:26:18'),
(37, 'Bk806167Sa', 'Re', 'Welcome to BeKind', 'Hi,Anthony Thank you for joining this family that is oriented towards helping all individuals\n                    with any need for help. Our services are one in a million. Free free to contact in times of worry.', '2019-04-30', '06:11:56'),
(38, 'Bk806167Sa', 'Ss', 'Service Donation Received', 'Hi, You have offered to render your services. We really appreciate that. Due procedure would be followed to make this dream possible. Please stand by for further detials.', '2019-05-01', '03:10:38');

-- --------------------------------------------------------

--
-- Table structure for table `Organizational_Info`
--

CREATE TABLE `Organizational_Info` (
  `Id` int(11) NOT NULL,
  `Orgid` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Orgname` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `orgImage` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `OrgInfo` varchar(10000) COLLATE utf8_unicode_ci NOT NULL,
  `OrgEmail` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `OrgPhone` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `OrgAddress` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `Addate` date NOT NULL,
  `Addtime` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `Organizational_Info`
--

INSERT INTO `Organizational_Info` (`Id`, `Orgid`, `Orgname`, `orgImage`, `OrgInfo`, `OrgEmail`, `OrgPhone`, `OrgAddress`, `Addate`, `Addtime`) VALUES
(1, 'BeKind0001Org', 'Agape Children\'s Home & Academy', 'BeKind0001Org.jpg', 'Our home first opened its doors in August of 2000. Since then they have expanded their\r\nservices to include a full-service orphanage, a Community At Risk program, a complete K-12\r\nacademic program, and a transition program for the older children. Today the academy provides\r\neducation for not only orphans in the home, but also many children from the local area.\r\nChildren are taken in who have lost both biological parents, and whose extended family\r\nmembers are incapable of supporting them. Unlike many other children’s homes in Ghana, our\r\nhome is based on a community care model of helping children at risk. Each child lives in a\r\nseparate family unit with foster parents and up to six other children. Contact with the greater\r\ncommunity is experienced on a daily basis, and interaction with relatives is encouraged. Our\r\nhome provides each child with three healthy meals a day, their own bed and clothes, access to\r\nmedical care, and education at the academy.\r\nThe Community at Risk program was born two years ago in response to the desperate poverty\r\nof the neighborhoods surrounding the home. It provides a full scholarship to school, along with a\r\nschool uniform, books, two nutritious meals a day, medical assistance, and family support to\r\nchildren whose families cannot afford to send them to the local Ghanaian schools.\r\nOur home believes in seeing the children s', ' admin@hopeforthenations.com ', '+1-250-712-2007', '222-1889 Springfield Rd\r\nKelowna, BC, Canada\r\nV1Y 5V5', '2019-04-23', '09:36:58'),
(2, 'BeKind0002Org', 'Egyam Children\'s Home Ghana', 'BeKind0002Org.jpg', 'The orphanage was registered on 18th of October 2005 with the registration number NO. G. 16,\r\n529 with the Registrar General\'s Department but was officially opened on the 29th of December\r\n2007.\r\nThe Department of Social Welfare in Ghana has registered the Egyam Orphanage with the\r\nregistration Number: DSW/4953 and license Number: ARH/WR/001/11.\r\nIt was officially openend by the Deputy Dutch Ambassador of The Netherlands in Ghana. It was\r\nfounded by a Dutch foundation, founder was Mrs. Thea van den Bosch together with the late\r\nFather Blay.\r\nThe home which started with 32 chidren is supporting 80 children. 47 Children in residence\r\nmade up of 33 boys and 14 girls. The rest of the children (33) are community children. 17 of\r\nthem are resettled children and we are also supporting 16 children in the communities which\r\nwe call half- orphans. They are supported in education and where possible, food. The ages of\r\nthe children ranges between 5 - 17 years.\r\nThe activities of the home are supervised by two committees in Ghana and Holland. Our future\r\ngoals are: vegetables garden,expand our library, look for support for our children education and\r\nbuild a dormitory or youth centre for resettled children who do not have family care.', 'egyamkids@yahoo.com / danielpayne467@gmail.com', '233 (024) 3931052 / \r\n028 955 2668', 'EGYAM CHILDREN\'S HOME\r\nP.O. BOX TD 1099\r\nTAKORADI\r\nGHANA-WEST AFRICA', '2019-04-27', '09:48:58'),
(3, 'BeKind0003Org', 'SOS Children’s Villages Ghana ', 'BeKind0003Org.jpg', 'Children who are orphaned and abandoned are amongst the world’s most vulnerable children. For over 43 years SOS Children’s Villages Ghana has been working to ensure these children have a family, a community and a promise of a brighter future. Together with our supporters, we work to prevent family breakdown and care for children who have lost parental care, or who risk losing it. We work with communities and partners to ensure that the rights of all children, in every society, are respected and fulfilled. ', 'sos.ghana@sosghana.org', '0372 095028 /\r\n026 5884495 / \r\n027 8848665 ', 'Near UDS Medical Schools\r\nKambonayili\r\nTamale', '2019-04-29', '02:35:58'),
(4, 'BeKind0004Org', 'Village Of Hope', 'BeKind0004Org.jpg', '\r\nAfter 18 years of existence, the Village of Hope is giving hope to over 1000 children in all the activities we are engaged in. There are:\r\n\r\n10 residential children’s homes where the physical, emotional, nutritional and spiritual needs of 168 children are being provided everyday.\r\n\r\nA hospital that serves the children and the people from over 14 communities.\r\n2 basic (elementary) schools that provide quality Christian education to over 1000 children.\r\nA (senior) high school educating students on the pillars of character, scholarship, service and leadership.\r\nA vocational training institute providing skills to former street children.\r\nA 50-acre farm where few crops are grown to supplement the food needs of the children’s homes.\r\n\r\nEach of these ministries is designed to take care of the needs of a particular group of people. God is being praised and glorified in all things.\r\n\r\nIt is the year 1989 and Jerry Reynolds, Emmanuel Asante and Christian Nsoah have purchased a 17-acre parcel of land at Ayawaso in the Greater Accra Region of Ghana. Their dream is to establish an orphanage. Three months after the land is purchased, the idea of the Village of Hope is already at the center of a court battle between three families claiming ownership of the land. Is the dream already about to be shattered?\r\n\r\nFast-forward to 1996 and, through God’s amazing providence, eight children and their house parents, Roland and Gladys Bulley, have a home at Ayawaso. However, by August 1998, the house accommodating the growing family is bursting at its seams and yet, a court injunction prevents further construction from happening. Again, God provides an answer with 25 acres of land in Fetteh in the Central Region. Two years and nine months later, three new homes are ready to house orphaned and destitute children.\r\n\r\nThis is how the Village of Hope story began and, right from the beginning, God’s love was never far away from his children.', 'thevillageofhope@gmail.com', '024 4313404 /\r\n020 8232585', 'Village of Hope\r\nGomoa Fetteh\r\nCentral Region', '2019-04-29', '03:27:28');

-- --------------------------------------------------------

--
-- Table structure for table `ServiceDonations`
--

CREATE TABLE `ServiceDonations` (
  `Id` int(11) NOT NULL,
  `UserId` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `OrganizationalId` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `ServiceId` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `Service` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Addate` date NOT NULL,
  `Addtime` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `ServiceDonations`
--

INSERT INTO `ServiceDonations` (`Id`, `UserId`, `OrganizationalId`, `ServiceId`, `Service`, `Addate`, `Addtime`) VALUES
(1, 'Bk156058Sa', 'BeKind0001Org', 'BeKindSe610898', 'Doctor', '2019-04-27', '09:31:54'),
(2, 'Bk156058Sa', 'BeKind0001Org', 'BeKindSe2132404', '', '2019-04-27', '09:39:01'),
(3, 'Bk156058Sa', 'BeKind0002Org', 'BeKindSe1255658', 'Counselling', '2019-04-29', '12:28:02'),
(4, 'Bk806167Sa', 'BeKind0003Org', 'BeKindSe1416152', 'Doctor', '2019-05-01', '03:10:38');

-- --------------------------------------------------------

--
-- Table structure for table `Updates`
--

CREATE TABLE `Updates` (
  `id` int(11) NOT NULL,
  `UserID` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `UpdateDate` date NOT NULL,
  `UpdateTime` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `Updates`
--

INSERT INTO `Updates` (`id`, `UserID`, `UpdateDate`, `UpdateTime`) VALUES
(1, 'Bk156058Sa', '2019-04-26', '06:04:14');

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE `Users` (
  `Id` int(11) NOT NULL,
  `UserID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `ProfileImage` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `Firstname` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `Lastname` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `Phone_number` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Gender` char(1) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Home_Address` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `Addate` date NOT NULL,
  `Addtime` time NOT NULL,
  `LOGGEDin` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`Id`, `UserID`, `ProfileImage`, `Firstname`, `Lastname`, `Phone_number`, `Gender`, `Email`, `Home_Address`, `Password`, `Addate`, `Addtime`, `LOGGEDin`) VALUES
(22, 'Bk941641Sa', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3W8UNYzNPaE38vzgzX_aebexbn94j3tKC-0MbMjmE5G30TdE7', 'Willis', 'IT', '1234567890', '-', 'willisit@gmail.com', 'tech', '$6$rounds=1000$IamGoingToBeKind$At1BAwAfy9sz1yGUC6i1O.4TvdJPbj3Ahy9BovgbeCoXeu13gGv/nhxLass8LMKDHGIIXELevmOeYpb5yDeaU/', '2019-04-25', '04:39:01', 0),
(23, 'Bk149121Sa', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3W8UNYzNPaE38vzgzX_aebexbn94j3tKC-0MbMjmE5G30TdE7', 'Dominic', 'Antwi', '0557716188', '-', 'wayneleo2017@gmail.com', 'kumasi', '$6$rounds=1000$IamGoingToBeKind$XTXbhQJ/CCrCHHT4wdUHwXLs2JY60XMd5Ya9XcwF9U6ejftlELreTFE8E1J8n7ugYTCGdmvPnHaRHi3u0Ilbj0', '2019-04-25', '05:25:30', 0),
(24, 'Bk677957Sa', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3W8UNYzNPaE38vzgzX_aebexbn94j3tKC-0MbMjmE5G30TdE7', 'Nadia', 'Opata', '0556408282', '-', 'lovelace.no@gmail.com', '', '$6$rounds=1000$IamGoingToBeKind$ILf6cX0kgUP/E5ptTXKAaqgJ6NmF53vjt1Atag1mSeRkCX/2gWLGPY7Y4sa/G/qsTiamI9UHd3ZcAwG/71.SB0', '2019-04-25', '05:56:17', 0),
(25, 'Bk309010Sa', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3W8UNYzNPaE38vzgzX_aebexbn94j3tKC-0MbMjmE5G30TdE7', 'Emmanuel', 'Manful', '0556188234', '-', 'emmanuelmanful49@gmail.com', '', '$6$rounds=1000$IamGoingToBeKind$SajmaFhWcJFNPSs0T5OTdAGRQN/dbMFDYy2prGiQloKyGmN5E2qcYaLlL9fRc6UL5kHfB0bJvk7F1gfoG8Dr41', '2019-04-25', '05:57:16', 0),
(27, 'Bk156058Sa', 'Bk156058Sa.jpg', 'Jonathan', 'Squirrel', '0268977129', '-', 'iamjesse75@gmail.com', 'KNUST, Gunter,, Netherlands.', '$6$rounds=1000$IamGoingToBeKind$McKD4bxTsnSIQs3LQFAEOdaMrqJ1jvhRdW/B6rYImGW6T6rv5G/yrPNx3/.JFPiHH1CbD/y4oar87WEeSUJ5x.', '2019-04-26', '03:17:17', 0),
(28, 'Bk806167Sa', 'nothing', 'Anthony', 'Teye-Adjei', '0246374564', '-', 'tonyteye@gmail.com', 'P.O. Box AH 8106', '$6$rounds=1000$IamGoingToBeKind$tS1YSBxgkTjtdBykU0n4jovUKjs6pq0JQBvV8uh25s.zfXFJAP0cDUj0zfl6XvN9vh/Sdm1HpZo4qpWD9yQIh/', '2019-04-30', '06:11:56', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Deleted`
--
ALTER TABLE `Deleted`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `Donations`
--
ALTER TABLE `Donations`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `Login_time`
--
ALTER TABLE `Login_time`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `MaterialDonations`
--
ALTER TABLE `MaterialDonations`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `Notifications`
--
ALTER TABLE `Notifications`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `Organizational_Info`
--
ALTER TABLE `Organizational_Info`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `ServiceDonations`
--
ALTER TABLE `ServiceDonations`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `Updates`
--
ALTER TABLE `Updates`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Deleted`
--
ALTER TABLE `Deleted`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `Donations`
--
ALTER TABLE `Donations`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `Login_time`
--
ALTER TABLE `Login_time`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `MaterialDonations`
--
ALTER TABLE `MaterialDonations`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `Notifications`
--
ALTER TABLE `Notifications`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `Organizational_Info`
--
ALTER TABLE `Organizational_Info`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `ServiceDonations`
--
ALTER TABLE `ServiceDonations`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `Updates`
--
ALTER TABLE `Updates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `Users`
--
ALTER TABLE `Users`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
