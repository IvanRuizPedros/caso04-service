package excepciones;

public class ConnectionException extends RuntimeException {
  
    private static final long serialVersionUID = 1L;

	public ConnectionException() {
        super();
    }
    
    public ConnectionException(String message) {
        super(message);
    }
}

