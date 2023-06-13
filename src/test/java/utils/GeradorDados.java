package utils;

import com.github.javafaker.Faker;

public class GeradorDados {
	
	private final Faker faker = new Faker();

	String password = faker.name().firstName();
	String nome = faker.name().firstName();
	String email = faker.internet().emailAddress();
	boolean administrador = faker.bool().bool();

	String nomeProduto = faker.commerce().productName();
	int preco = faker.number().randomDigit();
	String descricao = faker.commerce().material();
	String quantidade = faker.number().digits(2);

	public String getpassword() { return password; }
	public String getNome() { return nome;	}
	public String getEmail() { return email; }

	public boolean getAdministrador() { return administrador; }
	public String getnomeProduto() { return nomeProduto;}
	public int getPreco() { return preco; }
	public String getDescricao() { return descricao; }
	public String getQuantidade() { return quantidade; }

}
