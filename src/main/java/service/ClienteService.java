package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ClienteDAO;
import modelo.Cliente;

/* Ejemplo de capa service del dominio del cliente (usuario). 
 * Requiere modificar la tabla clientes añadiendo
 * los campos username y password
 * alter table clientes
		add column username varchar(20),
		add column password char(40);
	
    update clientes 
		set username = concat("usr", id),
			password = sha1('1234');
 */
public class ClienteService {
	ClienteDAO cliDao;

	public ClienteService() throws SQLException {
		cliDao = new ClienteDAO();

	}

	// Comprobar si ya existe (username) con findByUserName
	// Si existe: abortar (exception)
	// Sino, realiza inserción
	public void registraCliente(Cliente cli) throws Exception {
		
	}

	// Comprobar si existe (username) con findByUserName
	// Si existe, comprueba password.
	// Sino, abortar (exception)
	// También se podría añadir: si contraseña caducada, abortar (excepcion).
	public Cliente loginCliente(String username, String password) throws Exception {
		return null;
	};

	// Eliminar cliente y ¿otros registros de tablas relacionadas?
	public void bajaCliente(Cliente cli) throws Exception {
		
	}

	// Cambio de la contraseña del cliente (id o username)
	public void cambiaContrasenya(int idCliente, String password) throws Exception {
		
	}

	// Se usa para cambiar los datos del perfil del usuario: nombre, direccion,...
	public void cambiaPerfil(Cliente cli) throws Exception {
		
	}

	// Busca clientes por aproximación de nombre
	public List<Cliente> buscaPorNombre(String nombre) throws Exception {
		return new ArrayList<Cliente>();
	}

	// Obtención de un cliente
	public Cliente getCliente(int idCliente) throws Exception {
		return null;
	}

}
