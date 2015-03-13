<?php

/*********** Includes ***********/
include_once "../dao/acceso.php";
include_once "../utils/json.php";
include_once "../models/acceso.php";
include_once "../utils/session.php";


    if(isset($_REQUEST["password"]) and isset($_REQUEST["user"])){

        $pass = $_REQUEST["password"];
        $user = $_REQUEST["user"];

        $oAccess = new AccesoDAO();
        $access = $oAccess->sendCredentials($pass,$user);

        $oJson = new JsonUtil();

        if($access != false){
            /*Json parse*/
            $json_access = $oJson->getJsonData($access,false);
            $jsonDecode = (array)json_decode($json_access);
            $accesoDecode =(array)$jsonDecode['data'][0];

            /*Store model acceso*/
            $oAccModel = new accesoModel();
            $oAccModel->setUser($user);
            $oAccModel->setName($accesoDecode["acc_name"]);

            /*Store my session acceso*/
            $oSession = new sessionUtil("acceso");
            $oSession->storeMySession(serialize($oAccModel));

        }else{
           $json_access = $oJson->getJson($oAccess->getMessage());
        }

        echo $json_access;

    }else{
//        echo "ok";
        return false;
    }

?>