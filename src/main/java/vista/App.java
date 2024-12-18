package vista;

import java.util.List;

import excepciones.ServiceException;
import modelo.Cliente;
import service.ClienteService;

/* Ejemplo aplicación con dao+repository+service 
 * del dominio del cliente (usuario). 
 * Verifica que la tabla clientes tenga
 * los campos password
	 - alter table clientes
	 		add column username varchar(20) unique,
			add column password char(40);
	
     - update clientes 
		set username = concat("usr", id),
			password = sha1('1234');
 */

public class App {
	static ClienteService clienteServicio = null;

	public static void main(String[] args) {

		try {
			clienteServicio = new ClienteService();
		} catch (ServiceException e) {
			System.out.println("Error en app: " + e.getMessage());
		}

		if (clienteServicio != null) {
			ejemploLogin();
			ejemploCambioContrasenya();
			ejemploCambioPerfil();
			ejemploRegistro();
			ejemploBusquedaPorNombre();
//			ejemploMostrarTotalFacturacion();
		}

	}

	private static void ejemploLogin() {
		try {
			System.out.println("Login...");
			Cliente cli = clienteServicio.loginCliente("sss", "4321");
			System.out.println(cli);
			System.out.println("-> OK");
		} catch (ServiceException e) {
			System.out.println("Error en app: " + e.getMessage());
		}

	}

	private static void ejemploCambioContrasenya() {
		try {
			System.out.println("Cambio de contraseña...");
			clienteServicio.cambiaContrasenya(2, "abcd");
			System.out.println("Contraseña cambiada");
		} catch (ServiceException e) {
			System.out.println("Error en app: " + e.getMessage());
		}
	}

	private static void ejemploCambioPerfil() {
		try {
			System.out.println("Cambio de datos del perfil...");
			Cliente cli = clienteServicio.getCliente(1);
			cli.setNombre("Mateo Diseño");
			clienteServicio.cambiaPerfil(cli);
			System.out.println("Perfil cambiado correctamente");
		} catch (ServiceException e) {
			System.out.println("Error en app: " + e.getMessage());
		}
	}

	private static void ejemploRegistro() {
		try {
			System.out.println("Registrando cliente...");
			Cliente nuevoCliente = new Cliente("Insecticida", "C/ Mosca, 1", "ssii", "4321");
			clienteServicio.registraCliente(nuevoCliente);
			System.out.println("Cliente registrado satisfactoriamente..." + nuevoCliente);
		} catch (ServiceException e) {
			System.out.println("Error en app: " + e.getMessage());
		}
	}

	private static void ejemploBusquedaPorNombre() {
		try {
			System.out.println("Buscando cliente por nombre...");
			List<Cliente> lista = clienteServicio.buscaPorNombre("m");
			for (Cliente c : lista) {
				System.out.println(c);
			}
		} catch (ServiceException e) {
			System.out.println("Error en app: " + e.getMessage());
		}
	}

}
