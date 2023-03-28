package BalanceTable;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe che descrive una voce di bilancio.
 */
public class TableEntry implements Serializable {

    private Date date;
    private String text;
    private float money;

    /**
     * Oggetto "formatter" per uniformare il formato delle date come "DD/MM/YYYY";
     */
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Costruttore
     * @param date Campo data come oggetto "java.util.Date"
     * @param text Campo descrittivo della voce di bilancio
     * @param money Campo importo della voce di bilancio
     */
    public TableEntry(Date date, String text, float money) {
        this.date = date;
        this.text = text;
        this.money = money;
    }

    /**
     * Costruttore
     * @param date Campo data come stringa
     * @param text Campo descrittivo della voce di bilancio
     * @param money Campo importo della voce di bilancio
     */
    public TableEntry(String date, String text, float money) {
        this.setDate(date);
        this.text = text;
        this.money = money;
    }

    /**
     * Metodo getter per la data
     * @return parametro data come oggetto java.util.Date
     */
    public Date getDate(){
        return this.date;
    }

    /**
     * Metodo getter per la data
     * @return parametro data sotto forma di stringa formattata "DD/MM/YYYY"
     */
    public String getFormattedDate(){
        return dateFormatter.format(this.date);
    }

    /**
     * Metodo setter per la data
     * @param date Data sotto forma di stringa
     *
     * Viene controllato il corretto formato della data
     */
    public void setDate(String date) {
        try{
            this.date = dateFormatter.parse(date);
        }
        catch (ParseException e){}
    }

    /**
     * Metodo getter per la descrizione
     * @return descrizione
     */
    public String getText() {
        return text;
    }

    /**
     * Metodo setter per la descrizione
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Metodo getter per l'importo
     * @return valore float dell'importo
     */
    public float getMoney() {
        return this.money;
    }

    /**
     * Metodo setter per l'importo
     */
    public void setMoney(float money) {
        this.money = money;
    }

    /**
     * Metodo per trasformare una voce di bilancio in un oggetto generico
     * @return Object[] con i tre attributi della classe come elementi
     */
    public Object[] toObject(){
        return new Object[] { this.getFormattedDate(), this.text, this.money};
    }
}