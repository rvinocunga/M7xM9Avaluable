/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m7xm9clientgmailavaluable;

import com.mycompany.m7xm9mail.EmailClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class MenuPrincipal extends JFrame {

    private JTree arbre;
    private JTextArea emailContentTextArea;

    private List<String> folders;
    private EmailClient em = new EmailClient();

    public MenuPrincipal() throws MessagingException {
        setTitle("Client de correu electrònic");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        try {
            em.connect("adam22rvinocunga@inslaferreria.cat", "xxx");
        } catch (MessagingException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Crear el menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Opcions");
        menuBar.add(menu);

        // Icones per a les opcions de menú
        // actualitzar icon
        ImageIcon actualizarIcon = new ImageIcon("imatges\\actualitzar_icon.jpg");
        Image iconActualitzarEscalado = actualizarIcon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
        ImageIcon actualizarIconEscalado = new ImageIcon(iconActualitzarEscalado);

        // escriure icon
        ImageIcon escriureIcon = new ImageIcon("imatges\\escriure_icon.png");
        Image iconEscriureEscalado = escriureIcon.getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
        ImageIcon escriureIconEscalado = new ImageIcon(iconEscriureEscalado);

        // Opció "Actualitzar" amb icona
        JMenuItem actualizarMenuItem = new JMenuItem("Actualitzar", actualizarIconEscalado);
        actualizarMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualitzarCorreus();
            }
        });
        menu.add(actualizarMenuItem);

        // Opció "Escriure" 
        JMenuItem escriureMenuItem = new JMenuItem("Escriure", escriureIconEscalado);
        escriureMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EnviarMail enviarMail = new EnviarMail();
                enviarMail.setModal(false);
                enviarMail.setVisible(true);
            }
        });
        menu.add(escriureMenuItem);

        setJMenuBar(menuBar);

        // Crear el botón de alternancia para actualizar correos electrónicos
        JToggleButton actualizarToggleButton = new JToggleButton(actualizarIconEscalado);
        actualizarToggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (actualizarToggleButton.isSelected()) {
                    // actualizar
                    actualitzarCorreus();
                } else {
                    System.out.println("Actualización de correos electrónicos cancelada");
                }
            }

        });

        // Crear el botón de alternancia para actualizar correos electrónicos
        JToggleButton escriureToggleButton = new JToggleButton(escriureIconEscalado);
        escriureToggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (escriureToggleButton.isSelected()) {
                    EnviarMail enviarMail = new EnviarMail();
                    enviarMail.setModal(false);
                    enviarMail.setVisible(true);
                    System.out.println("Escribiendo");
                } else {
                    System.out.println("Cancelado");
                }
            }
        });

        // Agregar el botón de alternancia al panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(actualizarToggleButton);
        buttonPanel.add(escriureToggleButton);

        // Añadir el panel al contenedor principal
        add(buttonPanel, BorderLayout.NORTH);

        // Crear la JTree per a les carpetes
        // nodo raiz
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Correus");

        // implementar las carpetas hijas que se reciben
        folders = em.listFolders();
        //System.out.println(folders); // Para comprobar que se reciben las carpetas correctamente

        // Crear un nodo para cada carpeta y agregarlo como hijo del nodo raíz
        for (String folderName : folders) {
            DefaultMutableTreeNode folderNode = new DefaultMutableTreeNode(folderName);
            raiz.add(folderNode);
        }

        // modelo del JTree
        DefaultTreeModel modelo = new DefaultTreeModel(raiz);
        arbre = new JTree(modelo);
        

        JScrollPane treeScrollPane = new JScrollPane(arbre);
        treeScrollPane.setPreferredSize(new Dimension(200, 0));
        add(treeScrollPane, BorderLayout.WEST);

        // Àrea de text per al contingut del correu electrònic
        emailContentTextArea = new JTextArea();
        JScrollPane contentScrollPane = new JScrollPane(emailContentTextArea);
        add(contentScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    protected void actualitzarCorreus() {
        System.out.println("Actualizar correos...");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MenuPrincipal();
                } catch (MessagingException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
