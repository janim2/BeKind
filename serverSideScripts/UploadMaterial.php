<?php 

    if(isset($_POST["userId"])){
        require_once('config.php');
        
        $userid = $_POST["userId"];
        $materialID = $_POST["materialID"];
        $OrganizationID = $_POST["organizationID"];
        $materialImage = $_POST["materialImage"];
        $materialDescription = $_POST["material_description"];
        $materialPrizeWorth = $_POST["material_prize_worth"];

        $profileImagename = "";

        if($materialImage != ""){
            $imageDecoder = base64_decode($materialImage);
            file_put_contents("pictures/materialImage/".$materialID.".jpg",$imageDecoder);
            $materialImagename = $materialID.".jpg";
        }


        $uploadMaterial = $con->prepare("INSERT INTO MaterialDonations(UserId, MaterialId, OrgId, MaterialImage,MaterialDescription ,MaterialWorth,Addate,Addtime) 
        VALUES(?,?,?,?,?,?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'))");
        $uploadingIt = $uploadMaterial->execute(array($userid,$materialID,$OrganizationID,$materialImagename,$materialDescription,
        $materialPrizeWorth));

        if($uploadingIt){
            echo "Thanks. A child would be glad";
            
             //sending notifications
             $notifyId = $userid;
             $notifyImage = "DoS";
             $nofifySubject = "Donation Successful";
             $notifyMessage = "Hi,".$firstname." WoW. Did you hear the load shout of joy of a child. You have just helped this child to get food today. Your donation has been received by us and is being processed so that it goes to the right destination as soon as possible. All processes would be communicated to you. Keep it up. One point has been added to you. ";
             $notifications = $con->prepare("INSERT INTO Notifications(NotifyID,NotifyImage,NotifySubject,NotifyMessage,
             Addate,Addtime) VALUES(?,?,?,?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'));");
             $sentnotifications = $notifications->execute(array($notifyId,$notifyImage,$nofifySubject,$notifyMessage));

            if($sentnotifications){
                $addtodonations = $con->prepare("INSERT INTO Donations(UserId, OrgId, MaterialId, Addate, Addtime) VALUES(?,?,?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'))");
                $addtodonations->execute(array($userid,$OrganizationID,$materialID));

                $getEmail = $con->prepare("SELECT Email As emailhere FROM Users WHERE UserID = ?");
                $getEmail->execute(array($userid));
                $theemail = $getEmail->fetch();
                $sendtothis = $theemail["emailhere"];

                //getting the organizations name using the ID.
                    $getOrganizationName = $con->prepare("SELECT OrgName FROM Organizational_Info WHERE OrgID = ?");
                    $getOrganizationName->execute(array($OrganizationID));
            
                    $theOrganizationName = $getOrganizationName->fetch();
            
                    $to = $theemail;
                    $subject = "Donation Successful";
                    $txt = "Thank you for successfully donating to ".$theOrganizationName["OrgName"]." A smile has been 
                    put on the face of a child due to your generous contribution. Periodic noifications would be sent to you
                    concerning the whereabouts of your contributions until it is finally delivered at ".$theOrganizationName["OrgName"].
                    " 24 hour support is here for all who need help. Enjoy";
                    $headers = "From: beKind.ns01.000webhost.com" . "\r\n";
                    mail($to,$subject,$txt,$headers);
            }

        }else{
            echo "Donation Failed";
             //sending notifications
             $notifyId = $userid;
             $notifyImage = "DoF";
             $nofifySubject = "Donation Failed";
             $notifyMessage = "Hi,".$firstname." Sorry, but this donation was interupted somewhere. Please go through the donation process again. If the problem still persists do not hesitate to contact us. ";
             $notifications = $con->prepare("INSERT INTO Notifications(NotifyID,NotifyImage,NotifySubject,NotifyMessage,
             Addate,Addtime) VALUES(?,?,?,?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'));");
             $notifications->execute(array($notifyId,$notifyImage,$nofifySubject,$notifyMessage));
        }
    }else{
        die("<h1>Access Denied</h1>");

    }


?>
