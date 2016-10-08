<?php
	include_once("model_database.php");	
	include_once("model_user.php");	
	$database = Database::Instance();
    $connection = $database->connection;
	
	
	
	//user login details
	$user_email =$_POST['username'];//"prabushitha@gmail.com";
	$user_pass = $_POST['password'];//"123";
	
	//check login
	$user = User::getUserLoggin($connection,$user_email,$user_pass);
	if($user!=null){
		$data = array();
		$data['id']=$user->id;
		$data['email']=$user->email;
		echo json_encode($data);
	}else{
		echo "error";
	}
	
	
?>