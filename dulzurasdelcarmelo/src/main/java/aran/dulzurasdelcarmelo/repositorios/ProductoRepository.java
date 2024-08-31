package aran.dulzurasdelcarmelo.repositorios;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import aran.dulzurasdelcarmelo.entidades.*;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{
	
	@Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:nombre%")
    List<Producto> buscarPorNombre(@Param("nombre") String nombre);
	
}
