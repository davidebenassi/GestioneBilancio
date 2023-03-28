package ExportOnFile;

import BalanceTable.BalanceTableModel;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.IOException;

public class ExportODS extends Export{

    public ExportODS(String fileName) {
        super(fileName);
    }

    /**
     * Specifica il metodo della classe padre per l'esportazione in formato ODS (Open Document)
     * @param tableModel table model da esportare su file
     */
    @Override
    public void exportTableModel(BalanceTableModel tableModel) {
        try{
            TableModel exportModel = new DefaultTableModel(tableModel.getEntriesAsObjects(), tableModel.getHeaders() );
            SpreadSheet.createEmpty(exportModel).saveAs(new File(this.fileName));
            SpreadSheet.createEmpty(exportModel).saveAs(this.file);
        }
        catch(IOException e){

        }

    }
}
