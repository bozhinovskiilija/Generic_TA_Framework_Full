package listeners;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.LoggerUtils;

import java.io.ByteArrayInputStream;
import java.util.UUID;

public class TestListener extends LoggerUtils implements ITestListener {

    WebDriver driver=null;
    @Override
    public void onTestStart(final ITestResult result) {
        ITestListener.super.onTestStart(result);
    }


    @Override
    public void onTestSuccess(final ITestResult result) {
        ITestListener.super.onTestSuccess(result);
    }


    @Override
    public void onTestFailure(final ITestResult result) {
        //ITestListener.super.onTestFailure(result);

        ITestContext context = result.getTestContext();
        driver = (WebDriver) context.getAttribute("WebDriver");
        Allure.addAttachment(UUID.randomUUID().toString(), new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(
            OutputType.BYTES)));

    }


    @Override
    public void onTestSkipped(final ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }


    @Override
    public void onStart(final ITestContext context) {
        ITestListener.super.onStart(context);
    }


    @Override
    public void onFinish(final ITestContext context) {
        ITestListener.super.onFinish(context);
    }

}
