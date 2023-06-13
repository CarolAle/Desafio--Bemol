#language: pt

@Test
Funcionalidade: Cadastro de usuário

Cenario: Cadastrar novo usuário
	Dado que acesso a rota de simulações da API
	Quando envio os campos nome, email, password e administrador
	Então a API retorna status 201
	E a menssagem "Cadastro realizado com sucesso"
	
		