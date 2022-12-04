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
	final static String TABLA = "clientes";
	final static String PK = "id";

	final String SQLSELECTALL = "SELECT * FROM " + TABLA;
	final String SQLSELECTCOUNT = "SELECT count(*) FROM " + TABLA;
	final String SQLSELECTPK = "SELECT * FROM " + TABLA + " WHERE " + PK + " = ?";
	final String SQLSELECTUSER = "SELECT * FROM " + TABLA + " WHERE username = ?";
	final String SQLSELECTEXAMPLE = "SELECT * FROM " + TABLA + " where nombre like ? and direccion like ?";
	final String SQLINSERT = "INSERT INTO " + TABLA + " (nombre, direccion, username, passwd) VALUES (?, ?, ?, ?)";
	final String SQLUPDATE = "UPDATE " + TABLA + " SET nombre = ?, direccion = ?, username = ?, passwd = ? WHERE " + PK + " = ?";
	final String SQLDELETE = "DELETE FROM " + TABLA + " WHERE " + PK + " = ?";
	private final PreparedStatement pstSelectPK;
	private final PreparedStatement pstSelectAll;
	private final PreparedStatement pstSelectCount;
	private final PreparedStatement pstSelectExample;
	private final PreparedStatement pstInsert;
	private final PreparedStatement pstUpdate;
	private final PreparedStatement pstDelete;
	private final PreparedStatement pstSelectUserName;

	public ClienteDAO() throws SQLException {
		Connection con = ConexionBD.getConexion();
		pstSelectPK = con.prepareStatement(SQLSELECTPK);
		pstSelectUserName = con.prepareStatement(SQLSELECTUSER);
		pstSelectCount = con.prepareStatement(SQLSELECTCOUNT);
		pstSelectAll = con.prepareStatement(SQLSELECTALL);
		pstSelectExample = con.prepareStatement(SQLSELECTEXAMPLE);
		pstInsert = con.prepareStatement(SQLINSERT, PreparedStatement.RETURN_GENERATED_KEYS);
		pstUpdate = con.prepareStatement(SQLUPDATE);
		pstDelete = con.prepareStatement(SQLDELETE);
	}

	public void cerrar() throws SQLException {
		pstSelectPK.close();
		pstSelectUserName.close();
		pstSelectAll.close();
		pstSelectCount.close();
		pstSelectExample.close();
		pstInsert.close();
		pstUpdate.close();
		pstDelete.close();
	}

	private Cliente build(int id, String nombre, String direccion, String username, String passwd) {
		return new Cliente(id, nombre, direccion, username, passwd);
	}

	public Cliente find(int id) {
		Cliente c = null;
		try {
			pstSelectPK.setInt(1, id);
			ResultSet rs = pstSelectPK.executeQuery();
			if (rs.next()) {
				c = build(id, rs.getString("nombre"), rs.getString("direccion"), rs.getString("username"), rs.getString("passwd"));
			}
			rs.close();
			return c;
		} catch (SQLException e) {
			return null;
		}

	}
	
	public Cliente findByUserName(String username) {
		Cliente c = null;
		try {
			pstSelectUserName.setString(1, username);
			ResultSet rs = pstSelectUserName.executeQuery();
			if (rs.next()) {
				c = build(rs.getInt("id"), rs.getString("nombre"), rs.getString("direccion"), username, rs.getString("passwd"));
			}
			rs.close();
			return c;
		} catch (SQLException e) {
			return null;
		}

	}

	public List<Cliente> findAll() {
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		ResultSet rs;
		try {
			rs = pstSelectAll.executeQuery();
			while (rs.next()) {
				listaClientes.add(build(rs.getInt("id"), rs.getString("nombre"), rs.getString("direccion"), rs.getString("username"), rs.getString("passwd")));
			}
			rs.close();
			return listaClientes;
		} catch (SQLException e) {
			return listaClientes;
		}

	}

	public boolean insert(Cliente cliInsertar) {
		try {
			pstInsert.setString(1, cliInsertar.getNombre());
			pstInsert.setString(2, cliInsertar.getDireccion());
			pstInsert.setString(3, cliInsertar.getUsername());
			pstInsert.setString(4, cliInsertar.getPasswd());
			int insertados = pstInsert.executeUpdate();
			if (insertados == 1) {
				ResultSet rsClave = pstInsert.getGeneratedKeys();
				rsClave.next();
				cliInsertar.setId(rsClave.getInt(1));
				rsClave.close();
				return true;
			}
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean update(Cliente cliActualizar) {
		try {
			pstUpdate.setString(1, cliActualizar.getNombre());
			pstUpdate.setString(2, cliActualizar.getDireccion());
			pstUpdate.setString(3, cliActualizar.getUsername());
			pstUpdate.setString(4, cliActualizar.getPasswd());
			pstUpdate.setInt(5, cliActualizar.getId());
			int actualizados = pstUpdate.executeUpdate();
			return (actualizados == 1);
		} catch (SQLException e) {
			return false;
		}

	}

	public boolean save(Cliente cliGuardar) {
		if (this.exists(cliGuardar.getId())) {
			return this.update(cliGuardar);
		} else {
			return this.insert(cliGuardar);
		}
	}

	public boolean delete(int id) {
		try {
			pstDelete.setInt(1, id);
			int borrados = pstDelete.executeUpdate();
			return (borrados == 1);
		} catch (SQLException e) {
			return false;
		}

	}

	public boolean delete(Cliente cliEliminar) {
		return this.delete(cliEliminar.getId());
	}

	public int size() {
		ResultSet rs;
		try {
			rs = pstSelectCount.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			return 0;
		}

	}

	public boolean exists(int id) {
		if (find(id) != null) {
			return true;
		}
		return false;
	}

	public List<Cliente> findByExample(Cliente muestra) {
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		String filtroNombre = muestra.getNombre() == null ? "%" : "%" + muestra.getNombre() + "%";
		String filtroDireccion = muestra.getDireccion() == null ? "%" : "%" + muestra.getDireccion() + "%";
		try {
			pstSelectExample.setString(1, filtroNombre);
			pstSelectExample.setString(2, filtroDireccion);
			ResultSet rs = pstSelectExample.executeQuery();
			while (rs.next()) {
				listaClientes.add(build(rs.getInt("id"), rs.getString("nombre"), rs.getString("direccion"), rs.getString("username"), rs.getString("passwd")));
			}
			rs.close();
			return listaClientes;
		} catch (SQLException e) {
			return listaClientes;
		}
	}

	

}
