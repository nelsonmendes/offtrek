<?php
session_start();
?>
<!DOCTYPE html>

<html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <title>Offtrek</title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">


        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/index.css" rel="stylesheet">
    </head>


    <body  >

<div id="wrap">

<header class="masthead">
    <div class="container">
    <div class="row">
      <div class="col-sm-6">
        <h1><a href="#" title="Bootstrap Template">Offtrek</a>
          <p class="lead"></p></h1>
      </div>
      <?php
      if ($_SESSION['id'] != 1 && isset($_SESSION['id'])){
      ?>
      <div class="col-sm-6">
        <div class="pull-right  hidden-xs">
          <a href="pages/login.php" class="dropdown-toggle"><h3><i class="glyphicon glyphicon-user"></i></h3></a>
        </div>
      </div>
      <?php
    }
    if ($_SESSION['id'] == 1){
      ?>
      <div class="col-sm-6">
        <div class="pull-right  hidden-xs">
          <a href="pages/main.php" class="dropdown-toggle"><h3><i class="glyphicon glyphicon-user"></i></h3></a>
        </div>
      </div>
      <?php
    }
      ?>
    </div>
    </div>
</header>


<!-- Fixed navbar -->
<div class="navbar navbar-custom navbar-inverse navbar-static-top" id="nav">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
      </div>
      <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav nav-justified">
          <li><a href="#">Home</a></li>
          <li><a href="#section2">Product</a></li>
          <li class="active"><a href="#section1"><strong>Offtrek</strong></a></li>
          <li><a href="#section4">About</a></li>
          <li><a href="#section5">Contact</a></li>
        </ul>
      </div><!--/.nav-collapse -->
    </div><!--/.container -->
</div><!--/.navbar -->

<!-- Begin page content -->
<div class="divider" id="section1"></div>

<div class="container">
  <div class="col-sm-10 col-sm-offset-1">
    <div class="page-header text-center">
      <h1>Just record your treks</h1>
    </div>

    <p class="lead text-center">
 With a simple interface, we allow the users to share their experiences offroad, just by recording their treks and upload them to our plataform.    </p>
  </div>
</div>

<div class="divider" id="section2"></div>

<section class="bg-1">
  <div class="col-sm-6 col-sm-offset-3 text-center"><h2 style="padding:20px;background-color:rgba(5,5,5,.8)">Point the obstacles</h2></div>
</section>

<div class="divider"></div>

<div class="container" id="section3">
  	<div class="col-sm-8 col-sm-offset-2 text-center">
      <h1>Native Mobile App + Website</h1>

      <p>
      With the mobile app, record your activities and them consult all your treks online, just by logging in our service.
      </p>

      <hr>

      <img src="img/bg_smartphones.jpg" class="img-responsive">

      <hr>
  	</div><!--/col-->
</div><!--/container-->

<div class="divider"></div>

<section class="bg-3" id="section4">
  <div class="col-sm-6 col-sm-offset-3 text-center"><h2 style="padding:20px;background-color:rgba(5,5,5,.8)">Share your experience offroad</h2></div>
</section>

<div class="continer bg-4">
	<div class="row">


      </div><!--/col-->
	</div><!--/row-->
</div><!--/container-->

<div class="divider" id="section5"></div>

<div class="row">

  <h1 class="text-center">Take it anywhere…</h1>

  <div id="map-canvas"></div>

  <hr>



  </div>
  <div class="col-sm-3 pull-right">

      <address>
         Rua Doutor Roberto Frias s/n, 4200-465 Porto
      </address>

      <address>
        <strong>Email Us</strong><br>
        <a href="mailto:#">contact@offtrek.com</a>
      </address>
  </div>

</div><!--/row-->

<div class="container">
  	<div class="col-sm-8 col-sm-offset-2 text-center">
      <ul class="list-inline center-block">
        <li><a href="http://facebook.com/"><img src="img/facebook.svg"></a></li>
        <li><a href="http://twitter.com/"><img src="img/twitter.svg"></a></li>
        <li><a href="http://google.com/"><img class="google_img" src="img/google.png"></a></li>
      </ul>

  	</div><!--/col-->
</div><!--/container-->

</div><!--/wrap-->

<div id="footer">
  <div class="container">
    <p class="text-muted">Offtrek</p>
  </div>
</div>

<ul class="nav pull-right scroll-top">
  <li><a href="#" title="Scroll to top"><i class="glyphicon glyphicon-chevron-up"></i></a></li>
</ul>


        <script type='text/javascript' src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>


        <script type='text/javascript' src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>

<script type='text/javascript' src="http://maps.googleapis.com/maps/api/js?sensor=false&extension=.js&output=embed"></script>




        <!-- JavaScript jQuery code from Bootply.com editor -->

          <script src="js/index.js"></script>

    </body>
</html>
