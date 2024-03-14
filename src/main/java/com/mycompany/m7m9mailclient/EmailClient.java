
package com.mycompany.m7m9mailclient;

import jakarta.mail.*;
import java.util.Properties;

public class EmailClient {
    private Session session;
    private Store store;
    private String host = "imap.gmail.com"; // Canvia aquesta cadena pel teu servidor IMAP
    private String username = "adam22jlin@inslaferreria.cat"; // Canvia pel teu correu electrònic
    private String password = ""; // Canvia per la teva contrasenya

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

    public static void main(String[] args) {
        EmailClient client = new EmailClient();
        try {
            client.connect();
            client.listFolders();
            client.disconnect();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
