/*
 * CONTAS
 */
$(function(){
	getTodasAsContas();
});

function mostraContaPai(){
	$("#form-conta-pai").show();
}

function escondeContaPai(){
	$("#form-conta-pai").hide();
}

function submitContaParaCadastro(){
	if($("#cMatriz").is(":checked")){
		$.ajax({
			url: "/TesteHubFintech/api/contas/cadastro",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify({
				tipo:0,
				situacao:$("#inputSituacao").val(),
				nome:$("#inputNome").val(),
				dataCriacao:$("#inputData").val(),
				saldo:$("#inputSaldo").val(),
				idcliente:$("#inputIdCliente").val()
			}),
			success: function(data){
				window.location.replace("/TesteHubFintech/contas.jsp");				
			},
			error: function(xhr){
				alert(xhr.responseText);
			}
		});
	}
	else if($("#cFilial").is(":checked")){
		$.ajax({
			url: "/TesteHubFintech/api/contas/cadastro",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify({
				tipo:1,
				situacao:$("#inputSituacao").val(),
				nome:$("#inputNome").val(),
				dataCriacao:$("#inputData").val(),
				saldo:$("#inputSaldo").val(),
				idcliente:$("#inputIdCliente").val(),
				contaPai:$("#inputContaPai").val()
			}),
			success: function(data){
				window.location.replace("/TesteHubFintech/contas.jsp");				
			},
			error: function(xhr){
				alert(xhr.responseText);
			}
		});
	} 
	else {
		alert("Marque um tipo de conta");
	}
}

function getTodasAsContas(){
	$.ajax({
		url: "/TesteHubFintech/api/contas",
		type: "GET",
		success: function(data){
			$.each(data, function(){
				$("#tableBody").append(
					"<tr>" +
						"<td>" + this.numero + "</td>" +
						"<td>" + this.nome + "</td>" +
						"<td>" + this.contaPai + "</td>" +
						"<td>" + this.situacao + "</td>" +
						"<td>" + this.saldo + "</td>" +
						"<td>" + (this.tipo == "0" ? "Matriz" : "Filial") + "</td>" +
						"<td>" + this.idcliente + "</td>" +
						"<td>" + this.dataCriacao + "</td>" +
						"<td><button type='button' class='btn btn-danger' onclick='deletaConta(" + this.numero + ")'>Excluir</button></td>" +
					"</tr>"
				);
			});						
		}
	});
}

function deletaConta(numero){
	$.ajax({
		url: "/TesteHubFintech/api/contas/delete/" + numero,
		type: "DELETE",
		success: function(data){
			window.location.replace("/TesteHubFintech/contas.jsp");
		}
	});
}