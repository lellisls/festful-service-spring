package test;

import java.util.List;

import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroService;
import junit.framework.TestCase;

public class CarroTest extends TestCase {

	private CarroService carroService;

	public CarroTest() {
		super();
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		carroService = (CarroService) SpringUtil.getInstance().getBean(CarroService.class);
	}
	
	public void testListaCarros() {
		List<Carro> carros = carroService.getCarros();
		assertNotNull(carros);
		
		assertTrue(carros.size() > 0);
		
		Carro tucker = carroService.findByName("Tucker 1948").get(0);
		assertEquals("Tucker 1948", tucker.getNome());
		
		Carro ferrari = carroService.findByName("Ferrari FF").get(0);
		assertEquals("Ferrari FF", ferrari.getNome());
		
		Carro bugatti = carroService.findByName("Bugatti Veyron").get(0);
		assertEquals("Bugatti Veyron", bugatti.getNome());
		
	}

	public void testSalvarDeletarCarro() {
		Carro c = new Carro();
		
		c.setNome("Teste");
		c.setDesc("Teste desc");
		c.setUrlFoto("url foto aqui");
		c.setUrlFoto("url video aqui");
		c.setLatitude("lat");
		c.setLongitude("lng");
		c.setTipo("tipo");
		
		carroService.save(c);
		Long id = c.getId();
		assertNotNull(id);
		
		Carro c2 = carroService.getCarro(id);
		
		assertEquals(c.getId(),       id);
		assertEquals(c.getNome(),     c2.getNome());
		assertEquals(c.getDesc(),     c2.getDesc());
		assertEquals(c.getUrlFoto(),  c2.getUrlFoto());
		assertEquals(c.getUrlVideo(), c2.getUrlVideo());
		assertEquals(c.getLatitude(), c2.getLatitude());
		assertEquals(c.getLongitude(), c2.getLongitude());
		assertEquals(c.getTipo(),     c2.getTipo());
		
		c2.setNome("teste update");
		carroService.save(c2);
		
		Carro c3 = carroService.getCarro(id);
		assertEquals(c3.getId(), id);
		assertEquals("teste update", c3.getNome());
		
		carroService.delete(id);
		
		Carro c4 = carroService.getCarro(id);
		assertNull(c4);
	}
}
