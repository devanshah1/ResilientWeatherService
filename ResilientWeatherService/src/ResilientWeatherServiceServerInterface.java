import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The ResilientWeatherServiceServerInterface contains methods that can be invoked to
 * register or unregister for callbacks. This interface extends Remote 
 * so that the functions can be accessed remotely.
 * @author Devan Shah 100428864
 *
 */
public interface ResilientWeatherServiceServerInterface extends Remote
{

    /**
     * This function is used to return back "Hello world!", when called
     * this is used to make sure that the server is reachable from the
     * client. 
     * @return "Hello world!" to the caller.
     * @throws RemoteException
     */
    public String sayHello () throws RemoteException;

    /**
     * This function is used to register the client for callback. This function also performs
     * the callback to return back the number of clients that are already registered.
     * Handles not registering the same client twice. ( if the client calls registerForCallback 
     * with the same clientCallbackObject.)
     * 
     * @param callbackClientObject - This is the callback object sent from the client. 
     * @throws RemoteException
     */
    public void registerForCallback ( ResilientWeatherServiceClientInterface clientCallbackObject ) throws RemoteException ;

    /**
     * This function is used to unregister the client from the callback list.
     * @param callbackClientObject - This is the callback object sent from the client.
     * @throws RemoteException
     */
    public void unregisterForCallback ( ResilientWeatherServiceClientInterface clientCallbackObject ) throws RemoteException ;
    
    /**
     * This function is used to get the feeds data from the weather websites
     * @param cityCode  - Provide the city code to get the feeds data for
     * @return feeds - returns back the unparsed feed data
     * @throws Exception
     */
    public String getFeedsFromWeatherWebsite ( String cityCode ) throws RemoteException ;
    
    /**
     * This function is used to get the current weather data from the website
     * @param cityCode  - Provide the city code to get the feeds data for
     * @return currentWeather - returns back the unparsed current Weather data
     * @throws RemoteException
     */
    public String getCurrentWeatherFromWeatherWebsite ( String cityCode ) throws RemoteException ;
    
    /**
     * This function is used to get feeds and current weather data from the websites
     * @param city - Pass in the cityCode that the data needs to be retried for
     * @return weatherData array which contains the feeds and current weather data
     * @throws RemoteException
     */
    public String[] getWeatherWebsite ( String city ) throws RemoteException ;
}
