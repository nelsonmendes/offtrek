<?php
session_start();
?>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
      <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
      <span class="sr-only">Toggle navigation</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Offtrek</a>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
          <li class="navelem">
          <a href="pages/main.php">Home</a>
        </li>
          <li class="navelem">
          <a href="pages/profile.php">Profile</a>
        </li>
          <li class="navelem">
          <a href="pages/explore.php">Explore</a>
        </li>
          <li class="navelem">
          <a href="pages/contacts.php">Contacts</a>
        </li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
          <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown"><?php echo $_SESSION['username']; ?><b class="caret"></b></a>
            <ul class="dropdown-menu">
            <!-- <li class="divider"></li> -->
            <li><a href="logout.php">Log-off</a></li>
          </ul>
        </li>
      </ul>
        <form class="navbar-form navbar-right" role="search">
          <div class="form-group">
          <input type="text" class="form-control" placeholder="Search">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>

      </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>
