package GUI.Panels;

import BalanceTable.BalanceTableModel;

import javax.swing.*;
import java.awt.*;

/**
 * Classe che descrive il pannello laterale dell'interfaccia.<br>
 * Contiene 3 pannelli: <br>
 *  - Gestione del bilancio; <br>
 *  - Visualizzazione; <br>
 *  - Ricerca.
 */
public class SidePanel extends JPanel {

    public SidePanel(Dimension dimension, TablePanel tablePanel){
        setPreferredSize(dimension);
        BalanceTableModel table = tablePanel.getTableModel();

        ManagePanel tableManager = new ManagePanel(new Dimension(300, 250), table);
        VisualizePanel visualize = new VisualizePanel(new Dimension(300, 400), table);

        // Al pannello di ricerca passo l'intero pannello per poter ottenere sia il tablePanel che la table per evidenziarne le voci
        SearchPanel search = new SearchPanel(new Dimension(300, 250), tablePanel);

        add(tableManager);
        add(visualize);
        add(search);
    }

    /**
     * Metodo paint per disegnare alcune linee come elementi grafici
     */
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.GRAY);
        g.drawLine(0, 10, 0, 830);
        g.drawLine(30, 250, 270, 250);
        g.drawLine(30, 650, 270, 650);
    }
}
