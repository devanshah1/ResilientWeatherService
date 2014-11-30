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


public class ResilientWeatherServiceClientUI extends JFrame {


    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static String selectedProvince = "Ontario";
    public static String selectedCity = "Oshawa";
    public static JComboBox<String> supportedProvincesList;
    public static JComboBox<String> supportedCitiesList;
    public static String[] supportedProvinces = new String[] { "Ontario" };
    public static String[] supportedCities = new String[] { "Oshawa", "Vaughan" };
    public static JToolBar vertical;
    public static JPanel panel;
    public static JTextArea weatherTextArea;
    
    /**
     * 
     */
    public ResilientWeatherServiceClientUI() {

        constructResilientWeatherServiceClientUI();
    }

    /**
     * 
     */
    public void constructResilientWeatherServiceClientUI()
    {
        JMenuBar topMenuBar = new JMenuBar();
        JMenu discription = new JMenu("Weather Information Feeds/Current Weather");
        weatherTextArea = new JTextArea();
        panel = new JPanel();
        panel.setPreferredSize ( new Dimension(400, 440) );

        topMenuBar.add(discription);
        setJMenuBar(topMenuBar);
        
        constructComboBoxForProvincesAndCities();
        
        constructVerticalWeatherSelectionBar();
        
        weatherTextArea.setText ( "WELCOME ... Go to Setting to configure your weather settings." );
        
        add(weatherTextArea, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);
        add(vertical, BorderLayout.WEST);
        
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
        vertical = new JToolBar(JToolBar.VERTICAL);
        vertical.setFloatable(false);
        vertical.setMargin(new Insets(10, 5, 5, 5));

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
                remove(panel);
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
                remove(panel);
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
                panel.add(supportedProvincesList, BorderLayout.CENTER);
                panel.add(supportedCitiesList, BorderLayout.CENTER);
                add(panel, BorderLayout.CENTER);
                pack();
                repaint ();
            }
        } );

        vertical.add(selectFeeds);
        vertical.add(selectCurrentWeather);
        vertical.add(selectSettings);
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