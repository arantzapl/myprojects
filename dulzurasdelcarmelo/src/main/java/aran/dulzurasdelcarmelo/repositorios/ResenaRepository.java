package aran.dulzurasdelcarmelo.repositorios;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import aran.dulzurasdelcarmelo.entidades.*;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long>{

	Iterable<Resena> findByProducto(Producto producto);
	
}
