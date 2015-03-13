/**
 * Created by Renzo Santillán on 21/01/2015.
 */

function OcultarMensaje(id) {
    $(id).fadeOut(4500);
}

function MostrarMensaje(id) {
    $(id).fadeIn(0);
}

function sleep(milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - start) > milliseconds){
            break;
        }
    }
}

function initCpanel() {
    window.location = "/cpanel_rade/views/cpanel.php";//for localhost
    //window.location = "/views/cpanel.php"; //for domain
}