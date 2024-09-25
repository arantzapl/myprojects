package aran.dulzurasdelcarmelo.entidades;

import java.time.*;

import lombok.*;

import jakarta.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "resenas")
public class Resena {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@NotNull
//	@Size(min = 3, max = 100)
	private int puntuacion;

//	@NotNull
//	@Min(0)
	private String comentario;
	
	private LocalDate fecha;
	
	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	private Producto producto;
	
	
	// Constructores
	
	public Resena() {

	}

	public Resena(Long id, int puntuacion, String comentario, LocalDate fecha, Usuario usuario, Producto producto) {
		super();
		this.id = id;
		this.puntuacion = puntuacion;
		this.comentario = comentario;
		this.fecha = fecha;
		this.usuario = usuario;
		this.producto = producto;
	}

	
	// Getters y Setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	
	// To String
	
	@Override
	public String toString() {
		return "Resena [id=" + id + ", puntuacion=" + puntuacion + ", comentario=" + comentario + ", fecha=" + fecha
				+ ", usuario=" + usuario + ", producto=" + producto + "]";
	}
}