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
    private static String fromEmail = "";
    private static String toEmail = "";
    private static String emailServer = "";
    private static String emailServerUser = "";
    private static String emailServerUserPassword = "";

    
    
    
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
    	File data = new File( output );
    	
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
            fromEmail = props.getProperty(Statics.FROMEMAIL);
            toEmail = props.getProperty(Statics.TOEMAIL);
            emailServer = props.getProperty(Statics.EMAILSERVER);
            emailServerUser = props.getProperty(Statics.EMAILSERVERUSER);
            emailServerUserPassword = props.getProperty(Statics.EMAILSERVERPASSWORD);
            
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

	public static String getFromEmail() {
		return fromEmail;
	}

	public static void setFromEmail(String fromEmail) {
		PatentConfig.fromEmail = fromEmail;
	}

	public static String getToEmail() {
		return toEmail;
	}

	public static void setToEmail(String toEmail) {
		PatentConfig.toEmail = toEmail;
	}

	public static String getEmailServer() {
		return emailServer;
	}

	public static void setEmailServer(String emailServer) {
		PatentConfig.emailServer = emailServer;
	}

	public static String getEmailServerUser() {
		return emailServerUser;
	}

	public static void setEmailServerUser(String emailServerUser) {
		PatentConfig.emailServerUser = emailServerUser;
	}

	public static String getEmailServerUserPassword() {
		return emailServerUserPassword;
	}

	public static void setEmailServerUserPassword(String emailServerUserPassword) {
		PatentConfig.emailServerUserPassword = emailServerUserPassword;
	}

}
