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
         * ���õ����ԣ� mail.store.protocol / mail.transport.protocol / mail.host /
         * mail.user / mail.from
         */
        // ��ʾSMTP�����ʼ�����Ҫ���������֤
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.host", this.server);
        // �����˵��˺�
        props.put("mail.user", this.user);
        // ����SMTP����ʱ��Ҫ�ṩ������
        props.put("mail.password", passwd);

        // ������Ȩ��Ϣ�����ڽ���SMTP���������֤
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // �û���������
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // ʹ�û������Ժ���Ȩ��Ϣ�������ʼ��Ự
        Session mailSession = Session.getInstance(props, authenticator);
        // �����ʼ���Ϣ
        MimeMessage message = new MimeMessage(mailSession);
        // ���÷�����
        InternetAddress form = new InternetAddress(
               this.user);
        message.setFrom(form);

        // �����ռ���
        InternetAddress to = new InternetAddress(this.toe);
        message.setRecipient(RecipientType.TO, to);
        message.addRecipient(RecipientType.TO, new InternetAddress(this.user));

        // ���ó���
        //InternetAddress cc = new InternetAddress("luo_aaaaa@yeah.net");
        //message.setRecipient(RecipientType.CC, cc);

        // �������ͣ��������ռ��˲��ܿ������͵��ʼ���ַ
        //InternetAddress bcc = new InternetAddress("aaaaa@163.com");
        //message.setRecipient(RecipientType.CC, bcc);

        // �����ʼ�����
        message.setSubject("ר������� "+this.getCurrentDateString());

        // �����ʼ���������
        message.setContent(content, "text/plain;charset=UTF-8");

        // �����ʼ�
        Transport.send(message);
	}
}
