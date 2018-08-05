<?php

require_once('connection.php');
//global $connection;
if($_SERVER['REQUEST_METHOD'] == "POST")
{
	
	

		
		$first_name = $_POST['first_name'];		
                $last_name = $_POST['last_name'];

		$email = $_POST['email'];		
                $mob = $_POST['mob'];
		$country = $_POST['country'];

		$password = $_POST['password'];
		
		$sql = "Insert into passengers_tbl(p_first_name,p_last_name,p_email,p_mob,p_country,p_password) VALUES('$first_name','$last_name','$email','$mob','$country','$password')";
		$query = mysqli_query($connection,$sql);
		if($query)
		{
			$response['message'] = "Registration Successfull";
			$response['error'] = false;
                        $response['redirect']= true;
		}
		else
		{
			$response['message'] = "Registration Unsuccessfull. Please Try agian.";
			$response['error'] = true;
                        $response['redirect']= false;
                        
		}
	
	
}
else
{
	$response['message'] = "Invalid Method";
}
echo json_encode($response);

 ?>