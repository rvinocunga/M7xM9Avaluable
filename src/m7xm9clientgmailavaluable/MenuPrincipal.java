/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m7xm9clientgmailavaluable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class MenuPrincipal extends JFrame {

    private JTree arbre;
    private JTextArea emailContentTextArea;

    public MenuPrincipal() {
        setTitle("Client de correu electrònic");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

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

        // nodos (mails rebuts, esborranys, enviats, esborrats)
        DefaultMutableTreeNode recibidos = new DefaultMutableTreeNode("Rebuts");
        DefaultMutableTreeNode borradores = new DefaultMutableTreeNode("Esborranys");
        DefaultMutableTreeNode enviados = new DefaultMutableTreeNode("Enviats");
        DefaultMutableTreeNode borrados = new DefaultMutableTreeNode("Esborrats");

        // agregar mails recibidos
        // agregar sub nodos
        raiz.add(recibidos);
        raiz.add(borradores);
        raiz.add(enviados);
        raiz.add(borrados);

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
                new MenuPrincipal();
            }
        });
    }
}
