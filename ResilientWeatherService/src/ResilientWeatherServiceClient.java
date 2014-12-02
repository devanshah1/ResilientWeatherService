import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class is used initialize the hello client which is used to register for call backs and 
 * unregister for call backs after time has expired.
 * @author Devan Shah 100428864 Miguel Arindaeng 100394094
 *
 */
public class ResilientWeatherServiceClient
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
        //int registeredTimeout = 5 ;            // Default time to keep the client registered for.
        
        // Override the default values for hostname, portnumber, candidateName and votenumber if provided through command line.
        if ( args[0].length () != 0 ) { hostname          = args [0] ; }
        if ( args[1].length () != 0 ) { portnumber        = Integer.parseInt ( args [1] ) ; }
        //if ( args[2].length () != 0 ) { registeredTimeout = Integer.parseInt ( args [2] ) ; }
        
        try
        {
            // Construct the Resilient Weather Service Client User interface to display the weather information to the client.
            ResilientWeatherServiceClientUI resilientWeatherServiceClientUserInterface = new ResilientWeatherServiceClientUI();
            resilientWeatherServiceClientUserInterface.setVisible(true);
            
            // Retrieve the registry that is defined on a specific hostname and port number. Should match the server.
            Registry registry = LocateRegistry.getRegistry ( hostname, portnumber ) ;
            
            // Find and initialize the hello server interface for registering for callback
            ResilientWeatherServiceServerInterface resilientWeatherServiceServerCaller = ( ResilientWeatherServiceServerInterface ) registry.lookup ( "ResilientWeatherServiceServerInterface" ) ;
            System.out.println ( "Found Resilient Weather Service Callback Server!" ) ;
            
            // Get a response from the server
            System.out.println ( "Server responded with: " + resilientWeatherServiceServerCaller.sayHello () ) ;
            
            // Initialize the client object for call back
            ResilientWeatherServiceClientInterface resilientWeatherServiceClientCallBack = new ResilientWeatherServiceClientImplementation ( resilientWeatherServiceClientUserInterface.selectedProvince, 
                                                                                                                                             resilientWeatherServiceClientUserInterface.selectedCity,
                                                                                                                                             resilientWeatherServiceClientUserInterface
                                                                                                                                           ) ;
            // register the client object for a callback 
            resilientWeatherServiceServerCaller.registerForCallback ( resilientWeatherServiceClientCallBack ) ;
            System.out.println ( "Client registered with server for callback." ) ;
            
            //resilientWeatherServiceClientUserInterface.setResilientWeatherServiceClientCallBack ( resilientWeatherServiceClientCallBack );
            resilientWeatherServiceClientUserInterface.setResilientWeatherServiceServerCaller ( resilientWeatherServiceServerCaller );
            
//            // Sleep for the amount of time to keep the client registered for.
//            Thread.sleep ( registeredTimeout * 1000 ) ;
//            System.out.println ( "Stay Registered Timeout of: " + registeredTimeout + " seconds reached." ) ;
//            
//            // Perform the client unregister for classBack once the time out has exceeded. successfully 
//            resilientWeatherServiceServerCaller.unregisterForCallback ( resilientWeatherServiceClientCallBack ) ;
//            System.out.println ( "Client Successfully Unregistered from callback." ) ;         
        }
        // Catch the exception and provide the necessary information to the user.
        catch ( Exception e ) { System.out.println ( "Exception: " + e.getMessage () ) ; e.printStackTrace () ; }
    }
}
