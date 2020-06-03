<?php
	if(isset($_POST["userID"])){
		require_once('config.php');

		$userID = $_POST["userID"];

		$moveToAnother = $con->prepare("INSERT INTO Deleted (UserID, ProfileImage,Firstname,Lastname,Phone_number,Gender,Email,Home_Address,Password,Addate,Addtime,DeletedDate,DeletedTime) SELECT UserID, ProfileImage,Firstname,Lastname,Phone_number,Gender,Email,Home_Address, Password,Addate,Addtime, curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s') FROM Users WHERE Users.UserID = ?");

		$deleteAccount = $con->prepare("DELETE FROM Users WHERE UserID = ?");
		$moved = $moveToAnother->execute(array($userID));

		if($moved){
			//echo "Move successful";
			$deletedAccount = $deleteAccount->execute(array($userID));
			if($deletedAccount){
				echo "Account Deleted";
			}else{
			echo "Something went wrong";
		}
		
    }	
	    
	}else{
		die(require('lost.html'));
	}
?>
