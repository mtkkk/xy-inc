# xy-inc

## Arquitetura do projeto

O projeto possui dois recursos, um responsável por criar consulta e deletar modelos baseado no input do usuário e outro 
recurso que fornece um CRUD para estes modelos criados.

Para criar um modelo é necessário informar seus campos e tipo de dados para cada campo, além de um campo que será a chave primária.

Após a criação de um modelo as suas informações também são guardadas em tabelas de controle.


## Tecnologias e ferramentas utilizadas
	
	*Java JRE 1.8
	*Eclipse IDE JEE
	*MySQL
	*JAX-RS e Jackson Parser
	*JUnit
	*GitHub Desktop
	*Postman

## Instruções para configurar o projeto

1.	Instalar o MySQL Server Community - [Página de Download](https://dev.mysql.com/downloads/file/?id=474802)

	-A conexão no projeto está configurada para port 3306

	-Usuário do banco root senha root
	
2. 	Rodar o script CreateDb.sql disponível na pasta de resources do projeto	(Utilizar MySQL Workbench que será instalado junto)
	
	*As tabelas principais serão criadas e também as entradas necessárias para rodar os testes unitários

3. 	Baixar o Apache Tomcat 8.0.50 - [Página de Download](https://tomcat.apache.org/download-80.cgi)

4. 	Dentro do Eclipse após importar o projeto devem ser feitas as seguintes configurações dentro do build path:
	
	-Informar o caminho do Apache Tomcat 8 (caso já não possua)

	-Criar e mapear as bibliotecas JAX-RS-Jersey-API e Jackson-Parser-API (Caso elas já existam o melhor seria deletar e criar novamente)

		-Clique em Add Library -> User Library -> User Libraries -> New (nomeie JAX-RS-Jersey-API) -> Add External JARs

		-Adicione todos os JARs das pastas api,ext e lib dentro da pasta jaxrs-ri (disponível na pasta resources do projeto)

		-Clique em Add Library -> User Library -> User Libraries -> New (nomeie Jackson-Parser-API) -> Add External JARs
		
		-Adicione todos os JARs da pasta Jackson (disponível na pasta resources do projeto)

		
5.	Para importar as chamadas http do postman utilizar o arquivo .json na pasta resources e no postman apenas clicar em "import"

		
6.	Para rodar os testes unitários rode as classes de teste na source folder "test" enquanto o servidor estiver rodando as APIs

## Observações

O ideal para este projeto seria a utilização de algum banco de dados NoSQL (semelhante a um serviço como o Firebase), mas dado que 
possuía apenas 3 noites para finalizar e não possuo experiência profissional com bancos NoSQL optei por utilizar relacional mesmo, mas já 
iniciei estudos para tentar adaptar este projeto para NoSQL(a título de conhecimento mesmo).

Não deu tempo de fazer o front end mas já tenho experiência em consumo de APIs em front também (framework jQuery) e um pouco de bootstrap.

# xy-inc-nosql

##	Instruções para configurar o projeto

1.	Instalar o MongoDB Community 
	
	-Durante a instalação deixe marcado para instalar MongoDB Compass também

	-A conexão no projeto está configurada para o port 27017
	
	-Sem usuário e senha

	-Após instalação rodar o arquivo mongod.exe na pasta de instalação para inicializar o servidor(Padrão C:\Program Files\MongoDB\Server\3.6\bin)

	-Caso inicie e imediatamente feche verifique se a pasta C:\data\db foi criada (padrão)
	
2.	Na pasta de instalação do MongoDB (Padrão C:\Program Files\MongoDB\Server\3.6\bin) executar mongo.exe para abrir o shell

	-Rodar os comandos no arquivo MongoDB.txt (pasta resources do projeto), rodar uma linha por vez (Irá criar a database e as collections necessárias)