package service;

import java.util.List;

import excepciones.RepositoryException;
import excepciones.ServiceException;
import modelo.Cliente;
import repository.ClienteRepository;
import vista.Utilidades;

/*
 * Logica de negocio relacionada con el cliente (casos de uso del cliente)
 */

public class ClienteService {
	ClienteRepository clienteRepository;

	public ClienteService() throws ServiceException {
		try {
			clienteRepository = new ClienteRepository();
		} catch (RepositoryException e) {
			throw new ServiceException("Error en creación de service: " + e.getMessage());
		}
	}

	// Comprobar si ya existe (username) con findByUserName
	// Si existe: abortar (exception)
	// Sino, realiza inserción
	// Otra lógica que se podría añadir: si contraseña caducada...abortar.
	public void registraCliente(Cliente cli) throws ServiceException {
		try {
			if (!clienteRepository.existsByUsername(cli.getUsername())) {

				cli.setPassword(Utilidades.sha1(cli.getPassword()));
				clienteRepository.save(cli);
			} else {
				throw new ServiceException("El username ya está cogido");
			}
		} catch (RepositoryException e) {
			throw new ServiceException("Error en capa service: " + e.getMessage());
		}
	}

	// Comprobar si existe (username) con findByUserName
	// Si existe, comprueba password.
	// Sino, abortar (exception)
	// También se podría añadir más lógica:
	// ... si contraseña caducada, abortar (excepcion).
	public Cliente loginCliente(String username, String password) throws ServiceException {
		try {
			Cliente cliente = clienteRepository.getByUsername(username);
			if (cliente != null) {
				String pw = Utilidades.sha1(password);
				if (!pw.equals(cliente.getPassword())) {
					throw new ServiceException("Fallo de autenticación");
				} else {
					// Otras comprobaciones...
					// Ejemplo: comprobar fecha de caducidad...
				}
			} else {
				throw new ServiceException("Fallo de autenticación");
			}
			return cliente;
		} catch (RepositoryException e) {
			throw new ServiceException("Error en capa service: " + e.getMessage());
		}
	}

	// Eliminar cliente y, quizás, otras operaciones relacionadas...
	public void bajaCliente(Cliente cli) throws ServiceException {
		try {
			clienteRepository.delete(cli);
		} catch (RepositoryException e) {
			throw new ServiceException("Error en capa service: " + e.getMessage());
		}
	}

	// Cambio de la contraseña del cliente
	public void cambiaContrasenya(int idCliente, String password) throws ServiceException {
		try {
			String passEncriptado = Utilidades.sha1(password);
			clienteRepository.updatePassword(idCliente, passEncriptado);
		} catch (RepositoryException e) {
			throw new ServiceException("Error en capa service: " + e.getMessage());
		}
	}

	// Se usa para cambiar los datos del perfil del usuario: nombre, direccion,...
	public void cambiaPerfil(Cliente cli) throws ServiceException {
		try {
			clienteRepository.save(cli);
		} catch (RepositoryException e) {
			throw new ServiceException("Error en capa service: " + e.getMessage());
		}
	}

	// Busca clientes por aproximación de nombre
	public List<Cliente> buscaPorNombre(String nombre) throws ServiceException {
		try {
			List<Cliente> lista = clienteRepository.getByName(nombre);
			if (lista.size() == 0) {
				throw new ServiceException("No se han encontrado clientes con ese nombre");
			}
			return lista;
		} catch (RepositoryException e) {
			throw new ServiceException("Error en capa service: " + e.getMessage());
		}
	}

	// Obtención de un cliente
	public Cliente getCliente(int idCliente) throws ServiceException {
		try {
			Cliente cli = clienteRepository.get(idCliente);
			if (cli == null) {
				throw new ServiceException("Ha habido un problema obteniendo cliente. Probablemente no existe");
			}
			return cli;
		} catch (RepositoryException e) {
			throw new ServiceException("Error en capa service: " + e.getMessage());
		}
	}

	// Otras necesidades de funcionalidad:
	// - Calcular total facturado por cliente.
	// - Obtener  historial de compras (listado de sus facturas)
	// ...
	// Piensa qué cambios se necesitarían en todas las capas para poder
	// implementar estas funciones...

}
