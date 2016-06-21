package patentsniffer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

public class TasksParser {
	
	public static List<PaterntUnit> getUnits()
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
	            	  if( str.contains("<title>������</title>"))
	            		  flag = true;
	              }
	        }
	        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public static void parse()
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
		//������ʼ
		SimpleNodeIterator iterator = list.elements();
		while (iterator.hasMoreNodes()) {
			Node node = iterator.nextNode();
			//�õ��ýڵ���ӽڵ��б�
			NodeList childList = node.getChildren();
			//���ӽڵ�Ϊ�գ�˵����ֵ�ڵ�
			if (null == childList)
			{
				//�õ�ֵ�ڵ��ֵ
				String result = node.toPlainTextString();			
				
				System.out.println(result);
			} //end if
			//���ӽڵ㲻Ϊ�գ����������ú��ӽڵ�
			else 
			{
				processNodeList(childList);
			}//end else
		}//end wile
	}
	
	public static void main(String[] args)
	{
		TasksParser.parse();
	}
}
