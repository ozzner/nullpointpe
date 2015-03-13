/**
 * Created by Renzo Santillán on 21/01/2015.
 */

$(document).ready(function(){

    MostrarMensaje("#head_login");
    OcultarMensaje("#head_login");

    $("#submit_login").click(function(e){

        $("#head_login").text("");
        e.preventDefault();
        var method = $("#form_login").attr('method');
        var url = $("#form_login").attr('action');
        //alert(url);
        var user = $("#usuario_login").val();
        var password = $("#clave_login").val();

        if(user != ''  && password != ''){

            $.ajax({
                url:url,
                type:method,
                data:{user:user,password:password},
                dataType: 'json',
                success:function(response){

                    if (!response["error_status"]) {
                        e.preventDefault();
                        $.each(response["data"], function (key, value) {

                            console.log(value.acc_user);
                            console.log(value.acc_name);
                        });
                        MostrarMensaje("#head_login");

                        setTimeout( "jQuery('#head_login').text('¡Credenciales válidas!');", 0 );
                        setTimeout( "jQuery('#head_login').css({'color':'#02a902'});", 0 );


                        setTimeout( "jQuery('#head_login').css({'color':'blue'});", 1000);
                        setTimeout( "jQuery('#head_login').text('Cargando cpanel...');", 1000 );

                        setTimeout( "initCpanel()", 2000 );


                    }else{
                        MostrarMensaje("#head_login");
                        $("#head_login").text(response["message"]);
                        $("#head_login").css({"color":"red"});
                        OcultarMensaje("#head_login");

                         console.log(response["message"]);
                    }
                    console.log(response);
                },
                error:function(jqXHR, textStatus, errorThrown){
                    MostrarMensaje("#head_login");
                    $("#head_login").text("Problemas con el servidor.");
                    $("#head_login").css({"color":"#FFA500"});
                    OcultarMensaje("#head_login");

                    console.log(textStatus);
                    console.log(errorThrown);
                    console.log(jqXHR);
                }
            });//End ajax

        }else{
            swal({
                title: "Campos en blanco.",
                text: "Debe completar los campos requeridos.",
                type: "warning",
                confirmButtonText: "Correcto",
                confirmButtonColor: "#FFA500    "
            });
        }

    });
});