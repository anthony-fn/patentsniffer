package patentsniffer;

import java.io.IOException;
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
    public static String getHtml( String source )
    {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(source);
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gb2312");
        try {
            // Execute the method.
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
              System.err.println("Method failed: " + method.getStatusLine());
            }

            // Read the response body.
            byte[] responseBody = method.getResponseBody();

            // Deal with the response.
            // Use caution: ensure correct character encoding and is not binary data
            System.out.println(new String(responseBody));

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
    
    public static void main(String[] args)
    {
        logger.info(HtmlDownloader.getHtml("http://www.sipo-reexam.gov.cn/"));
    }
}
