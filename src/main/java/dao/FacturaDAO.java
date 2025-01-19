package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import excepciones.DataAccessException;
import modelo.Cliente;
import modelo.Factura;

public class FacturaDAO implements GenericDAO<Factura>{
	private static final String TABLA = "factura";
    private static final String PK = "id";
    private static final int DEFAULT_PAGE_SIZE = 10;

    private int pageSize = DEFAULT_PAGE_SIZE;

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLA + " WHERE " + PK + " = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLA;
    private static final String SQL_INSERT = "INSERT INTO " + TABLA + " (fecha, cliente_id, vendedor, formaPago) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE " + TABLA + " SET fecha = ?, cliente_id = ?, vendedor = ?, formaPago = ? WHERE " + PK + " = ?";
    private static final String SQL_DELETE = "DELETE FROM " + TABLA + " WHERE " + PK + " = ?";
    private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLA;
    
    private final PreparedStatement pstSelectById;
    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstDelete;
    private final PreparedStatement pstCount;
    
	public FacturaDAO() throws DataAccessException {
		Connection con = ConexionBD.getConexion();
		try {
			pstSelectAll = con.prepareStatement(SQL_SELECT_ALL);
            pstSelectById = con.prepareStatement(SQL_SELECT_BY_ID);
            pstInsert = con.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            pstUpdate = con.prepareStatement(SQL_UPDATE);
            pstDelete = con.prepareStatement(SQL_DELETE);
            pstCount = con.prepareStatement(SQL_COUNT);
        } catch (SQLException e) {
            throw new DataAccessException("Error preparando el DAO de facturas: " + e.getMessage());
        }
	}
	
	public void close() throws DataAccessException {
        try {
        	pstSelectAll.close();
            pstSelectById.close();
            pstInsert.close();
            pstUpdate.close();
            pstDelete.close();
            pstCount.close();
        } catch (SQLException e) {
            throw new DataAccessException("Error cerrando el DAO de facturas: " + e.getMessage());
        }
    }
	
	private Factura build(ResultSet rs) throws SQLException {
		ClienteDAO clienteDAO = new ClienteDAO();
	    Cliente cliente = clienteDAO.find(rs.getInt("cliente_id"));
		
        return new Factura(
            rs.getInt("id"),
            rs.getDate("fecha").toLocalDate(),
            cliente,
            rs.getInt("vendedor"),
            rs.getString("formaPago")
        );
    }
	
	public Factura find(int id) throws DataAccessException {
		Factura factura = null;
		try {
			pstSelectById.setInt(1, id);
			ResultSet rs = pstSelectById.executeQuery();
			if (rs.next()) {
				factura = build(rs);
			}
			rs.close();
		} catch (SQLException e) {
            throw new DataAccessException("Error buscando factura por ID: " + e.getMessage());
        }
		
		return factura;
	}
	
	public List<Factura> findAll(int pagina) throws DataAccessException {
		return null;
	}
	
	public List<Factura> findAll() throws DataAccessException {
		List<Factura> facturas = new ArrayList<>();
		try {
			ResultSet rs = pstSelectAll.executeQuery();
			if (rs.next()) {
				facturas.add(build(rs));
			}
			rs.close();
		} catch (SQLException e) {
            throw new DataAccessException("Error obteniendo todas las facturas: " + e.getMessage());
        }
		
		return facturas;
	}
	
	public List<Factura> findByCliente(int cli, int pagina) throws DataAccessException {
		return null;
	}
	
	public List<Factura> findByCliente(int cli) throws DataAccessException {
		return null;
	}
	
	public void insert(Factura facInsertar) throws DataAccessException {
		try {
			pstInsert.setDate(1, Date.valueOf(facInsertar.getFecha()));
			pstInsert.setInt(2, facInsertar.getCliente().getId());
            pstInsert.setInt(3, facInsertar.getVendedor());
            pstInsert.setString(4, facInsertar.getFormaPago());
            int filasInsertadas = pstInsert.executeUpdate();
            
            if (filasInsertadas == 1) {
            	try (ResultSet rs = pstInsert.executeQuery()) {
            		if (rs.next()) {
            			facInsertar.setId(rs.getInt(1));
            		}
            	}
            } else {
                throw new DataAccessException("No se pudo insertar la factura.");
            }
		} catch (SQLException e) {
            throw new DataAccessException("Error insertando factura: " + e.getMessage());
        }
	}
	
	public void update(Factura facActualizar) throws DataAccessException {
		try {
            pstUpdate.setDate(1, Date.valueOf(facActualizar.getFecha()));
            pstUpdate.setInt(2, facActualizar.getCliente().getId());
            pstUpdate.setInt(3, facActualizar.getVendedor());
            pstUpdate.setString(4, facActualizar.getFormaPago());
            pstUpdate.setInt(5, facActualizar.getId());
            int filasActualizadas = pstUpdate.executeUpdate();
            if (filasActualizadas == 0) {
                throw new DataAccessException("No se encontró la factura para actualizar.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error actualizando factura: " + e.getMessage());
        }
	}
	
	public void delete(int id) throws DataAccessException {
		try {
            pstDelete.setInt(1, id);
            int filasBorradas = pstDelete.executeUpdate();
            if (filasBorradas == 0) {
                throw new DataAccessException("No se encontró la factura para eliminar.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error eliminando factura: " + e.getMessage());
        }
	}
	
	public void delete(Factura facEliminar) throws DataAccessException {
		delete(facEliminar.getId());
	}
	
	public long size() throws DataAccessException {
		try (ResultSet rs = pstCount.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new DataAccessException("Error obteniendo el tamaño de la tabla facturas: " + e.getMessage());
        }
	}
	
	public List<Factura> findByExample(Factura muestra) throws DataAccessException {
		return null;
	}
	
	public double getImporteTotal(int factura) throws DataAccessException {
		return 0;
	}
	
	public int getPageSize() {
		return 0;
	}
	
	public void setPageSize(int pageSize) {
		
	}
	
	public long getNumPages() throws DataAccessException {
		return 0;
	}
	
	public float getTotalFacturadoByCliente(int clienteId) throws DataAccessException {
		return 0;
	}
}
