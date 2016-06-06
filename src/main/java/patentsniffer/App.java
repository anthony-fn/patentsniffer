package patentsniffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

    static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        System.out.println("专利嗅探器启动...");
        logger.info("专利嗅探器启动...");
        
        try {
            PatentConfig.init("config/configuration.properties");
        } catch (PatentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("专利嗅探器关闭...");
        logger.info("专利嗅探器关闭...");
    }

}
