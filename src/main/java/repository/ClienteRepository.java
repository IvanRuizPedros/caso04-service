package repository;

import java.sql.SQLException;
import java.util.List;

import dao.ClienteDAO;
import excepciones.DataAccessException;
import excepciones.RepositoryException;
import modelo.Cliente;

/*
 * El Repository debe centrarse en trabajar con el modelo de dominio. 
 * Ofrecerá métodos que reflejen operaciones más cercanas a la lógica de negocio
 */
public class ClienteRepository {
	ClienteDAO clienteDao;

	public ClienteRepository() throws RepositoryException {
		try {
			clienteDao = new ClienteDAO();
		} catch (DataAccessException e) {
			throw new RepositoryException("Error preparando repository de clientes: " + e.getMessage());
		}
	}

	public Cliente get(int id) throws RepositoryException {
		try {
			return clienteDao.find(id);
		} catch (DataAccessException e) {
			throw new RepositoryException("Error en repository clientes: " + e.getMessage());
		}
	}

	public List<Cliente> getAll() throws RepositoryException {
		try {
			return clienteDao.findAll();
		} catch (DataAccessException e) {
			throw new RepositoryException("Error en repository clientes: " + e.getMessage());
		}
	}

	public Cliente getByUsername(String userName) throws RepositoryException {
		try {
			return clienteDao.findByUsername(userName);
		} catch (DataAccessException e) {
			throw new RepositoryException("Error en repository de clientes: " + e.getMessage());
		}
	}

	public boolean exists(int id) throws RepositoryException {
		try {
			return (clienteDao.find(id) != null);
		} catch (DataAccessException e) {
			throw new RepositoryException("Error en repository de clientes: " + e.getMessage());
		}

	}

	public boolean existsByUsername(String userName) throws RepositoryException {
		try {
			return (clienteDao.findByUsername(userName) != null);
		} catch (DataAccessException e) {
			throw new RepositoryException("Error en repository clientes: " + e.getMessage());
		}

	}

	public void save(Cliente cliente) throws RepositoryException {
		try {
			if (cliente.getId() != 0) { // if (this.exists(cliente.getId())) {
				clienteDao.update(cliente);
			} else {
				clienteDao.insert(cliente);
			}
		} catch (DataAccessException e) {
			throw new RepositoryException("Error en repository clientes: " + e.getMessage());
		}

	}

	public void delete(Cliente cliente) throws RepositoryException {
		try {
			clienteDao.delete(cliente);
		} catch (DataAccessException e) {
			throw new RepositoryException("Error en repository clientes: " + e.getMessage());
		}
	}

	public List<Cliente> getByName(String nombre) throws RepositoryException {
		try {
			Cliente muestra = new Cliente(nombre, null);
			return clienteDao.findByExample(muestra);
		} catch (DataAccessException e) {
			throw new RepositoryException("Error en repository clientes: " + e.getMessage());
		}
	}

	public void updatePassword(int id, String newPassword) throws RepositoryException {
		try {
			Cliente c = clienteDao.find(id);
			if (c == null) {
				throw new RepositoryException("Cliente no existe: ");
			}
			c.setPassword(newPassword);
			clienteDao.update(c);
		} catch (DataAccessException e) {
			throw new RepositoryException("Error en repository clientes: " + e.getMessage());
		}
	}

	public void close() throws SQLException {
		try {
			clienteDao.close();
		} catch (DataAccessException e) {
			throw new RepositoryException("Error en repository clientes: " + e.getMessage());
		}
	}
}
