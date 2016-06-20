package patentsniffer;

import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class TasksParser {
	
	public static void parse()
	{
		try {
			Parser parser = new Parser("http://app.sipo-reexam.gov.cn/reexam_out/index2.jsp");
			parser.setEncoding("UTF-8");
			NodeList list = parser.parse(null);
			
			SimpleNodeIterator iterator = list.elements();
			
			while( iterator.hasMoreNodes() )
			{
				NodeList childList = iterator.nextNode().getChildren();
				
				if( null == childList )
				{}
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void processNodeList( NodeList list, String keyword )
	{
		
	}
}
