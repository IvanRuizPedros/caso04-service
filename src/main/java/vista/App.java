package vista;

import java.sql.SQLException;

import service.ClienteService;

public class App {

	public static void main(String[] args) {
		try {
			ClienteService clienteServicio = new ClienteService();
			// ...

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

}
