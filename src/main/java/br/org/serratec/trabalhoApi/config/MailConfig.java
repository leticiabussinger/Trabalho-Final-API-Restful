package br.org.serratec.trabalhoApi.config;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailConfig {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String username;
	
	@Value("${spring.mail.password}")
	private String password;
	
	static final String BODY = String.join(System.getProperty("line.separator"),
		    "<html><head></head><body><img src=\"cid:image\"></html><br>");
	
	public void sendEmail(String para, String assunto) throws AddressException, MessagingException {
		
		Properties props = new Properties();
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
		
		MimeMessage message = new MimeMessage(session);
		MimeMultipart multipart = new MimeMultipart();

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(BODY, "text/html");

		MimeBodyPart imagePart = new MimeBodyPart();
		FileDataSource img = new FileDataSource("src/main/resources/bem_vindo_email.jpg");
		imagePart.setDataHandler(new DataHandler(img));
		imagePart.setHeader("Content-ID", "<image>");

		multipart.addBodyPart(messageBodyPart);
		multipart.addBodyPart(imagePart);

		message.setContent(multipart);
		
		message.setFrom("grupo6.trabalho.api@gmail.com");
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(para));
		message.setSubject(assunto);
		javaMailSender.send(message);
	}
	
}

