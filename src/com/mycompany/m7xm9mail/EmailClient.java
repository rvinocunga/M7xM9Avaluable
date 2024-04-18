package com.mycompany.m7xm9mail;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.*;

public class EmailClient {

    private Session session;
    private Store store;
    private String host = "imap.gmail.com"; // Canvia aquesta cadena pel teu servidor IMAP
    private String username = "adam22rvinocunga@inslaferreria.cat"; // Canvia pel teu correu electrònic
    private String password = "xxxz"; // Canvia per la teva contrasenya
    String directoryPath = "..\\AdjuntsEmail"; // Definició de la ruta on vols desar els adjunts

    //Exemple directoris Enviats numero 15
    // Connectar-se al servidor IMAP
    public void connect() throws NoSuchProviderException, MessagingException {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.ssl.enable", "true");

        session = Session.getInstance(properties);
        store = session.getStore("imaps");
        store.connect(host, username, password);
        System.out.println("Connectat al servidor IMAP.");
    }

    // Desconnectar-se del servidor IMAP
    public void disconnect() throws MessagingException {
        if (store != null && store.isConnected()) {
            store.close();
            System.out.println("Desconnectat del servidor IMAP.");
        }
    }

    public void printMessagesFromTo(String folderName, int n) throws MessagingException, IOException {
        Folder folder = store.getFolder(folderName);
        if (!folder.isOpen()) {
            folder.open(Folder.READ_ONLY);
        }

        int messageCount = folder.getMessageCount();
        int from = Math.max(1, n - 9); // Ajusta el límit inferior per assegurar-se que no sigui menor que 1
        int to = Math.min(n, messageCount); // Ajusta el límit superior per assegurar-se que no superi el total de missatges

        // Obtenir els missatges en el rang especificat
        Message[] messages = folder.getMessages(from, to);
        for (Message message : messages) {
            System.out.println("--------------------------------------------------");
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + Arrays.toString(message.getFrom()));
            System.out.println("To: " + Arrays.toString(message.getRecipients(Message.RecipientType.TO)));
            System.out.println("Date: " + message.getReceivedDate());
            // Aquí pots afegir més detalls del missatge si és necessari
        }

        folder.close(false);
    }

    // Obtenir una llista dels directoris associats al compte de correu
    public void listFolders() throws MessagingException {
        if (!store.isConnected()) {
            throw new IllegalStateException("No estàs connectat. Si us plau, connecta't primer.");
        }

        Folder defaultFolder = store.getDefaultFolder();
        Folder[] folders = defaultFolder.list("*");
        System.out.println("Directoris:");
        for (Folder folder : folders) {
            System.out.println(folder.getFullName());
        }
    }

    public void printEmailContent(String folderName, int messageNumber) throws MessagingException, IOException {
        Folder folder = store.getFolder(folderName);
        if (!folder.isOpen()) {
            folder.open(Folder.READ_WRITE);
        }

        Message message = folder.getMessage(messageNumber);
        System.out.println("--------------------------------------------------");
        System.out.println("Subject: " + message.getSubject());
        System.out.println("From: " + Arrays.toString(message.getFrom()));
        System.out.println("To: " + Arrays.toString(message.getRecipients(Message.RecipientType.TO)));
        if (message.getRecipients(Message.RecipientType.CC) != null) {
            System.out.println("CC: " + Arrays.toString(message.getRecipients(Message.RecipientType.CC)));
        }
        System.out.println("Date: " + message.getSentDate());
        System.out.println("--------------------------------------------------");

        getPart(message);

        folder.close(false);
    }

    private void getPart(Part part) throws MessagingException, IOException {
        if (part.isMimeType("text/plain")) {
            System.out.println("Text: " + part.getContent());
        } else if (part.isMimeType("text/html")) {
            System.out.println("HTML: " + part.getContent());
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                getPart(multipart.getBodyPart(i));
            }
        } else if (part.isMimeType("message/rfc822")) {
            getPart((Part) part.getContent());
        } else if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition()) || Part.INLINE.equalsIgnoreCase(part.getDisposition())) {
            System.out.println("Attachment: " + part.getFileName() + " (" + part.getSize() + " bytes)");

            if (part instanceof MimeBodyPart) {
                MimeBodyPart mimeBodyPart = (MimeBodyPart) part;
                String attachmentPath = directoryPath + File.separator + part.getFileName();
                File file = new File(attachmentPath);
                if (!file.exists()) {
                    // Crea els directoris si no existeixen
                    file.getParentFile().mkdirs();
                    mimeBodyPart.saveFile(file);
                    System.out.println("L'adjunt s'ha desat en: " + attachmentPath);

                    // Obrir l'arxiu amb l'aplicació per defecte
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(file);
                        System.out.println("S'ha obert l'arxiu adjunt amb l'aplicació per defecte.");
                    } else {
                        System.out.println("El Desktop no està suportat, no es pot obrir l'arxiu adjunt.");
                    }
                }
            }
        }
    }

    public void deleteEmail(String folderName, int messageNumber) throws MessagingException {
        Folder folder = store.getFolder(folderName);
        if (!folder.isOpen()) {
            folder.open(Folder.READ_WRITE);
        }

        Message[] messagesToDelete = folder.getMessages(messageNumber, messageNumber);
        if (messagesToDelete.length > 0) {
            messagesToDelete[0].setFlag(Flags.Flag.DELETED, true);
            System.out.println("El correu s'ha marcat per a ser eliminat.");
        } else {
            System.out.println("No s'ha trobat el correu especificat.");
        }

        folder.expunge(); // Aquesta línia expurga els correus marcats per a eliminació
        folder.close(true);
        System.out.println("El correu ha estat eliminat.");
    }

    public void listEmailSummaries(String folderName) throws MessagingException {
        Folder folder = store.getFolder(folderName);
        if (!folder.isOpen()) {
            folder.open(Folder.READ_ONLY);
        }

        int messageCount = folder.getMessageCount();
        Message[] messages = folder.getMessages(Math.max(1, messageCount - 9), messageCount); // Últimos 10 mensajes, ajusta según necesidad

        System.out.println("Últimos mensajes en " + folderName + ":");
        for (int i = messages.length - 1; i >= 0; i--) { // Lista los más recientes primero
            Message message = messages[i];
            System.out.println((messageCount - i) + ": [" + message.getSubject() + "] De: " + Arrays.toString(message.getFrom()) + " Fecha: " + message.getReceivedDate());
        }

        folder.close(false);
    }

    public void sendEmail() throws MessagingException {
        Scanner scanner = new Scanner(System.in);

        // Configuración SMTP
        Properties smtpProperties = new Properties();
        smtpProperties.put("mail.smtp.auth", "true");
        smtpProperties.put("mail.smtp.starttls.enable", "true");
        smtpProperties.put("mail.smtp.host", "smtp.gmail.com");
        smtpProperties.put("mail.smtp.port", "587");

        // Crear una sesión con autenticación
        Session session = Session.getInstance(smtpProperties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        Message message = new MimeMessage(session);

        // Recoger información del correo a enviar
        System.out.println("Introduce los destinatarios (TO) separados por coma: ");
        String to = scanner.nextLine();
        InternetAddress[] toAddresses = InternetAddress.parse(to);
        message.setRecipients(Message.RecipientType.TO, toAddresses);

        System.out.println("Introduce los destinatarios (CC) separados por coma (opcional): ");
        String cc = scanner.nextLine();
        if (!cc.isEmpty()) {
            InternetAddress[] ccAddresses = InternetAddress.parse(cc);
            message.setRecipients(Message.RecipientType.CC, ccAddresses);
        }

        System.out.println("Introduce los destinatarios (BCC) separados por coma (opcional): ");
        String bcc = scanner.nextLine();
        if (!bcc.isEmpty()) {
            InternetAddress[] bccAddresses = InternetAddress.parse(bcc);
            message.setRecipients(Message.RecipientType.BCC, bccAddresses);
        }

        System.out.println("Introduce el asunto del correo: ");
        message.setSubject(scanner.nextLine());

        // Crear el contenido del mensaje y adjuntos si es necesario
        MimeBodyPart textBodyPart = new MimeBodyPart();
        System.out.println("Introduce el cuerpo del correo: ");
        textBodyPart.setText(scanner.nextLine(), "utf-8", "html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textBodyPart);

        // Aquí podrías añadir lógica para adjuntar archivos si es necesario
        message.setContent(multipart);

        // Enviar correo
        Transport.send(message);

        System.out.println("Correo enviado con éxito.");
    }

    public static void main(String[] args) {
        EmailClient client = new EmailClient();
        try ( Scanner scanner = new Scanner(System.in)) {
            client.connect();
            client.listFolders();

            while (true) {
                System.out.println("\nAccions disponibles:");
                System.out.println("1. Llistar directoris");
                System.out.println("2. Obtenir tot el contingut del correu");
                System.out.println("3. Obtenir les capçaleres del mail n fins al n-10 d’un directori concret");
                System.out.println("4. Descarregar i obrir un adjunt");
                System.out.println("5. Eliminar un correu");
                System.out.println("6. Llistar missatges d'un directori");
                System.out.println("7. Enviar un correu electrònic");
                System.out.println("8. Sortir");

                System.out.print("Selecciona una opció: ");
                int action = scanner.nextInt();
                scanner.nextLine(); // Netejar el buffer del scanner

                switch (action) {
                    case 1:
                        client.listFolders();
                        break;
                    case 2:
                        System.out.println("Introdueix el nom del directori (Ex: [Gmail]/Paperera, INBOX, [Gmail]/Enviats): ");
                        String folderName = scanner.nextLine().trim();
                        System.out.println("Introdueix el número del missatge: ");
                        int messageNumber = scanner.nextInt();
                        client.printEmailContent(folderName, messageNumber);
                        break;
                    case 3:
                        System.out.println("Introdueix el nom del directori (Ex: [Gmail]/Paperera, INBOX, [Gmail]/Enviats): ");
                        String folderNameForHeaders = scanner.nextLine().trim();
                        System.out.println("Introdueix el número del missatge inicial (n): ");
                        int n = scanner.nextInt();
                        client.printMessagesFromTo(folderNameForHeaders, n);
                        break;
                    case 4:
                        System.out.println("Introdueix el nom del directori de l'adjunt: ");
                        String attachFolderName = scanner.nextLine().trim();
                        System.out.println("Introdueix el número del missatge que conté l'adjunt: ");
                        int attachMsgNumber = scanner.nextInt();
                        client.printEmailContent(attachFolderName, attachMsgNumber);
                        break;
                    case 5:
                        System.out.println("Introdueix el nom del directori on vols eliminar un correu (Ex: INBOX): ");
                        String deleteFolderName = scanner.nextLine().trim();
                        System.out.println("Introdueix el número del missatge que vols eliminar: ");
                        int deleteMsgNumber = scanner.nextInt();
                        client.deleteEmail(deleteFolderName, deleteMsgNumber);
                        break;
                    case 6:
                        System.out.println("Introduce el nombre del directorio (p.ej., INBOX, [Gmail]/Enviats): ");
                        String dirName = scanner.nextLine().trim();
                        client.listEmailSummaries(dirName);
                        break;
                    case 7:
                        client.sendEmail();
                        break;
                    case 8:
                        System.out.println("Sortint...");
                        client.disconnect();
                        return;
                    default:
                        System.out.println("Opció no reconeguda. Si us plau, intenta-ho de nou.");
                        break;
                }
            }
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}
