/*
 * APORTES
 */
$(function(){
	getTodosOsAportes();
});

function submitAporteParaCadastro(){
	
	$.ajax({
		url: "/TesteHubFintech/api/contas/aporte",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify({
			numero_conta:$("#inputNumeroConta").val(),
			valor:$("#inputValor").val()
		}),
		success: function(data){
			window.location.replace("/TesteHubFintech/aportes.jsp");			
		},
		error: function(xhr){
			alert(xhr.responseText);
		}
	});
}

function submitAporteParaEstorno(){
	
	$.ajax({
		url: "/TesteHubFintech/api/contas/aporte/estorno",
		type: "PUT",
		contentType: "application/json",
		data: JSON.stringify({
			id_aporte:$("#inputIdAporte").val()
		}),
		success: function(data){
			window.location.replace("/TesteHubFintech/aportes.jsp");			
		},
		error: function(xhr){
			alert(xhr.responseText);
		}
	});
}

function getTodosOsAportes(){
	$.ajax({
		url: "/TesteHubFintech/api/contas/aportes",
		type: "GET",
		success: function(data){
			$.each(data, function(){
				$("#tableBody").append(
					"<tr>" +
						"<td>" + this.id + "</td>" +
						"<td>" + this.dataAporte + "</td>" +
						"<td>" + this.conta.numero + "</td>" +
						"<td>" + this.valor + "</td>" +
						"<td>" + this.tipo + "</td>" +						
					"</tr>"
				);
			});						
		}
	});
}