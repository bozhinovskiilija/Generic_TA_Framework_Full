package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JavaScriptUtils extends LoggerUtils{

    private static final String dragAndDropJavaScriptFilePath = "javascript/drag_and_drop.js";
    private static final String dragAndDropJavaScriptFilePath2 = "javascript/drag_and_drop_helper.js";

    private static String loadJavaScriptFile(String filePath){

        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(filePath);

        StringBuilder javaScript = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){

            String line;
            while((line = reader.readLine())!=null){
                javaScript.append(line).append(" ");
            }

        } catch (IOException e) {
            Assert.fail("Can not read JavaScript file '" + filePath + "'. Message " + e.getMessage());
        }

        return javaScript.toString();
    }

    public static void simulateDragAndDrop(WebDriver driver, String sourceLocator, String destinationLocator){

        log.trace("simulateDragAndDrop()");
        String JavaScript = loadJavaScriptFile(dragAndDropJavaScriptFilePath);
        JavaScript = JavaScript + "DndSimulator.simulate('"+ sourceLocator + "', '" + destinationLocator + "');";
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(JavaScript);
    }

    public static void simulateDragAndDrop2(WebDriver driver, String sourceLocator, String destinationLocator){

        log.trace("simulateDragAndDrop()");
        String JavaScript = loadJavaScriptFile(dragAndDropJavaScriptFilePath2);
        JavaScript = JavaScript + "$('"+sourceLocator+"').simulateDragDrop({ dropTarget: '"+destinationLocator+"'});";
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(JavaScript);
    }
}
