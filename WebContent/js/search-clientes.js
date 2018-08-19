var idResultCliente;

$(function() {

    $(".se-pre-con-page").fadeOut("slow");

	$('#dataCliente').hide();
    
});

function searchCliente(){
	$('#dataCliente').hide();
	$('input[name="clientResult.nome"]').val('');
	$('input[name="clientResult.cpfCnpj"]').val('');
	$('input[name="clientResult.endereco"]').val('');
	var params = {  
			"cliente.nome"       : $('input[name="cliente.nome"]').val() 
	};
    var l = $( '.ladda-button-cliente' ).ladda();
    l.ladda( 'start' );
    $.ajax({
        type: "POST",
        url: "./findAction?method=findClienteByNome",
        data: params,
        dataType:"json",
        contentType: "application/x-www-form-urlencoded;charset=ISO-8859-15",
        beforeSend : function(xhr) {
            xhr.setRequestHeader('Accept', "text/html; charset=ISO-8859-1");
        },
        success: function(msg){
//          alert(JSON.stringify(msg));
            l.ladda('stop');
           if (msg.idResult == 8) {
                swal({
                    title: "Atenção",
                    text: msg.result,
                    type: "warning"
                });
           } else{
        	   FillDataCliente(msg);
           }
        }
    });
}

function FillDataCliente(msg){
	
	idResultCliente = msg.id;
	
	$('input[name="clientResult.nome"]').val(msg.nome);
	
	$('input[name="clientResult.cpfCnpj"]').val(
            msg.cpfCnpj == 'undefined' ? "" : msg.cpfCnpj);
    digitsDocument = (msg.cpfCnpj).replace(/[^a-zA-Z 0-9]+/g,'');
    if (digitsDocument.length == 11) {
        $('input[name="clientResult.cpfCnpj"]').val($('input[name="clientResult.cpfCnpj"]').val().replace(/^(\d{3})(\d{3})(\d{3})(\d{2}).*/, '$1.$2.$3-$4'));
    }else{
        $('input[name="clientResult.cpfCnpj"]').val($('input[name="clientResult.cpfCnpj"]').val().replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2}).*/, '$1.$2.$3/$4-$5'));
    }
	
	$('input[name="clientResult.cpfCnpj"]').val(msg.cpfCnpj);
	$('input[name="clientResult.endereco"]').val(msg.endereco);
	$('#dataCliente').show();
	
	
}

function editClient() {
	window.location = "./register-cliente.html?mode=1&idClient="+idResultCliente;
}