package service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.SQLException;

import dao.ClienteDAO;
import modelo.Cliente;

/* Ejemplo de capa service del dominio del cliente. 
 * Requiere modificar la tabla clientes añadiendo
 * los campos username y password
 */
public class ClienteService {
	ClienteDAO cliDao;

	public ClienteService() throws SQLException {
		cliDao = new ClienteDAO();
	}

	// Comprobar si ya existe (username) con findByUserName
	// Si existe: abortar (exception)
	// Sino, realiza inserción encriptando contraseña con sha1
	public void registraCliente(Cliente cli) throws Exception {

	}

	// Comprobar si existe (username) con findByUserName
	// Si existe, comprueba password.
	// Sino, abortar (exception)
	// También se podría añadir: si contraseña caducada, abortar (excepcion).
	public void loginCliente(String username, String password) {

	};

	// Eliminar cliente y ¿otros registros de tablas relacionadas?
	public void bajaCliente(Cliente cli) {

	}

	// Cambio de la contraseña del cliente
	public void cambiaContrasenya(String password) {

	}

	// Se usa para cambiar los datos del perfil del usuario: nombre, direccion.
	public void cambiaPerfil(Cliente cli) {

	}

	// Busca clientes por aproximación de nombre
	public void buscaPorNombre(String nombre) {

	}

	private String sha1(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(password.getBytes("utf8"));
			return String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
