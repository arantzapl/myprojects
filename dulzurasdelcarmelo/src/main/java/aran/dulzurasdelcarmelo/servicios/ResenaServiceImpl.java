package aran.dulzurasdelcarmelo.servicios;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import aran.dulzurasdelcarmelo.entidades.*;
import aran.dulzurasdelcarmelo.repositorios.*;

@Service
public class ResenaServiceImpl implements ResenaService {
	
	@Autowired
	private ResenaRepository resenaRepository;
	
	@Override
	public Resena guardarResena(Resena resena) {
		return resenaRepository.save(resena);
	}

	@Override
	public Iterable<Resena> listarResenas() {
		return resenaRepository.findAll();
	}

	@Override
	public Iterable<Resena> listarResenasPorProducto(Producto producto) {
		return resenaRepository.findByProducto(producto);
	}

	
	
}
