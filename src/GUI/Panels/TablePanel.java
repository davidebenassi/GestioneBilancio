package GUI.Panels;

import BalanceTable.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;

public class TablePanel extends JPanel {

    private final BalanceTableModel tableModel;
    private final JTable balanceTable;
    public TablePanel(Dimension dimension){
        setPreferredSize(dimension);

        tableModel = new BalanceTableModel();
        balanceTable = new JTable(tableModel);
        balanceTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);     // Possibile selezione di una sola linea alla volta

        JScrollPane scrollableTable = new JScrollPane(balanceTable);
        scrollableTable.setPreferredSize(new Dimension(800, 750));

        JLabel totalMoneyLabel = new JLabel("Totale Movimenti Visualizzati : ");
        totalMoneyLabel.setFont(new Font("Dialog", Font.BOLD, 16 ));

        JLabel total = new JLabel("0,00", SwingConstants.RIGHT);
        total.setFont(new Font("Dialog", Font.BOLD, 16 ));
        total.setPreferredSize(new Dimension(100, 25));

        add(scrollableTable);
        add(Box.createRigidArea(new Dimension(800, 20)));
        add(totalMoneyLabel);
        add(Box.createHorizontalStrut(420));
        add(total);

        // Comunico al table model la voce selezionata dall'utente tramite mouse
        balanceTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                tableModel.setSelectedRow(balanceTable.getSelectedRow());
            }
        });

        // Listener delle modifiche della tabella per ricalcolare il totale visualizzato ad ogni cambiamento
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                float totalMoneyValue = tableModel.getTotalDisplayedMoney();
                if (totalMoneyValue < 0)
                    total.setForeground(Color.red);
                else{
                    total.setForeground(Color.black);
                }
                total.setText(String.format("%.2f", totalMoneyValue));
            }
        });

    }

    /**
     * Metodo che ritorna il table model contenuto nel pannello
     */
    public BalanceTableModel getTableModel(){
        return this.tableModel;
    }

    /**
     * Metodo che ritorna la JTable contenuta nel pannello
     */
    public JTable getTable(){
        return this.balanceTable;
    }
}
