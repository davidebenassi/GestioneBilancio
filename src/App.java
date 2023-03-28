import GUI.ActionListener.MenuBar.*;
import GUI.Panels.SidePanel;
import GUI.Panels.TablePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Entry-point del progetto - classe contenente il metodo "main"
 */
public class App {
    public static void main(String[] args) {
        new App();
    }

    /**
     * Costruttore della classe principale del progetto. <br>
     * Contiene la struttura dell'applicazione in termini di: <br>
     *  - Frame principale della GUI; <br>
     *  - Menù Bar; <br>
     *  - Creazione dei due pannelli principali; <br>
     *  - Visualizzazione generale e comportamento in apertura e chiusura. <br>
     */
    public App(){

        // --- FRAME --- //
        JFrame frame = new JFrame("Gestione Bilancio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // --- PANELS --- //
        TablePanel balanceTablePanel = new TablePanel(new Dimension(900, 900));
        SidePanel side = new SidePanel(new Dimension(300,900), balanceTablePanel);

        // --- MENU BAR --- //
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveMenuItem = new JMenuItem("Salva Bilancio");
        saveMenuItem.addActionListener(new SaveActionListener(balanceTablePanel));
        JMenuItem loadMenuItem = new JMenuItem("Carica Bilancio");
        loadMenuItem.addActionListener(new LoadActionListener(balanceTablePanel));
        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        JMenu exportMenu = new JMenu("Esporta");
        JMenuItem csvExportMenuItem = new JMenuItem("CSV");
        csvExportMenuItem.addActionListener(new ExportActionListener(balanceTablePanel));
        JMenuItem textExportMenuItem = new JMenuItem("Testo");
        textExportMenuItem.addActionListener(new ExportActionListener(balanceTablePanel));
        JMenuItem odExportMenuItem = new JMenuItem("Open Document");
        odExportMenuItem.addActionListener(new ExportActionListener(balanceTablePanel));
        exportMenu.add(csvExportMenuItem);
        exportMenu.add(textExportMenuItem);
        exportMenu.add(odExportMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(exportMenu);

        // --- COMPOSE & VISUALIZE FRAME --- //
        frame.setJMenuBar(menuBar);
        frame.add(balanceTablePanel, BorderLayout.CENTER);
        frame.add(side, BorderLayout.EAST);
        frame.setVisible(true);
    }
}
