var digitsDocument;

$(function() {

    $(".se-pre-con-page").fadeOut("slow");

    $('#cliente.cpfCnpj').bind('keydown',onlyNumbers);

    $('input[name="cliente.cpfCnpj"]').focusout(function(){
        var phone, element;
        element = $(this);
        element.unmask();
        digitsDocument = element.val().replace(/[^a-zA-Z 0-9]+/g,'');

        if (digitsDocument.length == 11) {
            element.mask("999.999.999-99?99999");
        }
        if (digitsDocument.length == 14) {
            element.mask("99.999.999/9999-99");
        }
    }).trigger('focusout');

    var parameters = location.search.substring(1).split("&");
    var temp = parameters[0].split("=");
    var mode = unescape(temp[1]);

    $("#btnSave").click(function(){
        var validatedDocument;
        if ($('input[name="cliente.cpfCnpj"]').val() == "") {
            validatedDocument = true;
        }else{
            if(validateDocument(digitsDocument.length, $('input[name="cliente.cpfCnpj"]').val()) == true){
                validatedDocument = true;
            }else{
                if(digitsDocument.length == 14){
                    typeDocument = "CNPJ";
                }else{
                    typeDocument = "CPF";
                }

                swal({
                    title: "Atenção",
                    text: typeDocument+" inválido.",
                    type: "warning",
                    showCancelButton: false,
                    confirmButtonText: "Ok",
                    closeOnConfirm: true
                });

                validatedDocument = false;
            }
        }

        if ($("form[name='form_cliente']").valid() &&
            validatedDocument == true) {
            if(mode == 1){
                updateDataClient();
            }else{
                saveClient();
            }
        } else {
            if ($('input[name="cliente.nome"]').val() == "") {
                $('input[name="cliente.nome"]').focus();
            } else if ($('input[name="cliente.endereco"]').val() == "") {
                $('input[name="cliente.endereco"]').focus();
            }
        }
        return false;
    });

    if (mode == 1) {
        // Edição de cliente
        temp = parameters[1].split("=");
        idClient = unescape(temp[1]);
        var params = {
            "idClient" : idClient
        };
        $.ajax({
                    type : "POST",
                    url : "./findAction?method=cliente",
                    data : params,
                    dataType : "json",
                    contentType : "application/x-www-form-urlencoded;charset=ISO-8859-15",
                    beforeSend : function(xhr) {
                        xhr.setRequestHeader('Accept', "text/html; charset=ISO-8859-1");
                    },
                    success : function(msg) {
                        idClientLogin = msg.idClientLogin;

                        $('input[name="cliente.nome"]').val(
                                msg.cliente.nome == 'undefined' ? "" : msg.cliente.nome);
                        $('input[name="cliente.endereco"]').val(
                                msg.cliente.endereco == 'undefined' ? "" : msg.cliente.endereco);

                        if(msg.cliente.cpfCnpj != undefined){
                            $('input[name="cliente.cpfCnpj"]').val(
                                    msg.cliente.cpfCnpj == 'undefined' ? "" : msg.cliente.cpfCnpj);
                            digitsDocument = (msg.cliente.cpfCnpj).replace(/[^a-zA-Z 0-9]+/g,'');
                            if (digitsDocument.length == 11) {
                                $('input[name="cliente.cpfCnpj"]').val($('input[name="cliente.cpfCnpj"]').val().replace(/^(\d{3})(\d{3})(\d{3})(\d{2}).*/, '$1.$2.$3-$4'));
                            }else{
                                $('input[name="cliente.cpfCnpj"]').val($('input[name="cliente.cpfCnpj"]').val().replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2}).*/, '$1.$2.$3/$4-$5'));
                            }
                        }
                    }
                });
    }
});

function saveClient(){
	alert("");
    var params = {  "cliente.nome"       : $('input[name="cliente.nome"]').val(),
                    "cliente.cpfCnpj"     : $('input[name="cliente.cpfCnpj"]').val().replace(/[^\d]+/g, ''),
                    "cliente.endereco"      : $('input[name="cliente.endereco"]').val()
    };
    var l = $( '.ladda-button-cliente' ).ladda();
    l.ladda( 'start' );
    $.ajax({
        type: "POST",
        url: "./registerAction?method=saveCliente",
        data: params,
        dataType:"json",
        contentType: "application/x-www-form-urlencoded;charset=ISO-8859-15",
        beforeSend : function(xhr) {
            xhr.setRequestHeader('Accept', "text/html; charset=ISO-8859-1");
        },
        success: function(msg){
//          alert(JSON.stringify(msg));
            l.ladda('stop');
            if (msg.idResult == 6) {
                swal({
                    title: "Bom Trabalho!",
                    text: msg.result,
                    type: "success",
                    showCancelButton: false,
                    confirmButtonText: "Ok",
                    closeOnConfirm: true
                }, function () {
                        clearAll();
                });
            } else if (msg.idResult == 7) {
                swal({
                    title: "Atenção",
                    text: msg.result,
                    type: "warning"
                });
            }
        }
    });
}

function updateDataClient()
{
    var params = {  
    		"cliente.idCliente"     : idCliente,
            "cliente.nome"          : $('input[name="cliente.nome"]').val(),
            "cliente.cpfCnpj"       : $('input[name="cliente.cpfCnpj"]').val().replace(/[^\d]+/g, ''),
            "cliente.endereco"      : $('input[name="cliente.endereco"]').val()
    };
    var l = $( '.ladda-button-cliente' ).ladda();
    l.ladda( 'start' );
    $.ajax({
        type: "POST",
        url:  "./registerAction?method=saveCliente",
        data: params,
        dataType:"json",
        contentType: "application/x-www-form-urlencoded;charset=ISO-8859-15",
        beforeSend : function(xhr) {
            xhr.setRequestHeader('Accept', "text/html; charset=ISO-8859-1");
        },
        success: function(msg){
//          alert(JSON.stringify(msg));
            l.ladda('stop');
            if (msg.idResult == 5) {
                swal({
                    title: "Bom Trabalho!",
                    text: msg.result,
                    type: "success",
                    showCancelButton: false,
                    confirmButtonText: "Ok",
                    closeOnConfirm: true
                }, function () {
                	clearAll();
                });
            } else if (msg.idResult == 7) {
                swal({
                    title: "Atenção",
                    text: msg.result,
                    type: "warning"
                });
            }
        }
    });
}

function clearAll() {
    $('input[name="cliente.nome"]').val("");
    $('input[name="cliente.cpfCnpj"]').val("");
    $('input[name="cliente.endereco"]').val("");
}

//Validando CNPJ/CPF
function validateDocument(fieldLenght, fieldDocument) {

    if (fieldLenght == 14) {

        cnpj = fieldDocument;

        cnpj = cnpj.replace(/[^\d]+/g, '');

        if (cnpj == '')
            return false;

        if (cnpj.length != 14)
            return false;

        // Elimina CNPJs invalidos conhecidos
        if (cnpj == "00000000000000" || cnpj == "11111111111111"
                || cnpj == "22222222222222" || cnpj == "33333333333333"
                || cnpj == "44444444444444" || cnpj == "55555555555555"
                || cnpj == "66666666666666" || cnpj == "77777777777777"
                || cnpj == "88888888888888" || cnpj == "99999999999999")
            return false;

        // Valida DVs
        tamanho = cnpj.length - 2
        numeros = cnpj.substring(0, tamanho);
        digitos = cnpj.substring(tamanho);
        soma = 0;
        pos = tamanho - 7;
        for (i = tamanho; i >= 1; i--) {
            soma += numeros.charAt(tamanho - i) * pos--;
            if (pos < 2)
                pos = 9;
        }
        resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
        if (resultado != digitos.charAt(0))
            return false;

        tamanho = tamanho + 1;
        numeros = cnpj.substring(0, tamanho);
        soma = 0;
        pos = tamanho - 7;
        for (i = tamanho; i >= 1; i--) {
            soma += numeros.charAt(tamanho - i) * pos--;
            if (pos < 2)
                pos = 9;
        }
        resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
        if (resultado != digitos.charAt(1))
            return false;
        return true;

    } else {

        cpf = fieldDocument;

        cpf = cpf.replace(/[^\d]+/g, '');

        if (cpf == '')
            return false;
        // Elimina CPFs invalidos conhecidos
        if (cpf.length != 11 || cpf == "00000000000" || cpf == "11111111111"
                || cpf == "22222222222" || cpf == "33333333333"
                || cpf == "44444444444" || cpf == "55555555555"
                || cpf == "66666666666" || cpf == "77777777777"
                || cpf == "88888888888" || cpf == "99999999999")
            return false;
        // Valida 1o digito
        add = 0;
        for (i = 0; i < 9; i++)
            add += parseInt(cpf.charAt(i)) * (10 - i);
        rev = 11 - (add % 11);
        if (rev == 10 || rev == 11)
            rev = 0;
        if (rev != parseInt(cpf.charAt(9)))
            return false;
        // Valida 2o digito
        add = 0;
        for (i = 0; i < 10; i++)
            add += parseInt(cpf.charAt(i)) * (11 - i);
        rev = 11 - (add % 11);
        if (rev == 10 || rev == 11)
            rev = 0;
        if (rev != parseInt(cpf.charAt(10)))
            return false;
        return true;
    }
}

//Validando teclado de números
function onlyNumbers(num) {
    var tecla=(window.event)?event.keyCode:e.which;
     if((tecla>47 && tecla<58)) return true;
     else{
     if (tecla==8 || tecla==0) return true;
     else  return false;
     }
}