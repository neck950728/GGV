package member.other;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import utility.HttpServletReq;

@Component
@PropertySource(value = "classpath:key.properties")
public class SMTP {
	@Value("${smtp_id}")
	private String id;
	
	@Value("${smtp_pw}")
	private String pw;
	
	@Value("${sender_email}")
	private String from;
	
	private MimeMessage msg;
	
	@Autowired
	public void init() {
		Properties properties = new Properties();
		// properties.put("mail.smtp.starttls.enable", "true"); // Gmail 사용 시 필수 properties
		properties.put("mail.smtp.host", "smtp.naver.com"); // Gmail : smtp.gmail.com
		properties.put("mail.smtp.auth", "true"); // 인증 시도 허용
		properties.put("mail.smtp.port", "587"); // 네이버 SMTP 포트 번호
		
		Authenticator auth = new MyAuthentication(id, pw);
		Session session = Session.getDefaultInstance(properties, auth); // 인증
		msg = new MimeMessage(session);
	}
	
	public void send_mail(String recipient_email, String id, String email_auth_key) {
		String to = recipient_email;
		
		try {
			msg.setSentDate(new Date()); // 전송 날짜
			msg.setFrom(new InternetAddress(from)); // 보내는 사람
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to)); // 받는 사람
			msg.setSubject("GGV 회원가입 인증 메일입니다.", "UTF-8"); // 제목
			
			// 내용
			HttpServletRequest req = HttpServletReq.request;
			String contents = "<button type='button' style='width:100px; height:40px; border:none; border-radius:7px; background-color:#dd4b39; color:white;'>" +
										  "<a href='" + req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/member/join/joinForm/emailAuthCheck?id=" + id + "&email_auth_key=" + email_auth_key + "'" +
										  "style='display:block; text-decoration:none; font-family:-webkit-pictograph; color:white;'>" +
										  	  "인증" +
										  "</a>" +
									  "</button>";
			
			String type = "text/html; charset=UTF-8";
			msg.setContent(contents, type);
			
			Transport.send(msg); // 전송
		}catch(AddressException addr_e) {
			addr_e.printStackTrace();
		}catch(MessagingException msg_e) {
			msg_e.printStackTrace();
		}
	}
	
	public int send_mail(String recipient_email) {
		String to = recipient_email;
		
		int authNum = (int)(Math.random() * (999999 - 100000 + 1)) + 100000;
		
		try {
			msg.setSentDate(new Date());
			msg.setFrom(new InternetAddress(from));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject("GGV 이메일 인증 메일입니다.", "UTF-8");
			
			String contents = "인증번호 : " + authNum;
			String type = "text/html; charset=UTF-8";
			msg.setContent(contents, type);
			
			Transport.send(msg);
		}catch(AddressException addr_e) {
			addr_e.printStackTrace();
		}catch(MessagingException msg_e) {
			msg_e.printStackTrace();
		}
		
		return authNum;
	}
}

class MyAuthentication extends Authenticator{
	private PasswordAuthentication account;
	
	public MyAuthentication(String id, String pw) {
		account = new PasswordAuthentication(id, pw);
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return account;
	}
}