# GestioneBilancio
Applicativo Java per la gestione di un bilancio - Esame "Programmazione ad Oggetti", Corso di Scienze Informatiche, Unimore 

### Caratteristiche principali di funzionamento
- Gestione di un bilancio tramite inserimento, eliminazione e modifica delle sue voci.
- Visualizzazione delle voci filtrata per giorno, settimana, mese ed anno.
- Ricerca sul campo testo delle voci di bilancio.
- Salvataggio e ri-apertura di un bilancio (formato binario specifico utilizzato dall'applicativo).
- Esportazione delle voci visualizzate in formato CSV, Testo, OpenDocument.

---

## Esecuzione
È presente il pacchetto **GestioneBilancio.jar** in `bin/app`

  ### Linux
  `java -jar GestioneBilancio.jar`
  
  ### Windows
  Doppio-click sul file **GestioneBilancio.jar**
  
## Dipendenze
  - Versione di Java: __Java 8__  (openJDK 11 per Linux)
  - L'applicativo utilizza i pacchetti **jdatepicker-2.0.3.jar** e **jOpenDocument-1.5.jar**
    - Sono già inclusi in GestioneBilancio.jar
    - Per l'eventuale compilazione del codice, sono presenti in `bin/lib`
