<?php
/**
 * Created by PhpStorm.
 * User: Renzo Santillán
 * Date: 20/01/2015
 * Time: 05:22 PM
 */

class AccesoDAO {

private $message ;
static $oConexion;
static $oJson;

    function __construct()
    {
        /*********** Includes ***********/
        include_once "../utils/constants.php";
        include_once "../database/conexion.php";
        include_once "../utils/json.php";

        self::$oConexion = new Mysql_Conexion();
        self::$oJson = new JsonUtil();
    }

    public function sendCredentials($pass,$user){
        $query = "SELECT acc_user, acc_name "
                  ."FROM tb_acceso "
                  ."WHERE acc_user = '$user' AND acc_pass = '$pass';";


        $connection = self::$oConexion->startConnection();

            if($connection != false){
              $response =  self::$oConexion->setQuery($query);

                if($response == null){
                    $this->message = (LOGIN_FAILED);
                    return false;

                }else if($response != false){
                 $access = self::$oConexion->setQuery($query);
                 return $access;

                }else{
                     $this->message = self::$oConexion->getMessage();
                     return false;
                 }

            }else{
                $this->message = self::$oConexion->getMessage();
                return false;
        }
    }



    /**
     * @return mixed
     */
    public function getMessage()
    {
        return $this->message;
    }

    /**
     * @param mixed $message
     */
    public function setMessage($message)
    {
        $this->message = $message;
    }



}