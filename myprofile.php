<?php

require_once('connection.php');
global $connection;
if($_SERVER['REQUEST_METHOD'] == "POST")
{
   $arr['first_name'] = "";   
   $arr['last_name'] = "";
   $arr['p_email'] = "";
   $arr['p_mob'] = "";
   $arr['p_country'] = "";

 $PID = $_POST['pid'];
 $SQL = "SELECT * FROM passengers_tbl WHERE pid = '$PID' LIMIT 1";
 $query = mysqli_query($connection,$SQL);
 while($row = mysqli_fetch_array($query))
 {
   $arr['first_name'] = $row['p_first_name'];   
   $arr['last_name'] = $row['p_last_name'];
   $arr['p_email'] = $row['p_email'];
   $arr['p_mob'] = $row['p_mob'];
   $arr['p_country'] = $row['p_country'];
   break;
 }
 

}
echo json_encode($arr);


