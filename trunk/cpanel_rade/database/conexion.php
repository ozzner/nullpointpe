<?php
/**
 * Created by PhpStorm.
 * User: Renzo Santillán
 * Date: 20/01/2015
 * Time: 04:36 PM
 */

class Mysql_Conexion {

    private $message;
    static $connection;

    function __construct()
    {
        require_once "../utils/constants.php";
    }


    /**
     * @return bool|mysqli
     */
    public function startConnection(){

        self::$connection = new mysqli(MySQL_DB_HOST,MySQL_DB_USER,MySQL_DB_PASS,MySQL_DB_NAME);
        $conn = self::$connection;

        if($conn->connect_errno){
            $this->message = CONNECTION_FAILED . mysqli_errno($conn);
            $conn->close();
            return false;
            exit;

        }else{
            $this->message = CONNECTION_SUCCESS;
            return $conn;
        }
    }


    public function setQuery($query){
        $output = array();
        $connection =  self::$connection;

        if (isset($query)) {
            $r = $connection->query($query);

            if($r){
                while ($row = $r->fetch_assoc()) {
                    $output[] = array_map("utf8_encode", $row);
                }

                if ($output == NULL) {
                    $this->message = DATA_NULL;
                    return null;

                } else {
                    return $output;
                }
            }else{
                $this->message = EXCECUTE_ERROR;
                return FALSE;
            }

        }else{
            $this->message = QUERY_ERROR;
            return FALSE;
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

?>