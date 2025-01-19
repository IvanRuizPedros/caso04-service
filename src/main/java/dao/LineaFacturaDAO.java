package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import excepciones.DataAccessException;
import modelo.LineaFactura;

public class LineaFacturaDAO {
	final String TABLA = "linfactura";
	final String PK = "linea";
	final String PK2 = "factura";

	final String SQL_SELECT_FACTURA = "SELECT * FROM " + TABLA + " WHERE " + PK2 + " = ?";
	final String SQL_INSERT = "INSERT INTO " + TABLA
			+ "(linea, factura, articulo, cantidad, importe) VALUES (?, ?, ?, ?, ?)";
	final String SQL_DELETE_ALL_FACTURA = "DELETE FROM " + TABLA + " WHERE " + PK2 + " = ?";
	private final PreparedStatement pstSelectFactura;
	private final PreparedStatement pstInsert;
	private final PreparedStatement pstDeleteAllFactura;

	public LineaFacturaDAO() throws DataAccessException {
		Connection con = ConexionBD.getConexion();
		try {
			pstSelectFactura = con.prepareStatement(SQL_SELECT_FACTURA);
			pstInsert = con.prepareStatement(SQL_INSERT);
			pstDeleteAllFactura = con.prepareStatement(SQL_DELETE_ALL_FACTURA);
		} catch (SQLException e) {
			throw new DataAccessException("Error preparando capa dao de lineas factura: " + e.getMessage());
		}
	}

	public void close() throws DataAccessException {
		try {
			pstSelectFactura.close();
			pstInsert.close();
			pstDeleteAllFactura.close();
		} catch (SQLException e) {
			throw new DataAccessException("Error cerrando capa dao de lineas de factura: " + e.getMessage());
		}
	}

	private LineaFactura build(ResultSet rs) throws SQLException {
		return new LineaFactura(rs.getInt("linea"), rs.getInt("factura"), rs.getInt("articulo"), rs.getInt("cantidad"),
				rs.getFloat("importe"));
	}

	public List<LineaFactura> findByFactura(int factura) throws DataAccessException {
		List<LineaFactura> listaLineaFacturas = new ArrayList<LineaFactura>();
		try {
			pstSelectFactura.setInt(1, factura);
			ResultSet rs = pstSelectFactura.executeQuery();
			while (rs.next()) {
				listaLineaFacturas.add(build(rs));
			}
			rs.close();
			return listaLineaFacturas;
		} catch (SQLException e) {
			throw new DataAccessException("Error buscando lineas de factura: " + e.getMessage());
		}
	}

	public void insert(LineaFactura linInsertar) throws DataAccessException {
		try {
			pstInsert.setInt(1, linInsertar.getLinea());
			pstInsert.setInt(2, linInsertar.getFactura());
			pstInsert.setFloat(3, linInsertar.getArticulo());
			pstInsert.setInt(4, linInsertar.getCantidad());
			pstInsert.setFloat(5, linInsertar.getImporte());
			int insertados = pstInsert.executeUpdate();
			if (insertados == 0) {
				throw new DataAccessException("No se ha insertado linea en factura");
			}
		} catch (SQLException e) {
			throw new DataAccessException("Error al intentar insertar linea de factura: " + e.getMessage());
		}
	}

	public void delelteAllLinesFactura(int factura) throws DataAccessException {
		try {
			pstDeleteAllFactura.setInt(1, factura);
			int borradas = pstDeleteAllFactura.executeUpdate();

			if (borradas == 0) {
				throw new DataAccessException("No se han encontrado lineas para borrar");
			}
		} catch (SQLException e) {
			throw new DataAccessException("Error al intentar borrar lineas de factura: " + e.getMessage());
		}
	}

}
