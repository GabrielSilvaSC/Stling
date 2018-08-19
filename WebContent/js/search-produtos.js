$(function() {

    $(".se-pre-con-page").fadeOut("slow");
    
    findTypeSearchProduto();
    
});

function searchProdutos(){
	var params;
	var url;
	if($('#typeSearchProduto').val() == "id"){
		url = "findProdutoById";
		params = {  "produto.id"       : $('input[name="produto.id"]').val() };
	}else{
		url = "findProdutoByDescricao";
		params = {  "produto.descricao"       : $('input[name="produto.descricao"]').val() };
	}
    var l = $( '.ladda-button-produto' ).ladda();
    l.ladda( 'start' );
    $.ajax({
        type: "POST",
        url: "./findAction?method="+url,
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
        	   FillDataProduto(msg);
           }
        }
    });
}

function FillDataProduto(msg){
	$('input[name="produtoResult.id"]').val(msg.id);
	$('input[name="produtoResult.descricao"]').val(msg.descricao);
	$('input[name="produtoResult.valor"]').val(msg.valor);
	$('#dataProduto').show();
}

//Search id or Description
function findTypeSearchProduto(){
	$('#dataProduto').hide();
	$('#productId').show();
	$('#productDescription').hide();
	$("#typeSearchProduto").empty();
	var dataSearchProduct = []; 
	dataSearchProduct.push({name:"Código", value: "id"});
	dataSearchProduct.push({name:"Descrição", value: "descricao"});
	var i = 0;
	while (i < dataSearchProduct.length) {
		$("#typeSearchProduto").append( "<option value='" + dataSearchProduct[i].value + "'>" + dataSearchProduct[i].name + "</option>");
		i++;
		
		$('#typeSearchProduto').on('change', function() {
			$('#dataProduto').hide();
			$('input[name="produto.id"]').val('');
			$('input[name="produto.descricao"]').val('');
			if(this.value == "id"){
				$('#productId').show();
				$('#productDescription').hide();
			}else{
				$('#productId').hide();
				$('#productDescription').show();
			}
		});
	}
}