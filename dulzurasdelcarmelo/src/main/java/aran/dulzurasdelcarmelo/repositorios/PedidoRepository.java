package aran.dulzurasdelcarmelo.repositorios;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import aran.dulzurasdelcarmelo.entidades.*;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	List<Pedido> findByUsuario(Usuario usuario);
	
}
