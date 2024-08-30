package aran.dulzurasdelcarmelo.servicios;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import aran.dulzurasdelcarmelo.entidades.*;
import aran.dulzurasdelcarmelo.repositorios.*;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Override
	public Pedido guardarPedido(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

	@Override
	public List<Pedido> listarPedidos() {
		return pedidoRepository.findAll();
	}
	
	@Override
	public Pedido verPedidoPorId(Long id) {
		return pedidoRepository.findById(id).orElse(null);
	}

	// 0000010
	public String generarNumeroPedido() {
		Integer numero = 0;
		String numeroConcatenado = "";

		List<Pedido> pedidos = listarPedidos();

		List<Integer> numeros = new ArrayList<Integer>();

		pedidos.stream().forEach(p -> numeros.add(Integer.parseInt(p.getNumero())));

		if (pedidos.isEmpty()) {
			numero = 1;
		} else {
			numero = numeros.stream().max(Integer::compare).get();
			numero++;
		}

		if (numero < 10) {
			numeroConcatenado = "000000000" + String.valueOf(numero);
		} else if (numero < 100) {
			numeroConcatenado = "00000000" + String.valueOf(numero);
		} else if (numero < 10000) {
			numeroConcatenado = "0000000" + String.valueOf(numero);
		} else if (numero < 100000) {
			numeroConcatenado = "000000" + String.valueOf(numero);
		} else if (numero < 1000000) {
			numeroConcatenado = "00000" + String.valueOf(numero);
		} else if (numero < 10000000) {
			numeroConcatenado = "0000" + String.valueOf(numero);
		} else if (numero < 100000000) {
			numeroConcatenado = "000" + String.valueOf(numero);
		} else if (numero < 1000000000) {
			numeroConcatenado = "00" + String.valueOf(numero);
		}
		return numeroConcatenado;
	}
}
