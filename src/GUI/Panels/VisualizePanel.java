package GUI.Panels;

import BalanceTable.BalanceTableModel;
import org.jdatepicker.ComponentColorDefaults;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Classe che descrive il pannello per la gestione dei modi visualizzazione e delle relative funzioni
 */
public class VisualizePanel extends JPanel implements ActionListener {

    private final ButtonGroup radioButtonGroup;
    private final JDatePicker dayDatePicker, weekDatePicker;
    private final JSpinner monthSpinner, yearSpinner;
    private BalanceTableModel tableModel;

    public VisualizePanel(Dimension dimension, BalanceTableModel tableModel){
        setPreferredSize(dimension);
        this.tableModel = tableModel;

        /* Date Picker month color setting */
        ComponentColorDefaults colors = ComponentColorDefaults.getInstance();
        colors.setColor(ComponentColorDefaults.Key.FG_MONTH_SELECTOR, Color.white);

        JLabel title = new JLabel("Visualizzazione");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("Dialog", Font.BOLD, 16 ));

        // --- Radio Button --- //
        radioButtonGroup = new ButtonGroup();

        JRadioButton dayRadioButton = new JRadioButton("Giorno");
        dayRadioButton.setActionCommand("Giorno");
        dayRadioButton.addActionListener(this);

        JRadioButton weekRadioButton = new JRadioButton("Settimana");
        weekRadioButton.setActionCommand("Settimana");
        weekRadioButton.addActionListener(this);

        JRadioButton  monthRadioButton = new JRadioButton("Mese");
        monthRadioButton.setActionCommand("Mese");
        monthRadioButton.addActionListener(this);

        JRadioButton yearRadioButton = new JRadioButton("Anno");
        yearRadioButton.setActionCommand("Anno");
        yearRadioButton.addActionListener(this);

        radioButtonGroup.add(dayRadioButton);
        radioButtonGroup.add(weekRadioButton);
        radioButtonGroup.add(monthRadioButton);
        radioButtonGroup.add(yearRadioButton);
        // -----            ----- //

        // --- Date Pickers --- //
        dayDatePicker = new JDatePicker(new UtilDateModel(), "dd/MM/yyyy");
        dayDatePicker.setPreferredSize(new Dimension(130, 30));
        dayDatePicker.setEnabled(false);

        weekDatePicker = new JDatePicker(new UtilDateModel(), "dd/MM/yyyy, w");
        weekDatePicker.setPreferredSize(new Dimension(130, 30));
        weekDatePicker.setEnabled(false);
        // -----            ----- //

        // --- Spinners --- //
        SpinnerDateModel monthModel = new SpinnerDateModel();
        monthSpinner = new JSpinner(monthModel);
        monthSpinner.setEditor(new JSpinner.DateEditor(monthSpinner, "MM/yyyy"));
        ((JSpinner.DefaultEditor) monthSpinner.getEditor()).getTextField().setEditable(false);
        monthSpinner.setEnabled(false);

        SpinnerDateModel yearModel = new SpinnerDateModel();
        yearSpinner = new JSpinner(yearModel);
        yearSpinner.setEditor(new JSpinner.DateEditor(yearSpinner, "yyyy"));
        ((JSpinner.DefaultEditor) yearSpinner.getEditor()).getTextField().setEditable(false);
        yearSpinner.setEnabled(false);
        // -----            ----- //

        // --- Buttons --- //
        JButton confirmButton = new JButton("Conferma");
        confirmButton.addActionListener(this);
        confirmButton.setPreferredSize(new Dimension(250, 22));

        JButton resetViewButton = new JButton("Visualizzazione Completa");
        resetViewButton.addActionListener(this);
        resetViewButton.setPreferredSize(new Dimension(250, 22));
        // -----            ----- //


        // PANEL COMPOSE //
        add(Box.createRigidArea(new Dimension(dimension.width, 30)));
        add(title);
        add(Box.createRigidArea(new Dimension(dimension.width, 30)));

        add(dayRadioButton);
        add(Box.createHorizontalStrut(36));
        add(dayDatePicker);

        add(weekRadioButton);
        add(Box.createHorizontalStrut(10));
        add(weekDatePicker);

        add(monthRadioButton);
        add(Box.createHorizontalStrut(102));
        add(monthSpinner);

        add(yearRadioButton);
        add(Box.createHorizontalStrut(124));
        add(yearSpinner);

        add(Box.createRigidArea(new Dimension(dimension.width, 30)));
        add(confirmButton);
        add(resetViewButton);
    }


    /**
     * Action Performed: <br>
     *  - abilitazione e disabilitazione dei vari componenti a seconda del radio button selezionato; <br>
     *  - chiamata lla funzione di modifica della visualizzazione in base a quanto selezionato.
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command){
            case "Giorno":
                this.dayDatePicker.setEnabled(true);
                this.weekDatePicker.setEnabled(false);
                this.monthSpinner.setEnabled(false);
                this.yearSpinner.setEnabled(false);
                break;

            case "Settimana":
                this.dayDatePicker.setEnabled(false);
                this.weekDatePicker.setEnabled(true);
                this.monthSpinner.setEnabled(false);
                this.yearSpinner.setEnabled(false);
                break;

            case "Mese":
                this.dayDatePicker.setEnabled(false);
                this.weekDatePicker.setEnabled(false);
                this.monthSpinner.setEnabled(true);
                this.yearSpinner.setEnabled(false);
                break;

            case "Anno":
                this.dayDatePicker.setEnabled(false);
                this.weekDatePicker.setEnabled(false);
                this.monthSpinner.setEnabled(false);
                this.yearSpinner.setEnabled(true);
                break;

            case "Conferma":
                this.dayDatePicker.setEnabled(false);
                this.weekDatePicker.setEnabled(false);
                this.monthSpinner.setEnabled(false);
                this.yearSpinner.setEnabled(false);
                if (this.radioButtonGroup.getSelection() != null) {
                    String visualisationCommand = this.radioButtonGroup.getSelection().getActionCommand();  // get selected radio button
                    changeVisualisation(visualisationCommand);                                              // invoke new visualisation
                }
                this.radioButtonGroup.clearSelection();
                break;

            case "Visualizzazione Completa":
                this.dayDatePicker.setEnabled(false);
                this.weekDatePicker.setEnabled(false);
                this.monthSpinner.setEnabled(false);
                this.yearSpinner.setEnabled(false);
                changeVisualisation("Completa");
                this.radioButtonGroup.clearSelection();
                break;

            default:
                break;
        }
    }

    /**
     * Metodo chiamato dall'Action Performed che gestisce le date selezionate dall'utente e chiama il metodo corretto sul table model
     */
    private void changeVisualisation(String command) {
        Date filterDate;
        SimpleDateFormat dateFormatter;
        switch (command){
            case "Giorno":
                dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    filterDate = dateFormatter.parse(dayDatePicker.getFormattedTextField().getText());
                    tableModel.filterByDay(filterDate);
                }
                catch( ParseException e){}
                break;
            case "Settimana":
                dateFormatter = new SimpleDateFormat("dd/MM/yyyy, w");
                try {
                    filterDate = dateFormatter.parse(weekDatePicker.getFormattedTextField().getText());
                    tableModel.filterByWeek(filterDate);
                }
                catch( ParseException e){}
                break;

            case "Mese":
                filterDate = (Date) monthSpinner.getValue();
                tableModel.filterByMonth(filterDate);
                break;

            case "Anno":
                filterDate = (Date) yearSpinner.getValue();
                tableModel.filterByYear(filterDate);
                break;

            case "Completa":
                tableModel.completeView();
                break;

            default:
                break;
        }
    }
}
