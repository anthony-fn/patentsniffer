package patentsniffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PatentConfig {
    
    private static Properties props = new Properties();
    
    private static String targetURL = "";
    private static String sourceURL = "";
    private static String output = ""; 
    private static String tempData = "";
    private static String previousTasks = "";
    
    
    public static String getTargetURL()
    {
        return targetURL;
    }
    
    public static String getSourceURL()
    {
        return sourceURL;
    }
    
    public static String getOutput()
    {
        return output;
    }
    
    private static boolean isValid()
    {
        if( targetURL.isEmpty() || sourceURL.isEmpty() || output.isEmpty())
            return false;
        
        return true;
    }
    
    private static void createDataFolderIfNonexists()
    {
    	File data = new File( "data" );
    	
    	if( data.exists() && data.isDirectory() )
    		return;
    	data.delete();
    	data.mkdir();
    }
    
    public static void init( String file ) throws PatentException
    {
        File config = new File(file);
        
        if( !config.isFile() || !config.exists() )
            throw new PatentException("Can't find config file: "+file);        

        try {
        	createDataFolderIfNonexists();
            InputStream in =  new FileInputStream(file);
            props.load(in);
            sourceURL = props.getProperty(Statics.SOURCEURL);
            targetURL = props.getProperty(Statics.TARGETURL);
            output = props.getProperty(Statics.OUTPUT);
            tempData = props.getProperty(Statics.TEMPDATA);
            previousTasks = props.getProperty(Statics.PREVIOUSTASKS);
            in.close();
        } catch (IOException e) {
            throw new PatentException("Can't get proper configuration information", e);
        }
        
        if( !isValid() )
            throw new PatentException("The configuration information is invalid!");
        
        
    }

	public static String getTempData() {
		return tempData;
	}

	public static void setTempData(String tempData) {
		PatentConfig.tempData = tempData;
	}

	public static String getPreviousTasks() {
		return previousTasks;
	}

	public static void setPreviousTasks(String previousTasks) {
		PatentConfig.previousTasks = previousTasks;
	}

}
