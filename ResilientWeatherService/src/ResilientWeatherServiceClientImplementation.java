import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void setFeeds ( String feeds ) throws RemoteException
    {
        // Split the feed input by lines to represent it in the client user interface
        String[] feedsParsed = feeds.split("\n");
        
        // Loop through the array and remove the xml tags <title> and <\title>
        for ( int i = 0; i < feedsParsed.length; i++ ) 
        {
            int left = feedsParsed[i].indexOf("<title>");
            int right = feedsParsed[i].indexOf("</title>"); 
            
            if ( feedsParsed[i].contains ( "xB0;" )) 
            {
                feedsParsed[i] = feedsParsed[i].substring(left+7, right-7) + "\u00b0" + "C";
            }
            else
            {
                feedsParsed[i] = feedsParsed[i].substring(left+7, right);
            }
        }
        
        resilientWeatherServiceClientUserInterface.weatherWatchesAndWarningsLabel.setText ( feedsParsed[1] );
        resilientWeatherServiceClientUserInterface.currentWeatherConditionsLabel.setText ( feedsParsed[2] );
        resilientWeatherServiceClientUserInterface.currentDayWeatherLabel.setText ( feedsParsed[3] );
        resilientWeatherServiceClientUserInterface.currentDayNightWeatherLabel.setText ( feedsParsed[4] );
        resilientWeatherServiceClientUserInterface.nextFirstDayWeatherLabel.setText ( feedsParsed[5] );
        resilientWeatherServiceClientUserInterface.nextSecondDayWeatherLabel.setText ( feedsParsed[6] );
        resilientWeatherServiceClientUserInterface.nextThirdDayWeatherLabel.setText ( feedsParsed[7] );
        resilientWeatherServiceClientUserInterface.nextForthDayWeatherLabel.setText ( feedsParsed[8] );
        resilientWeatherServiceClientUserInterface.nextFifthDayWeatherLabel.setText ( feedsParsed[9] );
        resilientWeatherServiceClientUserInterface.nextSixthDayWeatherLabel.setText ( feedsParsed[10] );  
    }

    @Override
    public void setCurrentWeather ( String currentWeather ) throws RemoteException
    {
        // Split the feed input by lines to represent it in the client user interface
        String[] currentWeatherParsed = currentWeather.split("\n");
        Pattern pattern ;
        Matcher matcher ;
        
        // Loop through the array and remove the xml tags <title> and <\title>
        for ( int i = 0; i < currentWeatherParsed.length; i++ ) 
        {
            if ( currentWeatherParsed[i].contains ( "currentimg" ) )
            {
                pattern = Pattern.compile("src=\"/[a-zA-Z]+/[0-9]+.[a-zA-Z]+\"");
                matcher = pattern.matcher(currentWeatherParsed[i]);
                
                if (matcher.find()) 
                {
                    currentWeatherParsed[i] = matcher.group(0) ;
                    System.out.println("PARSED: " + currentWeatherParsed[i] );
                }
            }
            else if ( i == 1 )
            {   
                pattern = Pattern.compile(">-?[0-9]+");
                matcher = pattern.matcher(currentWeatherParsed[i]);
                
                if (matcher.find()) 
                {
                    currentWeatherParsed[i] = "Wind Chill: " + matcher.group(0).substring ( 1 ) ;
                    System.out.println("PARSED: " + currentWeatherParsed[i] );
                }
            }
            else if ( i == 2 )
            {
                pattern = Pattern.compile(">[a-zA-Z]+\\s?[a-zA-Z]+<");
                matcher = pattern.matcher(currentWeatherParsed[i]);
                
                if (matcher.find()) 
                {
                    String temp = matcher.group(0).substring ( 1 );
                    currentWeatherParsed[i] = "Condition: " + temp.substring(0, temp.length()-1)  ;
                    System.out.println("PARSED: " + currentWeatherParsed[i] );
                }
            }
            else if ( i == 3 )
            {
                pattern = Pattern.compile(">-?[0-9]+");
                matcher = pattern.matcher(currentWeatherParsed[i]);
                
                if (matcher.find()) 
                {
                    currentWeatherParsed[i] = "Temperature: " + matcher.group(0).substring ( 1 ) + "\u00b0" + "C" ;
                    System.out.println("PARSED: " + currentWeatherParsed[i] );
                }
            }
            else if ( i == 4 )
            {
                pattern = Pattern.compile("[0-9]+%");
                matcher = pattern.matcher(currentWeatherParsed[i]);
                
                if (matcher.find()) 
                {
                    currentWeatherParsed[i] = "Humidity: " + matcher.group(0) ;
                    System.out.println("PARSED: " + currentWeatherParsed[i] );
                }
            }
        }
        
        resilientWeatherServiceClientUserInterface.windChill.setText ( currentWeatherParsed[1] );
        resilientWeatherServiceClientUserInterface.condition.setText ( currentWeatherParsed[2] );
        resilientWeatherServiceClientUserInterface.temp.setText ( currentWeatherParsed[3] );
        resilientWeatherServiceClientUserInterface.humidity.setText ( currentWeatherParsed[4] );
    }

    @Override
    public String getCity ()
    {
        return city;
    }
}
