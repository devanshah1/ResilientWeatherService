import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

/**
 * This class is used to construct the Resilient Weather Service Client UI.
 * Read through the below comments to get more details on the functionality of the
 * class and its dependent functions.
 * @author Devan Shah 100428864 Miguel Arindaeng 100394094
 *
 */
public class ResilientWeatherServiceClientUI extends JFrame 
{
    // Default serialization ID
    private static final long serialVersionUID = 1L;
    
    /**
     *  Make the Client UI keep track of the Resilient Weather Service Server Interface
     *  so that can register and unregister when needed. Also keep track of the the 
     *  clients call back object so can send it to the server to verify when needed.
     */
    public ResilientWeatherServiceServerInterface resilientWeatherServiceServerCaller;
    public ResilientWeatherServiceClientInterface resilientWeatherServiceClientCallBack;
    
    // Variable deceleration
    public String selectedProvince = "Ontario";
    public String selectedCity = "Oshawa";
    
    // Province and Cities Array deceleration
    public static String[] supportedProvinces = new String[] { "Ontario" };
    public static String[] supportedCities = new String[] { "Oshawa", "Vaughan" };
    
    // Swing components used to build the Resilient Weather Service Client UI
    public static JComboBox<String> supportedProvincesList;  // Used to construct a dropdown box for provinces
    public static JComboBox<String> supportedCitiesList;     // Used to construct a dropdown box for cities
    public static JToolBar verticalWeatherOptionsBar;        // Used to construct a side bar for options (feeds, current weather and settings)
    public static JPanel weatherInformationPanel;            // Used to construct a panel which will contain weather information
    public static JTextArea weatherTextArea;                 // Used to construct a text block to store information on weather
    public static JMenuBar weatherTopMenuBar;                // Used to construct a top menu bar for weather (has no function)
    public static JMenu weatherTopDescription;               // Used to construct a menu option for top menu bar (has no function)
    
    // Swing components for feeds
    public JLabel weatherLocationInformation;     // Used to store the location information of the feed that is presented
    public JLabel weatherWatchesAndWarningsLabel; // Used to store the information for watches and warnings
    public JLabel currentWeatherConditionsLabel;  // Used to store the information for the current weather conditions
    public JLabel currentDayWeatherLabel;         // Used to store the information for weather of the current day
    public JLabel currentDayNightWeatherLabel;    // Used to store the information for weather of the current day (nights)
    public JLabel nextFirstDayWeatherLabel;       // Used to store the information for weather for the next first day
    public JLabel nextSecondDayWeatherLabel;      // Used to store the information for weather for the next second day
    public JLabel nextThirdDayWeatherLabel;       // Used to store the information for weather for the next third day
    public JLabel nextForthDayWeatherLabel;       // Used to store the information for weather for the next forth day
    public JLabel nextFifthDayWeatherLabel;       // Used to store the information for weather for the next fifth day
    public JLabel nextSixthDayWeatherLabel;       // Used to store the information for weather for the next sixth day
    
    // Swing components for current weather
    //public static 
    
    /**
     * Default constructor for the ResilientWeatherServiceClientUI class, when 
     * new object of ResilientWeatherServiceClientUI is created it starts to 
     * construct the Resilient Weather Service Client UI for the users.
     */
    public ResilientWeatherServiceClientUI() 
    {
        // Construct the Resilient Weather Service Client UI
        constructResilientWeatherServiceClientUI();
    }

    /**
     * This function is used to construct the main Resilient Weather Service Client user interface.
     * This user interface will provided the ability to configure settings for the weather the 
     * user needs to fetch. This includes creating the feeds, current weather and settings box.
     */
    public void constructResilientWeatherServiceClientUI()
    {
        // Create the basic swing components
        weatherTopMenuBar       = new JMenuBar();
        weatherTopDescription   = new JMenu( "Information on Weather Feeds/Current Weather" );
        weatherTextArea         = new JTextArea();
        weatherInformationPanel = new JPanel();
        
        /**
         * Set the size of the weatherInformationPanel component to make sure that the 
         * data does not go out side of the window or the window does not self adjust 
         * when there is not a lot of data in the panel.
         */
        weatherInformationPanel.setPreferredSize ( new Dimension(400, 440) );

        // Add the weatherTopDescription menu to the Menu bar and set the Menu bar
        weatherTopMenuBar.add(weatherTopDescription);
        setJMenuBar(weatherTopMenuBar);
        
        // Construct the drop-down lists for provinces and cities
        constructComboBoxForProvincesAndCities();
        
        // Construct the side bar for weather options (feeds, current weather and settings) 
        constructVerticalWeatherSelectionBar();
        
        // Set the default text for the text area when first started
        weatherTextArea.setText ( "WELCOME ... Go to Setting to configure your weather settings." );
        
        // Start adding the major components to the JFrame
        add(weatherTextArea, BorderLayout.CENTER);          // Add the weather text area
        add(weatherInformationPanel, BorderLayout.CENTER);  // Add the weather information panel
        add(verticalWeatherOptionsBar, BorderLayout.WEST);  // Add the weather options side bar
        
        setPreferredSize( new Dimension(500, 500) );    // Set the preferred size of the JFrame
        pack();                                         // Pack the components that are added with in the preferred size
        setTitle("Resilient Weather Service");          // Set the title of the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Specify an operation to be performed on close of the JFrame
        setLocationRelativeTo(null);                    // Set the position of the JFrame
    }
    
    /**
     * This function is used to construct the drop-down lists for provinces and 
     * cities to be added for the settings tab on the main user interface. Also
     * sets up the action listeners so when the options are change the selected 
     * provinces and cities can be saved and sent to the server when needed.
     */
    public void constructComboBoxForProvincesAndCities()
    {
        /**
         *  Construct the drop-down boxes based on the data that is in the 
         *  supportedProvinces and supportedCities arrays. For the time being 
         *  these arrays are hard-coded for the purpose of simplicity.
         */
        supportedProvincesList = new JComboBox<String>(supportedProvinces);
        supportedCitiesList = new JComboBox<String>(supportedCities);
        
        // Perform some setup configurations for the supportedProvincesList 
        supportedProvincesList.setForeground ( Color.BLUE );              // Set the foreground to blue
        supportedProvincesList.setFont(new Font("Arial", Font.BOLD, 14)); // Set the fount of the list
        supportedProvincesList.setMaximumRowCount(10);                    // Set the maximum number of items to list before scroll is enabled
        
        // Perform some setup configurations for the supportedCitiesList 
        supportedCitiesList.setForeground ( Color.BLUE );              // Set the foreground to blue
        supportedCitiesList.setFont(new Font("Arial", Font.BOLD, 14)); // Set the fount of the list
        supportedCitiesList.setMaximumRowCount(10);                    // Set the maximum number of items to list before scroll is enabled
        
        /**
         *  Add an event listener for the supportedProvincesList drop-down list 
         *  when the default selection is changed by the user.
         */
        supportedProvincesList.addActionListener(new ActionListener() {

                @SuppressWarnings ( "unchecked" )
                @Override
                /**
                 * This function is used to perform the action of storing the province that is selected.
                 */
                public void actionPerformed(ActionEvent event) 
                {
                    // Get the province list and grab the selected item
                    JComboBox<String> provinceCombo = (JComboBox<String>) event.getSource();
                    selectedProvince = (String) provinceCombo.getSelectedItem();
                    
                    System.out.println("Province: " + selectedProvince);
                }
        });
        
        /**
         *  Add an event listener for the supportedProvincesList drop-down list 
         *  when the default selection is changed by the user.
         */
        supportedCitiesList.addActionListener(new ActionListener() {

                @SuppressWarnings ( "unchecked" )
                @Override
                /**
                 * This function is used to perform the action of storing the city that is selected.
                 */
                public void actionPerformed(ActionEvent event) 
                {
                    // Get the cities list and grab the selected item
                    JComboBox<String> cityCombo = (JComboBox<String>) event.getSource();
                    selectedCity = (String) cityCombo.getSelectedItem();
                    
                    System.out.println("City: " + selectedCity);
                }
        });
    }
    
    /**
     * This function is used to construct the side bar for the weather
     * selection of (feeds, current weather and settings).
     */
    public void constructVerticalWeatherSelectionBar()
    {
        /**
         *  Construct tool bar in the vertical form to provided the options
         *  of feeds, current weather and settings to the user. Also create 
         *  the needed feeds swing objects.
         */
        verticalWeatherOptionsBar      = new JToolBar(JToolBar.VERTICAL);
        weatherLocationInformation     = new JLabel();
        weatherWatchesAndWarningsLabel = new JLabel();
        currentWeatherConditionsLabel  = new JLabel();
        currentDayWeatherLabel         = new JLabel();
        currentDayNightWeatherLabel    = new JLabel();
        nextFirstDayWeatherLabel       = new JLabel();
        nextSecondDayWeatherLabel      = new JLabel();
        nextThirdDayWeatherLabel       = new JLabel();
        nextForthDayWeatherLabel       = new JLabel();
        nextFifthDayWeatherLabel       = new JLabel();
        nextSixthDayWeatherLabel       = new JLabel();
        
        // Set some of the options for the side bar
        verticalWeatherOptionsBar.setFloatable(false);                 // Disable moving of the side bar
        verticalWeatherOptionsBar.setMargin(new Insets(10, 5, 5, 5));  // Set a margin between icons and border

        // Create the icon images for feeds, current weather and settings.
        ImageIcon feeds          = new ImageIcon("feed.png");
        ImageIcon currentWeather = new ImageIcon("currentWeather.png");
        ImageIcon settings       = new ImageIcon("settings.png");

        // Create the button for feeds and set the border.
        JButton selectFeeds = new JButton(feeds);           // Button to select feeds
        selectFeeds.setBorder(new EmptyBorder(3, 0, 3, 0)); // Set the border button

        // Create the button for current weather and set the border.
        JButton selectCurrentWeather = new JButton(currentWeather);  // Button to select current weather
        selectCurrentWeather.setBorder(new EmptyBorder(3, 0, 3, 0)); // Set the border button 
        
        // Create the button for settings and set the border.
        JButton selectSettings = new JButton(settings);        // Button to select settings
        selectSettings.setBorder(new EmptyBorder(3, 0, 3, 0)); // Set the border button

        /**
         *  Add an event listener for the selectFeeds button 
         *  to handle when the button is pressed. When the button is pressed 
         *  the feeds are fetched from the server and it will 
         *  populate the required fields.
         */
        selectFeeds.addActionListener ( new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent event )
            {
                // Remove the un-needed components from the JFrame
                remove(weatherTextArea);
                remove(weatherInformationPanel);
                
                // Re-using the JPanel so need to remove the components
                weatherInformationPanel = new JPanel(new GridLayout(11,1));

                // Set the description label for the feed
                weatherLocationInformation.setText ( "Feed Information for: " + selectedCity + ", " + selectedProvince );
                
                // Add all the necessary feed information to the panel that will be displayed when feed clicked
                weatherInformationPanel.add(weatherLocationInformation, BorderLayout.PAGE_START);
                weatherInformationPanel.add(weatherWatchesAndWarningsLabel, BorderLayout.PAGE_START);
                weatherInformationPanel.add(currentWeatherConditionsLabel, BorderLayout.PAGE_START);
                weatherInformationPanel.add(currentDayWeatherLabel, BorderLayout.PAGE_START);
                weatherInformationPanel.add(currentDayNightWeatherLabel, BorderLayout.PAGE_START);
                weatherInformationPanel.add(nextFirstDayWeatherLabel, BorderLayout.PAGE_START);
                weatherInformationPanel.add(nextSecondDayWeatherLabel, BorderLayout.PAGE_START);
                weatherInformationPanel.add(nextThirdDayWeatherLabel, BorderLayout.PAGE_START);
                weatherInformationPanel.add(nextForthDayWeatherLabel, BorderLayout.PAGE_START);
                weatherInformationPanel.add(nextFifthDayWeatherLabel, BorderLayout.PAGE_START);
                weatherInformationPanel.add(nextSixthDayWeatherLabel, BorderLayout.PAGE_START);
                
                // Add the JPanel to the JFrame 
                add(weatherInformationPanel, BorderLayout.CENTER);
                
                pack();      // Pack the components that are added with in the preferred size
                repaint ();  // Re-draw the JFrame component
            }
        } );
        
        /**
         *  Add an event listener for the selectCurrentWeather button 
         *  to handle when the button is pressed. When the button is pressed 
         *  the current weather is fetched from the server and it will 
         *  populate the required fields.
         */
        selectCurrentWeather.addActionListener ( new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent event )
            {
                remove(weatherInformationPanel);
                add(weatherTextArea, BorderLayout.CENTER);
                weatherTextArea.setText ( "CURRENT WEATHER HERE" );
                repaint ();                                   // Re-draw the JFrame component
            }
        } );
        
        /**
         *  Add an event listener for the selectSettings button 
         *  to handle when the button is pressed. When the button is pressed
         *  the user will be able to select the weather configuration. 
         *  Can select provinces and cities that are available.
         */
        selectSettings.addActionListener ( new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent event )
            {
                // Remove unnecessary components that are in the JFrame
                remove(weatherTextArea);
                remove(weatherInformationPanel);
                
                // Re-using the JPanel so need to remove the components
                weatherInformationPanel = new JPanel();
                
                // Add the supportedProvincesList and supportedCitiesList drop-down lists to the panel
                weatherInformationPanel.add(supportedProvincesList, BorderLayout.CENTER);
                weatherInformationPanel.add(supportedCitiesList, BorderLayout.CENTER);
                
                // Add the JPanel to the JFrame 
                add(weatherInformationPanel, BorderLayout.CENTER);
                
                pack();      // Pack the components that are added with in the preferred size
                repaint ();  // Re-draw the JFrame component
            }
        } );

        // Add the image icons into the vertical weather option bar 
        verticalWeatherOptionsBar.add(selectFeeds);
        verticalWeatherOptionsBar.add(selectCurrentWeather);
        verticalWeatherOptionsBar.add(selectSettings);
    }

    /**
     * This function is used to set the resilientWeatherServiceServerCaller.
     * @param resilientWeatherServiceServerCaller - the server caller object
     */
    public void setResilientWeatherServiceServerCaller ( ResilientWeatherServiceServerInterface resilientWeatherServiceServerCaller )
    {
        // Set resilientWeatherServiceServerCaller
        this.resilientWeatherServiceServerCaller = resilientWeatherServiceServerCaller;
    }
    
    /**
     * This function is used to set the resilientWeatherServiceClientCallBack.
     * @param resilientWeatherServiceClientCallBack - the client call back object
     */
    public void setResilientWeatherServiceClientCallBack ( ResilientWeatherServiceClientInterface resilientWeatherServiceClientCallBack )
    {
        // Set resilientWeatherServiceClientCallBack
        this.resilientWeatherServiceClientCallBack = resilientWeatherServiceClientCallBack;
    }
}