package test;

import java.util.Base64;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import br.com.livro.domain.Carro;
import br.com.livro.domain.ResponseWithUrl;
import br.com.livro.rest.GsonMessageBodyHandler;
import junit.framework.TestCase;

public class RestTest extends TestCase {
	String URL = "http://localhost:8080/carros/rest/";

	public void testGetCarroId() {
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);

		client.register(GsonMessageBodyHandler.class);

		WebTarget target = client.target(URL).path("/carros/11");

		Response response = target.request(MediaType.APPLICATION_JSON).get();

		int status = response.getStatus();

//		String json = response.readEntity(String.class);
//		System.out.println(json);

		Carro c = response.readEntity(Carro.class);

		assertEquals(200, status);
		assertEquals("Ferrari FF", c.getNome());
	}

	public void testCreateCarro() {
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);

		client.register(GsonMessageBodyHandler.class);

		WebTarget target = client.target(URL).path("/carros/");

		Carro c = new Carro();

		c.setNome("New car");
		c.setTipo("delete");

		Entity<Carro> entity = Entity.entity(c, MediaType.APPLICATION_JSON);

		Response response = target.request(MediaType.APPLICATION_JSON).post(entity, Response.class);

		assertEquals(200, response.getStatus());

		br.com.livro.domain.Response s = response.readEntity(br.com.livro.domain.Response.class);

		assertEquals("OK", s.getStatus());
		assertEquals("Carro salvo com sucesso.", s.getMsg());
	}

	public void testPostFormParams() {
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);

		client.register(GsonMessageBodyHandler.class);

		String base64 = Base64.getEncoder().encodeToString("Ricardo Lecheta".getBytes());
		Form form = new Form();
		form.param("fileName", "nome.txt");
		form.param("base64", base64);

		WebTarget target = client.target(URL).path("/carros/postFotoBase64");
		Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE);

		Response response = target.request(MediaType.APPLICATION_JSON).post(entity);

		assertEquals(200, response.getStatus());

		ResponseWithUrl r = response.readEntity(ResponseWithUrl.class);

		assertEquals("OK", r.getStatus());
		assertEquals("File received successfully", r.getMsg());
		System.out.println(r.getUrl());
		assertNotNull(r.getUrl());
		assertTrue(r.getUrl().endsWith("nome.txt"));
	}
	
	public List<Carro> getCarrosByTipo(String tipo) {
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);

		client.register(GsonMessageBodyHandler.class);

		WebTarget target = client.target(URL).path("/carros/tipo/" + tipo);

		Response response = target.request(MediaType.APPLICATION_JSON).get();

		assertEquals(response.getStatus(), 200);

		List<Carro> carros = response.readEntity(new GenericType<List<Carro>>() {
		});
		return carros;
	}

	public void testDeleteCarroId() {
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);

		client.register(GsonMessageBodyHandler.class);

		List<Carro> carros = getCarrosByTipo("delete");
		Carro c = carros.get(0);

		WebTarget target = client.target(URL).path("/carros/" + c.getId());

		Response response = target.request(MediaType.APPLICATION_JSON).delete();

		int status = response.getStatus();

		assertEquals(200, status);

		br.com.livro.domain.Response s = response.readEntity(br.com.livro.domain.Response.class);

		assertEquals("OK", s.getStatus());
		assertEquals("Carro deletado com sucesso.", s.getMsg());
	}
}
