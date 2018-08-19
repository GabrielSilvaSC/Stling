$(function() {
	$("#msgErro").hide();	
});

function login() 
{
	$("#msgErro").hide();
	if(!validEmail($('input[name="usuario.email"]').val())){
		$("#msgErro").show();
		$("#msgErro").html("<button aria-hidden='true' data-dismiss='alert' class='close' type='button'>×</button> Preencha o campo email corretamente.");
	}else if($('input[name="usuario.senha"]').val() == ""){
		$("#msgErro").show();
		$("#msgErro").html("<button aria-hidden='true' data-dismiss='alert' class='close' type='button'>×</button> Preencha o campo senha.");
	}else{
		var params = {  				
				"usuario.email"	: $('input[name="usuario.email"]').val(),
				"usuario.senha"	: $('input[name="usuario.senha"]').val()
		};
		var l = $( '.ladda-button-login' ).ladda();
		l.ladda( 'start' );
		$.ajax({
			type: "POST",
			url: "./Login",
			data: params,
			dataType:"json",
			contentType: "application/x-www-form-urlencoded;charset=ISO-8859-15",
			beforeSend : function(xhr) {
	            xhr.setRequestHeader('Accept', "text/html; charset=ISO-8859-1");
	        },
			success: function(msg){
				l.ladda('stop');
				if (msg.idResult == 2) {
					window.location = "./page-error404.html";
				}else{
					$("#msgErro").show();
					$("#msgErro").html("<button aria-hidden='true' data-dismiss='alert' class='close' type='button'>×</button>" + msg.result);
				}
			}
		});	
	}
}

function validEmail(email){
	if( email == ""  || 
		email.indexOf('@')== -1 ||
		email.indexOf('.')== -1 ){
			return false;
	}
	return true;
}
