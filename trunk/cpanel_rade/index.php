<?php
/*********** Errors reporting ***********/
error_reporting(E_ALL ^ E_NOTICE);
error_reporting(E_ERROR | E_PARSE);


/*********** Includes ***********/
include_once "database/conexion.php";
include_once "utils/constants.php";
include_once "utils/session.php";

$sss = new sessionUtil("acceso");
$sss->destroySession();

if(isset($_REQUEST["message"])){
    $message = $_REQUEST["message"];
}else{
    $message = LOGIN_INFO;
}


?>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title><?php echo "Inicio de sesión";?></title>
    <link href="vendor/sweetalert-master/lib/sweet-alert.css" rel="stylesheet" type="text/css"/>
    <link href="css/css_mainstyles.css" rel="stylesheet">
    <link href="css/css_login.css" rel="stylesheet">

</head>
<body>

<header id="header_login">
    <div id="header_cont_tit"><h1>Cpanel rade - Login</h1></div>
</header>

<section id="main_login">

        <div id="div_login"><span id="head_login"><?php echo $message;?></span></div>
    <form id="form_login" method="post" action="controller/authentication.php">


        <div class="controles_form_login">
            <input autocomplete="off" id="usuario_login" class="MyInput" type="text" placeholder="Usuario" name="user"/>
            <input  autocomplete="off" id="clave_login" class="MyInput" type="password" placeholder="Contraseña" name="password"/><br>
            <br><br>
            <input id="submit_login" class="MyButton_green" type="submit" value="Iniciar"/>
            <div id="mensaje">
                <label id="lblMensaje"> </label>
            </div>
        </div>
        
    </form>
    <script src="js/jquery.js" type="application/javascript"></script>
    <script src="vendor/sweetalert-master/lib/sweet-alert.min.js" type="text/javascript"></script>
    <script src="js/login.js" type="application/javascript"></script>
    <script src="js/custom_functions.js" type="application/javascript"></script>

</section>

</body>
</html>