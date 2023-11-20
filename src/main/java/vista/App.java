package vista;

import service.ClienteService;

/* Ejemplo aplicación con dao+repository+service 
 * del dominio del cliente (usuario). 
 * Requiere modificar la tabla clientes añadiendo
 * los campos password
 * alter table clientes
 		add column username varchar(20) unique,
		add column password char(40);
	
    update clientes 
		set username = concat("usr", id),
			password = sha1('1234');
 */

public class App {
	static ClienteService clienteServicio = null;

	public static void main(String[] args) {

		try {
			clienteServicio = new ClienteService();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		if (clienteServicio != null) {
			ejemploLogin();
			ejemploCambioContrasenya();
			ejemploCambioPerfil();
			ejemploRegistro();
			ejemploBusquedaPorNombre();
		}

	}

	private static void ejemploLogin() {	

	}

	private static void ejemploCambioContrasenya() {		

	}

	private static void ejemploCambioPerfil() {		

	}

	private static void ejemploRegistro() {		

	}

	private static void ejemploBusquedaPorNombre() {		

	}

}
