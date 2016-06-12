<?php


// connect to the server
$conn = new mysqli("localhost","boshencu_tester","mysqlpassword","boshencu_test1");
//$mysqlpassword= "fuckyousql1!"

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
    $result = mysqli_query($conn,"SELECT * FROM SocialSelf WHERE facebook_id = $id");

   if (!empty($result)) {
        // check for empty result
        if (mysqli_num_rows($result) > 0) {
 
            $result = mysqli_fetch_array($result);


            // success
            $response["success"] = 1;

            $response["id"] = $result["facebook_id"];
            $response["fb_name"]= $result["fb_name"];
            $response["insta_name"]= $result["insta_name"];
            $response["twitter_name"]= $result["twitter_name"];
            $response["linkedin_name"]= $result["linkedin_name"];
            $response["email"]= $result["email"];
            $response["phone"]= $result["phone"];

            // echoing JSON response
            echo json_encode($response);
        } 
        else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No user found";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "Empty result";
 
        // echo no users JSON
        echo json_encode($response);
    }
}
 else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}

$conn->close();

?>