package aran.dulzurasdelcarmelo.entidades;

import java.time.*;
import java.util.*;

import lombok.*;

import jakarta.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "pedidos")
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@NotNull
//	@Size(min = 3, max = 100)
	private String numero;
	
//	@NotNull
//	@Min(0)
	private double precioTotal;
	
	private LocalDate fechaCreacion;
	
	@ManyToOne
	private Usuario usuario;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<DetallePedido> detallePedido = new ArrayList<>();

	

	public Pedido() {

	}

	public Pedido(Long id, String numero, double precioTotal, LocalDate fechaCreacion) {
		super();
		this.id = id;
		this.numero = numero;
		this.precioTotal = precioTotal;
		this.fechaCreacion = fechaCreacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<DetallePedido> getDetallePedido() {
		return detallePedido;
	}

	public void setDetallePedido(List<DetallePedido> detallePedido) {
		this.detallePedido = detallePedido;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", numero=" + numero + ", precioTotal=" + precioTotal + ", fechaCreacion="
				+ fechaCreacion + "]";
	}
	
	
}