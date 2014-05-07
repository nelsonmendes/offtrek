<?

	if(isset($_GET['coords'])){
	
		echo "<h1>THIS IS WHAT WAS RECORDED - GET</h1>";
		echo '<p>' . $_GET["coords"] . '</p>';
	}
	if(isset($_POST['coords'])){
	
		echo "<h1>THIS IS WHAT WAS RECORDED - POST</h1>";
		echo '<p>' . $_POST["coords"] . '</p>';
	}

?>