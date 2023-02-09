package utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.testng.Assert;

import java.io.FileReader;
import java.io.IOException;

public class CSVUtils extends LoggerUtils {


    public static int getNumberOfRows(String pathToFile) {

        int numberOfRows = 0;
        CSVReader csvReader = null;
        try {

            csvReader = new CSVReaderBuilder(new FileReader(pathToFile)).build();
            while (csvReader.readNext() != null) {
                numberOfRows++;
            }

        } catch (Exception e) {
            Assert.fail("Reading content of csv file '" + pathToFile + "' failed! Message: " + e.getMessage());
        } finally {
            if (csvReader != null) {
                try {
                    csvReader.close();
                } catch (IOException e) {
                    Assert.fail("Closing csv file '" + pathToFile + "' failed! Message: " + e.getMessage());
                }
            }
        }
        return numberOfRows;
    }


    public static String[] getRow(String pathToFile, int rowNumber) {

        CSVReader csvReader = null;
        String[] row = null;
        int numberOfRows = getNumberOfRows(pathToFile);
        Assert.assertTrue(rowNumber < numberOfRows,
            "Can not read row " + rowNumber + ". CSV file '" + pathToFile + "' has " + numberOfRows + "row(s)");

        try {
            csvReader = new CSVReaderBuilder(new FileReader(pathToFile)).withSkipLines(rowNumber).build();
            row = csvReader.readNext();

        } catch (Exception e) {
            Assert.fail("Reading content of csv file '" + pathToFile + "' failed! Message: " + e.getMessage());
        } finally {
            if (csvReader != null) {
                try {
                    csvReader.close();
                } catch (IOException e) {
                    Assert.fail("Closing csv file '" + pathToFile + "' failed! Message: " + e.getMessage());
                }
            }
        }
        return row;
    }


    public static int getPositionOfSpecificColumn(String pathToFile, String columnName) {

        String[] row = getRow(pathToFile, 0);
        int position = 0;
        boolean isFound = false;
        while (position < row.length) {
            if (row[position].equals(columnName)) {
                isFound = true;
                break;
            }
            position++;
        }
        Assert.assertTrue(isFound,"There is no column with name '" + columnName + "' in csv file '"+ pathToFile+"'!");
        return position;
    }

    public static String getValueFromSpecifiedCellInSpecificRow(String pathToFile,int rowNumber, int cellPosition){
        String[] row = getRow(pathToFile, rowNumber);
        int numberOfColumns = row.length;
        Assert.assertTrue(cellPosition<numberOfColumns, "Can not get cell value at position" + cellPosition + "because there is only " + numberOfColumns + " columns");
        return row[cellPosition];
    }

    public static int getRowPositionBySpecifiedValueByColumnPosition(String pathToFile, String fieldValue, int columnPosition){

        int numberOfRows = getNumberOfRows(pathToFile);
        int position = 0;
        boolean isFound = false;
        while(position<numberOfRows){
            String currentFieldValue = getValueFromSpecifiedCellInSpecificRow(pathToFile,position,columnPosition);
            if(currentFieldValue.equals(fieldValue)){
                isFound=true;
                break;
            }
            position++;
        }
        Assert.assertTrue(isFound, "There is no row with cell value '" + fieldValue + "' in column " + columnPosition + "in csv file '" + pathToFile + "'!");
        return position;
    }

    public static String getCellValueBySpecifiedRowAndSpecificColumn(String pathToFile, String rowName, String columnName){
        int columnNumber = getPositionOfSpecificColumn(pathToFile,columnName);
        int rowNumber = getRowPositionBySpecifiedValueByColumnPosition(pathToFile, rowName,0);
        return getValueFromSpecifiedCellInSpecificRow(pathToFile, rowNumber, columnNumber);
    }
}
