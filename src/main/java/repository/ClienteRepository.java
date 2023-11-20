package repository;

import java.sql.SQLException;
import java.util.List;

import dao.ClienteDAO;
import modelo.Cliente;

public class ClienteRepository {
	ClienteDAO clienteDao;
	
	public ClienteRepository() throws SQLException  {
		clienteDao = new ClienteDAO();
	}
	
	public Cliente get(int id) throws SQLException {
		return clienteDao.find(id);
	}
	
	public Cliente getByUsername(String userName) throws SQLException {
		return clienteDao.findByUsername(userName);
	}
		
	public boolean exists(int id) throws SQLException {
		if (clienteDao.find(id) != null) {
			return true;
		}
		return false;
	}
	
	public boolean existsByUsername(String userName) throws SQLException {
		if (clienteDao.findByUsername(userName) != null) {
			return true;
		}
		return false;
	}
	
	public Cliente save(Cliente cliente) throws SQLException {
		if (this.exists(cliente.getId())) {
			clienteDao.update(cliente);
		}
		else {
			clienteDao.insert(cliente);
		}
		return cliente;
	}
	
	public boolean delete(Cliente cliente) throws SQLException {
		return clienteDao.delete(cliente);
	}
	
	public List<Cliente> getByName(String nombre) throws SQLException {
		Cliente muestra = new Cliente(nombre, null);
		return clienteDao.findByExample(muestra);
	}	
	
	
	public void close() throws SQLException {
		clienteDao.close();
	}
}
