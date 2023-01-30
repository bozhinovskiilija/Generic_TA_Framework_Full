package pages;

import data.PageUrlPaths;
import data.Time;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class PracticePage extends CommonLoggedInPage{

    // Page Url Path
    private final String PRACTICE_PAGE_URL = getPageUrl(PageUrlPaths.PRACTICE_PAGE);

    // Locators
    private final String draggableImageLocatorString = "imgDrag-image";
    private final String dragAreaLocatorString = "div#drag-area";
    private final String dropAreaLocatorString = "div#drop-area";

    @FindBy(xpath = "//div[@id='useless-tooltip']/p[contains(@class,'h4 heading')]")
    private WebElement uselessTooltipTitle;

    private final By draggableImageLocator = By.id("drag-image");

    @FindBy(id = "useless-tooltip-text")
    private WebElement uselessTooltip;

    @FindBy(id = "drag-area")
    private WebElement dragArea;

    @FindBy(id = "drop-area")
    private WebElement dropArea;

    @FindBy(id = "drag-image")
    private WebElement draggableImage;

    @FindBy(id = "drag-and-drop-message")
    private  WebElement draAndDropMessage;


    // Constructor
    public PracticePage(WebDriver driver) {
        super(driver);
        log.trace("new PracticePage()");
    }

    public PracticePage open() {
        return open(true);
    }

    public PracticePage open(boolean bVerify) {
        log.debug("Open PracticePage (" + PRACTICE_PAGE_URL + ")");
        openUrl(PRACTICE_PAGE_URL);
        if (bVerify) {
            verifyPracticePage();
        }
        return this;
    }

    public PracticePage verifyPracticePage() {
        log.debug("verifyPracticePage()");
        waitForUrlChange(PRACTICE_PAGE_URL, Time.TIME_SHORTER);
        waitUntilPageIsReady(Time.TIME_SHORT);
        return this;
    }

    public boolean isUselessTooltipDisplayed(){
        log.debug("isUselessTooltipDisplayed()");
        return isWebElementDisplayed(uselessTooltip);
    }

    public String getUselessTooltipText(){
        log.debug("isUselessTooltipDisplayed()");

        //mouse move
        moveMouseToWebElement(uselessTooltip);

        //verify if tooltip is displayed
        Assert.assertTrue(isUselessTooltipDisplayed(),"Tooltip is not displayed");

        //get text from tooltip
        return getTextFromWebElement(uselessTooltip);

    }

    public boolean isDragAndDropMessageDisplayed(){
        log.debug("isDragAndDropMessageDisplayed()");
        return isWebElementDisplayed(draAndDropMessage);
    }

    public String getDragAndDropMessageText(){
        log.debug("getDragAndDropMessageText()");
        Assert.assertTrue(isDragAndDropMessageDisplayed(),"Message is not displayed on practice page");
        return getTextFromWebElement(draAndDropMessage);
    }

    //do not work on html 5
    public PracticePage dragAndDropImage(){
        log.debug("dragAndDropImage()");
        doDragAndDrop(draggableImage,dropArea);
        PracticePage practicePage = new PracticePage(driver);
        return practicePage.verifyPracticePage();
    }

    //work on html 5 pages // js simulator for drag and drop
    public PracticePage dragAndDropImageJS(){
        log.debug("dragAndDropImageJS()");
        doDragAndDropJS(draggableImageLocatorString,dropAreaLocatorString);
        PracticePage practicePage = new PracticePage(driver);
        return practicePage.verifyPracticePage();
    }

    public boolean isImagePresentInDragArea(){
        log.debug("isImagePresentInDragArea()");
        return isNestedWebElementDisplayed(dragArea, draggableImageLocator);
    }

    public boolean isImagePresentInDropArea(){
        log.debug("isImagePresentInDropArea()");
        return isNestedWebElementDisplayed(dropArea, draggableImageLocator);
    }
}
