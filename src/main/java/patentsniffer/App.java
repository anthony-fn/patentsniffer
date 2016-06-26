package patentsniffer;

import java.util.List;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        logger.info("Start to load patent information...");        
        
        try {
            PatentConfig.init("config/configuration.properties");
            logger.info("Task source URL: "+ PatentConfig.getSourceURL());
            logger.info("Change target URL:" + PatentConfig.getTargetURL());
            logger.info("Temp file:" + PatentConfig.getTempData());
            
            HtmlDownloader downloader = new HtmlDownloader(PatentConfig.getSourceURL(), PatentConfig.getTempData());
            downloader.loadHTMLintoFile();
            List<PaterntUnit> tasks = TasksParser.getFirstUnits(PatentConfig.getTempData());
            
            if( tasks == null || tasks.isEmpty() )
            {
            	logger.info("No update at all");
            	System.exit(0);
            }
            
            List<PaterntUnit> newOnes = UpdateFinder.find(tasks);
            
            StringBuilder sb = new StringBuilder();
            sb.append("今天更新专利个数："+newOnes.size());
            sb.append(Statics.LINE_SEPARATOR);
            for( PaterntUnit temp : newOnes )
            {
            	sb.append(temp.toString());
            	sb.append(Statics.LINE_SEPARATOR);
            }
            EmailProxy.sendEmail(sb.toString());
        } catch (PatentException e) {
            logger.error("Exception during running: "+e.getMessage(), e);
        } catch (MessagingException e) {
        	logger.error("Exception during running: "+e.getMessage(), e);
		}
        logger.info("Load patent information finished...");
    }

}
