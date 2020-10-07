<html>
    <head>
        <title>Doctor Plus</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/style.css" rel="stylesheet" type="text/css"/>
        <script src="js/jquery-1.11.1.min.js" type="text/javascript"></script>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-lg-offset-8 col-lg-4" style="margin-top: 50px;"><img src="images/logo.png"></div>
                <form action="actions.php?action=login" method="post" class="form-login">
                    <div class="col-lg-offset-8 col-lg-4 text-field " style="margin-top: 150px;">
                        <input type="text" name="email" placeholder="Username (Email id)" class="text-center"> 
                    </div>
                    <div class="col-lg-offset-8 col-lg-4 text-field" style="margin-top: 30px;">
                        <input type="password" name="password" placeholder="Password" class="text-center"> 
                    </div>
                    <div class="col-lg-offset-8 col-lg-4 buttons">
                        <button class="login_btn text-center " name="action" value="login" type="submit" name="login"><h4>Login</button>
                        <a href="signup.php"><button class="signup_btn text-center" type="button" name="sign_up"><h4>Sign Up</h4></button></a>
                    </div>
                </form>    
            </div>
        </div>
 </body>
</html>
