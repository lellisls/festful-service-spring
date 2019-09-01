package br.com.livro.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CarroService {

	@Autowired
	private CarroDAO db;

	public List<Carro> getCarros() {
		List<Carro> carros = db.getCarros();
		return carros;
	}

	public Carro getCarro(Long id) {
		return db.getCarroById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean delete(Long id) {
		return db.delete(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean save(Carro carro) {
		db.saveOrUpdate(carro);
		return true;
	}

	public List<Carro> findByName(String name) {
		return db.findByName(name);
	}

	public List<Carro> findByTipo(String tipo) {
		return db.findByTipo(tipo);
	}
}
