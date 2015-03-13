<?php
/*********** Includes ***********/
include_once "../utils/session.php";

$oSession = new sessionUtil("acceso");
$active = $oSession->isActiveSession();

    if(!$active){
        header("Location: ../index.php?message='Su session ha expirado.'");
        exit;
    }

?>