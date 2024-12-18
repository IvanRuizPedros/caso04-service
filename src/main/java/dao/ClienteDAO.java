package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import excepciones.DataAccessException;
import modelo.Cliente;

public class ClienteDAO implements GenericDAO<Cliente> {

	final static String TABLA = "cliente";
	final static String PK = "id";

	final String SQLSELECTALL = "SELECT * FROM " + TABLA;
	final String SQLSELECTCOUNT = "SELECT count(*) FROM " + TABLA;
	final String SQLSELECTPK = "SELECT * FROM " + TABLA + " WHERE " + PK + " = ?";
	final String SQL_SELECT_USERNAME = "SELECT * FROM " + TABLA + " WHERE username = ?";
	final String SQL_SELECT_EXAMPLE = "SELECT * FROM " + TABLA + " where nombre like ? and direccion like ?";
	final String SQLINSERT = "INSERT INTO " + TABLA + " (nombre, direccion, username, password) VALUES (?, ?, ?, ?)";
	final String SQLUPDATE = "UPDATE " + TABLA + " SET nombre = ?, direccion = ?, username = ?, password = ? WHERE "
			+ PK + " = ?";
	final String SQLDELETE = "DELETE FROM " + TABLA + " WHERE " + PK + " = ?";
	private final PreparedStatement pstSelectPK;
	private final PreparedStatement pstSelectAll;
	private final PreparedStatement pstSelectCount;
	private final PreparedStatement pstSelectUsername;
	private final PreparedStatement pstSelectExample;
	private final PreparedStatement pstInsert;
	private final PreparedStatement pstUpdate;
	private final PreparedStatement pstDelete;

	public ClienteDAO() throws DataAccessException {
		Connection con = ConexionBD.getConexion();
		try {
			pstSelectPK = con.prepareStatement(SQLSELECTPK);
			pstSelectUsername = con.prepareStatement(SQL_SELECT_USERNAME);
			pstSelectAll = con.prepareStatement(SQLSELECTALL);
			pstSelectExample = con.prepareStatement(SQL_SELECT_EXAMPLE);
			pstSelectCount = con.prepareStatement(SQLSELECTCOUNT);
			pstInsert = con.prepareStatement(SQLINSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			pstUpdate = con.prepareStatement(SQLUPDATE);
			pstDelete = con.prepareStatement(SQLDELETE);
		} catch (SQLException e) {
			throw new DataAccessException("Error preparando capa de acceso a datos de clientes: " + e.getMessage());
		}

	}

	public void close() throws DataAccessException {
		try {
			pstSelectPK.close();
			pstSelectUsername.close();
			pstSelectAll.close();
			pstSelectCount.close();
			pstSelectExample.close();
			pstInsert.close();
			pstUpdate.close();
			pstDelete.close();
		} catch (SQLException e) {
			throw new DataAccessException("Error cerrando capa de acceso a datos de clientes: " + e.getMessage());
		}
	}

	private Cliente build(ResultSet rs) throws SQLException {
		return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("direccion"), rs.getString("username"),
				rs.getString("password"));
	}

	private void setPrepared(PreparedStatement pst, Cliente registro) throws SQLException {
		pst.setString(1, registro.getNombre());
		pst.setString(2, registro.getDireccion());
		pst.setString(3, registro.getUsername());
		pst.setString(4, registro.getPassword());
	}

	private void setGeneratedKey(Cliente registro) throws SQLException, DataAccessException {
		try (ResultSet rsClave = pstInsert.getGeneratedKeys()) {
			if (rsClave.next()) {
				int idAsignada = rsClave.getInt(1);
				registro.setId(idAsignada);
			} else {
				throw new DataAccessException("Error al intentar insertar cliente: no se generó id");
			}
		}
	}

	@Override
	public Cliente find(int id) throws DataAccessException {
		Cliente c = null;
		try {
			pstSelectPK.setInt(1, id);
			ResultSet rs = pstSelectPK.executeQuery();
			if (rs.next()) {
				c = build(rs);
			}
			rs.close();
			return c;
		} catch (SQLException e) {
			throw new DataAccessException("Error buscando cliente por id: " + e.getMessage());
		}
	}

	public Cliente findByUsername(String userName) throws DataAccessException {
		Cliente c = null;
		try {
			pstSelectUsername.setString(1, userName);
			ResultSet rs = pstSelectUsername.executeQuery();
			if (rs.next()) {
				c = build(rs);
			}
			rs.close();
			return c;
		} catch (SQLException e) {
			throw new DataAccessException("Error buscando cliente por id: " + e.getMessage());
		}
	}

	@Override
	public List<Cliente> findAll() throws DataAccessException {
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		try {
			ResultSet rs;
			rs = pstSelectAll.executeQuery();
			while (rs.next()) {
				listaClientes.add(build(rs));
			}
			rs.close();
			return listaClientes;
		} catch (SQLException e) {
			throw new DataAccessException("Error obteniendo todos los clientes: " + e.getMessage());
		}

	}

	@Override
	public void insert(Cliente cliInsertar) throws DataAccessException {
		try {
			setPrepared(pstInsert, cliInsertar);
			int insertados = pstInsert.executeUpdate();
			if (insertados == 1) {
				setGeneratedKey(cliInsertar);
			} else {
				throw new DataAccessException("No se ha insertado cliente");
			}
		} catch (SQLException e) {
			throw new DataAccessException("Error al intentar insertar cliente: " + e.getMessage());
		}
	}

	@Override
	public void update(Cliente cliActualizar) throws DataAccessException {
		try {
			setPrepared(pstUpdate, cliActualizar);
			pstUpdate.setInt(5, cliActualizar.getId());
			int actualizados = pstUpdate.executeUpdate();
			if (actualizados == 0) {
				throw new DataAccessException("No se ha encontrado cliente para actualizar");
			}
		} catch (SQLException e) {
			throw new DataAccessException("Error al intentar modificar cliente: " + e.getMessage());
		}
	}

	@Override
	public void delete(int id) throws DataAccessException {
		try {
			pstDelete.setInt(1, id);
			int borrados = pstDelete.executeUpdate();
			if (borrados == 0) {
				throw new DataAccessException("No se ha encontrado cliente para eliminar");
			}
		} catch (SQLException e) {
			throw new DataAccessException("Error al intentar eliminar cliente: " + e.getMessage());
		}

	}

	@Override
	public void delete(Cliente cliEliminar) throws DataAccessException {
		this.delete(cliEliminar.getId());
	}

	@Override
	public long size() throws DataAccessException {
		try {
			ResultSet rs = pstSelectCount.executeQuery();
			rs.next();
			return rs.getLong(1);
		} catch (SQLException e) {
			throw new DataAccessException("Error al intentar leer total clientes: " + e.getMessage());
		}

	}

	@Override
	public List<Cliente> findByExample(Cliente muestra) throws DataAccessException {
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		String filtroNombre = muestra.getNombre() == null ? "%" : "%" + muestra.getNombre() + "%";
		String filtroDireccion = muestra.getDireccion() == null ? "%" : "%" + muestra.getDireccion() + "%";

		try {
			pstSelectExample.setString(1, filtroNombre);
			pstSelectExample.setString(2, filtroDireccion);
			ResultSet rs = pstSelectExample.executeQuery();
			while (rs.next()) {
				listaClientes.add(build(rs));
			}
			rs.close();
			return listaClientes;

		} catch (SQLException e) {
			throw new DataAccessException("Error obteniendo todos los clientes: " + e.getMessage());
		}
	}

}
