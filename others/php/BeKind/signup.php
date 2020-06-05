<?php
    if(isset($_POST["userId"])){
        require_once 'config.php';

        $userid = $_POST["userId"];
        $profileImage = "nothing";
        $firstname = $_POST["firstname"];
        $lastname = $_POST["lastname"];
        $telephone = $_POST["phone"];
        $gender = "-";
        $email = $_POST["email"];
        $homeAddress = $_POST["homeAddress"];
        $password = $_POST["password"];

        $encrypt_password = crypt($password, '$6$rounds=1000$IamGoingToBeKind$');

        $checkifpresent = $con->prepare("SELECT COUNT(1) AS areyouThere FROM Users WHERE Email = ?");
        $checkifpresent->execute(array($email));

        $useForCheking = $checkifpresent->fetch();

        if($useForCheking["areyouThere"] == 0){
            $register = $con->prepare("INSERT INTO Users(UserID,ProfileImage,Firstname,Lastname,
            Phone_number,Gender,Email,Home_Address,Password,Addate,Addtime,LOGGEDin) 
            VALUES(?,?,?,?,?,?,?,?,?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'),0)");
            $registering = $register->execute(array($userid,$profileImage,$firstname,$lastname,
            $telephone,$gender,$email,$homeAddress,$encrypt_password));
    
            if($registering){
                echo "Registration Complete";
                $to = $email;
                $subject = "BeKind Registration Successful";
                $txt = "Welcome to the global BeKind society. Here we are passionate about the welfare of our fellow man.
                Children especially are at the center of our mission. Feel free to donate, help as many people as posible
                that will be made available on this platform. 24 hour support is here for all who need help. Enjoy";
                $headers = "From: beKind.ns01.000webhost.com" . "\r\n";

                $sentMail = mail($to,$subject,$txt,$headers);
                if($sentMail){
                    $notifyId = $userid;
                    $notifyImage = "Re";
                    $nofifySubject = "Welcome to BeKind";
                    $notifyMessage = "Hi,".$firstname." Thank you for joining this family that is oriented towards helping all individuals
                    with any need for help. Our services are one in a million. Free free to contact in times of worry.";
                    $notifications = $con->prepare("INSERT INTO Notifications(NotifyID,NotifyImage,NotifySubject,NotifyMessage,
                    Addate,Addtime) VALUES(?,?,?,?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'));");
                    $notifications->execute(array($notifyId,$notifyImage,$nofifySubject,$notifyMessage));
                }
            }else{
                echo "Registration Failed.Try Again Later";
            }
        }else{
            echo "User already exists.";
        }

        
    }else{
        die("<h1>Access Denied.</h1>");
    }

    function sendmail($theemail){
        $to = $email;
        $subject = "BeKind Registration Successful";
        $txt = "Welcome to the global BeKind society. Here we are passionate about the welfare of our fellow man.
        Children especially are at the center of our mission. Feel free to donate, help as many people as posible
        that will be made available on this platform. 24 hour support is here for all who need help. Enjoy";
        $headers = "From: beKind.ns01.000webhost.com" . "\r\n";
        mail($to,$subject,$txt,$headers);
    }

    function sendNotifications($UserID,$firstname){
        $notifyId = $UserID;
        $notifyImage = "Re";
        $nofifySubject = "Welcome to BeKind";
        $notifyMessage = "Hi,".$firstname." Thank you for joining this family that is oriented towards helping all individuals
        with any need for help. Our services are one in a million. Free free to contact in times of worry.";
        $notifications = $con->prepare("INSERT INTO Notifications(NotifyID,NotifyImage,NotifySubject,NotifyMessage,
        Addate,Addtime) VALUES(?,?,?,?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'));");
        $notifications->execute(array($notifyId,$notifyImage,$nofifySubject,$notifyMessage));
    }
?>
