<?
include('../db/connection.php');
session_start();
$username = htmlspecialchars($_POST["username"]);
$password = htmlspecialchars($_POST["password"]);

//firstconnect();
login($username,$password);

header('Location: ../index.php');

?>