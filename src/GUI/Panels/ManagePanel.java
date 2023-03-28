package GUI.Panels;

import javax.swing.*;
import java.awt.*;

import BalanceTable.BalanceTableModel;
import GUI.ActionListener.Manage.*;

/**
 * Classe che descrive il pannello con le funzioni di gestione del bilancio:<br>
 *  - Aggiunta voce;<br>
 *  - Modifica voce:<br>
 *  - Eliminazione voce.
 */
public class ManagePanel extends JPanel {

    public ManagePanel(Dimension dimension, BalanceTableModel table){
        setPreferredSize(dimension);

        JLabel title = new JLabel("Gestione Voci");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("Dialog", Font.BOLD, 16 ));

        JButton addButton = new JButton("Aggiungi");
        addButton.addActionListener(new AddEntryListener(table));
        addButton.setPreferredSize(new Dimension(200, 25));

        JButton modifyButton = new JButton("Modifica");
        modifyButton.addActionListener(new ModifyEntryListener(table));
        modifyButton.setPreferredSize(new Dimension(200, 25));

        JButton deleteButton = new JButton("Elimina");
        deleteButton.addActionListener(new DeleteEntryListener(table));
        deleteButton.setPreferredSize(new Dimension(200, 25));

        add(Box.createRigidArea(new Dimension(dimension.width, 20)));
        add(title);
        add(Box.createRigidArea(new Dimension(dimension.width, 30)));
        add(addButton);
        add(modifyButton);
        add(deleteButton);
    }
}
