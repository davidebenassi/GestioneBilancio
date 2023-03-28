package ExportOnFile;

import BalanceTable.BalanceTableModel;
import BalanceTable.TableEntry;

import java.io.FileWriter;
import java.io.IOException;


public class ExportText extends Export{
    public ExportText(String fileName) {
        super(fileName);
    }

    /**
     * Specifica il metodo della classe padre per l'esportazione in formato testo - Carattere separatore "TAB"
     * @param tableModel table model da esportare su file
     */
    @Override
    public void exportTableModel(BalanceTableModel tableModel) {
        try {
            FileWriter fileWriter = new FileWriter(this.file);

            for (TableEntry entry : tableModel.getTableEntries(false)) {
                String toWrite = entry.getFormattedDate() + "\t" + entry.getText() + "\t" + entry.getMoney() + "\n";
                fileWriter.write(toWrite);
            }
            fileWriter.close();
        }
        catch(IOException e){}
    }
}
