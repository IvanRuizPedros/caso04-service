package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Cliente;
import repository.ClienteRepository;

// Logica de negocio relacionada con el cliente (casos de uso del cliente)

public class ClienteService {
	ClienteRepository clienteRepository;

	public ClienteService() throws SQLException {
		 clienteRepository = new ClienteRepository();

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

	// Cambio de la contraseña del cliente
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
	
	// Otras necesidades de funcionalidad:
	// Calcular total facturado por cliente.
	
	// Obtener listado de sus facturas. 
	
	// ...
	
	

}
