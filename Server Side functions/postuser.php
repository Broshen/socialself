<?php

// connect to the server
$conn = new mysqli("localhost","boshencu_tester","mysqlpassword","boshencu_test1");
//$mysqlpassword= "fuckyousql1!"

// Check connection
if ($conn->connect_error) {

    die("Connection failed: " . $conn->connect_error);

} 

// check for required fields
if (isset($_POST['id']) && isset($_POST['fb']) && isset($_POST['insta'])&& isset($_POST['twitter'])&& isset($_POST['linkedin'])&& isset($_POST['email'])&& isset($_POST['phone'])) {

    $id = $_POST['id'];
    $fb = $_POST['fb'];
    $insta = $_POST['insta'];
    $twitter = $_POST['twitter'];
    $linkedin = $_POST['linkedin'];
    $email = $_POST['email'];
    $phone = $_POST['phone'];
  
    
    //check to see if user exists
    $check =mysqli_query($conn, "SELECT * FROM SocialSelf WHERE facebook_id = $id");

    $result;
    $response;

    //if user exists, update existing user info
    if(mysqli_num_rows($check) > 0){
        $query="UPDATE SocialSelf SET facebook_id='$id',fb_name='$fb', insta_name='$insta', twitter_name='$twitter', linkedin_name='$linkedin', email='$email', phone='$phone' WHERE facebook_id = $id";
        $result = mysqli_query($conn, $query);
         // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "User successfully updated";
    }
    //otherwise, create a new user info
    else{
        $query = "INSERT INTO SocialSelf(facebook_id, fb_name, insta_name, twitter_name, linkedin_name, email, phone) VALUES ('$id', '$fb', '$insta', '$twitter', '$linkedin', '$email', '$phone')";
        $result = mysqli_query($conn, $query);
         // successfully inserted into database
        $response["success"] = 2;
        $response["message"] = "User successfully created";
    }

    //return the success of the query
    if ($result) {

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