package repository;

import java.util.List;

import dao.ClienteDAO;
import dao.ConexionBD;
import dao.FacturaDAO;
import dao.LineaFacturaDAO;
import excepciones.DataAccessException;
import excepciones.RepositoryException;
import modelo.Factura;

public class FacturaRepository {
	FacturaDAO facturaDao;
	ClienteDAO clienteDao;
	LineaFacturaDAO lineaFacturaDao;
	
	public FacturaRepository() throws RepositoryException {
		try {
            facturaDao = new FacturaDAO();
			facturaDao = new FacturaDAO();
			lineaFacturaDao = new LineaFacturaDAO();
        } catch (DataAccessException e) {
            throw new RepositoryException("Error preparando repository de facturas: " + e.getMessage());
        }
	}

	// Accede al dao de facturas para buscar la factura 
	public Factura get(int id) throws RepositoryException {
		try {
			return facturaDao.find(id);
		} catch (DataAccessException e) {
			throw new RepositoryException("Error en repository facturas: " + e.getMessage());
		}
	}
	
	// Comprueba que la factura no sea nula.
	// Accede al dao de cliente para recoger la información completa del cliente. 
	public void loadCliente(Factura factura) throws RepositoryException {
		if (factura == null) {
            throw new RepositoryException("No se encuentra la factura.");
        }
		try {
			factura.setCliente(clienteDao.find(factura.getCliente().getId()));
        } catch (DataAccessException e) {
            throw new RepositoryException("Error en repository facturas: " + e.getMessage());
        }
	}
	
	// Comprueba que la factura no sea nula.
	// Accede al dao de lineas de factura para recoger todas la lineas. 
	public void loadLinFacturas(Factura factura) throws RepositoryException {
		if (factura == null) {
            throw new RepositoryException("No se encuentra la factura.");
        }
		try {
			factura.setLineas(lineaFacturaDao.findByFactura(factura.getId()));
        } catch (DataAccessException e) {
            throw new RepositoryException("Error en repository facturas: " + e.getMessage());
        }
	}
	
	// Accede al dao de facturas para buscar las facturas de la página indicada 
	public List<Factura> getPage(int pagina) throws RepositoryException {
		try {
			return facturaDao.findAll(pagina);
		} catch (DataAccessException e) {
            throw new RepositoryException("Error en repository facturas: " + e.getMessage());
        }
	}
	
	// Comprueba si la factura facilitada existe o no.
	public boolean exists(int idFactura) throws RepositoryException {
		try {
			return facturaDao.find(idFactura) != null;
		} catch (DataAccessException e) {
            throw new RepositoryException("Error en repository facturas: " + e.getMessage());
        }
	}
	
	// Haciendo uso del dao de facturas y, de forma transaccional, 
	// realizará la inserción de la factura y todas sus lineas. 
	public void save(Factura factura) throws RepositoryException {
		try {
			if (factura.getId() != 0) { // if (this.exists(factura.getId())) {
				facturaDao.update(factura);
			} else {
				facturaDao.insert(factura);
			}
		} catch (DataAccessException e) {
			throw new RepositoryException("Error en repository clientes: " + e.getMessage());
		}
	}
	
	// Haciendo uso del dao de facturas y, de forma transaccional, 
	// realizará la eliminación de la factura y todas sus lineas. 
	public void delete(Factura factura) throws RepositoryException {
		try {
			facturaDao.delete(factura);
		} catch (DataAccessException e) {
			throw new RepositoryException("Error en repository clientes: " + e.getMessage());
		}
	}
	
	// Obtiene el total de páginas de facturas existentes. 
	public long getNumPages() throws RepositoryException {
		try {
			return facturaDao.getNumPages();
		} catch (DataAccessException e) {
			throw new RepositoryException("Error en repository facturas: " + e.getMessage());
		}
	}
	
	public void setPageSize (int pageSize) {
		facturaDao.setPageSize(pageSize);
	}
	public void close() throws RepositoryException {
		try {
			facturaDao.close();
			clienteDao.close();
			lineaFacturaDao.close();
		} catch (DataAccessException e) {
			throw new RepositoryException("Error en repository facturas: " + e.getMessage());
		}	
	}
			
}
