import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class is used initialize the hello client which is used to register for call backs and 
 * unregister for call backs after time has expired.
 * @author Devan Shah 100428864
 *
 */
public class HelloClient
{

    /**
     * This is the main function of the hello client that is responsible for
     * initializing the rmi service to communicate with the server that is on
     * the same host and port. Handles registering for callback with the server
     * and unregistering a callback when specified timeout has exceeded.
     * @param args - args[0] - The host name.
     *               args[1] - The rmi port on which to register for rmi.
     *               args[2] - the time in seconds to stay registered to the server for.
     */
    public static void main ( String args [] )
    {
        // Variable deceleration 
        String hostname       = "localhost" ;  // Default host to use 
        int portnumber        = 1099 ;         // Default rmi port
        int registeredTimeout = 5 ;            // Default time to keep the client registered for.
        
        // Override the default values for hostname, portnumber, candidateName and votenumber if provided through command line.
        if ( args[0].length () != 0 ) { hostname          = args [0] ; }
        if ( args[1].length () != 0 ) { portnumber        = Integer.parseInt ( args [1] ) ; }
        if ( args[2].length () != 0 ) { registeredTimeout = Integer.parseInt ( args [2] ) ; }
        
        try
        {
            // Retrieve the registry that is defined on a specific hostname and port number. Should match the server.
            Registry registry = LocateRegistry.getRegistry ( hostname, portnumber ) ;
            
            // Find and initialize the hello server interface for registering for callback
            HelloServerInterface helloServerCaller = ( HelloServerInterface ) registry.lookup ( "HelloServerInterface" ) ;
            System.out.println ( "Found Hello Callback Server!" ) ;
            
            // Get a response from the server
            System.out.println ( "Server responded with: " + helloServerCaller.sayHello () ) ;
            
            // Initialize the client object for call back
            HelloClientInterface clientCallBack = new HelloClientImplementation () ;

            // register the client object for a callback 
            helloServerCaller.registerForCallback ( clientCallBack ) ;
            System.out.println ( "Client registered with server for callback." ) ;
            
            // Sleep for the amount of time to keep the client registered for.
            Thread.sleep ( registeredTimeout * 1000 ) ;
            System.out.println ( "Stay Registered Timeout of: " + registeredTimeout + " seconds reached." ) ;
            
            // Perform the client unregister for classBack once the time out has exceeded. successfully 
            helloServerCaller.unregisterForCallback ( clientCallBack ) ;
            System.out.println ( "Client Successfully Unregistered from callback." ) ;
            
        }
        // Catch the exception and provide the necessary information to the user.
        catch ( InterruptedException e ) { System.out.println ( "Interrup Occured: " + e.getMessage () ) ; e.printStackTrace () ; }
        catch ( Exception e ) { System.out.println ( "Exception: " + e.getMessage () ) ; e.printStackTrace () ; }
    }
}
