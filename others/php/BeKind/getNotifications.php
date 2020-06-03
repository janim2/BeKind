<?php
	if(isset($_POST["userID"])){
		require_once('config.php');


		$userID = $_POST["userID"];
		$getNotified  = $con->prepare("SELECT NotifyID, NotifyImage, NotifySubject, NotifyMessage, Addate, Addtime FROM Notifications Where NotifyID = ?"); 
		$getNotified->execute(array($userID));

		$notifications_array = array();

		while($getIt = $getNotified->fetch()){
			array_push($notifications_array, array(
			"NotifyID" => $getIt["NotifyID"],
			"NotifyImage" => $getIt["NotifyImage"],
			"NotifySubject" => $getIt["NotifySubject"],
			"NotifyMessage" => $getIt["NotifyMessage"],
			"Addate" => $getIt["Addate"],
			"Addtime" => $getIt["Addtime"]
		));
		}

		echo json_encode(array("Server_response" => $notifications_array));
	}else{
		die(require('lost.html'));
	}


?>


