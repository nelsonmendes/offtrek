<html>
		<head>
		<?php
		session_start();
		?>
	</head>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/main.css" rel="stylesheet">
		<div class="panel panel-default">
			<div class="col-md-6">
				<?php echo $_SESSION['id']; ?>
			</div>
			<?php if(isset($_SESSION['id'])){
				?>
			<div class="col-md-6" >
				<a class="btn btn-lg btn-primary btn-block" href="pages/logout.php">Logout</a>
			</div>
			<?
			}
			else{
			?>
			<div class="col-md-6" >
				<a class="btn btn-lg btn-primary btn-block" href="pages/login.php">Login</a>
			</div>
			<?
			}
			?>
	</div>
	<script src="js/jquery-1.10.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/main.js"></script>
	<script src="js/TweenLite.min.js"></script>
</html>