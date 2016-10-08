<?php
	include_once("model_database.php");	
	include_once("model_community.php");	
	$database = Database::Instance();
    $connection = $database->connection;
	
	
	
	
	//check login
	$avg = Community::day_based_average($connection,7);
	if($avg!=null){
		echo json_encode($avg);
	}else{
		echo "error";
	}
	
	
?>