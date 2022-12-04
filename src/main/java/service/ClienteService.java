package service;

import java.sql.SQLException;
import java.util.List;

import dao.ClienteDAO;
import modelo.Cliente;
import vista.Utilidades;

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
	// Sino, realiza inserción
	public void registraCliente(Cliente cli) throws Exception {
		if (cliDao.findByUserName(cli.getUsername()) == null) {
			cli.setPasswd(Utilidades.sha1(cli.getPasswd()));
			if (!cliDao.insert(cli)) {
				throw new Exception("Error al registrar usuario");
			}
		} else {
			throw new Exception("El nombre de usuario ya está cogido");
		}

	}

	// Comprobar si existe (username) con findByUserName
	// Si existe, comprueba password.
	// Sino, abortar (exception)
	// También se podría añadir: si contraseña caducada, abortar (excepcion).
	public Cliente loginCliente(String username, String password) throws Exception {
		Cliente cli = cliDao.findByUserName(username);
		if (cli != null) {
			String pw = Utilidades.sha1(password);
			if (!pw.equals(cli.getPasswd())) {
				throw new Exception("Fallo en la autenticación");
			} else {
				// if (cli.getFechaCaducidad() ...)
				// throw new Exception("Contraseña caducada. Debes cambiarla para poder
				// acceder.");
				// }
			}
		} else {
			throw new Exception("Fallo en la autenticación");
		}
		return cli;
	};

	// Eliminar cliente y ¿otros registros de tablas relacionadas?
	public void bajaCliente(Cliente cli) throws Exception {
		if (cliDao.delete(cli) == false) {
			throw new Exception("Imposible dar de baja el cliente");
		}

	}

	// Cambio de la contraseña del cliente (id o username)
	public void cambiaContrasenya(int idCliente, String password) throws Exception {
		Cliente cliente = cliDao.find(idCliente);
		if (cliente != null) {
			cliente.setPasswd(Utilidades.sha1(password));
			// cliente.setFechaCaducidad(LocalDate.now());  // actualizamos la fecha caducidad contraseña
			if (cliDao.update(cliente) == false) {
				throw new Exception("Ha habido un problema cambiando la contraseña");
			}
		} else {
			throw new Exception("El cliente especificado no existe");
		}
	}

	// Se usa para cambiar los datos del perfil del usuario: nombre, direccion,...
	public void cambiaPerfil(Cliente cli) throws Exception {
		if (cliDao.update(cli) == false) {
			throw new Exception("Ha habido un problema actualizando el perfil");
		}
	}

	// Busca clientes por aproximación de nombre
	public List<Cliente> buscaPorNombre(String nombre) throws Exception {
		Cliente patron = new Cliente(nombre, null);
		List<Cliente> listaResultado = cliDao.findByExample(patron);
		if (listaResultado.size() == 0) {
			throw new Exception("No se han encontrado clientes con ese nombre");
		} else {
			return listaResultado;
		}
	}

	// Obtención de un cliente
	public Cliente getCliente(int idCliente) throws Exception {
		Cliente cli = cliDao.find(idCliente);
		if (cli != null) {
			return cli;
		} else {
			throw new Exception("Ha habido un problema obteniendo cliente. Probablemente no existe");
		}
	}

}
