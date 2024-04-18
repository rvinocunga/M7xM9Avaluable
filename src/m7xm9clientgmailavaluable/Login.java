package m7xm9clientgmailavaluable;

import com.mycompany.m7xm9mail.EmailClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.border.EmptyBorder;

public class Login extends JDialog {

    public static EmailClient em = new EmailClient();
    private JTextField correuElectronic;
    private JPasswordField contrasenya;

    public Login() {
        // Configurar la ventana
        this.setTitle("Iniciar sesion gmail");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(240, 200);
        this.setLayout(new BorderLayout());

        // Panel
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS)); // Corrección aquí
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));

        // agregar Panel
        add(panel1, BorderLayout.PAGE_START);

        // label correo 
        JLabel labelCorreu = new JLabel("Correu electronic: ");
        panel1.add(labelCorreu);
        // field correo
        correuElectronic = new JTextField(50);
        panel1.add(correuElectronic);

        // label password
        JLabel labelPassword = new JLabel("Contraseña: ");
        panel1.add(labelPassword);
        // field password
        contrasenya = new JPasswordField(50);
        panel1.add(contrasenya);

        // Panel para el área de texto
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(panel2, BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout());

        JButton btnIniciar = new JButton("Iniciar sesion");
        btnIniciar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    if (em.connect(correuElectronic.getText(), contrasenya.getText())) {
                        // Cerrar la ventana actual (Login)
                        JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor((Component) e.getSource());
                        dialog.dispose();

                        // Abrir la ventana del menú principal
                        MenuPrincipal mp = new MenuPrincipal();
                        mp.setVisible(true);
                    }
                } catch (MessagingException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                correuElectronic.setText("");
                contrasenya.setText("");
            }
        });

        // agregar botones
        btns.add(btnIniciar);
        btns.add(btnLimpiar);

        add(btns, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        Login app = new Login();
        app.setVisible(true);
    }
}
