import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The HelloServerInterface contains methods that can be invoked to
 * register or unregister for callbacks. This interface extends Remote 
 * so that the functions can be accessed remotly.
 * @author Devan Shah 100428864
 *
 */
public interface HelloServerInterface extends Remote
{

    /**
     * This function is used to return back "Hello world!", when called
     * this is used to make sure that the server is reachable from the
     * client. 
     * 
     * @return "Hello world!" to the caller.
     * @throws RemoteException
     */
    public String sayHello () throws RemoteException;

    /**
     * This function is used to register the client for callback. This function also performs
     * the callback to return back the number of clients that are already registered.
     * Handles not registering the same client twice. ( if the client calls registerForCallback 
     * with the same clientCallbackObject.
     * 
     * @param callbackClientObject - This is the callback object sent from the client. 
     * @throws RemoteException
     */
    public void registerForCallback ( HelloClientInterface clientCallbackObject ) throws RemoteException ;

    /**
     * This function is used to unregister the client from the callback list.
     * @param callbackClientObject - This is the callback object sent from the client.
     * @throws RemoteException
     */
    public void unregisterForCallback ( HelloClientInterface clientCallbackObject ) throws RemoteException ;
    
}
