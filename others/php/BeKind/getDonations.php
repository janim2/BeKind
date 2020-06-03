<?php

    if(isset($_POST["userID"])){
        require('config.php');

        $userid = $_POST["userID"];
        // $userid = "Bk156058Sa";

        $getDonations = $con->prepare("SELECT OrgId,MaterialId,OrgName,OrgImage,Addate,Addtime FROM Donations WHERE UserID = ?");
        $getDonations->execute(array($userid));

        $donationsArray = array();

        while($gettingIt = $getDonations->fetch()){
            array_push($donationsArray,array(
                            
            "OrgId" => $gettingIt["OrgId"],
            "MaterialId" => $gettingIt["MaterialId"],
            "OrgName" => $gettingIt["OrgName"],
            "OrgImage" => $gettingIt["OrgImage"],
            "Addate" => $gettingIt["Addate"],
            "Addtime" => $gettingIt["Addtime"],
        ));
        }
        echo json_encode(array("Server_response" => $donationsArray));
   
    }else{
        die(require('lost.html'));
    }


    // if(isset($_POST["userID"])){
    //     require('config.php');

    //     $userid = $_POST["userID"];

    //     $getdonations = $con->prepare("SELECT MaterialDonations.MaterialId AS usethisID,MaterialDonations.OrgId, 
    //     MaterialDonations.MaterialImage,MaterialDonations.MaterialDescription,MaterialDonations.MaterialWorth,MaterialDonations.Addate,
    //     MaterialDonations.Addtime,Organizational_Info.Orgname,Organizational_Info.orgImage,Organizational_Info.OrgPhone 
    //     FROM MaterialDonations,Donations,Organizational_Info 
    //     WHERE Donations.MaterialId = MaterialDonations.MaterialId and MaterialDonations.OrgId = Organizational_Info.Orgid 
    //     AND Donations.UserId = ?;");

    //     $getdonations->execute(array($userid));

    //     $checkifsuccess = $getdonations->fetch();

    //     if($checkifsuccess["usethisID"] == ""){
    //         $getfromservice = $con->prepare("SELECT ServiceDonations.OrganizationalId,ServiceDonations.ServiceId, ServiceDonations.Service,
    //         ServiceDonations.Addate,ServiceDonations.Addtime,Organizational_Info.Orgname,Organizational_Info.orgImage,
    //         Organizational_Info.OrgPhone FROM ServiceDonations,Donations,Organizational_Info 
    //         WHERE Donations.MaterialId = ServiceDonations.ServiceId AND Donations.UserId = ?");

    //         $getfromservice->execute(array($userid));


    //         $donationsArray = array();

    //         while($gettingIt = $getfromservice->fetch()){
    //             array_push($donationsArray,array(
 	 	 	 	 	 	 	 	
    //             "OrganizationalId" => $gettingIt["OrganizationalId"],
    //             "ServiceId" => $gettingIt["ServiceId"],
    //             "Service" => $gettingIt["Service"],
    //             "Addate" => $gettingIt["Addate"],
    //             "Addtime" => $gettingIt["Addtime"],
    //             "Orgname" => $gettingIt["Orgname"],
    //             "orgImage" => $gettingIt["orgImage"],
    //             "OrgPhone" => $gettingIt["OrgPhone"],
    //         ));
    //         }
    //         echo json_encode(array("Server_response" => $donationsArray));

    //     }else{
        
    //         $donationsArray = array();

    //         while(checkifsuccess){
    //             array_push($donationsArray,array(

    //             "MaterialId" => $gettingIt["MaterialId"],
    //             "OrgId" => $gettingIt["OrgId"],
    //             "MaterialImage" => $gettingIt["MaterialImage"],
    //             "MaterialDescription" => $gettingIt["MaterialDescription"],
    //             "MaterialWorth" => $gettingIt["MaterialWorth"],
    //             "Addate" => $gettingIt["Addate"],
    //             "Addtime" => $gettingIt["Addtime"],
    //             "Orgname" => $gettingIt["Orgname"],
    //             "orgImage" => $gettingIt["orgImage"],
    //             "OrgPhone" => $gettingIt["OrgPhone"],
    //         ));
    //         }

    //         echo json_encode(array("Server_response" => $donationsArray));
    //     }

    // }else{
    //     die(require('lost.html'));

    // }


?>