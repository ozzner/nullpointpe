<?php
/*********** Includes ***********/
include_once "../includes/session_active.php";
include_once "../models/acceso.php";

 $SessionAcc = unserialize($_SESSION["acceso"]);
?>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>cpanel rade</title>

    <link href="../css/css_cpanel.css" rel="stylesheet">
    <link href="../vendor/DataTable/css/jquery.dataTables.css" rel="stylesheet">



    <!-- Bootstrap Core CSS -->
    <link href="../vendor/simple-sidebar-1.0.0/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../vendor/simple-sidebar-1.0.0/css/simple-sidebar.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<div id="wrapper">

    <!-- Sidebar -->
    <div id="sidebar-wrapper">
        <ul class="sidebar-nav">
            <li class="sidebar-brand">
                <a href="#">
                   Cpanel rade
                </a>
            </li>
            <li>
                <a href="#">Valores generales</a>
            </li>
            <li>
                <a href="#">Gestión de usuarios</a>

            </li>
            <li>
                <a href="#">Establecimientos</a>
            </li>
            <li>

            </li>
            <li>
                <a href="#">About</a>
            </li>

        </ul>
    </div>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">

        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
<!--                    <h1>Bienvenid@ --><?php //echo ($SessionAcc->getName());?><!-- al panel de control</h1>-->
<!--                    <p>This template has a responsive menu toggling system. The menu will appear collapsed on smaller screens, and will appear non-collapsed on larger screens. When toggled using the button below, the menu will appear/disappear. On small screens, the page content will be pushed off canvas.</p>-->
<!--                    <p>Make sure to keep all page content within the <code>#page-content-wrapper</code>.</p>-->
                    <div id="MyHeader">
                        <a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Toggle Menu</a><label>Hello,<?php echo ($SessionAcc->getName());?> </label>
                    </div><br>
                    <table id="tb_locales" class="display" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Direccion</th>
                            <th>Ruc</th>
                            <th>Categoría</th>
                            <th>Distrito</th>
                            <th>Latitud</th>
                            <th>Longitud</th>
                            <th>Estado</th>
                        </tr>
                        </thead>

                        <tfoot>
                        <tr>
                            <th>Nombre</th>
                            <th>Direccion</th>
                            <th>Ruc</th>
                            <th>Categoría</th>
                            <th>Distrito</th>
                            <th>Latitud</th>
                            <th>Longitud</th>
                            <th>Estado</th>
                        </tr>
                        </tfoot>

                        <tbody>

                        </tbody>
                    </table>

                </div>

            </div>
        </div>
    </div>
    <!-- /#page-content-wrapper -->

</div>
<!-- /#wrapper -->

<!-- jQuery -->
<script src="../vendor/simple-sidebar-1.0.0/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="../vendor/simple-sidebar-1.0.0/js/bootstrap.min.js"></script>

<!-- Menu Toggle Script -->
<script>
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
</script>

<!-- Data table -->
<script src="../vendor/dataTable/js/jquery.dataTables.min.js"></script>


<script>
    $(document).ready(function() {
        $('#tb_locales').dataTable({
            "ajax": "../json/example.json"
        });
    } );
</script>

</body>

</html>