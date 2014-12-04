import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * This class is used to start up the monitoring system for the Resilient Weather Service system.
 * @author Devan Shah 100428864
 *
 */
public class ResilientWeatherServiceMonitor
{

    /**
     * This function is used to continuously check to make sure that the Resilient Weather Service
     * server is running at all times. This uses Java RMI to check the register that is set on the 
     * hostname and the default Java RMI port.
     * @param args - args[0] - The hostname to use, default is localhost if not provided
     */
    public static void main ( String [] args )
    {
        // Variable deceleration
        String hostname = "localhost" ; // Default host to use
        boolean serverDown = false ;
        
        // Override the default values for hostname if passed through command line.
        if ( args [0].length () != 0 ) { hostname = args [0] ; }
        
        // Continuously run the checking algo
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
                    // Set the boolean as true identifying that the server has been detected as down
                    serverDown = true ;
                    System.out.println( "Found ResilientWeatherServiceServer not running \n Restarting ResilientWeatherServiceServer ..." ); 
                    
                    // Schedule the task to start up the server is the background by running the approiate commands
                    ScheduledTaskResilientWeatherServices restartResilientWeatherServiceServerScheduler = new ScheduledTaskResilientWeatherServices();
                    restartResilientWeatherServiceServerScheduler.startScheduleTask();
                    Thread.sleep ( 5000 );
                }
                
                // If the server is not down keep printing that the monitor is monitoring
                if ( !serverDown )
                {
                    System.out.println( "Monitoring ResilientWeatherServiceServer" ) ;   
                }
            }
            // Catch the exception and provide the necessary information to the user.        
            catch ( Exception e ) { System.out.println ( "Exception: " + e.getMessage () ) ; e.printStackTrace () ; }    
        }
    }
}

/**
 * This class services the purpose to schedule the task to start up the server as a background process
 * when detected that the server is down.
 * @author Devan Shah 100428864
 *
 */
class ScheduledTaskResilientWeatherServices
{
    // Variable deceleration for the new thread pool 
    private final ScheduledExecutorService resilientWeatherServiceServerScheduler = Executors.newScheduledThreadPool ( 1 );

    /**
     * This function is used to start the scheduled task
     */
    public void startScheduleTask ()
    {
        /**
         * This acts as a task started on a different thread and stays active for 15 days
         */
        final ScheduledFuture <?> taskHandle = resilientWeatherServiceServerScheduler.scheduleAtFixedRate (
                
            new Runnable ()
            {
                /**
                 * This is the run function of the scheduler which runs the command to start the server
                 */
                public void run ()
                {   
                    try
                    {
                        // Open up commnad windows and run script to start the server
                        Runtime.getRuntime ().exec (new String [] { "cmd", "/k", "start", "startup.cmd" } );
                    }
                    // Catch the exception and provide the necessary information to the user.        
                    catch ( Exception e ) { System.out.println ( "Exception: " + e.getMessage () ) ; e.printStackTrace () ; }
                }
            }, 0, 15, TimeUnit.DAYS 
        );
    }
}
