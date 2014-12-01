import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class contains the method implementation that are defined in HelloClientInterface.
 * @author Devan Shah 100428864
 *
 */
public class ResilientWeatherServiceClientImplementation extends UnicastRemoteObject implements ResilientWeatherServiceClientInterface
{

    // Default serialization ID
    private static final long serialVersionUID = 1L ;

    public String province;
    public String city;
    
    /**
     * Constructor of the class.
     * @param selectedCity 
     * @param selectedProvince 
     * @throws RemoteException
     */
    public ResilientWeatherServiceClientImplementation ( String selectedProvince , String selectedCity ) throws RemoteException
    {
        super () ;
        this.province = selectedProvince;
        this.city     = selectedCity;
    }

    /**
     * This function is used to send the information back to the client. 
     * This function is mainly used to send back the number of registered
     * when calling to register.
     */
    public String notifyMe ( String message )
    {
        System.out.println ( message ) ;
        return message ;
    }
    
    /**
     * 
     * @param newProvince
     */
    public void setProvince ( String newProvince )
    {
        province = newProvince;
    }
    
    /**
     * 
     * @param newCity
     */
    public void setCity ( String newCity )
    {
        city = newCity;
    }
}
