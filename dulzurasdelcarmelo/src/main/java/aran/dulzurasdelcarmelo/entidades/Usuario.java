package aran.dulzurasdelcarmelo.entidades;

import java.util.*;

import lombok.*;

import jakarta.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@NotNull
//	@Size(min = 3, max = 100)
	private String nombre;
	
//	@NotNull
//	@Min(0)
	private String email;
	
	private String imagen;
	
	private String direccion;
	
	private String localidad;
	
	private String password;
	
	private String tipo;
	
	private int numTelefono;	
	
	private int codPostal;
	
	@OneToMany(mappedBy = "usuario")
	private List<Producto> productos;
	
	@OneToMany(mappedBy = "usuario")
	private List<Pedido> pedidos;

	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getNumTelefono() {
		return numTelefono;
	}

	public void setNumTelefono(int numTelefono) {
		this.numTelefono = numTelefono;
	}

	public int getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(int codPostal) {
		this.codPostal = codPostal;
	}
	
	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}	

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Usuario(Long id, String nombre, String email, String imagen, String direccion, String localidad,
			String password, String tipo, int numTelefono, int codPostal) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.imagen = imagen;
		this.direccion = direccion;
		this.localidad = localidad;
		this.password = password;
		this.tipo = tipo;
		this.numTelefono = numTelefono;
		this.codPostal = codPostal;
	}

	public Usuario() {
		
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", imagen=" + imagen + ", direccion="
				+ direccion + ", localidad=" + localidad + ", password=" + password + ", tipo=" + tipo
				+ ", numTelefono=" + numTelefono + ", codPostal=" + codPostal + ", productos=" + productos
				+ ", pedidos=" + pedidos + "]";
	}
	
	
}