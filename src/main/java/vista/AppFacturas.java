package vista;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import excepciones.ServiceException;
import modelo.Articulo;
import modelo.Cliente;
import modelo.Factura;
import modelo.Linfactura;
import modelo.Vendedor;
import service.ClienteService;
import service.FacturaService;

public class AppFacturas {
	static FacturaService facturaServicio = null;
	static ClienteService clienteServicio = null;

	public static void main(String[] args) {
		casosUsoFacturas();
	}

	private static void casosUsoFacturas() {
		try {
			facturaServicio = new FacturaService();
			clienteServicio = new ClienteService();
		} catch (ServiceException e) {
			System.out.println("Error: " + e.getMessage());
		}

		if (facturaServicio != null && clienteServicio != null) {
			// casos
//			ejemploObtenerFacturas(); 	// Lista todas las facturas página a página.
//			ejemploObtenerFactura(); 	// Obtiene todos los datos de una factura a partir de un número.

//			ejemploNuevaFactura(); 		// Nueva factura (cabecera y lineas). Transaccional.
//			ejemploEliminaFactura(); 	// Elimina factura y sus lineas. Transaccional.
//			
//			ejemploTotalFacturadoCliente();		// Calcula facturación de un cliente

		}
	}
	
	private static void ejemploObtenerFacturas() {
		try {
			List<Factura> lista;
			long totalPaginas = facturaServicio.obtenerNumPaginas();
			System.out.println("Total paginas: " + totalPaginas);
			for (int i = 1; i <= totalPaginas; i++) {
				System.out.println("***** Página " + i + " *****");
				lista = facturaServicio.obtenerFacturas(i);				
				for (Factura factura : lista) {
					facturaServicio.cargarDatosFactura(factura);					
					System.out.print(factura);
					System.out.println("  ->" + factura.getCliente());
//					for (Linfactura lin : factura.getLinfacturas()) {
//						System.out.println("\t" + lin);
//					}
				}
			}
		} catch (ServiceException e) {
			e.getMessage();
		}
	}
	
	private static void ejemploObtenerFactura() {
		Factura factura;
		try {
			factura = facturaServicio.obtenerFactura(4);
			facturaServicio.cargarDatosFactura(factura);
			System.out.println(factura);
			for (Linfactura lin : factura.getLinfacturas()) {
				System.out.println("\t" + lin);
			}
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}
	

	private static void ejemploNuevaFactura() {
		try {
			System.out.println("Creando nueva factura");
			Cliente cliente = clienteServicio.getCliente(1);
			
			Factura factura = new Factura(LocalDate.now(), cliente, new Vendedor(1), "tarjeta");
			Set<Linfactura> lineas = new HashSet<Linfactura>();
			lineas.add(new Linfactura(new Articulo(1), 10));
			lineas.add(new Linfactura(new Articulo(7), 20));
			lineas.add(new Linfactura(new Articulo(2), 12));
			factura.setLinfacturas(lineas);
			facturaServicio.guardaFactura(factura);
			System.out.println("Factura insertada correctamente: " + factura);
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void ejemploEliminaFactura() {
		try {
			System.out.println("Eliminando factura...");
			Factura factura = facturaServicio.obtenerFactura(5003);
			facturaServicio.eliminaFactura(factura);
			System.out.println("Factura eliminada correctamente");
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}	
	
	private static void ejemploTotalFacturadoCliente() {
		try {
			System.out.println("Total facturación del cliente...");			
			float total = clienteServicio.getFacturacionCliente(1);
			System.out.println(" --> " + total + " euros");
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
	}

}
