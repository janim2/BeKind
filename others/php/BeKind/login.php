<?php
    if(isset($_POST["email"]) && isset($_POST["password"])){
        require_once 'config.php';
        
        $email = $_POST["email"];
        $password  = $_POST["password"];

        // encryting the password to Sha-512(denoted by $6$)
        // first parameter is the password to encrypt and the second one is the encryption key sort of
        $encrypted_password = crypt($password, '$6$rounds=1000$IamGoingToBeKind$');

        $loginMeIn = $con->prepare("SELECT COUNT(1) AS login_check,UserID,LOGGEDin FROM Users 
        WHERE Email= ? AND Password= ?");

        $loginMeIn->execute(array($email,$encrypted_password));

        $loggedIn = $loginMeIn->fetch();

        if($loggedIn["login_check"] == 1){
            //checking if user is already logged in
            if($loggedIn["LOGGEDin"] == 0){
                echo "login Successful";
                $checkForuserID = $con->prepare("SELECT COUNT(1) AS check_if_present 
                FROM Login_time 
                WHERE UserId = ?");
                $checkForuserID->execute($loggedIn["UserID"]);
                $fetchcheckUserID = $checkForuserID->fetch();

                //setting logged in to 1 to indicate that user has signed in
                $changeloginStatus = $con->prepare("UPDATE Users SET LOGGEDin = 1 WHERE Email = ? AND Password = ?");
                $changeloginStatus->execute($email,$encrypted_password);

                // checking to see if userID is already in the login_time table and then updating date and time if its is
                if($fetchcheckUserID["check_if_present"] == 1){
                    $update_login_info = $con->prepare("UPDATE Login_time 
                    SET LastLoginDate = curdate(),LastLoginTime  = TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s')
                    WHERE UserId = ?");
                    $update_login_info->execute($loggedIn["UserID"]);
                    
                }else{
                    // else  creating new columns to the user
                    $updateLoginDetails = $con->prepare("INSERT INTO Login_time(UserId,LastLoginDate,LastLoginTime)
                    VALUES(?,curdate(),TIME_FORMAT(CURRENT_TIME(),'%h:%i:%s'))");
                    $updateLoginDetails->execute(array($loggedIn["UserID"]));
                }
            }else{
                echo "Already Logged In From Another Device.";
            }
        }else{
            echo "Incorrect Credentials";
        }
    }
    else{
		die(require('lost.html'));
    }
?>
