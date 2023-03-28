package GUI.ActionListener.Manage;

import BalanceTable.BalanceTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Action Listener associato al pulsante per l'eliminazione di una voce
 */
public class DeleteEntryListener implements ActionListener {

    private BalanceTableModel tableModel;

    public DeleteEntryListener(BalanceTableModel tableModel){
        this.tableModel = tableModel;
    }

    /**
     * Action Performed che elimina la voce selezionata nella tabella
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = tableModel.getSelectedRow();
        if (index >= 0){
            tableModel.deleteEntry(index);
        }
    }
}
