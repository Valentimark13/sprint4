package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPageModel extends AbstractPageModel {
    private final By dropDownList = new By.ByClassName("accordion");
    private final By topOrderBtn = new By.ByXPath("//*[@id=\"root\"]/div/div/div[1]/div[2]/button[1]");
    private final By bottomOrderBtn = new By.ByXPath("//*[@id=\"root\"]/div/div/div[4]/div[2]/div[5]/button");

    private final By scooterLogo = new By.ByXPath("//*[@id=\"root\"]/div/div/div[1]/div[1]/a[2]");

    private final By yandexLogo = new By.ByXPath("//*[@id=\"root\"]/div/div/div[1]/div[1]/a[1]");

    private final By statusBtn = new By.ByClassName("Header_Link__1TAG7");

    private final By orderNumberInput = new By.ByClassName("Input_Input__1iN_Z");

    private final By checkButton = new By.ByClassName("Header_Button__28dPO");

    private final By accordionItems = new By.ByCssSelector("[data-accordion-component='AccordionItemButton']");

    public MainPageModel(WebDriver driver) {
        super(driver);
    }

    public void clickDropDownList(int index) throws InterruptedException {
        Thread.sleep(1000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(dropDownList));
        Thread.sleep(2000);
        List<WebElement> accordionButtons = driver.findElements(accordionItems);
        accordionButtons.get(index).click();
    }

    public void clickTopOrderBtn() {
        driver.findElement(topOrderBtn).click();
    }

    public void clickBottomOrderBtn() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(bottomOrderBtn));
        Thread.sleep(2000);
        driver.findElement(bottomOrderBtn).click();
    }

    public void clickScooterLogo() {
        driver.findElement(scooterLogo).click();
    }

    public WebElement getYandexLogoElement() {
        return driver.findElement(this.yandexLogo);
    }

    public void checkOrderStatus(int orderNumber) throws InterruptedException {
        Thread.sleep(1000);
        driver.findElement(statusBtn).click();
        Thread.sleep(1000);
        driver.findElement(orderNumberInput).sendKeys(Integer.toString(orderNumber));
        Thread.sleep(1000);
        driver.findElement(checkButton).click();
    }
}
