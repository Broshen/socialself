<?php

// connect to the server
require 'passwords.php';

// Check connection
if ($conn->connect_error) {

    die("Connection failed: " . $conn->connect_error);

} 


//after connection, retrieve data
// array for JSON response
$response = array();
// check for post data
if (isset($_POST["id"])) {
 

    $id = $_POST['id'];
    
    $stmt = $conn->prepare("SELECT * FROM SocialSelf WHERE facebook_id = ?");

    $isbound = $stmt->bind_param("s", $id);


    $isexecute = $stmt->execute();
    $hasresult = $stmt->bind_result($id, $fb, $insta, $twitter, $linkd, $email, $phone);
    $hasfetched = $stmt->fetch();


   if ($hasfetched) {

            // success
            $response["success"] = 1;

            $response["id"] = $id;
            $response["fb_name"]= $fb;
            $response["insta_name"]= $insta;
            $response["twitter_name"]= $twitter;
            $response["linkedin_name"]= $linkd;
            $response["email"]= $email;
            $response["phone"]= $phone;

            // echoing JSON response
            echo json_encode($response);
    }
     else {
        // required field is missing
        $response["success"] = 0;
        $response["message"] = "No user found";
     
        // echoing JSON response
        echo json_encode($response);
    }
}

$conn->close();

?>