package no.statnett.noa.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by arne-richard.hofsoy on 09/12/14.
 */
public class Importerer {

    public Importerer(File file) throws IOException, InvalidFormatException{
        Workbook wb = WorkbookFactory.create(file);
        Sheet mySheet = wb.getSheetAt(0);
        int rowNum = mySheet.getLastRowNum() + 1;
        int colNum = mySheet.getRow(0).getLastCellNum();
    }
}
