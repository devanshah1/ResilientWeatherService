import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class is used to initialize the Hello server that is used to perform 
 * callbacks and store multiple registered clients.
 * @author Devan Shah 100428864
 *
 */
public class HelloServer
{
    /**
     * Main server function used to perform the look up and rebind of the HelloServerInterface
     * to all for remote method invocation.
     * @param args - args[0] - The hostname to use, default is localhost if not provided
     */
    public static void main ( String args [] )
    {
        // Variable deceleration
        String hostname = "localhost" ; // Default host to use
        
        // Override the default values for hostname if passed through command line.
        if ( args [0].length () != 0 ) { hostname = args [0] ; }
        
        try
        {
            // Set the system property for "java.rmi.server.hostname".
            System.setProperty ( "java.rmi.server.hostname", hostname ) ;
            
            // Initialize the interface to access all the remote functions.
            HelloServerImplementation registerObject = new HelloServerImplementation () ;

            // Declare registry variable
            Registry registry ;
            
            // This try catch is to make sure that the registry is created
            try 
            {
                // Try to get the remote object Registry for the local host on the default registry port of 1099.
                registry = LocateRegistry.getRegistry() ;
                registry.list() ; // Fetch the names bounded to the registry
            }
            // Catch the exception where communication with the registry fails and create the registry.
            catch ( RemoteException e ) 
            {
                // Create the registry on the default rmi port 1099
                System.out.println ( "RMI registry cannot be located at port " + Registry.REGISTRY_PORT ) ;
                registry = LocateRegistry.createRegistry ( Registry.REGISTRY_PORT ) ;
                System.out.println ( "RMI registry created at port " + Registry.REGISTRY_PORT ) ;
            }
            
            // Once the registry is successfully created, rebind the HelloServerInterface to the remote reference created above.
            registry.rebind ( "HelloServerInterface", registerObject ) ;
            System.out.println ( "Callback Server ready." ) ;
        }
        // Catch the exception and provide the necessary information to the user.        
        catch ( Exception e ) { System.out.println ( "Exception: " + e.getMessage () ) ; e.printStackTrace () ; }
    }
}
