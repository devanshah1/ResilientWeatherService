import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The ResilientWeatherServiceClientInterface contains methods that can be invoked 
 * to notify the client of the messages and set data in the user interface. This interface extends
 * Remote so that the functions can be accessed remotely.
 * @author Devan Shah 100428864
 *
 */
public interface ResilientWeatherServiceClientInterface extends Remote
{
    /**
     * This function is used to send the information back to the client. 
     * This function is mainly used to send back the number of registered
     * when calling to register.
     * @param message - The message to be displayed when notify is called
     * @return message - Return the same message that is passed in.
     * @throws RemoteException
     */
    public String notifyMe ( String message ) throws RemoteException ;

    /**
     * This function is used to set the province on the client interface so 
     * that the server has access to it in the case that it changes.
     * @param newProvince - The new province to be set when there is a change made by the user.
     */
    public void setProvince ( String newProvince ) throws RemoteException ;
    
    /**
     * This function is used to set the city on the client interface so 
     * that the server has access to it in the case that it changes.
     * @param newCity - The new city to be set when there is a change made by the user.
     */
    public void setCity ( String newCity ) throws RemoteException ;
    
    /**
     * This function is used to parse the feed data and then set the feeds 
     * in the Resilient Weather Service user interface.
     * @param feeds - Unparsed feed data
     * @throws RemoteException
     */
    public void setFeeds ( String feeds ) throws RemoteException ;
    
    /**
     * This function is used to parse the current weather data and then set the current
     * weather in the Resilient Weather Service user interface.
     * @param weatherData - Unparsed current weather data
     * @throws RemoteException
     */
    public void setCurrentWeather ( String weatherData ) throws RemoteException ;

    /**
     * Return the current city that is set.
     * @return city - the city that is set in the object
     */
    public String getCity () throws RemoteException;
}
