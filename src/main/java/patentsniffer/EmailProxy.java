package patentsniffer;

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
	private static String from = "anthony_fn@163.com";
	private static String psswd = "Test1234";
	private static String server = "smtp.163.com";
	
	
	public static void sendEmail( String content ) throws MessagingException
	{
		final Properties props = new Properties();
        /*
         * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
         * mail.user / mail.from
         */
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.host", server);
        // 发件人的账号
        props.put("mail.user", from);
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", psswd);

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
                props.getProperty("mail.user"));
        message.setFrom(form);

        // 设置收件人
        InternetAddress to = new InternetAddress("anthony_fn@163.com");
        message.setRecipient(RecipientType.TO, to);

        // 设置抄送
        //InternetAddress cc = new InternetAddress("luo_aaaaa@yeah.net");
        //message.setRecipient(RecipientType.CC, cc);

        // 设置密送，其他的收件人不能看到密送的邮件地址
        //InternetAddress bcc = new InternetAddress("aaaaa@163.com");
        //message.setRecipient(RecipientType.CC, bcc);

        // 设置邮件标题
        message.setSubject("测试邮件");

        // 设置邮件的内容体
        message.setContent(content, "text/html;charset=UTF-8");

        // 发送邮件
        Transport.send(message);
	}
}
