<?php 

    if(isset($_POST["userId"])){
        require_once('config.php');
        
        $userid = $_POST["userId"];
        $OrganizationID = $_POST["orgId"];
        $serviceID = $_POST["serviceId"];
        $service = $_POST["service"];

        $uploadMaterial = $con->prepare("INSERT INTO ServiceDonations(UserId, OrganizationalId, ServiceId ,Service,Addate,Addtime) 
        VALUES(?,?,?,?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'))");
        $uploadingIt = $uploadMaterial->execute(array($userid,$OrganizationID,$serviceID,$service));

        if($uploadingIt){
            echo "Offer has been received";
            
             //sending notifications
             $notifyId = $userid;
             $notifyImage = "Ss";
             $nofifySubject = "Service Donation Received";
             $notifyMessage = "Hi,".$firstname." You have offered to render your services. We really appreciate that. Due procedure would be followed to make this dream possible. Please stand by for further detials.";
             $notifications = $con->prepare("INSERT INTO Notifications(NotifyID,NotifyImage,NotifySubject,NotifyMessage,
             Addate,Addtime) VALUES(?,?,?,?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'));");
             $sentnotifications = $notifications->execute(array($notifyId,$notifyImage,$nofifySubject,$notifyMessage));

            if($sentnotifications){

                $getorganizationalDetials = $con->prepare("SELECT Orgname,orgImage FROM Organizational_Info WHERE OrgId = ?");
                $getorganizationalDetials->execute(array($OrganizationID));
                $getOrgValues = $getorganizationalDetials->fetch();

                $addtodonations = $con->prepare("INSERT INTO Donations(UserId, OrgId, MaterialId, OrgName, OrgImage, Addate, Addtime) VALUES(?,?,?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'))");
                $addtodonations->execute(array($userid,$OrganizationID,$serviceID,$getOrgValues["Orgname"],$getOrgValues["orgImage"]));

                $getEmail = $con->prepare("SELECT Email As emailhere FROM Users WHERE UserID = ?");
                $getEmail->execute(array($userid));
                $theemail = $getEmail->fetch();

                //getting the organizations name using the ID.
                    $getOrganizationName = $con->prepare("SELECT OrgName FROM Organizational_Info WHERE OrgID = ?");
                    $getOrganizationName->execute(array($OrganizationID));
            
                    $theOrganizationName = $getOrganizationName->fetch();
            
                    $to = $theemail["emailhere"];
                    $subject = "BeKind Service Donation Recieved";
                    $txt = "Thank you for successfully for offering to render your services to ".$theOrganizationName["OrgName"].". They need all the help they can get to help the children grow 
                    up to become suceesfull individuals in future.".$theOrganizationName["OrgName"].
                    " would contact you soon to find out how you can work together. Please standby for further details and instructions.";
                    $headers = "From: beKind.ns01.000webhost.com" . "\r\n";
                    mail($to,$subject,$txt,$headers);
            }

        }else{
            echo "Service Delivary Failed";
             //sending notifications
             $notifyId = $userid;
             $notifyImage = "Sf";
             $nofifySubject = "Donation Failed";
             $notifyMessage = "Hi,".$firstname." Sorry, but your process to volunteer failed.Please go through the donation process again. If the problem still persists do not hesitate to contact us. ";
             $notifications = $con->prepare("INSERT INTO Notifications(NotifyID,NotifyImage,NotifySubject,NotifyMessage,
             Addate,Addtime) VALUES(?,?,?,?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'));");
             $notifications->execute(array($notifyId,$notifyImage,$nofifySubject,$notifyMessage));
        }
    }else{
        die(require('lost.html'));

    }


?>