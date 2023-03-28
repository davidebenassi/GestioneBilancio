package GUI.ActionListener.MenuBar;

import BalanceTable.BalanceTableModel;
import BalanceTable.TableEntry;
import GUI.Panels.TablePanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Action Listener per la gestione del salvataggio su file dell'intero bilancio
 */
public class SaveActionListener implements ActionListener {

    private final TablePanel balanceTablePanel;
    public SaveActionListener(TablePanel balanceTablePanel){
        this.balanceTablePanel = balanceTablePanel;
    }

    /**
     * Action Performed che dispone un file chooser e salva le varie voci serializzandole
     */
    public void actionPerformed(ActionEvent e) {
        BalanceTableModel tableModel = balanceTablePanel.getTableModel();

        // Controllo se ci sono voci da salvare, altrimenti lo comunico con un OptionPane
        if(!tableModel.getTableEntries(true).isEmpty()){

            // Il file chooser inizia dalla home directory dell'utente
            File fileChooserStartDirectory = new File( System.getProperty("user.home") );

            JFileChooser fileChooser = new JFileChooser(fileChooserStartDirectory);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(new FileNameExtensionFilter("*.jbm", "jbm"));
            int returnValue = fileChooser.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                    if(!fileName.endsWith(".jbm"))
                        fileName = fileName + ".jbm";

                    FileOutputStream fileOut = new FileOutputStream(fileName);
                    ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);

                    for (TableEntry entry : tableModel.getTableEntries(true)) {
                        outputStream.writeObject(entry);
                    }

                    outputStream.close();
                    fileOut.close();

                    JOptionPane.showMessageDialog(null, "Bilancio salvato con successo", "Gestione Bilancio", JOptionPane.PLAIN_MESSAGE);
                } catch (FileNotFoundException fileNotFoundException) {
                } catch (IOException IOexception) {}
            }
        }
        else{
            String errorMessage = "Non ci sono voci nel bilancio da salvare";
            JOptionPane.showMessageDialog(null, errorMessage,"Gestione Bilancio", JOptionPane.ERROR_MESSAGE);
        }
    }
}
