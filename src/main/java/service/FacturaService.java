package service;

import java.util.List;

import excepciones.RepositoryException;
import excepciones.ServiceException;
import modelo.Factura;
import repository.FacturaRepository;
import vista.Utilidades;

public class FacturaService {
	FacturaRepository facturaRepository;
	
	public FacturaService() throws ServiceException {
		try {
			facturaRepository = new FacturaRepository();
		} catch (RepositoryException e) {
			throw new ServiceException("Error en creación de service: " + e.getMessage());
		}
	}
	
	// Permite obtener una factura a partir de su id
	public Factura obtenerFactura(int id) throws ServiceException {
		try {
			return facturaRepository.get(id);
		} catch (RepositoryException e) {
			throw new ServiceException("Error en capa service: " + e.getMessage());
		}
	}
	
	// Permite cargar los datos completos de la factura: datos del cliente
	// y sus lineas.
	public void cargarDatosFactura(Factura factura) throws RepositoryException {
		try {
			facturaRepository.loadCliente(factura);
			facturaRepository.loadLinFacturas(factura);
		} catch (RepositoryException e) {
			throw new ServiceException("Error en capa service: " + e.getMessage());
		}
	}
	
	// Permite obtener el número de páginas disponibles
	public long obtenerNumPaginas() throws ServiceException {
		try {
			return facturaRepository.getNumPages();
		} catch (RepositoryException e) {
			throw new ServiceException("Error en capa service: " + e.getMessage());
		}
	}
	
	// Permite obtener las facturas de la página indicada
	public List<Factura> obtenerFacturas(int pagina) throws ServiceException {
		try {
			return facturaRepository.getPage(pagina);
		} catch (RepositoryException e) {
			throw new ServiceException("Error en capa service: " + e.getMessage());
		}
	}
	
	// Inserta o actualiza una factura.
	public void guardaFactura(Factura factura) throws ServiceException {
		try {
			facturaRepository.save(factura);
		} catch (RepositoryException e) {
			throw new ServiceException("Error en capa service: " + e.getMessage());
		}
	}
	
	// Permite eliminar la factura que se le pase como parámetro
	public void eliminaFactura(Factura factura) throws ServiceException {
		try {
			facturaRepository.delete(factura);
		} catch (RepositoryException e) {
			throw new ServiceException("Error en capa service: " + e.getMessage());
		}
	}
}
