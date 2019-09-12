package br.com.livro.rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroService;
import br.com.livro.domain.Response;
import br.com.livro.domain.ResponseWithUrl;
import br.com.livro.domain.UploadService;

@Path("/carros")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Component
@PermitAll
public class CarrosResource {

	@Autowired
	private CarroService carroService;

	@Autowired
	private UploadService uploadService;

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
	public List<Carro> getByTipo(@PathParam("tipo") String tipo) {
		List<Carro> carros = carroService.findByTipo(tipo);
		return carros;
	}

	@GET
	@Path("/nome/{tipo}")
	public List<Carro> getByName(@PathParam("tipo") String tipo) {
		List<Carro> carros = carroService.findByName(tipo);
		return carros;
	}

	@DELETE
	@Path("{id}")
	@RolesAllowed("admin")
	public Response delete(@PathParam("id") long id) {
		carroService.delete(id);
		return Response.Ok("Carro deletado com sucesso.");
	}

	@POST
	@RolesAllowed("admin")
	public Response post(Carro carro) {
		carroService.save(carro);
		return Response.Ok("Carro salvo com sucesso.");
	}

	@PUT
	@RolesAllowed("admin")
	public Response put(Carro carro) {
		carroService.save(carro);

		return Response.Ok("Carro atualizado com sucesso.");
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public ResponseWithUrl postFoto(final FormDataMultiPart multiPart) throws Exception {

		if (multiPart != null && multiPart.getFields() != null) {
			Set<String> keys = multiPart.getFields().keySet();
			for (String key : keys) {
				FormDataBodyPart field = multiPart.getField(key);
				InputStream in = field.getValueAs(InputStream.class);

				try {
					String filename = field.getFormDataContentDisposition().getFileName();
					String path = uploadService.upload(filename, in);
					System.out.println("File: " + path);
					return ResponseWithUrl.Ok("File received sucessfully", path);
				} catch (IOException e) {
					e.printStackTrace();
					return ResponseWithUrl.Error("Error sending the file.");
				}
			}
		}
		return ResponseWithUrl.Error("Invalid request");
	}
	
	@POST
	@Path("/toBase64")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String postToBase64(final FormDataMultiPart multiPart) {

		if (multiPart != null && multiPart.getFields() != null) {
			Set<String> keys = multiPart.getFields().keySet();
			for (String key : keys) {
				try {
					FormDataBodyPart field = multiPart.getField(key);
					InputStream in = field.getValueAs(InputStream.class);
					byte[] bytes = IOUtils.toByteArray(in);
					String base64 = Base64.getEncoder().encodeToString(bytes);
					return base64;
				}catch (Exception e) {
					e.printStackTrace();
					return "Error: " + e.getMessage();
				}
			}
		}
		return "Invalid request";
	}

	@POST
	@Path("/postFotoBase64")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public ResponseWithUrl postFotoBase64(@FormParam("fileName") String fileName, @FormParam("base64") String base64) {
		if( fileName != null && base64 != null ) {
			try {
				// Decode
				byte[] bytes = Base64.getDecoder().decode(base64);
				InputStream in = new ByteArrayInputStream(bytes);
				// Faz o upload
				String path = uploadService.upload(fileName, in);
				System.out.println("File: " + path);
				return ResponseWithUrl.Ok("File received successfully", path);
			} catch( Exception e ) {
				e.printStackTrace();
				return ResponseWithUrl.Error("Error sending the file.");
			}
		}
		
		return ResponseWithUrl.Error("Invalid request");
	}

}
