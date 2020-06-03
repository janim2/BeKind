<?php
    if(isset($_POST["email"]) && isset($_POST["password"])){
        require_once 'config.php';

        $email = $_POST["email"];
        $password = $_POST["password"];

        // encryting the password to Sha-512(denoted by $6$)
        // first parameter is the password to encrypt and the second one is the encryption key sort of
        $encrypted_password = crypt($password, '$6$rounds=1000$IamGoingToBeKind$');

        $getuser = $con->prepare("SELECT UserID,ProfileImage,Firstname,Lastname,Phone_number,Gender,Email,Home_Address,Password 
        FROM Users WHERE Email = ? AND Password = ?;");

        $getuser->execute(array($email,$encrypted_password));

        $user_row = $getuser->fetch();

        $userid = $user_row["UserID"];
        $profileImage = $user_row["ProfileImage"];
        $firstname = $user_row["Firstname"];
        $lastname = $user_row["Lastname"];
        $telephone = $user_row["Phone_number"];
        $gender = $user_row["Gender"];
        $email = $user_row["Email"];
        $homeAddress = $user_row["Home_Address"];
        $password = $user_row["Password"];

        if($userid != null){
            $userarray = array();
		//values in square barackets are the ones that are used as keys in the android asyntask
            $userarray[UserID] = $userid;
            $userarray[ProfileImage] = $profileImage;
            $userarray[Firstname] = $firstname;
            $userarray[Lastname] = $lastname;
            $userarray[phone] = $telephone;
            $userarray[Gender] = $gender;
            $userarray[Email] = $email;
            $userarray[Home_Address] = $homeAddress;
            $userarray[Password] = $password;

            echo json_encode($userarray);

        }else{
            echo "Unable to Find User";
        }

    }else{
		die(require('lost.html'));
    }

?>
