package aran.dulzurasdelcarmelo.entidades;

import java.util.*;

import lombok.*;

import jakarta.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "productos")
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@NotNull
//	@Size(min = 3, max = 100)
	private String nombre;
	
	private String descripcion;
	
	private String imagen;
	
//	@NotNull
	private Double precioUnidad;
	
	private Double precioBandeja;
	
	private Double precioCaja;
	
	private String tipo;
	
	private boolean tienePresentaciones;
	
	
	@ManyToOne
	private Usuario usuario;

	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Resena> resenas;
	
	
	// Método para verificar si tiene presentaciones opcionales
    public boolean tienePresentaciones() {
        return precioBandeja != null || precioCaja != null;
    }
	
	
	// Constructores

	public Producto() {

	}

	public Producto(Long id, String nombre, String descripcion, String imagen, Double precioUnidad, Double precioBandeja,
			Double precioCaja, String tipo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.imagen = imagen;
		this.precioUnidad = precioUnidad;
		this.precioBandeja = precioBandeja;
		this.precioCaja = precioCaja;
		this.tipo = tipo;
	}

	
	// Getters y Setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Double getPrecioUnidad() {
		return precioUnidad;
	}

	public void setPrecioUnidad(Double precioUnidad) {
		this.precioUnidad = precioUnidad;
	}

	public Double getPrecioBandeja() {
		return precioBandeja;
	}

	public void setPrecioBandeja(Double precioBandeja) {
		this.precioBandeja = precioBandeja;
	}

	public Double getPrecioCaja() {
		return precioCaja;
	}

	public void setPrecioCaja(Double precioCaja) {
		this.precioCaja = precioCaja;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Resena> getResenas() {
		return resenas;
	}

	public void setResenas(List<Resena> resenas) {
		this.resenas = resenas;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	// To String

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", imagen=" + imagen
				+ ", precioUnidad=" + precioUnidad + ", precioBandeja=" + precioBandeja + ", precioCaja=" + precioCaja
				+ ", tipo=" + tipo + "]";
	}
	
	
}