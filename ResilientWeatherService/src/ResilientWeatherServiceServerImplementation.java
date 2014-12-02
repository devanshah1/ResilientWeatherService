import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * This class contains the methods implementation that are defined in HelloServerInterface. 
 * Also contains one extra function that is used to perform the call back.
 * @author Devan Shah 100428864 Miguel Arindaeng 100394094
 *
 */
public class ResilientWeatherServiceServerImplementation extends UnicastRemoteObject implements ResilientWeatherServiceServerInterface
{

    // Default serialization ID
    private static final long serialVersionUID = 1L ;
    
    public static String USER_AGENT = "Mozilla/5.0";
    
    public static Map<String, String> supportedProvinces = new HashMap<String, String>();
    static {
        supportedProvinces.put("ON", "Ontario");
        supportedProvinces.put ( "MB", "Manitoba" );
    }

    public static Map<String, String> supportedCities = new HashMap<String, String>();
    static {
        supportedCities.put("Oshawa", "on-117");
        supportedCities.put("Vaughan", "on-64");
        supportedCities.put ( "Winnipeg", "mb-38" );
    }
    
    /*
     * Stores the object of the HelloClientInterface:
     *    Used to keep track of the registered clients.
     */
    private Vector < ResilientWeatherServiceClientInterface > registeredClients ;

    /**
     * Constructor of the class that is used to initialize the registeredClients 
     * as a vector of HelloClienInterface objects.  
     * @throws RemoteException
     */
    public ResilientWeatherServiceServerImplementation () throws RemoteException
    {
        super ();
        registeredClients = new Vector < ResilientWeatherServiceClientInterface > () ;
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
    public synchronized void registerForCallback ( ResilientWeatherServiceClientInterface resilientWeatherServiceClientCallbackObject )
    {
       /*
        *  Only register the client if the client call back object does not exist.
        *  This is to make sure that the same client is not trying to register twice.
        */        
       if ( ! ( registeredClients.contains ( resilientWeatherServiceClientCallbackObject ) ) )
       {
          // Once it is determine that the client registering is new add the client to the vector
          registeredClients.addElement ( resilientWeatherServiceClientCallbackObject ) ;
          System.out.println ( "Registered new client" ) ;
          
          try
          {
              doCallbacks ( getFeedsFromWeatherWebsite ( supportedCities.get ( resilientWeatherServiceClientCallbackObject.getCity() ) ) );
          }
          // Catch the exception and provide the necessary information to the user.
          catch ( Exception e ) { System.out.println ( "Exception: " + e.getMessage () ) ; e.printStackTrace () ; }
       }
       else {
           System.out.println( "Already Registered Client is trying to register again." ) ;
       }
    }

    /**
     * This function is used to unregister the client from the callback list.
     */
    public synchronized void unregisterForCallback ( ResilientWeatherServiceClientInterface resilientWeatherServiceClientCallbackObject )
    {
       // Remove the client from the vector of registered clients.
       if ( registeredClients.removeElement ( resilientWeatherServiceClientCallbackObject ) )
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
     * @param feeds 
     */
    private synchronized void doCallbacks (String feeds )
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
            ResilientWeatherServiceClientInterface nextClient = ( ResilientWeatherServiceClientInterface ) registeredClients.elementAt ( i ) ;

            try
            {
                // Notify the Client the number of register clients that remain.
                nextClient.notifyMe ( "Number of Registered Clients that Remain = " + registeredClients.size () ) ;
                nextClient.setFeeds ( feeds );
            }
            // Catch the exception and provide the necessary information to the user.
            catch ( RemoteException e ) { System.out.println( "Remote Exception: " + e.getMessage () ) ; e.printStackTrace() ; }
        }
        
        System.out.println ( "-----------------------------------------------------------" ) ;
        System.out.println ( "                      Callback Completed                   " ) ;
        System.out.println ( "-----------------------------------------------------------" ) ;
    }
    
    /**
     * 
     * @param cityCode
     * @return
     * @throws Exception
     */
    public String getFeedsFromWeatherWebsite ( String cityCode )
    {
        // Build the URL to grab the data for
        String weatherURLPath = "http://weather.gc.ca/rss/city/" + cityCode + "_e.xml";

        // Variable deceleration
        URL url;
        StringBuffer response = null;
        int responseCode;
        
        try
        {
            url = new URL(weatherURLPath);
            
            HttpURLConnection weatherSiteConnection = (HttpURLConnection) url.openConnection();

            // Set the type of request that is going to be performed 
            weatherSiteConnection.setRequestMethod("GET");

            // Add request header
            weatherSiteConnection.setRequestProperty("User-Agent", USER_AGENT);

            // Get the response code from the connection
            responseCode = weatherSiteConnection.getResponseCode();
            
            System.out.println( "Sending 'GET' Request to URL: " + url + "\n");
            System.out.println( "Response Code: " + responseCode);

            // Only attempt to read the web-site if the connections was successful  
            if ( responseCode == 200 ) 
            {
               // Read the web-site into a buffer reader
               BufferedReader in = new BufferedReader( new InputStreamReader( weatherSiteConnection.getInputStream() ) );
               
               // Variable deceleration
               String inputLine;

               // Construct the string buffer
               response = new StringBuffer();
                
               // Loop through the buffer reader and grab the lines and store them.
               while ( ( inputLine = in.readLine() ) != null ) 
               {
                   if ( inputLine.contains ( "title" ) )
                   {
                       // Append the string that was retrieved into a string buffer
                       response.append(inputLine + "\n");
                   }
               }
               in.close();
            }
            else {
                System.out.println ( "Connection to the website: " + weatherURLPath + "failed." );
            }

        }
        // Catch the exception and provide the necessary information to the user.
        catch ( MalformedURLException e ) { System.out.println ( "MalformedURLException: " + e.getMessage () ) ; e.printStackTrace () ; }
        catch ( IOException e ) { System.out.println ( "IOException: " + e.getMessage () ) ; e.printStackTrace () ; }
        
        return response.toString ();
    }
}
