package BalanceTable;

import javax.swing.table.AbstractTableModel;
import java.util.*;

/**
 * Classe descrittiva del TableModel
 */

public class BalanceTableModel extends AbstractTableModel {

    private final List<TableEntry> tableEntries;
    private List<TableEntry> displayedEntries;
    private final String[] headers = {"Data", "Descrizione", "Importo"};
    private int selectedRow;

    /**
     * Costruttore: <br>
     *  - inizializza l'ArrayList di voci totali; <br>
     *  - inizializza l'ArrayList di voci visualizzate.
     */
    public BalanceTableModel() {
        this.tableEntries = new ArrayList<>();
        this.displayedEntries = new ArrayList<>();
        this.selectedRow = -1;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    /**
     * Ritorna il numero di voci visualizzate (non quelle totali)
     */
    @Override
    public int getRowCount() {
        return displayedEntries.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        TableEntry entry = displayedEntries.get(row);

        switch (column){
            case 0:
                return entry.getFormattedDate();
            case 1:
                return entry.getText();
            case 2:
                return String.format("%.2f", entry.getMoney());
            default:
                return null;
        }
    }

    /**
     * Metodo getter dell'array di headers della tabella
     * @return Array di String
     */
    public String[] getHeaders() {
        return headers;
    }

    /**
     * Metodo getter per ottenere l'indice della linea selezionata. <br>
     * Ritorna -1 se non sto visualizzando tutte le voci di bilancio, per evitare errori in modifica e/o eliminazione.
     */
    public int getSelectedRow(){
        if(!tableEntries.equals(displayedEntries))
            selectedRow = -1;

        return this.selectedRow;
    }

    /**
     * Metodo setter per l'indice di linea selezionata. Utilizzato per evidenziare una linea specifica.
     * @param selectedRow indice della linea da evidenziare
     */
    public void setSelectedRow(int selectedRow){
        this.selectedRow = selectedRow;
    }

    /**
     * Aggiunge una voce al bilancio
     * @param entry Oggetto della classe BalanceTable.TableEntry
     */
    public void addEntry(TableEntry entry) {
        this.tableEntries.add(entry);
        this.displayedEntries.add(entry);
        this.fireTableDataChanged();
    }

    /**
     * Elimina la voce selezionata dal bilancio
     * @param index intero che indica la posizione della voce da eliminare
     */
    public void deleteEntry(int index) {
        this.tableEntries.remove(index);
        this.displayedEntries.remove(index);
        this.fireTableDataChanged();
    }

    /**
     * Modifica la voce selezionata, impostandone i parametri passati
     * @param index intero che indica la posizione della voce da modificare
     * @param date nuova data
     * @param text nuova descrizione
     * @param money nuovo importo
     */
    public void modifyEntry(int index, String date, String text, float money){
        TableEntry tmpEntry = this.tableEntries.get(index);
        tmpEntry.setDate(date);
        tmpEntry.setText(text);
        tmpEntry.setMoney(money);

        tmpEntry = this.displayedEntries.get(index);
        tmpEntry.setDate(date);
        tmpEntry.setText(text);
        tmpEntry.setMoney(money);

        fireTableDataChanged();
    }

    /**
     * Ritorna una voce del bilancio
     * @param index intero che indica la posizione della voce da ritornare
     * @return voce di bilancio come oggetto BalanceTable.TableEntry
     */
    public TableEntry getEntry(int index){
        return this.tableEntries.get(index);
    }

    /**
     * Metodo che restituisce la lista completa delle voci di bilancio
     * @param allBalance 'true' ritorna tutte le voci -- 'false' ritorna solo le voci visualizzate in quel momento
     * @return List di TableEntry
     * Il parametro allBalance viene utilizzato nel seguente modo: <br>
     *  - Il salvataggio salva tutte le voci di bilancio; <br>
     *  - L'esportazione esporta solo le voci visualizzate.
     */
    public List<TableEntry> getTableEntries(Boolean allBalance){

        if (allBalance)
            return this.tableEntries;
        else
            return this.displayedEntries;
    }

    /**
     * Metodo per trasformare la lista di voci in un array di oggetti.<br>
     * Chiama il metodo toObject della classe BalanceTable.TableEntry sulle voci di bilancio.
     * @return Array di voci sotto forma di oggetti generici
     */
    public Object[][] getEntriesAsObjects(){
        Object[][] object = new Object[this.getRowCount()][3];
        for(int i = 0; i<this.getRowCount(); i++){
            object[i] = this.displayedEntries.get(i).toObject();
        }

        return object;
    }

    /**
     * Metodo per svuotare il bilancio da tutte le voci
     */
    public void removeAllEntries(){
        this.tableEntries.clear();
        this.displayedEntries.clear();
        fireTableDataChanged();
    }

    /**
     * Metodo che calcola e ritorna il totale degli importi delle voci visualizzate
     */
    public float getTotalDisplayedMoney(){
        float total = 0;
        for(TableEntry entry: displayedEntries){
            total += entry.getMoney();
        }
        return total;
    }


    /**
     * Metodo per cercare le voci che contengono un testo arbitrario
     * @param searchText testo da ricercare nel campo descrizione della voce
     * @return List con posizioni delle voci di bilancio che rispettano il match con il parametro passato
     */
    public List<Integer> getOccurrences(String searchText){
        List<Integer> occurrencesList = new ArrayList<>();

        for(TableEntry entry : tableEntries){
            if (entry.getText().contains(searchText))
                occurrencesList.add( tableEntries.indexOf(entry));
        }

        return occurrencesList;
    }

    /*      ----- FUNZIONI PER VISUALIZZAZIONE -----     */

    /**
     * Metodo che visualizza nella tabella solo le voci registrate nel giorno specificato
     * @param day giorno dell'anno da visualizzare
     */
    public void filterByDay(Date day){
        displayedEntries.clear();
        for(TableEntry entry : tableEntries){
            if(entry.getDate().equals(day))
                displayedEntries.add(entry);
        }
        fireTableDataChanged();
    }

    /**
     * Metodo che visualizza nella tabella solo le voci registrate nella settimana specificata
     * @param dayOfTheWeek un giorno nella settimana d'interesse
     */
    public void filterByWeek(Date dayOfTheWeek){
        displayedEntries.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dayOfTheWeek);
        int selectedWeek = calendar.get(Calendar.WEEK_OF_YEAR);

        for(TableEntry entry : tableEntries){
            calendar.setTime(entry.getDate());
            if( calendar.get(Calendar.WEEK_OF_YEAR) == selectedWeek )
                displayedEntries.add(entry);
        }
        fireTableDataChanged();
    }

    /**
     * Metodo che visualizza nella tabella solo le voci registrate nel mese specificato
     * @param month mese e anno formattati come data
     */
    public void filterByMonth(Date month){
        displayedEntries.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(month);
        int selectedMonth = calendar.get(Calendar.MONTH);
        int selectedYear = calendar.get(Calendar.YEAR);

        for(TableEntry entry : tableEntries){
            calendar.setTime(entry.getDate());
            if( calendar.get(Calendar.YEAR) == selectedYear && calendar.get(Calendar.MONTH) == selectedMonth)
                displayedEntries.add(entry);
        }
        fireTableDataChanged();
    }

    /**
     * Metodo che visualizza nella tabella solo le voci registrate nell'anno specificato
     * @param year anno formattato come data
     */
    public void filterByYear(Date year){
        displayedEntries.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(year);
        int selectedYear = calendar.get(Calendar.YEAR);

        for(TableEntry entry : tableEntries){
            calendar.setTime(entry.getDate());
            if( calendar.get(Calendar.YEAR) == selectedYear)
                displayedEntries.add(entry);
        }
        fireTableDataChanged();
    }

    /**
     * Metodo che visualizza nella tabella tutte le voci registrate nel bilancio
     */
    public void completeView(){
        displayedEntries = new ArrayList<>(tableEntries);
        fireTableDataChanged();
    }
}
