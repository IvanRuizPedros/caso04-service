package dao;

import java.util.List;

import excepciones.DataAccessException;

/*
 * Versión DAO más orientada a excepciones
 */
public interface GenericDAO<T> {
	T find(int id) throws DataAccessException;    
    List<T> findAll() throws DataAccessException; 
    
    void insert(T t) throws DataAccessException;     
    void update(T t) throws DataAccessException;     
    void delete(int id) throws DataAccessException;
    void delete(T t) throws DataAccessException; 
    
    long size() throws DataAccessException; 
    List<T> findByExample(T t) throws DataAccessException;
}



