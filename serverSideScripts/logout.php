<?php
	if(isset($_POST["userID"])){
		require_once('config.php');

		$userID = $_POST["userID"];

		$loggingOut = $con->prepare("UPDATE Users SET LOGGEDin = '0' WHERE UserID = ?");
		$wasloggedOut = $loggingOut->execute(array($userID));
		
		if($wasloggedOut){
			echo "You are out Now";
		}else{
			echo "Something went wrong";
		}
		
	}else{
		echo "<h1>Access Denied</h1>";
	}
?>

