<?php
    if(isset($_POST["userID"])){
        require_once 'config.php';

        $userid = $_POST["userID"];

        $signOut = $con->prepare("UPDATE Users SET LOGGEDin = 0 WHERE UserId = ?");

        $successfullyOut = $signOut->execute($userid);

        if($successfullyOut){
            echo "Logout Successful";
        }else{
            echo "Something Went Wrong";
        }
    }
?>