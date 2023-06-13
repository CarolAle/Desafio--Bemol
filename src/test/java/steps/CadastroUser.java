package steps;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.*;
import utils.GeradorDados;

public class CadastroUser {

	Response response;
	RequestSpecification request;
	private String requestBody;
	public static String nome = null;
	public static String email = null;
	public static String password = null;
	public static boolean administrador = true;

	GeradorDados geradorDados;

	public void cadastro() {
		geradorDados = new GeradorDados();
		nome = geradorDados.getNome();
		email = geradorDados.getEmail();
		password = geradorDados.getpassword();
		administrador = geradorDados.getAdministrador();
	}

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "https://serverest.dev";
	}

	@Dado("que acesso a rota de simulações da API")
	public void que_acesso_a_rota_de_simulações_da_api() {
		request = RestAssured.given()
				.accept("application/json")
				.contentType(ContentType.JSON);
	}

	@Quando("envio os campos nome, email, password e administrador")
	public void envio_os_campos_obrigatórios_corretamente() {
		cadastro();
		requestBody = "{ "
				+ " \"nome\": \"" + nome +
				"\", \"email\": \"" + email +
				"\", \"password\": \"" + password + 
				"\", \"administrador\": \"" + administrador +
				"\" }";
		response = request.body(requestBody)
				.when().log().all()
				.post("/usuarios");
	}

	@Entao("a API retorna status {int}")
	public void a_api_retorna_status(int status) {
		response.then()
		.statusCode(status);
	}

	@Entao("a menssagem {string}")
	public void a_mensagem_de(String msg) {
		response.then()
		.body("message", equalTo(msg))
		.extract()
		.response();

		//verifica usuário criado
		String UserID = response.path("_id");

		RestAssured.given().contentType("application/json")
		.when().log().all()
		.get("/usuarios/" + UserID)
		.then()
		.assertThat().body("_id", equalTo(UserID));
	}

}
