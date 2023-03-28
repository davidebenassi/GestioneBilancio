package GUI.ActionListener.MenuBar;

import BalanceTable.BalanceTableModel;
import ExportOnFile.Export;
import ExportOnFile.ExportCSV;
import ExportOnFile.ExportODS;
import ExportOnFile.ExportText;
import GUI.Panels.TablePanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Action Listener per la gestione dell'esportazione su file delle voci di bilanzio visualizzate
 */
public class ExportActionListener implements ActionListener {
    private final TablePanel balanceTablePanel;

    public ExportActionListener(TablePanel balanceTablePanel) {
        this.balanceTablePanel = balanceTablePanel;
    }

    /**
     * Action Performed che dispone un file chooser e salva le varie voci nel formato scelto dal menu tramite un oggetto ExportOnFile.Export
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        BalanceTableModel tableModel = this.balanceTablePanel.getTableModel();

        // Controllo se ci sono entries visualizzate da esportare
        if(!tableModel.getTableEntries(false).isEmpty()){

            // Generico oggetto Export che viene specializzato nel momento in cui si sceglie il formato di export
            Export export;

            String exportType = e.getActionCommand();
            String fileName;
            int returnValue;

            File fileChooserStartDirectory = new File( System.getProperty("user.home") );

            JFileChooser fileChooser = new JFileChooser(fileChooserStartDirectory);
            fileChooser.setAcceptAllFileFilterUsed(false);

            switch (exportType){
                case "CSV":
                    fileChooser.setFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
                    returnValue = fileChooser.showSaveDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        fileName = fileChooser.getSelectedFile().getAbsolutePath();

                        if(!fileName.endsWith(".csv"))
                            fileName = fileName + ".csv";

                        export = new ExportCSV(fileName);
                        export.exportTableModel(tableModel);
                    }
                    break;

                case "Testo":
                    fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
                    returnValue = fileChooser.showSaveDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        fileName = fileChooser.getSelectedFile().getAbsolutePath();

                        if(!fileName.endsWith(".txt"))
                            fileName = fileName + ".txt";

                        export = new ExportText(fileName);
                        export.exportTableModel(tableModel);
                    }
                    break;

                case "Open Document":
                    fileChooser.setFileFilter(new FileNameExtensionFilter("*.ods", "ods"));
                    returnValue = fileChooser.showSaveDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        fileName = fileChooser.getSelectedFile().getAbsolutePath();

                        if(!fileName.endsWith(".ods"))
                            fileName = fileName + ".ods";

                        export = new ExportODS(fileName);
                        export.exportTableModel(tableModel);
                    }
                    break;

                default:
                    break;
            }
            JOptionPane.showMessageDialog(null, "Voci visualizzate esportate con successo", "Gestione Bilancio", JOptionPane.PLAIN_MESSAGE);
        }
        else{
            String errorMessage = "Non ci sono voci visualizzate da esportare";
            JOptionPane.showMessageDialog(null, errorMessage,"Gestione Bilancio", JOptionPane.ERROR_MESSAGE);
        }


    }
}
