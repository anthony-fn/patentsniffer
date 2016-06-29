package patentsniffer.facilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import patentsniffer.PatentConfig;
import patentsniffer.PatentException;
import patentsniffer.PatentUnit;
import patentsniffer.PatentUnitFactory;

public class UpdateFinder {
	
	private static final Logger logger = LogManager.getLogger();
	
	private static Map<String, PatentUnit> map = null;
	private static void updatePrevious( List<PatentUnit> latest ) throws PatentException
	{
		File previous = new File(PatentConfig.getPreviousTasks());
		
		if( previous.exists() )
			previous.delete();
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			 fw = new FileWriter(previous);
			 bw = new BufferedWriter(fw);
			 
			 for( PatentUnit unit : latest )
			 {
				 bw.write(unit.toString());
				 bw.newLine();
			 }
			 
			 bw.flush();
		} catch (IOException e) {
			logger.error("Exception during update previous tasks file: "+e.getMessage(), e);
			throw new PatentException("Exception during update previous tasks file: "+e.getMessage(), e);
		}
		finally{
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				
			}
			
		}
	}
	
	private static void loadPrevious() throws PatentException
	{
		map = new HashMap<String, PatentUnit>();
		
		FileReader reader = null;
		BufferedReader br = null;
		try {
			reader = new FileReader(PatentConfig.getPreviousTasks());
			br = new BufferedReader(reader);
		       
	        String str = null;
	        while((str = br.readLine()) != null) {
	        	PatentUnit temp = PatentUnitFactory.getUnitOneFromPrevious(str);
	        	map.put(temp.getNumber(), temp);
	        }
	        
		} catch (FileNotFoundException e) {
			logger.error("Can't find the previous file for compare ");
			throw new PatentException("Can't find the previous file for compare ");
		} catch (IOException e) {
			logger.error("parse the previous file with error "+ e.getMessage(),e);
			throw new PatentException("parse the previous file with error "+ e.getMessage(),e);
		}finally{
			try {
				if( reader != null)
				   reader.close();
				br.close();
			} catch (IOException e) {
			}
			
		}
	}
	public static List<PatentUnit> find( List<PatentUnit> latest ) throws PatentException
	{
		File previous = new File(PatentConfig.getPreviousTasks());
		
		if( !previous.exists() )
		{
			updatePrevious(latest);
			return latest;
		}
		
		loadPrevious();
		
		List<PatentUnit> result = new LinkedList<PatentUnit>();
		
		for( PatentUnit temp : latest )
		{
			if( !map.containsKey(temp.getNumber()) )
				result.add(temp);
		}
		updatePrevious(latest);
		return result;
	}
	

}
