package GUI.ActionListener.Manage;

import BalanceTable.BalanceTableModel;
import BalanceTable.TableEntry;
import org.jdatepicker.ComponentColorDefaults;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action Listener associato al pulsante per la modifica di una voce
 */
public class ModifyEntryListener implements ActionListener {

    private BalanceTableModel tableModel;

    public ModifyEntryListener(BalanceTableModel tableModel){
        this.tableModel = tableModel;
    }

    /**
     * Action Performed che dispone un JOptionPane con i valori attuali della voce selezionata per poterli modificare e reimpostare
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = tableModel.getSelectedRow();
        if (index >= 0){
            TableEntry srcEntry = tableModel.getEntry(index);

            JPanel modifyValuesPanel = new JPanel();
            modifyValuesPanel.setPreferredSize(new Dimension(350, 85));
            JLabel moneyLabel = new JLabel("Importo : ");
            JTextField moneyTextField = new JTextField(5);
            moneyTextField.setText(String.format("%.2f", srcEntry.getMoney()));

            JLabel dateLabel = new JLabel("Data : ");

            // picker docs https://github.com/JDatePicker/JDatePicker
            ComponentColorDefaults colors = ComponentColorDefaults.getInstance();
            colors.setColor(ComponentColorDefaults.Key.FG_MONTH_SELECTOR, Color.white);

            JDatePicker datePicker = new JDatePicker(new UtilDateModel(srcEntry.getDate()), "dd/MM/yyyy");
            datePicker.setPreferredSize(new Dimension(120, 30));

            JLabel textLabel = new JLabel("Descrizione : ");
            JTextField textField = new JTextField(20);
            textField.setText(srcEntry.getText());

            modifyValuesPanel.add(moneyLabel);
            modifyValuesPanel.add(moneyTextField);
            modifyValuesPanel.add(Box.createHorizontalStrut(10));
            modifyValuesPanel.add(dateLabel);
            modifyValuesPanel.add(datePicker);
            modifyValuesPanel.add(Box.createVerticalStrut(40));
            modifyValuesPanel.add(textLabel);
            modifyValuesPanel.add(textField);

            int answer = JOptionPane.showConfirmDialog(null, modifyValuesPanel, "Modifica Voce", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (answer == JOptionPane.OK_OPTION) {

                String newText = textField.getText();
                float newMoney = srcEntry.getMoney();                       // Prendo il vecchio valore per evitare il campo vuoto
                String moneyString = moneyTextField.getText();
                if(!moneyString.equals("")){
                    moneyString = moneyString.replace(',', '.');
                    try{
                        newMoney = Float.parseFloat(moneyString);
                    }
                    catch (NumberFormatException exception){}
                }

                // Modifica data -- se cancellata lascio la precedente
                String newDate = datePicker.getFormattedTextField().getText();

                tableModel.modifyEntry(index, newDate, newText, newMoney);
            }
        }
    }
}
