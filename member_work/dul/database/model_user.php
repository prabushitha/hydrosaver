<?php
  class User{
     public $id;
	   public $email;
	 
	 


      public static function getUserLoggin($connection,$email,$password){
          $sql = "SELECT * FROM user WHERE email=:email AND password=:password";
          $statement = $connection->prepare($sql);
          $statement->execute(array("email"=>$email, "password"=>$password));
          $results = $statement->fetchAll();
          if(count($results)>0){
              $result = $results[0];
              $user = new User();
              $user->id = $result['id'];
              $user->email = $result['email'];
			  
              return $user;
          }else{
              return null;
          }
      }
 }
 
 ?>
 