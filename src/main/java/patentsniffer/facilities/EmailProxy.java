package patentsniffer.facilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailProxy {
	private String frome = "anthony_fn@163.com";
	private String toe = "";
	private String user = "";
	private String passwd = "Test1234";
	private String server = "smtp.163.com";
	
	public EmailProxy( String fromEmail, String toEmail, String emailServer, String emailServerUser, String emailServerPassword )
	{
		frome = fromEmail;
		toe = toEmail;
		user = emailServerUser;
		passwd = emailServerPassword;
		server = emailServer;
	}
	
	private String getCurrentDateString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		
		return sdf.format(new Date());
	}
	public void sendEmail( String content ) throws MessagingException
	{
		final Properties props = new Properties();
        /*
         * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
         * mail.user / mail.from
         */
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.host", this.server);
        // 发件人的账号
        props.put("mail.user", this.user);
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", passwd);

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(
               this.user);
        message.setFrom(form);

        // 设置收件人
        InternetAddress to = new InternetAddress(this.toe);
        message.setRecipient(RecipientType.TO, to);
        message.addRecipient(RecipientType.TO, new InternetAddress(this.user));

        // 设置抄送
        //InternetAddress cc = new InternetAddress("luo_aaaaa@yeah.net");
        //message.setRecipient(RecipientType.CC, cc);

        // 设置密送，其他的收件人不能看到密送的邮件地址
        //InternetAddress bcc = new InternetAddress("aaaaa@163.com");
        //message.setRecipient(RecipientType.CC, bcc);

        // 设置邮件标题
        message.setSubject("专利检测结果 "+this.getCurrentDateString());

        // 设置邮件的内容体
        message.setContent(content, "text/plain;charset=UTF-8");

        // 发送邮件
        Transport.send(message);
	}
}
