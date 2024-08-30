package aran.dulzurasdelcarmelo.servicios;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import aran.dulzurasdelcarmelo.entidades.*;
import aran.dulzurasdelcarmelo.repositorios.*;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {
	
	@Autowired
	private DetallePedidoRepository detallePedidoRepository;

	@Override
	public DetallePedido guardarDetallePedido(DetallePedido detallePedido) {
		return detallePedidoRepository.save(detallePedido);
	}
	
	
}
