package patentsniffer;

public class PaterntUnit {

	private String number = "";
	private String href = "";
	private String name = "";
	private String date = "";
	private String location = "";
	
	/*public static void main( String [] args )
	{
		String content = "<td>&nbsp;110318</td>"
+"<td>"
+"<a href=\"http://app.sipo-reexam.gov.cn:80/reexam_out/searchdoc/decidedetail.jsp?jdh=110318&lx=fm\" target=\"_blank\">"
     +"&nbsp;用于减少去除胶带工艺中残留的UV曝光法</a></td>"
+"<td style=\"color: #999999\">&nbsp;2016-06-06</td>";
		
		PaterntUnit temp = new PaterntUnit(content);
	}*/
	
	public PaterntUnit()
	{
		super();
	}
	
	public PaterntUnit( String content )
	{
		int start = content.indexOf(";");
		int end = content.indexOf("</td>");
		String first = content.substring(start+1, end);
		number = first.replace(".", "");
		
		start = content.indexOf("<a href=\"");
		end = content.indexOf("\" target=\"");
		String second = content.substring(start+9, end);
		href = second;
		
		content = content.substring(50);
		start = content.indexOf("&nbsp;");
		end = content.indexOf("</a></td>");
		String third = content.substring(start+6, end);
		name = third;
		
		start = content.lastIndexOf(">&nbsp;");
		end = content.lastIndexOf("</td>");
		String fourth = content.substring(start+7, end);
		date = fourth;
	}
	public String getNumber()
	{
		return number;
	}
	public void setNumber(String number )
	{
		this.number = number;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getHref()
	{
		return href;
	}
	
	public void setHref( String href )
	{
		this.href = href;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public void setDate( String date )
	{
		this.date = date;
	}
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(number+";"+name+";"+date+";"+location+";"+href);
		return sb.toString();
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
