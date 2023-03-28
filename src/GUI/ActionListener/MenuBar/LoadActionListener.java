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
 * Action Listener per la gestione del caricamento da file dell'intero bilancio
 */
public class LoadActionListener implements ActionListener {

    private TablePanel balanceTablePanel;
    public LoadActionListener(TablePanel balanceTablePanel){
        this.balanceTablePanel = balanceTablePanel;
    }

    /**
     * Action Performed che dispone un file chooser e carica le varie voci de-serializzandole. <br>
     * Viene comunicato che le voci non salvate vengono eliminate prima di caricare le nuove voci dal file
     */
    @Override
    public void actionPerformed(ActionEvent exception) {
        BalanceTableModel table = this.balanceTablePanel.getTableModel();

        // Dialog per chiedere conferma all'utente sull'eliminazione delle entries non salate
        String warningMessage = "Le eventuali voci non salvate verranno eliminate.\n" +
                                "Si vuole procedere?";

        int response = JOptionPane.showConfirmDialog(null, warningMessage, "Gestione Bilancio", JOptionPane.WARNING_MESSAGE);

        if(response == JOptionPane.OK_OPTION){
            table.removeAllEntries();

            File fileChooserStartDirectory = new File( System.getProperty("user.home") );

            JFileChooser fileChooser = new JFileChooser(fileChooserStartDirectory);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(new FileNameExtensionFilter("*.jbm", "jbm"));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {

                //Lettura delle entries da file e aggiunta all'array
                try{
                    FileInputStream fileIn = new FileInputStream(fileChooser.getSelectedFile().getAbsolutePath());
                    ObjectInputStream inputStream = new ObjectInputStream(fileIn);

                    try{
                        while (true){
                            table.addEntry((TableEntry) inputStream.readObject());
                        }
                    }
                    catch (EOFException e){
                        inputStream.close();
                        fileIn.close();
                    }

                }
                catch (FileNotFoundException e) {}
                catch (IOException e){}
                catch (ClassNotFoundException e) {}
            }
        }
    }
}
