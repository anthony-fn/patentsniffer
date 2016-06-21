package patentsniffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        logger.info("涓撳埄鍡呮帰鍣ㄥ惎鍔�...");
        logger.warn("warn test");
        logger.error("warn test");
        
        try {
            PatentConfig.init("config/configuration.properties");
            HtmlDownloader.writeDown();
            TasksParser.getUnits();
        } catch (PatentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info("涓撳埄鍡呮帰鍣ㄥ叧闂�...");
    }

}
