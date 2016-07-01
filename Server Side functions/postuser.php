<?php

// connect to the server
require 'passwords.php';

// Check connection
if ($conn->connect_error) {

    die("Connection failed: " . $conn->connect_error);

} 

// check for required fields
if (isset($_POST['id']) && isset($_POST['fb']) && isset($_POST['insta'])&& isset($_POST['twitter'])&& isset($_POST['linkedin'])&& isset($_POST['email'])&& isset($_POST['phone'])) {

    $id = $_POST['id'];  


    //check to see if user exists
    $stmt = $conn->prepare("SELECT * FROM SocialSelf WHERE facebook_id = ?");

    $isbound = $stmt->bind_param("s", $id);

    $isexecute = $stmt->execute();
    $hasresult = $stmt->bind_result($id, $fb, $insta, $twitter, $linkedin, $email, $phone);
    $hasfetched = $stmt->fetch();

    $fb = $_POST['fb'];
    $insta = $_POST['insta'];
    $twitter = $_POST['twitter'];
    $linkedin = $_POST['linkedin'];
    $email = $_POST['email'];
    $phone = $_POST['phone'];

    $query;
    $response;
    $stmt->store_result();

    //if user exists, update existing user info
    if($hasfetched){

        $query="UPDATE SocialSelf SET facebook_id=?, fb_name=?, insta_name=?, twitter_name=?, linkedin_name=?, email=?, phone=? WHERE facebook_id=?";

        $response["success"] = 1;
        $response["message"] = "User successfully updated";
        echo "1";
    }
    //otherwise, create a new user info
    else{
        $query = "INSERT INTO SocialSelf(facebook_id, fb_name, insta_name, twitter_name, linkedin_name, email, phone) VALUES (?,?,?,?,?,?,?)";

         // successfully inserted into database
        $response["success"] = 2;
        $response["message"] = "User successfully created";
    }


    // echo gettype($id);
    // echo gettype($fb);
    // echo gettype($insta);
    // echo gettype($twitter);
    // echo gettype($linkedin);
    // echo gettype($email);
    // echo gettype($phone);

    // echo $id;
    // echo $fb;
    // echo $insta;
    // echo $twitter;
    // echo $linkedin;
    // echo $email;
    // echo $phone;


    $stmt2 = $conn->prepare($query);

    if($hasfetched){
        $isbound = $stmt2->bind_param("ssssssss", $id, $fb, $insta, $twitter, $linkedin, $email, $phone, $id);
    }
    else{
        $isbound = $stmt2->bind_param('sssssss',$id, $fb, $insta, $twitter, $linkedin, $email, $phone);
    }

    $isexecute = $stmt2->execute();

    echo "4";
    //return the success of the query
    if ($isexecute) {

        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}

$conn->close();

?>