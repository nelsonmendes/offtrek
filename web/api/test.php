<?php
	header('Content-type: application/json');

	$result = array();

	if(isset($_POST['coords'])){

		$result['coord'] = $_POST['coords'];
		echo json_encode($result);
	}else{
		$result['coord'] = 'FAILURE';
		echo json_encode($result);
	}
	


?>



