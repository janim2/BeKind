<?php 

    function sendmail($theemail,$theorganizationID){

        //getting the organizations name using the ID.
        $getOrganizationName = $con->prepare("SELECT OrgName FROM Organizations WHERE OrgID = ?");
        $getOrganizationName->execute($theorganizationID);

        $theOrganizationName = $getOrganizationName->fetch();

        
        $to = $theemail;
        $subject = "Donation Successful";
        $txt = "Thank you for successfully donating to ".$theOrganizationName["OrgName"]." A smile has been 
        put on the face of a child due to your generous contribution. Periodic noifications would be sent to you
        concerning the whereabouts of your contributions until it is finally delivered at ".$theOrganizationName["OrgName"].
        " 24 hour support is here for all who need help. Enjoy";
        $headers = "From: beKind@ns01.000webhost.com" . "\r\n";
        mail($to,$subject,$txt,$headers);
        
    }
    
    if(isset($_POST[$userID])){
        require_once('config.php');

        $OrganizationID = $_POST[""];
        $userid = $_POST[""];
        $materialName = $_POST[""];
        $materialDescription = $_POST[""];
        $materialImage = $_POST[""];
        $materialQuantity = $_POST[""];
        $materialPrizeWorth = $_POST[""];

        $uploadMaterial = $con->prepare("INSERT INTO MaterialDonations(OrgID,UserID, MaterialName, MaterialDescription,
        MaterialImage, MaterialQuantity, MaterialWorth,Addate,Addtime) 
        VALUES(?,?,?,?,?,?,?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'))");

        $uploadingIt = $uploadMaterial->execute($OrganizationID,$userID,$materialName,$materialDescription,
        $materialImage,$MaterialQuantity,$materialPrizeWorth);

        if($uploadingIt){
            echo "Successfully Changed A Life";
        }else{
            echo "Donation Failed";
        }
    }else{
        die("<h1>Access Denied</h1>");

    }


?>