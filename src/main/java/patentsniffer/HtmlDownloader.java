package patentsniffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HtmlDownloader {
	
    private static final Logger logger = LogManager.getLogger();
    
    private String targetFile = "";
    private String sourceURL = "";
    
    // tempData.txt
    // "http://app.sipo-reexam.gov.cn/reexam_out/index2.jsp"
    public HtmlDownloader( String sourceURL, String targetFile )
    {
    	this.targetFile = targetFile;
    	this.sourceURL = sourceURL;
    }
    
    public void loadHTMLintoFile() throws PatentException
    {
    	File file = new File(targetFile);
    	if( file.exists() )
    		file.delete();
    	
    	OutputStream os;
		try {
			os = new FileOutputStream(file);
			String content = getHtml(sourceURL);
			os.write(content.getBytes("UTF-8"));
			os.flush();
	    	os.close();
		} catch (FileNotFoundException e) {
			logger.error("There's not file named "+targetFile);
			throw new PatentException( "There's not file named "+targetFile, e);
		} catch (IOException e) {
			throw new PatentException( e.getMessage(), e);
		}   	   
    }
    
    private String inputStream2String(InputStream is) throws PatentException{
    	   BufferedReader in = new BufferedReader(new InputStreamReader(is));
    	   StringBuffer buffer = new StringBuffer();
    	   String line = "";
    	   try {
			while ((line = in.readLine()) != null){
			     buffer.append(line);
			     buffer.append(Statics.LINE_SEPARATOR);
			   }
		} catch (IOException e) {
			logger.error("InputSteam from HTML to String filed.");
			throw new PatentException("InputSteam from HTML to String filed.", e);
		}
    	   return buffer.toString();
    	}
    
    private String getHtml( String source ) throws PatentException
    {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(source);
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK");
        try {
            // Execute the method.
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
              System.err.println("Method failed: " + method.getStatusLine());
            }

            return inputStream2String(method.getResponseBodyAsStream());

          } catch (HttpException e) {
            logger.error("Fatal protocol violation: " + e.getMessage());
            throw new PatentException("Fatal protocol violation: " + e.getMessage(), e);
          } catch (IOException e) {
            logger.error("Fatal transport error: " + e.getMessage());
            throw new PatentException("Fatal transport error: " + e.getMessage(), e);
          } finally {
            // Release the connection.
            method.releaseConnection();
          }  
    }
}
