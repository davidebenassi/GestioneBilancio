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
import java.util.Date;

/**
 * Action Listener associato al pulsante per l'aggiunta di una voce
 */
public class AddEntryListener implements ActionListener {
    private BalanceTableModel tableModel;

    public AddEntryListener(BalanceTableModel tableModel){
        this.tableModel = tableModel;
    }

    /**
     * Action Performed che acquisisce i parametri della voce di bilancio tramite un JOptionPane e la aggiunge alla tabella
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        JPanel entryValuesPanel = new JPanel();
        entryValuesPanel.setPreferredSize(new Dimension(350, 85));
        JLabel moneyLabel = new JLabel("Importo : ");
        JTextField moneyTextField = new JTextField(5);
        moneyTextField.setText(String.format("%.2f", 0.0));

        JLabel dateLabel = new JLabel("Data : ");

        // picker docs https://github.com/JDatePicker/JDatePicker
        ComponentColorDefaults colors = ComponentColorDefaults.getInstance();
        colors.setColor(ComponentColorDefaults.Key.FG_MONTH_SELECTOR, Color.white);
        JDatePicker datePicker = new JDatePicker(new UtilDateModel(new Date()), "dd/MM/yyyy");      //Costruttore di JDatePicker che assegna la data odierna, tramite new Date(), formattata
        datePicker.setPreferredSize(new Dimension(120, 30));

        JLabel textLabel = new JLabel("Descrizione : ");
        JTextField textField = new JTextField(20);

        entryValuesPanel.add(moneyLabel);
        entryValuesPanel.add(moneyTextField);
        entryValuesPanel.add(Box.createHorizontalStrut(10));
        entryValuesPanel.add(dateLabel);
        entryValuesPanel.add(datePicker);
        entryValuesPanel.add(Box.createVerticalStrut(40));
        entryValuesPanel.add(textLabel);
        entryValuesPanel.add(textField);

        int answer = JOptionPane.showConfirmDialog(null, entryValuesPanel, "Aggiungi Voce", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (answer == JOptionPane.OK_OPTION) {

            String text = textField.getText();
            String moneyString = moneyTextField.getText();
            String date = datePicker.getFormattedTextField().getText();
            float money = 0;

            if(!moneyString.equals("")){                                            // Controllo che ci sia almeno l'importo
                moneyString = moneyString.replace(',', '.');        // Posso inserire sia float con la virgola che con il punto

                try{
                    money = Float.parseFloat(moneyString);
                }
                catch (NumberFormatException exception){}

                if(date.equals("")){
                    Date today = new Date();
                    tableModel.addEntry(new TableEntry(today, text, money));        // Costruttore con parametro Date
                }
                else
                    tableModel.addEntry(new TableEntry(date, text, money));         // Costruttore con parametro String
            }
        }

    }
}
