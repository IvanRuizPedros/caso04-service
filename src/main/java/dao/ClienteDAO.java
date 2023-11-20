package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Cliente;

/**
 *
 * @author sergio
 */
public class ClienteDAO implements GenericDAO<Cliente> {
	final String TABLA = "clientes";
	final String PK = "id";

	final String SQL_SELECT_ALL = "SELECT * FROM " + TABLA;
	final String SQL_SELECT_COUNT = "SELECT count(*) FROM " + TABLA;
	final String SQL_SELECT_PK = "SELECT * FROM " + TABLA + " WHERE " + PK + " = ?";
	final String SQL_SELECT_USERNAME = "SELECT * FROM " + TABLA + " WHERE username = ?";
	final String SQL_SELECT_EXAMPLE = "SELECT * FROM " + TABLA + " where nombre like ? and direccion like ? and username like ?";
	final String SQL_INSERT = "INSERT INTO " + TABLA + " (nombre, direccion, username, password) VALUES (?, ?, ?, ?)";
	final String SQL_UPDATE = "UPDATE " + TABLA + " SET nombre = ?, direccion = ?, password = ? WHERE " + PK + " = ?";
	final String SQL_DELETE = "DELETE FROM " + TABLA + " WHERE " + PK + " = ?";
	private final PreparedStatement pstSelectPK;
	private final PreparedStatement pstSelectUsername;
	private final PreparedStatement pstSelectAll;
	private final PreparedStatement pstSelectCount;
	private final PreparedStatement pstSelectExample;
	private final PreparedStatement pstInsert;
	private final PreparedStatement pstUpdate;
	private final PreparedStatement pstDelete;

	public ClienteDAO() throws SQLException {
		Connection con = ConexionBD.getConexion();
		pstSelectPK = con.prepareStatement(SQL_SELECT_PK);
		pstSelectUsername = con.prepareStatement(SQL_SELECT_USERNAME);
		pstSelectCount = con.prepareStatement(SQL_SELECT_COUNT);
		pstSelectAll = con.prepareStatement(SQL_SELECT_ALL);
		pstSelectExample = con.prepareStatement(SQL_SELECT_EXAMPLE);
		pstInsert = con.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
		pstUpdate = con.prepareStatement(SQL_UPDATE);
		pstDelete = con.prepareStatement(SQL_DELETE);		
	}

	public void close() throws SQLException {
		pstSelectPK.close();
		pstSelectUsername.close();
		pstSelectAll.close();
		pstSelectCount.close();
		pstSelectExample.close();
		pstInsert.close();
		pstUpdate.close();
		pstDelete.close();
	}

	private Cliente build(ResultSet rs) throws SQLException {
		return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("direccion"), rs.getString("username"), rs.getString("password"));
	}

	private void setPreparedInsert(Cliente registro) throws SQLException {
		pstInsert.setString(1, registro.getNombre());
		pstInsert.setString(2, registro.getDireccion());
		pstInsert.setString(3, registro.getUsername());
		pstInsert.setString(4, registro.getPassword());
	}

	private void setGeneratedKey(Cliente registro) throws SQLException {
		ResultSet rsClave = pstInsert.getGeneratedKeys();
		rsClave.next();
		int idAsignada = rsClave.getInt(1);
		registro.setId(idAsignada);
		rsClave.close();
	}

	private void setPreparedUpdate(Cliente registro) throws SQLException {
		pstUpdate.setString(1, registro.getNombre());
		pstUpdate.setString(2, registro.getDireccion());
		pstUpdate.setString(3, registro.getPassword());
		pstUpdate.setInt(4, registro.getId());
	}


	public Cliente find(int id) throws SQLException {
		Cliente c = null;
		pstSelectPK.setInt(1, id);
		ResultSet rs = pstSelectPK.executeQuery();
		if (rs.next()) {
			c = build(rs);
		}
		rs.close();
		return c;

	}
	
	public Cliente findByUsername(String userName) throws SQLException {
		Cliente c = null;
		pstSelectUsername.setString(1, userName);
		ResultSet rs = pstSelectPK.executeQuery();
		if (rs.next()) {
			c = build(rs);
		}
		rs.close();
		return c;

	}

	public List<Cliente> findAll() throws SQLException {
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		ResultSet rs;
		rs = pstSelectAll.executeQuery();
		while (rs.next()) {
			listaClientes.add(build(rs));
		}
		rs.close();
		return listaClientes;

	}

	public Cliente insert(Cliente cliInsertar) throws SQLException {
		setPreparedInsert(cliInsertar);
		int insertados = pstInsert.executeUpdate();
		if (insertados == 1) {
			setGeneratedKey(cliInsertar);
			return cliInsertar;
		}
		return null;
	}

	public boolean update(Cliente cliActualizar) throws SQLException {
		setPreparedUpdate(cliActualizar);
		int actualizados = pstUpdate.executeUpdate();
		return (actualizados == 1);
	}

	public boolean delete(int id) throws SQLException {
		pstDelete.setInt(1, id);
		int borrados = pstDelete.executeUpdate();
		return (borrados == 1);
	}

	public boolean delete(Cliente cliEliminar) throws SQLException {
		return this.delete(cliEliminar.getId());
	}

	public long size() throws SQLException {
		ResultSet rs;
		rs = pstSelectCount.executeQuery();
		rs.next();
		return rs.getInt(1);
	}

	public List<Cliente> findByExample(Cliente muestra) throws SQLException {
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		String filtroNombre = muestra.getNombre() == null ? "%" : "%" + muestra.getNombre() + "%";
		String filtroDireccion = muestra.getDireccion() == null ? "%" : "%" + muestra.getDireccion() + "%";

		pstSelectExample.setString(1, filtroNombre);
		pstSelectExample.setString(2, filtroDireccion);
		ResultSet rs = pstSelectExample.executeQuery();
		while (rs.next()) {
			listaClientes.add(build(rs));
		}
		rs.close();
		return listaClientes;
	}

}
