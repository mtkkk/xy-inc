# xy-inc

Instalar o MySQL Server Community (https://dev.mysql.com/downloads/file/?id=474802)

	-A conexão no projeto está configurada para port 3306

	-Usuário do banco root senha root
	
Rodar o script CreateDb.sql disponível na pasta de resources do projeto	(Utilizar MySQL Workbench que será instalado junto)

Baixar o Apache Tomcat 8.0.50 (https://tomcat.apache.org/download-80.cgi)

Dentro do Eclipse após importar o projeto devem ser feitas as seguintes configurações dentro do build path:
	
	-Informar o caminho do Apache Tomcat 8 (caso já não possua)

	-Criar e mapear as bibliotecas JAX-RS-Jersey-API e Jackson-Parser-API (Caso elas já existam o melhor seria deletar e criar novamente)

		-Clique em Add Library -> User Library -> User Libraries -> New (nomeie JAX-RS-Jersey-API) -> Add External JARs

		-Adicione todos os JARs das pastas api,ext e lib dentro da pasta jaxrs-ri (disponível na pasta resources do projeto)

		-Clique em Add Library -> User Library -> User Libraries -> New (nomeie Jackson-Parser-API) -> Add External JARs

Para rodar os testes unitários rode as classes teste na source folder "test" enquanto o servidor estiver rodando as APIs