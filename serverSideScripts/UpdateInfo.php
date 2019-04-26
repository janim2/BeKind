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
        Phone_number = ?, Gender = ?,
        Email = ?,Home_Address = ?,Password = ? WHERE UserID = ?");
        $updating = $update->execute($profileImagename,$firstname,$lastname,$telephone,$email,
        $homeAddress,$userid);

        if($updating){
            echo "Update Complete";
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
        }else{
            echo "Something Went Wrong.";
        }
    }else{
        die("<h1>Cannot Access This Page</h1>");
    }




?>
