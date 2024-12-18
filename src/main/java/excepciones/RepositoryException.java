package excepciones;

public class RepositoryException extends RuntimeException {
  
    private static final long serialVersionUID = 1L;

	public RepositoryException() {
        super();
    }
    
    public RepositoryException(String message) {
        super(message);
    }
}

