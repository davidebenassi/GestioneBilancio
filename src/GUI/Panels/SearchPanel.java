package GUI.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Classe che descrive il pannello con funzione di ricerca
 */
public class SearchPanel extends JPanel implements ActionListener {

    private JButton searchButton, nextButton, stopButton;
    private JTextField textField;

    private TablePanel tablePanel;
    private List<Integer> occurrences;
    private int occurrencesListIndex;
    public SearchPanel(Dimension dimension, TablePanel tablePanel){
        setPreferredSize(dimension);
        this.tablePanel = tablePanel;

        JLabel title = new JLabel("Ricerca Voci");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("Dialog", Font.BOLD, 16 ));

        textField = new JTextField(22);
        textField.setAlignmentX(CENTER_ALIGNMENT);

        searchButton = new JButton("Cerca");
        searchButton.setPreferredSize(new Dimension(80, 20));
        searchButton.addActionListener(this);

        nextButton = new JButton(">");
        nextButton.setPreferredSize(new Dimension(45, 20));
        nextButton.addActionListener(this);
        nextButton.setEnabled(false);

        stopButton = new JButton("X");
        stopButton.setPreferredSize(new Dimension(45, 20));
        stopButton.addActionListener(this);
        stopButton.setEnabled(false);

        add(Box.createRigidArea(new Dimension(dimension.width, 20)));
        add(title);
        add(Box.createRigidArea(new Dimension(dimension.width, 15)));
        add(textField);
        add(searchButton);
        add(Box.createHorizontalStrut(60));
        add(nextButton);
        add(stopButton);
    }

    /**
     * Action Performed per la gestione dei pulsanti per ricerca, successivo e interruzione della ricerca
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Cerca")){
            String searchText = textField.getText();

            if (!searchText.equals("")){
                this.tablePanel.getTableModel().completeView();     //Quando si fa una ricerca, si fa per forza sulla lista intera di entries
                this.stopButton.setEnabled(true);

                occurrences = this.tablePanel.getTableModel().getOccurrences(searchText);
                if(occurrences.isEmpty()){
                    this.textField.setBackground(new Color(250, 35, 35));
                }
                else{
                    this.textField.setBackground(Color.white);
                    this.occurrencesListIndex = 0;
                    int rowIndex = occurrences.get(this.occurrencesListIndex);
                    this.tablePanel.getTable().setRowSelectionInterval(rowIndex, rowIndex);
                    this.nextButton.setEnabled(true);
                }
            }
        }

        else if (command.equals("X")){
            this.textField.setText("");
            this.textField.setBackground(Color.white);
            this.nextButton.setEnabled(false);
            this.stopButton.setEnabled(false);
            this.tablePanel.getTable().getSelectionModel().clearSelection();
        }

        else if (command.equals(">")){
            if (this.occurrencesListIndex < this.occurrences.size()-1){
                this.occurrencesListIndex += 1;
            }
            int rowIndex = occurrences.get(this.occurrencesListIndex);
            this.tablePanel.getTable().setRowSelectionInterval(rowIndex, rowIndex);
        }
    }
}
