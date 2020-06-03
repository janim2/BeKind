<?php
    if(isset($_POST["userId"])){
        require_once 'config.php';

        $userid = $_POST["userId"];
        $profileImage = $_POST["profileImage"];
        $firstname = $_POST["firstname"];
        $lastname = $_POST["lastname"];
        $telephone = $_POST["phone"];
        $email = $_POST["email"];
        $homeAddress = $_POST["homeAddress"];

        // encryting the password to Sha-512(denoted by $6$)
        // first parameter is the password to encrypt and the second one is the encryption key sort of
        $encrypted_password = crypt($password, '$6$rounds=1000$IamGoingToBeKind$');

        $profileImagename = "";

        if($profileImage != ""){
            $imageDecoder = base64_decode($profileImage);
            file_put_contents("pictures/profilepics/".$userid.".jpg",$imageDecoder);
            $profileImagename = $userid.".jpg";
        }

        $update = $con->prepare("UPDATE Users SET ProfileImage = ?, Firstname = ?, Lastname = ?, 
        Phone_number = ?, Email = ?,Home_Address = ? WHERE UserID = ?");
        $updating = $update->execute(array($profileImagename,$firstname,$lastname,$telephone,$email,
        $homeAddress,$userid));

        if($updating){
            echo "Update Complete";

		    //sending notifications
                $notifyId = $userid;
                $notifyImage = "UpS";
                $nofifySubject = "Update Successful";
                $notifyMessage = "Hi,".$firstname." You have successfully updated your profile details. Keep doing so regularly so that it would be easy for us to contact you when we need to. Keep on doing good and Have a nice day";
                $notifications = $con->prepare("INSERT INTO Notifications(NotifyID,NotifyImage,NotifySubject,NotifyMessage,
                Addate,Addtime) VALUES(?,?,?,?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'));");
                $sentnotifications = $notifications->execute(array($notifyId,$notifyImage,$nofifySubject,$notifyMessage));

                if($sentnotifications){
                    //sending mail
		            $to = $email;
                    $subject = "BeKind Profile Update Successful";
                    $txt = "Recent activity on your account confirms that you have successfully updated your profile detials. Feel free to contact us in times of any difficulty.";
                    $headers = "From: beKind.ns01.000webhost.com" . "\r\n";
                    mail($to,$subject,$txt,$headers);
                    //sending mail ends
                    
                    $checkifupdate = $con->prepare("SELECT COUNT(1) AS updatecheck FROM Updates WHERE UserID = ?");
                    $checkifupdate->execute(array($userid));
    
                    $gettherevalue = $checkifupdate->fetch();
                    if($gettherevalue["updatecheck"] == 0){
                        $addvalue = $con->prepare("INSERT INTO Updates(UserID, UpdateDate, UpdateTime) VALUES(?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'))");
                        $addvalue->execute(array($userid));
                    }else{
                        $updateit->prepare("UPDATE Updates SET UpdateDate = curdate(), UpdateTime = TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s') WHERE UserID = ?");
                        $updateit->execute(array($userid));
                    }
                }
                }else{
                    echo "Something Went Wrong.";

            //sending the failed notification
                $notifyId = $userid;
                            $notifyImage = "UpSF";
                            $nofifySubject = "Update Failed";
                            $notifyMessage = "Hi,".$firstname." Update of profile failed. Please try again later and of the problem persists do not hesitate to contact us for assistance.";
                            $notifications = $con->prepare("INSERT INTO Notifications(NotifyID,NotifyImage,NotifySubject,NotifyMessage,
                            Addate,Addtime) VALUES(?,?,?,?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'));");
                            $notifications->execute(array($notifyId,$notifyImage,$nofifySubject,$notifyMessage));
            //failed notification ends here
        }
    }else{
		die(require('lost.html'));
    }




?>
