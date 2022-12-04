package modelo;

public class Cliente {
	private int id;
	private String nombre;
	private String direccion;
	private String username;
	private String passwd;

	public Cliente() {
	}

	public Cliente(String nombre, String direccion) {
		this.nombre = nombre;
		this.direccion = direccion;
	}

	public Cliente(String nombre, String direccion, String username, String passwd) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.username = username;
		this.passwd = passwd;
	}

	public Cliente(int id, String nombre, String direccion) {
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
	}

	public Cliente(int id, String nombre, String direccion, String username, String passwd) {
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.username = username;
		this.passwd = passwd;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", username=" + username
				+ ", passwd=" + passwd + "]";
	}

}
