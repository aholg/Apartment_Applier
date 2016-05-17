

package aholg.apartment_applier;

/**
 * Class for sending mail to list of users, informing them about new apartment.
 * @author Anton
 */
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

public class sendMail {

    private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
    //enter your own mail details
    private static final String SMTP_AUTH_USER = "";
    private static final String SMTP_AUTH_PWD = "";
    
    public sendMail(String appartment) throws NoSuchProviderException, MessagingException {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");
        System.out.println(appartment);
        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);

        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);

        Multipart multipart = new MimeMultipart("alternative");

        BodyPart part1 = new MimeBodyPart();
        part1.setText("Checking to see what box this mail goes in ?");

        BodyPart part2 = new MimeBodyPart();
        part2.setContent("<p>" + appartment + "</p>", "text/html");

        multipart.addBodyPart(part1);
        multipart.addBodyPart(part2);

        message.setContent(multipart);
        message.setFrom(new InternetAddress(""));
        message.setSubject("Ny lägenhet");
        //enter your own recipents
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(""));
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(""));

        transport.connect();
        transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {

        public PasswordAuthentication getPasswordAuthentication() {
            String username = SMTP_AUTH_USER;
            String password = SMTP_AUTH_PWD;
            return new PasswordAuthentication(username, password);
        }
    }
}
