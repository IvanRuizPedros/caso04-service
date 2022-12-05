package vista;

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
