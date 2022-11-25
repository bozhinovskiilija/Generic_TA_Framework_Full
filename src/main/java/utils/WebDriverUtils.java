package utils;

import data.Time;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class WebDriverUtils extends LoggerUtils {


    public static WebDriver setupDriver() {

        log.info("setupDriver()");
        WebDriver driver = null;

        String browser = PropertiesUtils.getBrowser();
        boolean remote = PropertiesUtils.getRemote();
        boolean headless = PropertiesUtils.getHeadless();
        String hubUrl = PropertiesUtils.getHubUrl();
        String driverFolder = PropertiesUtils.getDriverFolder();

        String pathDriverChrome = driverFolder + "chromedriver.exe";
        String pathDriverFirefox = driverFolder + "geckodriver.exe";
        String pathDriverEdge = driverFolder + "msedgedriver.exe";

        try {
            switch (browser) {
                case "chrome": {
                    ChromeOptions options = new ChromeOptions();
                    options.setHeadless(headless);
                    options.addArguments("--window-size=1600x900");

                    if (remote) {

                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(hubUrl), options);
                        remoteDriver.setFileDetector(new LocalFileDetector());
                        driver = remoteDriver;

                    } else {

                        // WebDriverManager.chromedriver().setup();
                        // ChromeOptions opt = new ChromeOptions();
                        //
                        // opt.addArguments("--no-sandbox");
                        //
                        // opt.addArguments("--disable-dev-shm-usage");
                        //
                        // opt.addArguments("--headless");

                        //driver = new ChromeDriver(opt);
                        System.setProperty("webdriver.chrome.driver", pathDriverChrome);
                        driver = new ChromeDriver(options);
                    }
                    break;
                }
                case "firefox": {
                    FirefoxOptions options = new FirefoxOptions();
                    options.setHeadless(headless);
                    options.addArguments("--window-size=1600x900");

                    if (remote) {

                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(hubUrl), options);
                        remoteDriver.setFileDetector(new LocalFileDetector());
                        driver = remoteDriver;

                    } else {
                        WebDriverManager.firefoxdriver().setup();
                        driver=new FirefoxDriver();
                        //System.setProperty("webdriver.gecko.driver", pathDriverFirefox);
                       // driver = new FirefoxDriver(options);
                    }


                    break;
                }
                case "edge": {

                    EdgeOptions options = new EdgeOptions();
                    options.setHeadless(headless);
                    options.addArguments("--window-size=1600x900");

                    if (remote) {

                        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(hubUrl), options);
                        remoteDriver.setFileDetector(new LocalFileDetector());
                        driver = remoteDriver;

                    } else {
                        System.setProperty("webdriver.edge.driver", pathDriverEdge);
                        driver = new EdgeDriver(options);
                    }

                }
                default: {
                    Assert.fail("Can not create driver! Browser: '" + browser + "' is not recognized");
                }
            }
        } catch (MalformedURLException e) {
            Assert.fail(
                "Can not create driver! Path to browser: '" + browser + "' driver is not correct! Message: " + e.getMessage());
        }

        //Setup implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Time.TIME_LONG));
        //wait for the DOM strucure to be loaded(that doesn't mean page is fully loaded)
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Time.TIME_LONG));
        //only for asinc script
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(Time.TIME_LONGEST));


        //maximize the browser (not work on headless browser) - you should set manualy window size
        driver.manage().window().maximize();

        return driver;
    }

    public static SessionId getSessionID(WebDriver driver){
        return ((RemoteWebDriver) driver).getSessionId();
    }

    //when sessionID is null but driver is not null(driver reference is still valid)
    public static boolean hasDriverQuit(WebDriver driver){
        if(driver!=null){
            return getSessionID(driver)==null;
        }else{
            return true;
        }
    }

    //QuitDriver()
    public static void QuitDriver(WebDriver driver){
        log.info("Quit driver");
        if(!hasDriverQuit(driver)){
            driver.quit();
        }



    }


    public static void setImplicitWait(WebDriver driver, int timeout) {

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
    }

}
