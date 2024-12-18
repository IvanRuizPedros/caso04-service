package excepciones;

public class ServiceException extends RuntimeException {
  
    private static final long serialVersionUID = 1L;

	public ServiceException() {
        super();
    }
    
    public ServiceException(String message) {
        super(message);
    }
}

