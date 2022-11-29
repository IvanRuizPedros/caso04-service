package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

// Patrón singleton
public class ConexionFile {

    private static final String PATH_FILE = "bdclientes.bd";    
    private static RandomAccessFile raf = null;    

    public static RandomAccessFile getConexion() throws FileNotFoundException {
        if (raf == null) {
            raf = new RandomAccessFile(PATH_FILE, "rw");
        }
        return raf;
    }

    public static void cerrar() throws IOException  {
        if (raf != null) {
            raf.close();

        }
    }

}
