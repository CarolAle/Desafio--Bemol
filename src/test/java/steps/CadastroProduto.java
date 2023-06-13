package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.GeradorDados;

import static org.hamcrest.Matchers.*;

public class CadastroProduto {

	Response response;
	RequestSpecification request;
	private String requestBody;
	GeradorDados geradorDados;
	
	//Usuário utilizado
	String email = "fulano@qa.com";
	String password = "teste";
	String token = null;

	String nomeProduto = null;
	int preco = 0;
	String descricao = null;
	String quantidade = null;

	public void cadastro() {
		geradorDados = new GeradorDados();
		nomeProduto = geradorDados.getnomeProduto();
		preco = geradorDados.getPreco();
		descricao = geradorDados.getDescricao();
		quantidade = geradorDados.getQuantidade();	
	}

	@Dado("que eu realizo login no sistema")
	public void que_eu_realizo_login_no_sistema() {
		request = RestAssured.given()
				.accept("application/json")
                .contentType(ContentType.JSON);
		
		requestBody = "{ "
				+ "\"email\": \"" + email +
				"\", \"password\": \"" + password +
				"\" }";
		response = request.body(requestBody)
				.when().log().all()
				.post("/login");

		response.then().log().all()
		.assertThat()
		.statusCode(200);
	}

	@Quando("realizo um cadastro com os campo nome, preço, descrição e quantidade")
	public void realizo_um_cadastro_com_os_campo_nome_preço_descrição_e_quantidade() {
		cadastro();

		//Pega o token
		response.then()
		.extract().response();
		token = response.body().jsonPath().getString("authorization");

		requestBody = "{ "
				+ "\"nome\": \"" + nomeProduto +
				"\", \"preco\": \"" + preco +
				"\", \"descricao\": \"" + descricao +
				"\", \"quantidade\": \"" + quantidade +
				"\"}";

		response = request.headers("authorization", token)
				.body(requestBody)
				.when().log().all()
				.post("/produtos");		
	}

	@Entao("a API retorna o status {int}")
	public void a_api_retorna_status(int status) {
		response.then()
		.statusCode(status);
	}

	@Entao("a menssagem de {string}")
	public void a_mensagem_de(String msg) {
		response.then()
		.body("message", equalTo(msg))
		.extract()
		.response();

		//verifica produto criado
		String UserID = response.path("_id");

		RestAssured.given().headers("authorization", token)
		.when().log().all()
		.get("/produtos/" + UserID)
		.then()
		.assertThat().body("_id", equalTo(UserID));
	}

}
