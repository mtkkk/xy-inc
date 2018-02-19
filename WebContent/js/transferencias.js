/*
 * TRANSFERENCIAS
 */
$(function(){
	getTodasAsTransferencias();
});

function submitTransferenciaParaCadastro(){
	
		$.ajax({
			url: "/TesteHubFintech/api/contas/transferencia",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify({
				conta1:$("#inputConta1").val(),
				conta2:$("#inputConta2").val(),
				valor:$("#inputValor").val()
			}),
			success: function(data){
				window.location.replace("/TesteHubFintech/transferencias.jsp");				
			},
			error: function(xhr){
				alert(xhr.responseText);
			}
		
		});
}

function submitTransferenciaParaEstorno(){
	
	$.ajax({
		url: "/TesteHubFintech/api/contas/transferencia/estorno",
		type: "PUT",
		contentType: "application/json",
		data: JSON.stringify({
			id_transf:$("#inputIdTransferencia").val()
		}),
		success: function(data){
			window.location.replace("/TesteHubFintech/transferencias.jsp");		
		},
		error: function(xhr){
			alert(xhr.responseText);
		}
	});
}

function getTodasAsTransferencias(){
	$.ajax({
		url: "/TesteHubFintech/api/contas/transferencias",
		type: "GET",
		success: function(data){
			$.each(data, function(){
				$("#tableBody").append(
					"<tr>" +
						"<td>" + this.id + "</td>" +
						"<td>" + this.dataTransferencia + "</td>" +
						"<td>" + this.conta1.numero + "</td>" +
						"<td>" + this.conta2.numero + "</td>" +
						"<td>" + this.valor + "</td>" +
						"<td>" + this.tipo + "</td>" +						
					"</tr>"
				);
			});						
		}
	});
}