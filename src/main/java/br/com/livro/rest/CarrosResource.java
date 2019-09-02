package br.com.livro.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postFoto(final FormDataMultiPart multiPart) {

		if (multiPart != null && multiPart.getFields() != null) {
			Set<String> keys = multiPart.getFields().keySet();
			for (String key : keys) {
				FormDataBodyPart field = multiPart.getField(key);
				InputStream in = field.getValueAs(InputStream.class);

				try {
					String filename = field.getFormDataContentDisposition().getFileName();
					// JVM's temporary folder
					File tmpDir = new File(System.getProperty("java.io.tmpdir"), "carros");
					if (!tmpDir.exists()) {
						tmpDir.mkdir();
					}
					// Creates the file
					File file = new File(tmpDir, filename);
					FileOutputStream out = new FileOutputStream(file);

					IOUtils.copy(in, out);
					IOUtils.closeQuietly(out);
					System.out.println("File: " + file);
					return Response.Ok("File received sucessfully");
				} catch (IOException e) {
					e.printStackTrace();
					return Response.Error("Error sending the file.");
				}
			}
		}
		return Response.Ok("Requisição inválida");
	}
}
