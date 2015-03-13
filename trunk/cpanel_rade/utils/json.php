<?php
class JsonUtil{


    /**
     * @param $array Input to parse JSON.
     * @param $status Error status false if no an error.
     * @return string Output JSON.
     */
    function getJsonData($array, $status) {
//        var_dump($array);
        $arry_json = array();
        $arry_json['error_status'] = $status;
        $arry_json['data'] = $array;

        return json_encode($arry_json, 256);
    }

    /**
     * @param $message Input to parse JSON.
     * @return string Output JSON.
     */
    function getJson($message) {

        $arry_json = array();
        $arry_json['error_status'] = true;
        $arry_json['message'] = $message;

        return json_encode($arry_json, 256);
    }
}

?>