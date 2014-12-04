import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Devan Shah 100428864
 *
 */
public class ResilientWeatherServiceMonitor
{

    /**
     * 
     * @param args
     */
    public static void main ( String [] args )
    {

        // Variable deceleration
        String hostname = "localhost" ; // Default host to use
        boolean serverDown = false ;
        
        // Override the default values for hostname if passed through command line.
        if ( args [0].length () != 0 ) { hostname = args [0] ; }
        
        while ( true )
        {
            try
            {
                // Set the system property for "java.rmi.server.hostname".
                System.setProperty ( "java.rmi.server.hostname", hostname ) ;

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
                    serverDown = true ;
                    System.out.println( "Found ResilientWeatherServiceServer not running \n Restarting ResilientWeatherServiceServer ..."); 
                    
                    ScheduledTaskExample restartResilientWeatherServiceServerScheduler = new ScheduledTaskExample();
                    restartResilientWeatherServiceServerScheduler.startScheduleTask();
                    Thread.sleep ( 5000 );
                }
                
                if ( !serverDown )
                {
                    System.out.println("Monitoring ResilientWeatherServiceServer") ;   
                }
            }
            // Catch the exception and provide the necessary information to the user.        
            catch ( Exception e ) { System.out.println ( "Exception: " + e.getMessage () ) ; e.printStackTrace () ; }    
        }
    }
}

/**
 * 
 * @author 100428864
 *
 */
class ScheduledTaskExample
{
    private final ScheduledExecutorService resilientWeatherServiceServerScheduler = Executors.newScheduledThreadPool ( 1 );

    /**
     * 
     */
    public void startScheduleTask ()
    {
        /**
         * not using the taskHandle returned here, but it can be used to cancel
         * the task, or check if it's done (for recurring tasks, that's not
         * going to be very useful)
         */
        final ScheduledFuture <?> taskHandle = resilientWeatherServiceServerScheduler.scheduleAtFixedRate (
                
            new Runnable ()
            {
                /**
                 * 
                 */
                public void run ()
                {   
                    try
                    {
                        Runtime.getRuntime ().exec (new String [] { "cmd", "/k", "start", "startup.cmd" } );
                    }
                    // Catch the exception and provide the necessary information to the user.        
                    catch ( Exception e ) { System.out.println ( "Exception: " + e.getMessage () ) ; e.printStackTrace () ; }
                }
            }, 0, 15, TimeUnit.MINUTES );
    }
}
