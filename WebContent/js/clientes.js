/*
 * CLIENTES
 */
$(function(){
	getTodosOsClientes();
});

function mostraPF(){
	$("#pf-dados").show();
	$("#pj-dados").hide();
}

function mostraPJ(){
	$("#pf-dados").hide();
	$("#pj-dados").show();
}

function submitClienteParaCadastro(){
	
	if($("#pf").is(":checked")){
		$.ajax({
			url: "/TesteHubFintech/api/clientes/cadastro",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify({
				sitJuridica:"F",
				identificacao:$("#inputIdentificacao").val(),
				nome:$("#inputNome").val(),
				dataNascimento:$("#inputDataNascimento").val()
			}),
			success: function(data){
				window.location("/TesteHubFintech/clientes.jsp");				
			},
			error: function(xhr){
				alert(xhr.responseText);
			}
		});
	}
	else if($("#pj").is(":checked")){
		$.ajax({
			url: "/TesteHubFintech/api/clientes/cadastro",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify({
				sitJuridica:"J",
				identificacao:$("#inputIdentificacao").val(),
				razaoSocial:$("#inputRazaoSocial").val(),
				nomeFantasia:$("#inputNomeFantasia").val()
			}),
			success: function(data){
				window.location("/TesteHubFintech/clientes.jsp");				
			},
			error: function(xhr){
				alert(xhr.responseText);
			}
		});
	} 
	else {
		alert("Marque um tipo");
	}
}

function getTodosOsClientes(){
	$.ajax({
		url: "/TesteHubFintech/api/clientes",
		type: "GET",
		success: function(data){
			$.each(data, function(){
				$("#tableBody").append(
					"<tr>" +
						"<td>" + this.sitJuridica + "</td>" +
						"<td>" + this.identificacao + "</td>" +
						"<td>" + (typeof this.nome !== 'undefined' ? this.nome : "") + "</td>" +
						"<td>" + (typeof this.dataNascimento !== 'undefined' ? this.dataNascimento : "") + "</td>" +
						"<td>" + (typeof this.razaoSocial !== 'undefined' ? this.razaoSocial : "") + "</td>" +		
						"<td>" + (typeof this.nomeFantasia !== 'undefined' ? this.nomeFantasia : "") + "</td>" +
						"<td><button type='button' class='btn btn-danger' onclick='deletaCliente(" + this.identificacao + ")'>Excluir</button></td>" +
					"</tr>"
				);
			});						
		}
	});
}

function deletaCliente(identificacao){
	$.ajax({
		url: "/TesteHubFintech/api/clientes/delete/" + identificacao,
		type: "DELETE",
		success: function(data){
			window.location.replace("/TesteHubFintech/clientes.jsp");
		}
	});
}