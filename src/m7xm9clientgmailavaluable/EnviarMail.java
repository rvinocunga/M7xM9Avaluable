package m7xm9clientgmailavaluable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class EnviarMail extends JDialog {

    private JTextField destinatarioField;
    private JTextField asuntoField;
    private JTextArea cuerpoTextArea;

    public EnviarMail() {
        // Configurar la ventana
        this.setTitle("Enviar mail");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(600, 400);
        this.setLayout(new BorderLayout());

        // Panel
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS)); // Corrección aquí
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));

        // agregar Panel
        add(panel1, BorderLayout.PAGE_START);

        // Campos (destinatario/s, bcc, arxius?)
        // label asunto 
        JLabel labelAsunto = new JLabel("Asumpte: ");
        panel1.add(labelAsunto);
        // field remitente
        asuntoField = new JTextField(50);
        panel1.add(asuntoField);

        // label destinatario
        JLabel labelDestinatario = new JLabel("Destinatari: ");
        panel1.add(labelDestinatario);
        // field remitente
        destinatarioField = new JTextField(50);
        panel1.add(destinatarioField);
        
        
        
        // Panel para el área de texto
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel labelCuerpo = new JLabel("Missatge: ");
        panel2.add(labelCuerpo, BorderLayout.NORTH);
        cuerpoTextArea = new JTextArea(10, 50);
        JScrollPane scrollPane = new JScrollPane(cuerpoTextArea);
        panel2.add(scrollPane, BorderLayout.CENTER);
        
        add(panel2, BorderLayout.CENTER);
        
        JPanel btns = new JPanel(new FlowLayout());
        
        JButton btnEnviar = new JButton("Enviar");
        JButton btnAdjuntarFitxer = new JButton("Fitxer");
        JButton btnEsborrany = new JButton("Esborrany");
        btns.add(btnEnviar);
        btns.add(btnEsborrany);
        
        add(btns, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        EnviarMail app = new EnviarMail();
        app.setVisible(true);
    }
}
