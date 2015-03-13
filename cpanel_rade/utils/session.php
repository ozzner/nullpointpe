<?php
/**
 * Created by PhpStorm.
 * User: Renzo Santillán
 * Date: 21/01/2015
 * Time: 09:29 AM
 */

class sessionUtil {

    private $session_name;

    function __construct($session_name) {
        $this->session_name = $session_name;
        session_start();
    }


    public function storeMySession($object) {

        if (isset($object)) {
            $_SESSION[$this->session_name] = ($object);
        }
    }

    public function destroySession() {
        session_destroy();
        session_unset();
    }

    public function destroySpecificSession($session_name){
        unset($_SESSION[$session_name]);
    }

    public function isActiveSession() {

        if (isset($_SESSION[$this->session_name])) {
            return true;
        } else {
            return false;
        }
    }

    /* +++++++++++++++ SET AND GET +++++++++++++++ */

    public function getSession_name() {
        return $this->session_name;
    }

    public function setSession_name($session_name) {
        $this->session_name = $session_name;
    }


}