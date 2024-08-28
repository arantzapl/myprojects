package aran.dulzurasdelcarmelo.entidades;

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
	
//	private Usuario usuario;
//	
//	private Producto producto;
	
	public Resena() {

	}

	public Resena(Long id, int puntuacion, String comentario) {
		super();
		this.id = id;
		this.puntuacion = puntuacion;
		this.comentario = comentario;
	}

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

	@Override
	public String toString() {
		return "Resena [id=" + id + ", puntuacion=" + puntuacion + ", comentario=" + comentario + "]";
	}



}