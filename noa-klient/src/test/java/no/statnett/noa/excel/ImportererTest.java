package no.statnett.noa.excel;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class ImportererTest {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ImportererTest.class);

    File testExcelFil, testExcelFilTomtArk, testExcelFilMedFeil;
    Importerer importerer;

    @Before
    public void setup() throws Exception {
        testExcelFil = new File(getClass().getClassLoader().getResource("Nettavregningsomr_AEN_test.xlsx").getFile());
        testExcelFilTomtArk = new File(getClass().getClassLoader().getResource("Nettavregningsomr_AEN_test_tomt_ark.xlsx").getFile());
        testExcelFilMedFeil = new File(getClass().getClassLoader().getResource("Nettavregningsomr_AEN_test_med_feil.xlsx").getFile());
        importerer = new Importerer();
    }

    @Test
    public void testAtFilFinnesOgHarInnhold() {
        assertThat(testExcelFil).isNotNull();
        assertThat(testExcelFil.length()).isGreaterThan(1);
    }

    @Test
    public void testImport() {
        Map<String[], LinkedList<String[]>> result = importerer.importer(testExcelFil);
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        logger.info("Følgende områder importer fra fil" + testExcelFil.getAbsolutePath());
        for (Map.Entry<String[], LinkedList<String[]>> entry : result.entrySet()) {
            String[] key = entry.getKey();
            logger.info("######### NETTAVREGNINGSOMRÅDE #########");
            logger.info(Arrays.toString(key));
            logger.info("######### UTVEKSLINGSPUNKTER #########");
            LinkedList<String[]> value = entry.getValue();
            for (String[] sa : value) {
                logger.info(Arrays.toString(sa));
            }
        }
    }

    @Test
    public void testAtLoggBlirSkrevetVedNullFil() {
        Map<String[], LinkedList<String[]>> result = importerer.importer(null);
        assertThat(importerer.getLogg()).isNotEmpty();
        assertThat(importerer.getLogg().get(0)).isEqualTo("Feilet fil. Filen er null");
    }

    @Test
    public void testAtLoggBlirSkrevetVedFilMedTomtArk() {
        Map<String[], LinkedList<String[]>> result = importerer.importer(testExcelFilTomtArk);
        assertThat(importerer.getLogg()).isNotEmpty();
        assertThat(importerer.getLogg().get(0)).isEqualTo("Avvist ark Ark4 er ikke gyldig");
    }

    @Test
    public void testAtLoggBlirSkrevetVedFilMedFeil() {
        Map<String[], LinkedList<String[]>> result = importerer.importer(testExcelFilMedFeil);
        assertThat(importerer.getLogg()).isNotEmpty();
        assertThat(importerer.getLogg().get(0)).isEqualTo("Feilet ark Nettavregningsområde 1, Felt for navn, beskrivelse, nettselskap eller nettnivå kan ikke være tomt");
    }
}