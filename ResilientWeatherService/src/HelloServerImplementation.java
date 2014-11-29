import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

/**
 * This class contains the methods implementation that are defined in HelloServerInterface. 
 * Also contains one extra function that is used to perform the call back.
 * @author Devan Shah 100428864
 *
 */
public class HelloServerImplementation extends UnicastRemoteObject implements HelloServerInterface
{

    // Default serialization ID
    private static final long serialVersionUID = 1L ;
    
    /*
     * Stores the object of the HelloClientInterface:
     *    Used to keep track of the registered clients.
     */
    private Vector < HelloClientInterface > registeredClients ;

    /**
     * Constructor of the class that is used to initialize the registeredClients 
     * as a vector of HelloClienInterface objects.  
     * @throws RemoteException
     */
    public HelloServerImplementation () throws RemoteException
    {
        super ();
        registeredClients = new Vector < HelloClientInterface > () ;
    }

    /**
     * This function is used to return back "Hello world!", when called
     * this is used to make sure that the server is reachable from the
     * client. 
     */
    public String sayHello () throws RemoteException
    {
        System.out.println ( "Running sayHello()" ) ;
        return "Hello world!" ;
    }

    /**
     * This function is used to register the client for callback. This function also performs
     * the callback to return back the number of clients that are already registered.
     * Handles not registering the same client twice. ( if the client calls registerForCallback 
     * with the same clientCallbackObject.
     */
    public synchronized void registerForCallback ( HelloClientInterface clientCallbackObject )
    {
       /*
        *  Only register the client if the client call back object does not exist.
        *  This is to make sure that the same client is not trying to register twice.
        */        
       if ( ! ( registeredClients.contains ( clientCallbackObject ) ) )
       {
          // Once it is determine that the client registering is new add the client to the vector
          registeredClients.addElement ( clientCallbackObject ) ;
          System.out.println ( "Registered new client" ) ;
          doCallbacks () ;
       }
       else {
           System.out.println( "Already Registered Client is trying to register again." ) ;
       }
    }

    /**
     * This function is used to unregister the client from the callback list.
     */
    public synchronized void unregisterForCallback ( HelloClientInterface clientCallbackObject )
    {
       // Remove the client from the vector of registered clients.
       if ( registeredClients.removeElement ( clientCallbackObject ) )
       {
          System.out.println ( "Client Unregistered Successfully" ) ;
       }
       // Handle where the client that is attempting to unregister is not registered at all.
       else
       {
          System.out.println ( "Client is not registerd at all ... No need to unregister." ) ;
       }
    }

    /**
     * This is a private function that is used to report back to the clients that are
     * registered for callback with the number of clients that are currently registered
     * with the server.
     */
    private synchronized void doCallbacks ()
    {
        System.out.println ( "-----------------------------------------------------------" ) ;
        System.out.println ( "                      Callback Invoked                     " ) ;
        System.out.println ( "-----------------------------------------------------------" ) ;
        
        /*
         *  Loop through all the clients and send the information about the number 
         *  of clients currently registered on the server.
         */
        for ( int i = 0; i < registeredClients.size (); i++ )
        {
            System.out.println ( "          Sending Callback information to: " + i + " client." ) ;
            
            // Grab the next client in the vector.
            HelloClientInterface nextClient = ( HelloClientInterface ) registeredClients.elementAt ( i ) ;

            try
            {
                // Notify the Client the number of register clients that remain.
                nextClient.notifyMe ( "Number of Registered Clients that Remain = " + registeredClients.size () ) ;
            }
            // Catch the exception and provide the necessary information to the user.
            catch ( RemoteException e ) { System.out.println( "Remote Exception: " + e.getMessage () ) ; e.printStackTrace() ; }
        }
        
        System.out.println ( "-----------------------------------------------------------" ) ;
        System.out.println ( "                      Callback Completed                   " ) ;
        System.out.println ( "-----------------------------------------------------------" ) ;
    }
}
