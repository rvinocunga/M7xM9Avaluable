/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package m7xm9clientgmailavaluable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPrincipal extends JFrame {

    private JTree foldersTree;
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
        ImageIcon actualizarIcon = new ImageIcon("imatges/actualitzar_icon.svg");
        ImageIcon escriureIcon = new ImageIcon("imatges/escriure_icon.svg");

        // Opció "Actualitzar" amb icona
        JMenuItem actualizarMenuItem = new JMenuItem("Actualitzar", actualizarIcon);
        actualizarMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Codificar l'acció d'actualitzar el correu electrònic aquí
            }
        });
        menu.add(actualizarMenuItem);

        // Opció "Escriure" amb icona
        JMenuItem escriureMenuItem = new JMenuItem("Escriure", escriureIcon);
        escriureMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Codificar l'acció d'escriure un nou correu electrònic aquí
                // Podries obrir un nou JDialog per a la redacció del correu electrònic
            }
        });
        menu.add(escriureMenuItem);

        setJMenuBar(menuBar);

        // Crear la JTree per a les carpetes
        foldersTree = new JTree();
        JScrollPane treeScrollPane = new JScrollPane(foldersTree);
        treeScrollPane.setPreferredSize(new Dimension(200, 0));
        add(treeScrollPane, BorderLayout.WEST);

        // Àrea de text per al contingut del correu electrònic
        emailContentTextArea = new JTextArea();
        JScrollPane contentScrollPane = new JScrollPane(emailContentTextArea);
        add(contentScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal();
            }
        });
    }
}
