<?php
/**
 * Created by PhpStorm.
 * User: Renzo Santillán
 * Date: 21/01/2015
 * Time: 09:27 AM
 */

class accesoModel {

    private $user;
    private $name;

    /**
     * @return mixed
     */
    public function getUser()
    {
        return $this->user;
    }

    /**
     * @param mixed $user
     */
    public function setUser($user)
    {
        $this->user = $user;
    }

    /**
     * @return mixed
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * @param mixed $name
     */
    public function setName($name)
    {
        $this->name = $name;
    }


}