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
    
    public static void init( String file ) throws PatentException
    {
        File config = new File(file);
        
        if( !config.isFile() || !config.exists() )
            throw new PatentException("找不到配置文件:"+file);        

        try {
            InputStream in =  new FileInputStream(file);
            props.load(in);
            sourceURL = props.getProperty(Statics.SOURCEURL);
            targetURL = props.getProperty(Statics.TARGETURL);
            output = props.getProperty(Statics.OUTPUT);
            in.close();
        } catch (IOException e) {
            throw new PatentException("载入配置文件失败。", e);
        }
        
        if( !isValid() )
            throw new PatentException("没有被正确初始化，请检查配置文件。");
        
        
    }

}
