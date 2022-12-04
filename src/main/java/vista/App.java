package vista;

import java.util.List;

import modelo.Cliente;
import service.ClienteService;

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
		try {
			Cliente cli = clienteServicio.loginCliente("usr12", "1234");
			System.out.println("Autenticado " + cli);
		} catch (Exception e) {
			System.out.println("Error en autenticación: " + e.getMessage());
		}

	}

	private static void ejemploCambioContrasenya() {
		try {
			clienteServicio.cambiaContrasenya(12, "1234");
			System.out.println("Contraseña cambiada con éxito (cliente 12)");
		} catch (Exception e) {
			System.out.println("Error cambiando contraseña: " + e.getMessage());
		}

	}

	private static void ejemploCambioPerfil() {
		try {
			Cliente cli = clienteServicio.getCliente(12);
			cli.setNombre("Mateo");
			clienteServicio.cambiaPerfil(cli);
			System.out.println("Perfil cambiado satisfactoriamente: " + cli);
		} catch (Exception e) {
			System.out.println("Error cambiando perfil: " + e.getMessage());
		}

	}

	private static void ejemploRegistro() {
		try {
			Cliente nuevoCliente = new Cliente("Aa Aaaa", "C/ Aa", "usr_b", Utilidades.sha1("aaaa"));
			clienteServicio.registraCliente(nuevoCliente);
			System.out.println("Registro nuevo usuario OK: " + nuevoCliente);
		} catch (Exception e) {
			System.out.println("Error registrando usuario: " + e.getMessage());
		}

	}

	private static void ejemploBusquedaPorNombre() {
		try {
			System.out.println("Busqueda por nombre 'm'");
			List<Cliente> listaClientes = clienteServicio.buscaPorNombre("m");
			for (Cliente c : listaClientes) {
				System.out.println(c);
			}
		} catch (Exception e) {
			System.out.println("Error en búsqueda: " + e.getMessage());
		}

	}

}
