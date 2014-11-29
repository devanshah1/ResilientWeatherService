import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class contains the method implementation that are defined in HelloClientInterface.
 * @author Devan Shah 100428864
 *
 */
public class HelloClientImplementation extends UnicastRemoteObject implements HelloClientInterface
{

    // Default serialization ID
    private static final long serialVersionUID = 1L ;

    /**
     * Constructor of the class.
     * @throws RemoteException
     */
    public HelloClientImplementation () throws RemoteException
    {
        super () ;
    }

    /**
     * This function is used to send the information back to the client. 
     * This function is mainly used to send back the number of registered
     * when calling to register.
     */
    public String notifyMe ( String message )
    {
        System.out.println ( message ) ;
        return message ;
    }

}
