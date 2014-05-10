<?php
@ob_start();
session_start();
?>
<html lang="en">
        <head>
            <head>
            <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
            <meta name="description" content=""/>
            <title>Dashboard</title>
            <!-- START Javascript -->
            <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js"></script>
            <script type="text/javascript" src="../js/bootstrap.min.js"></script>
            <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
            <script type="text/javascript" src="../js/googlemaps.js"></script>
            <!-- START CSS -->
                <link href='http://fonts.googleapis.com/css?family=Open+Sans:700,300' rel='stylesheet' type='text/css'>
                <link href="../css/bootstrap.min.css" rel="stylesheet">
                    <link href='../css/timeline.css' rel='stylesheet' type='text/css'>
                    <link href='../css/new.css' rel='stylesheet' type='text/css'>
                    <link href='../css/maps.css' rel='stylesheet' type='text/css'>
                    </head>
                        <body>
                            <div class="container">
                                <div class="row">
                                <?php
                                    include 'navbar.php'
                                ?>
                            </div>
                            
                                <div class="row">
                                    <div class="col-md-12">
                                    <h1>Paredes-Porto-Gaia</h1>
                                </div>
                            </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div id="dvMap">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <script>
                        $(document).ready(function(){
                        googlemaps();
                        });
                        </script>
                    </div>
                </body>