import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ResilientWeatherServiceMonitor
{

    public static void main ( String [] args )
    {

        while (true) 
        {
            try
            {
                Thread.sleep ( 3000 );
            }
            catch ( InterruptedException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("Monitoring ResilientWeatherServiceServer") ; 
            
            File file = new File("monitorFile.txt");
            
            if(file.exists() && !file.isDirectory()) 
            { 
                System.out.println( "Found ResilientWeatherServiceServer not running \n Restarting ResilientWeatherServiceServer ..."); 
            }
        }
    }

    @SuppressWarnings ( "unused" )
    private String executeCommand ( String command )
    {

        StringBuffer output = new StringBuffer ();

        Process p;
        try
        {
            p = Runtime.getRuntime ().exec ( command );
            p.waitFor ();
            BufferedReader reader =
                    new BufferedReader ( new InputStreamReader (
                            p.getInputStream () ) );

            String line = "";
            while ( ( line = reader.readLine () ) != null )
            {
                output.append ( line + "\n" );
            }

        }
        catch ( Exception e )
        {
            e.printStackTrace ();
        }

        return output.toString ();

    }

}
