import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class is used initialize the Resilient Weather Service client which is used to 
 * set up the user interface to display the weather feeds, current weather and also 
 * provides the ability to the user to provided specific weather configurations. 
 * The weather feeds and current weather are updated based on the settings that are 
 * specified by the user. The communication protocol that is used to communicate
 * with the server is using Java RMI.
 * @author Devan Shah 100428864
 * 
 */
public class ResilientWeatherServiceClient
{

    /**
     * This is the main function of the Resilient Weather Service client that is
     * responsible for initializing the Java RMI service to communicate with the server
     * that is initialized on the same host and port. Also handles registering for
     * callback with the server which allows continuously updating of the data that 
     * is presented to the user through the user interface. This main function also 
     * initializes the user interface and sets the client callback and server caller
     * objects within the user interface, to allow for setting data in JSwing objects.
     * @param args - args[0] - The host name.
     *               args[1] - The rmi port on which to register for Java RMI.
     */
    public static void main ( String args [] )
    {
        // Variable deceleration 
        String hostname       = "localhost" ;  // Default hostname to use 
        int portnumber        = 1099 ;         // Default Java RMI port
        
        // Override the default values for hostname and portnumber if provided through command line.
        if ( args[0].length () != 0 ) { hostname          = args [0] ; }
        if ( args[1].length () != 0 ) { portnumber        = Integer.parseInt ( args [1] ) ; }
        
        try
        {
            // Construct the Resilient Weather Service Client User interface to display the weather information to the user.
            ResilientWeatherServiceClientUI resilientWeatherServiceClientUserInterface = new ResilientWeatherServiceClientUI();
            
            // Retrieve the registry that is defined on a specific hostname and port number. Should match the server.
            Registry registry = LocateRegistry.getRegistry ( hostname, portnumber ) ;
            
            // Find and initialize the Resilient Weather Service server interface for registering for callbacks
            ResilientWeatherServiceServerInterface resilientWeatherServiceServerCaller = ( ResilientWeatherServiceServerInterface ) registry.lookup ( "ResilientWeatherServiceServerInterface" ) ;
            System.out.println ( "Found Resilient Weather Service Callback Server!\n" ) ;
            
            // Get a response from the server to make sure server and client can communicate
            System.out.println ( "Server responded with: " + resilientWeatherServiceServerCaller.sayHello () + "\n" ) ;
            
            /**
             * Initialize the client object for call back, when initializing the object pass in the default selected Province and city
             */
            ResilientWeatherServiceClientInterface resilientWeatherServiceClientCallBack = new ResilientWeatherServiceClientImplementation ( resilientWeatherServiceClientUserInterface.selectedProvince, 
                                                                                                                                             resilientWeatherServiceClientUserInterface.selectedCity,
                                                                                                                                             resilientWeatherServiceClientUserInterface
                                                                                                                                           ) ;
            // Register the Resilient Weather Service Client object for callback with the server
            resilientWeatherServiceServerCaller.registerForCallback ( resilientWeatherServiceClientCallBack ) ;
            System.out.println ( "Client registered with server for callback." ) ;
            
            /**
             * Set the resilientWeatherServiceClientCallBack and resilientWeatherServiceServerCaller in the server to allow for
             * registering and unregistering when settings are changed.
             */
            resilientWeatherServiceClientUserInterface.setResilientWeatherServiceClientCallBack ( resilientWeatherServiceClientCallBack );
            resilientWeatherServiceClientUserInterface.setResilientWeatherServiceServerCaller ( resilientWeatherServiceServerCaller );       
            
            /**
             * Make the User interface visible now after the Resilient Weather Service Client 
             * has been registered and all setting are set for the user interface.
             */
            resilientWeatherServiceClientUserInterface.setVisible(true);
        }
        // Catch the exception and provide the necessary information to the user.
        catch ( Exception e ) { System.out.println ( "Exception: " + e.getMessage () ) ; e.printStackTrace () ; }
    }
}
