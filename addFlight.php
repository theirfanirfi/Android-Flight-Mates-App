<?php

require_once('connection.php');
global $connection;
if($_SERVER['REQUEST_METHOD'] == "POST")
{
	
	$response['isAdded'] = false;

		
	$fn = $_POST['fn'];
        $pnr = $_POST['pnr'];
        $from = $_POST['from'];
        $to = $_POST['to'];
        $pid = $_POST['pid'];
        $STATUS = 0;
        $TAKE_OFF_DATE = $_POST['takeoff_date'];
        //$response['message'] = "working";
        $SQL = "INSERT INTO flights_tbl(pid,flight_number,pnr,p_from,p_to,takeoff_date,flight_status) VALUES('$pid','$fn','$pnr','$from','$to','$TAKE_OFF_DATE','$STATUS')";
        $q = mysqli_query($connection, $SQL);
        if($q)
        {
            $response['isAdded'] = true;
        }
	
}
else
{
	$response['message'] = "Invalid Method";
}
echo json_encode($response);

 ?>