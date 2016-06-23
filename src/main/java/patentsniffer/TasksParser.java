package patentsniffer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TasksParser {
	private static final Logger logger = LogManager.getLogger();
	
	public static List<PaterntUnit> getFirstUnits( String file ) throws PatentException
	{
		List<PaterntUnit> result = new LinkedList<PaterntUnit>();
		FileReader reader = null;
		BufferedReader br = null;
		try {
			reader = new FileReader(file);
			br = new BufferedReader(reader);
		       
	        String str = null;
	        boolean flag = false;
	        String content = "";
	        while((str = br.readLine()) != null) {
	        	if( str.contains("</table>"))
	        		break;
	        	else if( flag && str.contains("</tr>"))
	        	{
	        		result.add(PaternUnitFactory.getUnitFirst(content));
	        		content = "";
	        		flag = false;
	        		continue;
	        	}
	              if( flag )
	              {
	            	 content += str;
	              }
	              else
	              {
	            	  if( str.startsWith("<tr><td>"))
	            	  {
	            		  flag = true;
	            		  content+=str;
	            	  }
	              }
	        }
	        
		} catch (FileNotFoundException e) {
			logger.error("Can't find the tempData file for TasksParser "+ file,e);
			throw new PatentException("Can't find the tempData file for TasksParser "+ file,e);
		} catch (IOException e) {
			logger.error("parse the tempData file for TasksParser error "+ e.getMessage(),e);
			throw new PatentException("parse the tempData file for TasksParser error "+ e.getMessage(),e);
		}finally{
			try {
				if( reader != null)
				   reader.close();
				br.close();
			} catch (IOException e) {
			}
			
		}
		
		return result;
	}
	public static List<PaterntUnit> getSecondUnits()
	{
		List<PaterntUnit> result = new LinkedList<PaterntUnit>();
		FileReader reader = null;
		BufferedReader br = null;
		try {
			reader = new FileReader("/Users/Anthony/GitHub/patentsniffer/tempData.txt");
			br = new BufferedReader(reader);
		       
	        String str = null;
	        boolean flag = false;
	        boolean inside = false;
	        String content = "";
	        while((str = br.readLine()) != null) {
	        	if( inside )
	        	{
	        		if( str.equals("<td>"))
	        			continue;
	        		if( str.equalsIgnoreCase("<td></tr>") )
	        		{
	        			result.add(new PaterntUnit(content));
	        			content = "";
	        			inside = false;
	        		}
	        		content += str;
	        	}
	              if( flag )
	              {
	            	  
	            	  if( str.startsWith("<td>&nbsp;") )
	            	  {
	            		  content += str;
	            		  inside = true;
	            		  
	            	  }
	            	  continue;
	              }
	              else
	              {
	            	  if( str.contains("<title>审查决定</title>"))
	            		  flag = true;
	              }
	        }
	        
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if( reader != null)
				   reader.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        
        for( PaterntUnit temp : result)
        {
        	System.out.println(temp.toString());
        }
       
        
		
		return result;
	}
	/*public static void parse()
	{
		try {
			//Parser parser = new Parser();
			Parser parser = new Parser("http://app.sipo-reexam.gov.cn/reexam_out/index2.jsp");
			parser.setEncoding("ISO-8859-1");
			NodeList list = parser.parse(null);
			
			processNodeList(list);
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void processNodeList(NodeList list) {
		//迭代开始
		SimpleNodeIterator iterator = list.elements();
		while (iterator.hasMoreNodes()) {
			Node node = iterator.nextNode();
			//得到该节点的子节点列表
			NodeList childList = node.getChildren();
			//孩子节点为空，说明是值节点
			if (null == childList)
			{
				//得到值节点的值
				String result = node.toPlainTextString();			
				
				System.out.println(result);
			} //end if
			//孩子节点不为空，继续迭代该孩子节点
			else 
			{
				processNodeList(childList);
			}//end else
		}//end wile
	}*/
	
	
}
