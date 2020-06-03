<?php 
	require_once('config.php');

	$getOrgs = $con->prepare("SELECT Orgid,Orgname,orgImage,OrgInfo,OrgEmail,OrgPhone,OrgAddress FROM Organizational_Info");
	$getOrgs->execute();

	$organizations_array = array();

	while($gettingIt = $getOrgs->fetch()){
		array_push($organizations_array,array(

		"Orgid" => $gettingIt["Orgid"],
		"Orgname" => $gettingIt["Orgname"],
		"orgImage" => $gettingIt["orgImage"],
		"OrgInfo" => $gettingIt["OrgInfo"],
		"OrgEmail" => $gettingIt["OrgEmail"],
		"OrgPhone" => $gettingIt["OrgPhone"],
		"OrgAddress" => $gettingIt["OrgAddress"]
	));
	}

	echo json_encode(array("Server_response" => $organizations_array));

?>
