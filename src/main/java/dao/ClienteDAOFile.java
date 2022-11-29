/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import modelo.Cliente;

/**
 *
 * @author sergio
 */
public class ClienteDAOFile implements GenericDAO<Cliente> {

	private final RandomAccessFile raf;
	static final short TAM = 284; // total bytes que ocupa el registro en memoria: 4+120+160
	static final short TNOMBRE = 60; // tamaño máximo campo nombre: 2 bytes por caracter
	static final short TDIRECCION = 80; // tamaño máximo campo apellidos: 2 bytes por caracter

	public ClienteDAOFile() throws Exception {
		raf = ConexionFile.getConexion();
	}

	public Cliente find(int id) {
		Cliente cli = buscar(id);
		return cli;
	}

	public List<Cliente> findAll() {
		Cliente cli;
		List<Cliente> lista = new ArrayList<Cliente>();
		try {
			raf.seek(0);			
			while (raf.getFilePointer() < raf.length()) {
				cli = leer();
				if (cli.getId() > 0) {
					lista.add(cli);
				}
			}
			return lista;
		} catch (IOException e) {
			return lista;
		}
		
	}

	public boolean insert(Cliente t) {
		Cliente cliNuevo = (Cliente) t;
		int idAutonum = 1;
		try {
			if (raf.length() > 0) {
				raf.seek(raf.length() - TAM);
				idAutonum = Math.abs(raf.readInt());
				idAutonum++;
			}
			cliNuevo.setId(idAutonum);
			raf.seek(raf.length());
			return escribir(cliNuevo);
		} catch (IOException e) {
			return false;
		}

	}

	public boolean update(Cliente t) {
		Cliente cliModif = (Cliente) t;
		Cliente cli = buscar(cliModif.getId());
		if (cli != null) {
			escribir(cliModif);
		}
		return true;
	}

	public boolean delete(int id) {
		Cliente cli = buscar(id);
		if (cli != null) {
			cli.setId(cli.getId() * (-1));
			escribir(cli);
		}
		return true;
	}

	public boolean delete(Cliente t) {
		return this.delete(t.getId());
	}

	public void cerrar() throws Exception {
		raf.close();
	}

	private Cliente leer() {
		int id; // 2 bytes. Si es negativo indica registro borrado
		StringBuilder nombre; // max. 20 caracteres -> 40 bytes (2 bytes por caracter)
		StringBuilder direccion; // max. 40 caracteres -> 80 bytes
		try {
			id = raf.readInt();
			String aux = "";
			for (int i = 0; i < TNOMBRE; i++) {
				aux = aux + raf.readChar();
			}
			nombre = new StringBuilder(aux);
			nombre.setLength(TNOMBRE);
			aux = "";
			for (int i = 0; i < TDIRECCION; i++) {
				aux = aux + raf.readChar();
			}
			direccion = new StringBuilder(aux);
			direccion.setLength(TDIRECCION);
			return new Cliente(id, nombre.toString(), direccion.toString());
		} catch (IOException e) {
			return null;
		}

	}

	private boolean escribir(Cliente c) {
		StringBuilder nombre = new StringBuilder(c.getNombre());
		nombre.setLength(TNOMBRE);
		StringBuilder direccion = new StringBuilder(c.getDireccion());
		direccion.setLength(TDIRECCION);
		try {
			raf.writeInt(c.getId());
			raf.writeChars(nombre.toString());
			raf.writeChars(direccion.toString());
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	private Cliente buscar(int id) {
		Cliente cli = null;
		boolean encontrado = false;
		try {
			raf.seek(0);
			while (!encontrado && raf.getFilePointer() < raf.length()) {
				cli = leer();
				if (cli.getId() == id) {
					encontrado = true;
				}
			}
			if (!encontrado) {
				return null;
			} else {
				raf.seek(raf.getFilePointer() - TAM);
				return cli;
			}
		} catch (IOException e) {
			return null;
		}

	}

	public int size() {
		try {
			return (int) (raf.length() / TAM);
		} catch (IOException e) {
			return -1;
		}
	}

	public List<Cliente> findByExample(Cliente cliMuestra) {
		Cliente cli;
		List<Cliente> lista = new ArrayList<Cliente>();
		try {
			raf.seek(0);
			while (raf.getFilePointer() < raf.length()) {
				cli = leer();
				if (cli.getId() > 0) {
					if (cliMuestra.getNombre() == null && cliMuestra.getDireccion() == null) {
						lista.add(cli);
					} else if (cliMuestra.getNombre() != null && cliMuestra.getDireccion() != null) {
						if (cli.getNombre().contains(cliMuestra.getNombre())
								&& cli.getDireccion().contains(cliMuestra.getDireccion())) {
							lista.add(cli);
						}
					} else if (cliMuestra.getNombre() != null) {
						if (cli.getNombre().contains(cliMuestra.getNombre())) {
							lista.add(cli);
						}
					} else {
						if (cli.getDireccion().contains(cliMuestra.getDireccion())) {
							lista.add(cli);
						}
					}
				}
			}
			return lista;
		} catch (IOException e) {
			return lista;
		}	
		
	}
	
	
	
}
