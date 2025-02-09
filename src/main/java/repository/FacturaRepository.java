package repository;

import java.util.List;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.LineaFacturaDAO;
import excepciones.DataAccessException;
import excepciones.RepositoryException;
import modelo.Factura;

public class FacturaRepository {
	
	public FacturaRepository() throws RepositoryException {
		
	}

	// Accede al dao de facturas para buscar la factura 
	public Factura get(int id) throws RepositoryException {
		
	}
	
	// Comprueba que la factura no sea nula.
	// Accede al dao de cliente para recoger la información completa del cliente. 
	public void loadCliente(Factura factura) throws RepositoryException {
		
	}
	
	// Comprueba que la factura no sea nula.
	// Accede al dao de lineas de factura para recoger todas la lineas. 
	public void loadLinFacturas(Factura factura) throws RepositoryException {
		
	}
	
	// Accede al dao de facturas para buscar las facturas de la página indicada 
	public List<Factura> getPage(int pagina) throws RepositoryException {
		
	}
	
	// Comprueba si la factura facilitada existe o no.
	public boolean exists(int idFactura) throws RepositoryException {
		
	}
	
	// Haciendo uso del dao de facturas y, de forma transaccional, 
	// realizará la inserción de la factura y todas sus lineas. 
	public void save(Factura factura) throws RepositoryException {
		
	}
	
	// Haciendo uso del dao de facturas y, de forma transaccional, 
	// realizará la eliminación de la factura y todas sus lineas. 
	public void delete(Factura factura) throws RepositoryException {
		
	}
	
	// Obtiene el total de páginas de facturas existentes. 
	public long getNumPages() throws RepositoryException {
		
	}
	
	public void setPageSize (int pageSize) {
		
	}
	public void close() throws RepositoryException {
		
	}
			
}
