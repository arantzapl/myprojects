package aran.dulzurasdelcarmelo.entidades;

import java.time.*;

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
	private int numero;
	
//	@NotNull
//	@Min(0)
	private double precioTotal;
	
	private LocalDate fechaCreacion;
	
	@ManyToOne
	private Usuario usuario;
	
	@OneToOne(mappedBy = "pedido")
	private DetallePedido detallePedido;

	

	public Pedido() {

	}

	public Pedido(Long id, int numero, double precioTotal, LocalDate fechaCreacion) {
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

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
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

	public DetallePedido getDetallePedido() {
		return detallePedido;
	}

	public void setDetallePedido(DetallePedido detallePedido) {
		this.detallePedido = detallePedido;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", numero=" + numero + ", precioTotal=" + precioTotal + ", fechaCreacion="
				+ fechaCreacion + "]";
	}
	
	
}