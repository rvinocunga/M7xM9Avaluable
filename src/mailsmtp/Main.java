package mailsmtp;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import m7xm9clientgmailavaluable.Connexion;

public class Main {

	public static void main(String[] args) throws AddressException, MessagingException {
		String user = "adam22rvinocunga@inslaferreria.cat";
		String pwd  = "xxx";
		
		Session session = Connexion.getSession();
		
		MimeMessage message = new MimeMessage(session);
		message.addRecipient(Message.RecipientType.TO,
				new InternetAddress("adam22rvinocunga@inslaferreria.cat"));
		message.setSubject("email desde java");
		message.setText("Funciona!!!!");
		
		Transport transport = session.getTransport("smtp");
		transport.connect(user, pwd);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

}




