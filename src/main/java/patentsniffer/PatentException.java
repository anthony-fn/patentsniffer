package patentsniffer;

public class PatentException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 4542381977454496725L;

    public PatentException( String message )
    {
        super(message);
    }
    
    public PatentException( String message, Exception e )
    {
        super( message, e);
    }
}
