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
    //private MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager(); 
    static String lineSeparator = System.getProperty("line.separator", "\n");  
    public static void writeDown()
    {
    	File file = new File("/Users/Anthony/GitHub/patentsniffer/tempData.txt");
    	// "http://app.sipo-reexam.gov.cn/reexam_out/index2.jsp"
    	if( file.exists() )
    		file.delete();
    	OutputStream os;
		try {
			os = new FileOutputStream(file);
			String content = getHtml("http://app.sipo-reexam.gov.cn/reexam_out/index2.jsp");
			os.write(content.getBytes("UTF-8"));
			os.flush();
	    	os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	   
    }
    
    static String inputStream2String(InputStream is){
    	   BufferedReader in = new BufferedReader(new InputStreamReader(is));
    	   StringBuffer buffer = new StringBuffer();
    	   String line = "";
    	   try {
			while ((line = in.readLine()) != null){
			     buffer.append(line);
			     buffer.append(lineSeparator);
			   }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   return buffer.toString();
    	}
    
    private static String  getHtml( String source )
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
            System.err.println("Fatal protocol violation: " + e.getMessage());
            e.printStackTrace();
          } catch (IOException e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            e.printStackTrace();
          } finally {
            // Release the connection.
            method.releaseConnection();
          }
		return "";  
    }
}
