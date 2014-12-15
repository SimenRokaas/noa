package no.statnett.noa.excel;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Klassen skal importere data fra exel ark og skrive til databasen de
 * objekter som opprettes.
 */
public final class Importerer {

    enum ImportExcelFelt {
        NAVN("B", 1),
        BESKRIVELSE("B", 3),
        NETTSELSKAP("B", 6),
        NETTNIVÅ("B", 8),
        UTVEKSLINGSPUNKT_BESKRIVELSE("C", -1),
        UTVEKSLINGSPUNKT_LOK("D", -1),
        UTVEKSLINGSPUNKT_SPENNINGSNIVÅ("E", -1),
        UTVEKSLINGSPUNKT_LINJEN("F", -1),
        UTVEKSLINGSPUNKT_NETTSELSKAP("G", -1),
        UTVEKSLINGSPUNKT_MÅLERTYPE("H", -1),
        UTVEKSLINGSPUNKT_MÅLERID("I", -1),
        UTVEKSLINGSPUNKT_KOMPONENTKODE("J", -1),
        UTVEKSLINGSPUNKT_KOORD("K", -1);

        int rad;
        String kolonne;

        private ImportExcelFelt(String kolonne, int rad) {
            this.rad = rad;
            this.kolonne = kolonne;
        }

        public int getRad() {
            return rad;
        }

        public int getKolonne() {
            return CellReference.convertColStringToIndex(kolonne);
        }
    }

    private Workbook arbeidsbok;
    private final int UTVEKSLINGSPUNKT_STARTRAD = 14;
    private List<String> logg;
    private HashMap<String[], LinkedList<String[]>> resultat;

    public Importerer() throws IOException, InvalidFormatException{
        logg = new LinkedList<>();
        resultat = new HashMap<>();
    }

    public HashMap<String[], LinkedList<String[]>> importer(File fil) {
        try {
            arbeidsbok = WorkbookFactory.create(fil);
            if (arbeidsbok.getNumberOfSheets() == 0) {
                logg.add("Feilet fil " + fil.getAbsolutePath() + " har ingen gyldige ark");
            }
            for (int i = 0; i < arbeidsbok.getNumberOfSheets(); i++) {
                Sheet ark = arbeidsbok.getSheetAt(i);
                // TODO: better way to check valid data?
                try {
                    if (ark.getSheetName().contains("N")) {
                        besteInnsatsOversettelse(ark);
                        logg.add("Vellykket import av ark " + ark.getSheetName() + " fra fil " + fil.getAbsolutePath());
                    } else {
                        logg.add("Avvist ark " + ark.getSheetName() + " er ikke gyldig");
                    }
                } catch (Exception e) {
                    logg.add("Feilet ark " + ark.getSheetName() + ", " + e.getMessage());
                }
            }
        } catch (NullPointerException ne) {
            logg.add("Feilet fil. Filen er null");
        } catch(InvalidFormatException | IOException e) {
            logg.add("Feilet fil " + fil.getAbsolutePath() + ", " + e.getMessage());
        }
        return resultat;
    }

    private void besteInnsatsOversettelse(Sheet ark) throws DataIkkeFunnetException{
        if (UTVEKSLINGSPUNKT_STARTRAD > ark.getLastRowNum() + 1) {
            throw new DataIkkeFunnetException("Mulig feil i arket, fant ikke forventet start rad for Utvekslingspungter. Rad : " + UTVEKSLINGSPUNKT_STARTRAD);
        }

        String navn = ark.getRow(ImportExcelFelt.NAVN.getRad()).getCell(ImportExcelFelt.NAVN.getKolonne()).getStringCellValue().trim();
        String beskrivelse = ark.getRow(ImportExcelFelt.BESKRIVELSE.getRad()).getCell(ImportExcelFelt.BESKRIVELSE.getKolonne()).getStringCellValue().trim();
        String nettselskap = ark.getRow(ImportExcelFelt.NETTSELSKAP.getRad()).getCell(ImportExcelFelt.NETTSELSKAP.getKolonne()).getStringCellValue().trim();
        String nettnivå = ark.getRow(ImportExcelFelt.NETTNIVÅ.getRad()).getCell(ImportExcelFelt.NETTNIVÅ.getKolonne()).getStringCellValue().trim();

        // TODO: kan vi forvente disse felter påkrevd?
        if (isNullEllerTom(navn) || isNullEllerTom(beskrivelse) || isNullEllerTom(nettselskap) || isNullEllerTom(nettnivå)) {
            throw new DataIkkeFunnetException("Felt for navn, beskrivelse, nettselskap eller nettnivå kan ikke være tomt");
        }

        String[] områdeinfo = {navn, beskrivelse, nettselskap, nettnivå};
        LinkedList<String[]> utvekslingspunkter = new LinkedList<>();

        for (int i = UTVEKSLINGSPUNKT_STARTRAD; i < ark.getLastRowNum() + 1; i++) {

            String utvpnkt_beskrivelse = getValueOrNull(ark.getRow(i).getCell(ImportExcelFelt.UTVEKSLINGSPUNKT_BESKRIVELSE.getKolonne()));
            String utvpnkt_loc = getValueOrNull(ark.getRow(i).getCell(ImportExcelFelt.UTVEKSLINGSPUNKT_LOK.getKolonne()));
            String utvpnkt_spenningsnivå = getValueOrNull(ark.getRow(i).getCell(ImportExcelFelt.UTVEKSLINGSPUNKT_SPENNINGSNIVÅ.getKolonne()));
            String utvpnkt_linjen = getValueOrNull(ark.getRow(i).getCell(ImportExcelFelt.UTVEKSLINGSPUNKT_LINJEN.getKolonne()));
            String utvpnkt_nettselskap = getValueOrNull(ark.getRow(i).getCell(ImportExcelFelt.UTVEKSLINGSPUNKT_NETTSELSKAP.getKolonne()));
            String utvpnkt_målertype = getValueOrNull(ark.getRow(i).getCell(ImportExcelFelt.UTVEKSLINGSPUNKT_MÅLERTYPE.getKolonne()));
            String utvpnkt_målerid = getValueOrNull(ark.getRow(i).getCell(ImportExcelFelt.UTVEKSLINGSPUNKT_MÅLERID.getKolonne()));
            String utvpnkt_komponentkode = getValueOrNull(ark.getRow(i).getCell(ImportExcelFelt.UTVEKSLINGSPUNKT_KOMPONENTKODE.getKolonne()));
            String utvpnkt_koord = getValueOrNull(ark.getRow(i).getCell(ImportExcelFelt.UTVEKSLINGSPUNKT_KOORD.getKolonne()));

            // TODO: kan vi forvente noen felter påkrevd?
            if (utvpnkt_beskrivelse != null && !utvpnkt_beskrivelse.isEmpty()) {
                String[] punkt = {utvpnkt_beskrivelse, utvpnkt_loc, utvpnkt_spenningsnivå, utvpnkt_linjen,
                        utvpnkt_nettselskap, utvpnkt_målertype, utvpnkt_målerid, utvpnkt_komponentkode, utvpnkt_koord};
                utvekslingspunkter.add(punkt);
            }
        }

        resultat.put(områdeinfo, utvekslingspunkter);
    }

    private boolean isNullEllerTom(String verdi) {
        boolean retval = false;
        if (verdi == null) {
            retval = true;
        } else if (verdi.isEmpty()) {
            retval = true;
        }
        return retval;
    }

    private String getValueOrNull(Cell celle) {
        if (celle == null) {
            return null;
        }
        switch (celle.getCellType()) {
            case Cell.CELL_TYPE_STRING: return celle.getStringCellValue().trim();
            case Cell.CELL_TYPE_NUMERIC: return String.valueOf(celle.getNumericCellValue()).trim();
            case Cell.CELL_TYPE_BLANK: return "";
            default: throw new RuntimeException("Celle type ukjent! " + celle.getCellType());
        }
    }

    public List<String> getLogg() {
        return logg;
    }
}

class DataIkkeFunnetException extends Exception {

    public DataIkkeFunnetException(String message) {
        super(message);
    }

}
