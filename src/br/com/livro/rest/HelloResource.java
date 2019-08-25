package br.com.livro.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.livro.domain.Response;

@Path("/hello")
public class HelloResource {
	
	@GET
	@Consumes(MediaType.TEXT_HTML)
	@Produces(MediaType.TEXT_HTML + ";charset=utf-8")
	public String get() {
		return "<b>Olá mundo HTML!</b>";
	}
	
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	public String helloTextPlain() {
		return "Olá mundo Texto!";
	}
	
	@GET
	@Consumes({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	@Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public Response helloXml( ) {
		return Response.Ok("Olá mundo XML");
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response helloJson() {
		return Response.Ok("Olá mundo JSON");
	}
}
