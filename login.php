<?php

require_once('connection.php');
//global $connection;
if($_SERVER['REQUEST_METHOD'] == "POST")
{
	//$response['message'] = "i got it";
	$first_name = "";
        $pid = "";
        $email_found = "";
        $pass= "";
        $is_Found = false;

		
		$email = $_POST['email'];
		
		$password = $_POST['password'];
		
		$sql = "SELECT * FROM passengers_tbl WHERE p_email = '$email'";
             
                
		$query = mysqli_query($connection,$sql);
		if($query)
		{
                    
                    while (($row = mysqli_fetch_array($query)))
                    {
                        if($password == $row['p_password'])
                        {
                            $first_name = $row['p_first_name'];
                            $pid = $row['pid'];
                            $email_found = $row['p_email'];
                            $pass = $row['p_password'];
                            $is_Found = true;
                           
                            break;
                        }
                        else
                        {
                        $response['message'] = "Your password is not correct "; // comparing or matching user paassword
                        $response['error'] = true;
                        break;
                        }
                    }
                    // if $is_Found Variable contained true , so the user exists
                    if($is_Found){
			$response['message'] = "Authentication completed.";
                        $response['first_name'] = $first_name;                        
                        $response['pid'] = $pid;
                        $response['email'] = $email_found;
                        $response['pass'] = $pass;
                        $response['isFound'] = true;

			$response['error'] = false;
                    }
                  // the followine else block mean that the $isFound variable contain false and the false mean user doesn't exist
                    else
                    {
                        $response['message'] = "User doesn't exit";
                        $response['isFound'] = false;
                        $response['error'] = true;
                    }
		}
                //the following else block is of the query exeqution if block
		else
		{
			$response['message'] = "User credentials are incorrect ";
                        $response['error'] = true;
		}
	
	
}
else
{
	$response['message'] = "Invalid Method";
        $response['error'] = true;
}
echo json_encode($response);

 ?>
