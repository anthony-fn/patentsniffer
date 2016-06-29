package patentsniffer;

public class PatentUnitFactory {
	
	
	/*public static void main( String [] args )
	{
		String content = "<tr><td>01103328.2</td><td><a href=\"http://app.sipo-reexam.gov.cn:80/reexam_out/koushen/oraldetail.jsp?id=33618\" target=\"_blank\">包含1,1,1,2,3-五氟丙烷或2,3,3,3-四氟丙烯的组合物</a></td><td style=\"color: #999999\">2016-06-24上午</td><td>第六审理庭";
		
		try {
			PaternUnitFactory.getUnitFirst(content);
		} catch (PatentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public static PatentUnit getUnitFirst( String content ) throws PatentException
	{
		if( content == null || content.isEmpty() )
			throw new PatentException("Empty input for paternt unit factory");
		
		PatentUnit result = new PatentUnit();
		
		int start = "<tr><td>".length();
		int end = content.indexOf("</td>");
		String number = content.substring(start, end).replace(".", "");
		result.setNumber(number);
		
		content = content.substring(content.indexOf("</td>")+5);
		start = content.indexOf("<a href=\"");
		end = content.indexOf("\" target=");
		String href = content.substring(start+9, end);
		result.setHref(href);
		
		content = content.substring(content.indexOf("blank\">"));
		end = content.indexOf("</a>");
		String name = content.substring("blank\">".length(), end);
		result.setName(name);
		
		content = content.substring(content.indexOf("</td>")+"</td>".length());
		start = content.indexOf(">");
		end = content.indexOf("</td>");
		String date = content.substring(start + 1, end);
		result.setDate(date);
		
		content = content.substring(content.indexOf("</td>")+"</td>".length());
		start = content.indexOf("<td>");
		if( content.contains("</td>") )
			end = content.indexOf("</td>");
		else 
			end = content.length();
		String location = content.substring(start + 4, end);
		result.setLocation(location);				
		
		return result;
	}
	public static PatentUnit getUnitSecond( String content ) throws PatentException
	{
		if( content == null || content.isEmpty() )
			throw new PatentException("Empty input for paternt unit factory");
		
		PatentUnit result = new PatentUnit();
		
		int start = content.indexOf(";");
		int end = content.indexOf("</td>");
		String first = content.substring(start+1, end);
		result.setNumber(first.replace(".", ""));
		
		start = content.indexOf("<a href=\"");
		end = content.indexOf("\" target=\"");
		String second = content.substring(start+9, end);
		result.setHref(second);
		
		content = content.substring(50);
		start = content.indexOf("&nbsp;");
		end = content.indexOf("</a></td>");
		String third = content.substring(start+6, end);
		result.setName(third);
		
		start = content.lastIndexOf(">&nbsp;");
		end = content.lastIndexOf("</td>");
		String fourth = content.substring(start+7, end);
		result.setDate(fourth);
		
		return result;
	}
	
	public static PatentUnit getUnitOneFromPrevious( String content )
	{
		if( content == null || content.isEmpty() )
			return null;
		
		String [] contents = content.split(";");
		
		PatentUnit result = new PatentUnit();
		result.setNumber(contents[0]);
		result.setName(contents[1]);
		result.setDate(contents[2]);
		result.setLocation(contents[3]);
		result.setHref(contents[4]);
		return result;
	}

}
