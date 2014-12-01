import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * This class is used initialize the hello client which is used to register for call backs and 
 * unregister for call backs after time has expired.
 * @author Devan Shah 100428864
 *
 */
public class ResilientWeatherServiceClient
{
    public static String USER_AGENT = "Mozilla/5.0";

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
            ResilientWeatherServiceClientUI resilientWeatherServiceClientUserInterface = new ResilientWeatherServiceClientUI();
            resilientWeatherServiceClientUserInterface.setVisible(true);
            resilientWeatherServiceClientUserInterface.weatherWatchesAndWarningsLabel.setText ( "WARNING" );
            resilientWeatherServiceClientUserInterface.currentWeatherConditionsLabel.setText ( "Current Weather Condition" );
            resilientWeatherServiceClientUserInterface.currentDayWeatherLabel.setText ( "Current Day Weather" );
            resilientWeatherServiceClientUserInterface.currentDayNightWeatherLabel.setText ( "Current Day Night Weather" );
            resilientWeatherServiceClientUserInterface.nextFirstDayWeatherLabel.setText ( "Firsy Day Weather " );
            resilientWeatherServiceClientUserInterface.nextSecondDayWeatherLabel.setText ( "Second Day Weather" );
            resilientWeatherServiceClientUserInterface.nextThirdDayWeatherLabel.setText ( "Third Day Weather" );
            resilientWeatherServiceClientUserInterface.nextForthDayWeatherLabel.setText ( "Forth Day Weather" );
            resilientWeatherServiceClientUserInterface.nextFifthDayWeatherLabel.setText ( "Fifth Day Weather" );
            resilientWeatherServiceClientUserInterface.nextSixthDayWeatherLabel.setText ( "Sixed Day Weather" );
            
            // Retrieve the registry that is defined on a specific hostname and port number. Should match the server.
            Registry registry = LocateRegistry.getRegistry ( hostname, portnumber ) ;
            
            System.out.println (sendGet());
            
            Document doc = convertStringToDocument(sendGet());
            
            // Find and initialize the hello server interface for registering for callback
            ResilientWeatherServiceServerInterface helloServerCaller = ( ResilientWeatherServiceServerInterface ) registry.lookup ( "ResilientWeatherServiceServerInterface" ) ;
            System.out.println ( "Found Hello Callback Server!" ) ;
            
            // Get a response from the server
            System.out.println ( "Server responded with: " + helloServerCaller.sayHello () ) ;
            
            // Initialize the client object for call back
            ResilientWeatherServiceClientInterface clientCallBack = new ResilientWeatherServiceClientImplementation () ;

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
    
    
    // HTTP GET request
    public static String sendGet() throws Exception 
    {
        String url = "http://weather.gc.ca/rss/city/on-122_e.xml";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) 
        {
            response.append(inputLine);
        }
        in.close();
        
        return response.toString();
    }
    
    private static Document convertStringToDocument ( String xmlStr )
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
        DocumentBuilder builder;
        
        try
        {
            builder = factory.newDocumentBuilder ();
            Document doc = builder.parse ( new InputSource ( new StringReader ( xmlStr ) ) );
            System.out.println ( doc.toString () );
            return doc;
        }
        catch ( Exception e )
        {
            e.printStackTrace ();
        }
        
        return null;
    }
 
}
