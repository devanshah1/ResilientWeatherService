import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class contains the method implementation that are defined in HelloClientInterface.
 * @author Devan Shah 100428864 Miguel Arindaeng 100394094
 *
 */
public class ResilientWeatherServiceClientImplementation extends UnicastRemoteObject implements ResilientWeatherServiceClientInterface
{

    // Default serialization ID
    private static final long serialVersionUID = 1L ;

    public String province;
    public String city;
    public ResilientWeatherServiceClientUI resilientWeatherServiceClientUserInterface;
    
    /**
     * Constructor of the class.
     * @param selectedCity 
     * @param selectedProvince 
     * @param resilientWeatherServiceClientUserInterface 
     * @throws RemoteException
     */
    public ResilientWeatherServiceClientImplementation ( String selectedProvince , String selectedCity, ResilientWeatherServiceClientUI resilientWeatherServiceClientUserInterface  ) throws RemoteException
    {
        super () ;
        this.province                                   = selectedProvince;
        this.city                                       = selectedCity;
        this.resilientWeatherServiceClientUserInterface = resilientWeatherServiceClientUserInterface;
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

    @Override
    public void setFeeds () throws RemoteException
    {
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
    }

    @Override
    public void setCurrentWeather () throws RemoteException
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getCity ()
    {
        return city;
    }
}
