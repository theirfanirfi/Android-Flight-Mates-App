<?php

require_once('connection.php');
global $connection;
$response['fn'] = "";
$response['pnr'] = "";
$repsonse['to'] = "";
$repsonse['from'] = "";
$repsonse['takeoff'] = "";
$repsonse['isLanded'] = "";
$response['landingDate'] = "";
$response['flight_id'] = "";
$flightnumbers = array();
$pnrs = array();
$toarray = array();
$fromarray = array();
$takeoffarray = array();
$isLandedArray = array();
$landingdateArray = array();
$flightidsArray = array();


$repsonse['message'] = "working";



if($_SERVER['REQUEST_METHOD'] == "GET")
{
    $pid = $_GET['pid'];
    $SQL = "SELECT * FROM flights_tbl WHERE pid = '$pid'";
    $q = mysqli_query($connection, $SQL);
    while($row = mysqli_fetch_array($q)){
        $flightidsArray[] = $row['flight_id'];
$flightnumbers[] = $row['flight_number'];
$pnrs[] = $row['pnr'];
$toarray[] =$row['p_to'];

$fromarray[] = $row['p_from'];
$takeoffarray[] = $row['takeoff_date'];

if($row['flight_status'] == 1){
    $isLandedArray[] = $row['flight_status'];
$response['landingDate'] = $row['landing_date'];
}
else{
    $isLandedArray[] = $row['flight_status'];
    $response['landingDate'] = "Not Landed";
}
    }
    
$response['fn'] = $flightnumbers;
$response['pnr'] = $pnrs;
$repsonse['to'] = $toarray;
$repsonse['from'] = $fromarray;
$repsonse['takeoff'] = $takeoffarray;
$repsonse['isLanded'] = $isLandedArray;
$response['landingDate'] = $landingdateArray;
$response['flight_id'] = $flightidsArray;
    
    
}
 else {
    $response['message'] = "Invalid Request Method";
}
echo json_encode($response);