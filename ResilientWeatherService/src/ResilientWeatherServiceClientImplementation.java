import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains the method implementation that are defined in ResilientWeatherServiceClientInterface.
 * @author Devan Shah 100428864
 *
 */
public class ResilientWeatherServiceClientImplementation extends UnicastRemoteObject implements ResilientWeatherServiceClientInterface
{

    // Default serialization ID
    private static final long serialVersionUID = 1L ;

    // Variable/Object deceleration
    public String province;
    public String city;
    public ResilientWeatherServiceClientUI resilientWeatherServiceClientUserInterface;
    
    /**
     * Constructor of the class, used to also set some variables and object on object creation
     * @param selectedCity - The default city that is selected on the user interface.
     * @param selectedProvince - The default Province that is selected on the user interface
     * @param resilientWeatherServiceClientUserInterface - The user interface object so that can set 
     *                                                     JSwing objects for the user interface when 
     *                                                     there are new feeds or current weather is requested.
     * @throws RemoteException
     */
    public ResilientWeatherServiceClientImplementation ( String selectedProvince , String selectedCity, ResilientWeatherServiceClientUI resilientWeatherServiceClientUserInterface  ) throws RemoteException
    {
        super () ;
        
        // Set the required variables and objects as global that were passed with object creation.
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
        // Print out the message to the client that was provided from the server
        System.out.println ( message ) ;
        
        return message ;
    }
    
    /**
     * This function is used to set the province on the client interface so 
     * that the server has access to it in the case that it changes.
     * @param newProvince - The new province to be set when there is a change made by the user.
     */
    public void setProvince ( String newProvince )
    {
        province = newProvince;
    }
    
    /**
     * This function is used to set the city on the client interface so 
     * that the server has access to it in the case that it changes.
     * @param newCity - The new city to be set when there is a change made by the user.
     */
    public void setCity ( String newCity )
    {
        city = newCity;
    }

    /**
     * This function is used to parse the feed data and then set the feeds 
     * in the Resilient Weather Service user interface.
     * @param feeds - Unparsed feed data
     * @throws RemoteException
     */
    @Override
    public void setFeeds ( String feeds ) throws RemoteException
    {
        // Split the feed input by lines to represent it in the client user interface by label
        String[] feedsParsed = feeds.split("\n");
        
        // Loop through the array and remove the xml tags <title> and <\title>
        for ( int i = 0; i < feedsParsed.length; i++ ) 
        {
            // Get the left hand side index where "<title>" is found
            int left = feedsParsed[i].indexOf("<title>");
            // Get the right hand side index where "</title>" is found
            int right = feedsParsed[i].indexOf("</title>"); 
            
            // This if block is used to correctly set the feeds for the data
            if ( feedsParsed[i].contains ( "xB0;" ) ) 
            {
                /**
                 * Remove the tags "<title>" and "<\title>" and convert the 
                 * temp values to degrees Celsius. Also represents the degree
                 * symbol in the user interface.
                 */
                feedsParsed[i] = feedsParsed[i].substring(left+7, right-7) + "\u00b0" + "C";
            }
            else
            {
                // Remove the tags "<title>" and "<\title>"
                feedsParsed[i] = feedsParsed[i].substring(left+7, right);
            }
        }
        
        // Set all the data feed information into the User interface after the feed data is parsed
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

    /**
     * This function is used to parse the current weather data and then set the current
     * weather in the Resilient Weather Service user interface.
     * @param weatherData - Unparsed current weather data
     * @throws RemoteException
     */
    @Override
    public void setCurrentWeather ( String currentWeather ) throws RemoteException
    {
        // Split the feed input by lines to represent it in the client user interface
        String[] currentWeatherParsed = currentWeather.split("\n");
        
        // Object deceleration for regex matching
        Pattern pattern ;
        Matcher matcher ;
        
        // Loop through the array and remove XML data that is present in the important lines.
        for ( int i = 0; i < currentWeatherParsed.length; i++ ) 
        {
            // Parse settings for the image source
            if ( i == 0 )
            {
                // Match the regex and extract the phrase
                pattern = Pattern.compile("src=\"/[a-zA-Z]+/[0-9]+.[a-zA-Z]+\"");
                matcher = pattern.matcher(currentWeatherParsed[i]);
                
                // When the match is found set the array index with the new data
                if ( matcher.find() ) 
                {
                    currentWeatherParsed[i] = matcher.group(0) ;
                }
            }
            // Parse information for wind chill of the weather
            else if ( i == 1 )
            {   
                // Match the regex and extract the phrase
                pattern = Pattern.compile(">-?[0-9]+");
                matcher = pattern.matcher(currentWeatherParsed[i]);
                
                // When the match is found set the array index with the new data
                if ( matcher.find() ) 
                {
                    currentWeatherParsed[i] = "Wind Chill: " + matcher.group(0).substring ( 1 ) ;
                }
            }
            // Parse information for the weather condition
            else if ( i == 2 )
            {
                // Match the regex and extract the phrase
                pattern = Pattern.compile(">[a-zA-Z]+\\s?[a-zA-Z]+<");
                matcher = pattern.matcher(currentWeatherParsed[i]);
                
                // When the match is found set the array index with the new data
                if ( matcher.find() ) 
                {
                    // Remove the trailing character
                    String temp = matcher.group(0).substring ( 1 );
                    currentWeatherParsed[i] = "Condition: " + temp.substring(0, temp.length()-1)  ;
                }
            }
            // Parse information for the Temperature values
            else if ( i == 3 )
            {
                // Match the regex and extract the phrase
                pattern = Pattern.compile(">-?[0-9]+");
                matcher = pattern.matcher(currentWeatherParsed[i]);
                
                // When the match is found set the array index with the new data
                if ( matcher.find() ) 
                {
                    /**
                     * Convert the temp values to degrees Celsius. Also represents the degree
                     * symbol in the user interface. 
                     */
                    currentWeatherParsed[i] = "Temperature: " + matcher.group(0).substring ( 1 ) + "\u00b0" + "C" ;
                }
            }
            // Parse information for the humidity values
            else if ( i == 4 )
            {
                // Match the regex and extract the phrase
                pattern = Pattern.compile("[0-9]+%");
                matcher = pattern.matcher(currentWeatherParsed[i]);
                
                // When the match is found set the array index with the new data
                if ( matcher.find() ) 
                {
                    currentWeatherParsed[i] = "Humidity: " + matcher.group(0) ;
                }
            }
        }
        
        // Set all the data current weather information into the User interface after the current weather data is parsed
        resilientWeatherServiceClientUserInterface.currentWeatherWindChillLabel.setText ( currentWeatherParsed[1] );
        resilientWeatherServiceClientUserInterface.currentWeatherConditionLabel.setText ( currentWeatherParsed[2] );
        resilientWeatherServiceClientUserInterface.currentWeatherTemperatureLabel.setText ( currentWeatherParsed[3] );
        
        if ( currentWeatherParsed.length > 4 )
        {
            resilientWeatherServiceClientUserInterface.currentWeatherHumidityLabel.setText ( currentWeatherParsed[4] );    
        }
    }

    /**
     * Return the current city that is set.
     * @return city - the city that is set in the object
     */
    @Override
    public String getCity ()
    {
        return city;
    }
}
