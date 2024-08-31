package aran.dulzurasdelcarmelo.servicios;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import aran.dulzurasdelcarmelo.entidades.*;
import aran.dulzurasdelcarmelo.repositorios.*;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public Producto guardarProducto(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	public Iterable<Producto> listarProductos() {
		return productoRepository.findAll();
	}

	@Override
	public Producto verProductoPorId(Long id) {
		return productoRepository.findById(id).orElse(null);
	}

	@Override
	public void modificarProducto(Producto productoActualizado) {
		productoRepository.save(productoActualizado);
	}

	@Override
	public void borrarProducto(Long id) {
		productoRepository.deleteById(id);
	}

	@Override
	public List<Producto> buscarPorNombre(String nombre) {
		return productoRepository.buscarPorNombre(nombre);
	}

	
	
	
}
