package m7xm9clientgmailavaluable;

import java.util.Properties;
import javax.mail.Session;

public class Connexion {

    public static Session getSession() {
        Properties smtpProps = new Properties();
        smtpProps.setProperty("mail.smtp.host", "smtp.gmail.com");
        smtpProps.setProperty("mail.smtp.auth", "true");
        smtpProps.setProperty("mail.smtp.starttls.enable", "true");
        smtpProps.setProperty("mail.smtp.port", "587");

        return Session.getDefaultInstance(smtpProps);
    }
}
