package aran.dulzurasdelcarmelo.entidades;

import lombok.*;

import jakarta.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@NotNull
//	@Size(min = 3, max = 100)
//	private String nombre;
	
//	@NotNull
//	@Min(0)
	private int cantidad;
	
	private double precio;
	
	private double precioTotal;
	
	private String tipoPresentacion;
	
	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;
	
	@ManyToOne
	private Producto producto;

	
	// Constructores

	public DetallePedido() {

	}

	public DetallePedido(Long id, int cantidad, double precio, double precioTotal, String tipoPresentacion) {
		super();
		this.id = id;
		this.cantidad = cantidad;
		this.precio = precio;
		this.precioTotal = precioTotal;
		this.tipoPresentacion = tipoPresentacion;
	}


	// Getters y setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public String getTipoPresentacion() {
		return tipoPresentacion;
	}

	public void setTipoPresentacion(String tipoPresentacion) {
		this.tipoPresentacion = tipoPresentacion;
	}
	
	
	
	// To String

	@Override
	public String toString() {
		return "DetallePedido [id=" + id + ", cantidad=" + cantidad + ", precio=" + precio + ", precioTotal="
				+ precioTotal + ", pedido=" + pedido + ", producto=" + producto + "]";
	}

	
	
}