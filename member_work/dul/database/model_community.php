<?php 
	class Community{
		public static function day_based_average($connection,$duration){
			$time = strtotime(date('Y-m-d').' -0 years -0 months -'.$duration.' days');
			$day = date('Y-m-d',$time);
			$sql = "SELECT units,DAYNAME(usage_date) as day_name FROM individual_usage where usage_date > :date";
			$statement = $connection->prepare($sql);
            $statement->execute(array("date"=>$day));
          	$results = $statement->fetchAll();
          if(count($results)>0){
			  $userCount=0;
			  $sum=0;
			  $days_of_week=array('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday');
			  $count_users=array(0,0,0,0,0,0,0);
			  $count_units=array(0,0,0,0,0,0,0);
			  foreach($results as $result){
				  $dayName=$result['day_name'];
				  switch($dayName){
					  case $days_of_week[0]:$count_users[0]++;$count_units[0]+=(int)$result['units'];break;
					  case $days_of_week[1]:$count_users[1]++;$count_units[1]+=(int)$result['units'];break;
					  case $days_of_week[2]:$count_users[2]++;$count_units[2]+=(int)$result['units'];break;
					  case $days_of_week[3]:$count_users[3]++;$count_units[3]+=(int)$result['units'];break;
					  case $days_of_week[4]:$count_users[4]++;$count_units[4]+=(int)$result['units'];break;
					  case $days_of_week[5]:$count_users[5]++;$count_units[5]+=(int)$result['units'];break;
					  case $days_of_week[6]:$count_users[6]++;$count_units[6]+=(int)$result['units'];break;
				  }
				  

				 
			  }
			  
			  $avg=array(0.0,0.0,0.0,0.0,0.0,0.0,0.0);
			  for($x=0;$x<sizeof($avg);$x++){
				  if($count_users[$x]!=0){
					    $avg[$x]=$count_units[$x]/$count_users[$x];
			  
				  }
			  }
			  if($avg!=null){
				  return $avg;
			  }else{
				   return null; 
			  }
             
            
          }else{
              return null;
          }
		}
}
?>