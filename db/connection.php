<?php
		session_start();

function firstconnect(){
		try {
		  # SQLite Database
		  $DBH = new PDO("sqlite:../assets/db.db");
		}
		catch(PDOException $e) {
		    echo $e->getMessage();
		}

		$STH = $DBH->query('SELECT username, password from tbl_user');
		 
		$STH->setFetchMode(PDO::FETCH_ASSOC);
		 
		while($row = $STH->fetch()) {
		    echo $row['username'] . "\n";
		    echo $row['password'] . "\n";
		}
		unset($DBH);
}
function login($username,$password){
		try {
		  # SQLite Database
		  $DBH = new PDO("sqlite:../assets/db.db");
		}
		catch(PDOException $e) {
		    echo $e->getMessage();
		}

		$STH = $DBH->query('SELECT username, password from tbl_user');
		 
		$STH->setFetchMode(PDO::FETCH_ASSOC);
		 
		while($row = $STH->fetch()) {
		    if($row['username'] == $username && $row['password']==$password){
		    	$_SESSION['id'] = 1;
		    	$_SESSION['username'] = $username;
		    }
		    else{
		    	$_SESSION['id'] = 2;
		    }
		}
		unset($DBH);
}
?>