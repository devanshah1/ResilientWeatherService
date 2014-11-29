import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The HelloClientInterface contains methods that can be invoked 
 * to notify the client of the messages. This interface extends
 * Remote so that the functions can be accessed remotely.
 * @author Devan Shah 100428864
 *
 */
public interface HelloClientInterface extends Remote
{
    /**
     * This function is used to send the information back to the client. 
     * This function is mainly used to send back the number of registered
     * when calling to register.
     * @param message - The message to be displayed when notify is called
     * @return message - Return the same message that is passed in.
     * @throws RemoteException
     */
    public String notifyMe ( String message ) throws RemoteException ;

}
