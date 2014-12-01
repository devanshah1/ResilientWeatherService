import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
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
    
    // Variable deceleration
    public static String selectedProvince = "Ontario";
    public static String selectedCity = "Oshawa";
    
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
     * 
     */
    public void constructResilientWeatherServiceClientUI()
    {
        weatherTopMenuBar = new JMenuBar();
        weatherTopDescription = new JMenu("Weather Information Feeds/Current Weather");
        weatherTextArea = new JTextArea();
        weatherInformationPanel = new JPanel();
        weatherInformationPanel.setPreferredSize ( new Dimension(400, 440) );

        weatherTopMenuBar.add(weatherTopDescription);
        setJMenuBar(weatherTopMenuBar);
        
        constructComboBoxForProvincesAndCities();
        
        constructVerticalWeatherSelectionBar();
        
        weatherTextArea.setText ( "WELCOME ... Go to Setting to configure your weather settings." );
        
        add(weatherTextArea, BorderLayout.CENTER);
        add(weatherInformationPanel, BorderLayout.CENTER);
        add(verticalWeatherOptionsBar, BorderLayout.WEST);
        
        pack();
        setSize(500, 500);
        setTitle("Resilient Weather Service");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    /**
     * 
     */
    public void constructComboBoxForProvincesAndCities()
    {
        // Create a combo box with items specified in the String array:
        supportedProvincesList = new JComboBox<String>(supportedProvinces);
        supportedCitiesList = new JComboBox<String>(supportedCities);
        
        supportedProvincesList.setForeground ( Color.BLUE );
        supportedProvincesList.setFont(new Font("Arial", Font.BOLD, 14));
        supportedProvincesList.setMaximumRowCount(10);
        
        supportedCitiesList.setForeground ( Color.BLUE );
        supportedCitiesList.setFont(new Font("Arial", Font.BOLD, 14));
        supportedCitiesList.setMaximumRowCount(10);
        
        // add an event listener for the combo box
        supportedProvincesList.addActionListener(new ActionListener() {

                @SuppressWarnings ( "unchecked" )
                @Override
                public void actionPerformed(ActionEvent event) 
                {
                    JComboBox<String> combo = (JComboBox<String>) event.getSource();
                    selectedProvince = (String) combo.getSelectedItem();
                    
                    System.out.println("Province: " + selectedProvince);
                }
        });
        
        // add an event listener for the combo box
        supportedCitiesList.addActionListener(new ActionListener() {

                @SuppressWarnings ( "unchecked" )
                @Override
                public void actionPerformed(ActionEvent event) 
                {
                    JComboBox<String> combo = (JComboBox<String>) event.getSource();
                    selectedCity = (String) combo.getSelectedItem();
                    
                    System.out.println("City: " + selectedCity);
                }
        });
    }
    
    /**
     * 
     */
    public void constructVerticalWeatherSelectionBar()
    {
        verticalWeatherOptionsBar = new JToolBar(JToolBar.VERTICAL);
        verticalWeatherOptionsBar.setFloatable(false);
        verticalWeatherOptionsBar.setMargin(new Insets(10, 5, 5, 5));

        ImageIcon feeds = new ImageIcon("feed.png");
        ImageIcon currentWeather = new ImageIcon("currentWeather.png");
        ImageIcon settings = new ImageIcon("settings.png");

        JButton selectFeeds = new JButton(feeds);
        selectFeeds.setBorder(new EmptyBorder(3, 0, 3, 0));

        JButton selectCurrentWeather = new JButton(currentWeather);
        selectCurrentWeather.setBorder(new EmptyBorder(3, 0, 3, 0));
        
        JButton selectSettings = new JButton(settings);
        selectSettings.setBorder(new EmptyBorder(3, 0, 3, 0));

        selectFeeds.addActionListener ( new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent event )
            {
                weatherTextArea.setText ( "FEEDS ARE HERE" );
                remove(weatherInformationPanel);
                add(weatherTextArea, BorderLayout.CENTER);
                repaint ();
            }
        } );
        
        selectCurrentWeather.addActionListener ( new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent event )
            {
                weatherTextArea.setText ( "CURRENT WEATHER HERE" );
                remove(weatherInformationPanel);
                add(weatherTextArea, BorderLayout.CENTER);
                repaint ();
            }
        } );
        
        selectSettings.addActionListener ( new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent event )
            {
                remove(weatherTextArea);
                weatherInformationPanel.add(supportedProvincesList, BorderLayout.CENTER);
                weatherInformationPanel.add(supportedCitiesList, BorderLayout.CENTER);
                add(weatherInformationPanel, BorderLayout.CENTER);
                pack();
                repaint ();
            }
        } );

        verticalWeatherOptionsBar.add(selectFeeds);
        verticalWeatherOptionsBar.add(selectCurrentWeather);
        verticalWeatherOptionsBar.add(selectSettings);
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                ResilientWeatherServiceClientUI ex = new ResilientWeatherServiceClientUI();
                ex.setVisible(true);
            }
        });
    }
}