#language: pt

@Test
Funcionalidade: Cadastro de produto

Contexto: logado no sistema como administrador 
	Dado que acesso a rota de simulações da API
	E que eu realizo login no sistema

Cenario: Cadastrar novo produto
	Quando realizo um cadastro com os campo nome, preço, descrição e quantidade
	Então a API retorna o status 201
	E a menssagem de "Cadastro realizado com sucesso"

	