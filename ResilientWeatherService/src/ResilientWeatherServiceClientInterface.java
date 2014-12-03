import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The HelloClientInterface contains methods that can be invoked 
 * to notify the client of the messages. This interface extends
 * Remote so that the functions can be accessed remotely.
 * @author Devan Shah 100428864 Miguel Arindaeng 100394094
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
     * This function is used to set the feeds in the Resilient Weather Service
     * user interface.
     * @param feeds 
     * @throws RemoteException
     */
    public void setFeeds (String feeds ) throws RemoteException ;
    
    /**
     * 
     * @param weatherData 
     * @throws RemoteException
     */
    public void setCurrentWeather ( String weatherData ) throws RemoteException ;

    /**
     * 
     * @return
     */
    public String getCity () throws RemoteException;
}
