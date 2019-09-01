package br.com.livro.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroService;
import br.com.livro.domain.Response;

@Path("/carros")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Component
public class CarrosResource {
	
	@Autowired
	private CarroService carroService;
	
	@GET
	public List<Carro> get() {
		List<Carro> carros = carroService.getCarros();
		return carros;
	}
	
	@GET
	@Path("{id}")
	public Carro get(@PathParam("id") long id) {
		Carro carro = carroService.getCarro(id);
		return carro;
	}
	
	@GET
	@Path("/tipo/{tipo}")
	public List<Carro> getByTipo(@PathParam("tipo") String tipo ) {
		List<Carro> carros = carroService.findByTipo(tipo);
		return carros;
	}

	@GET
	@Path("/nome/{tipo}")
	public List<Carro> getByName(@PathParam("tipo") String tipo ) {
		List<Carro> carros = carroService.findByName(tipo);
		return carros;
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") long id) {
		carroService.delete(id);
		return Response.Ok("Carro deletado com sucesso.");
	}
	
	@POST
	public Response post(Carro carro) {
		carroService.save(carro);
		return Response.Ok("Carro salvo com sucesso.");
	}
	
	@PUT
	public Response put(Carro carro) {
		carroService.save(carro);
		
		return Response.Ok("Carro atualizado com sucesso.");
	}
	
}
