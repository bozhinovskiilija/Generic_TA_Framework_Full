package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.LoggerUtils;

public class TestListener extends LoggerUtils implements ITestListener {

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
