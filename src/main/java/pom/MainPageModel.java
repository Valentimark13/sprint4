package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
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

    private final By accordionPanel = new By.ByClassName("accordion__panel");

    public MainPageModel(WebDriver driver) {
        super(driver);
    }

    public void clickDropDownList(int index) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(dropDownList));
        List<WebElement> accordionButtons = driver.findElements(accordionItems);
        waitDriver.until(ExpectedConditions.elementToBeClickable(accordionButtons.get(index))).click();
    }

    public String getAccordionDataPanelText(int index) {
        List<WebElement> panels = driver.findElements(accordionPanel);
        return waitDriver.until(ExpectedConditions.visibilityOf(panels.get(index))).getText().trim();
    }

    public void clickTopOrderBtn() {
        driver.findElement(topOrderBtn).click();
    }

    public void clickBottomOrderBtn() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(bottomOrderBtn));
        waitDriver.until(ExpectedConditions.elementToBeClickable(bottomOrderBtn)).click();
    }

    public void clickScooterLogo() {
        driver.findElement(scooterLogo).click();
    }

    public WebElement getYandexLogoElement() {
        return driver.findElement(this.yandexLogo);
    }

    public void checkOrderStatus(int orderNumber) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10));

        // Ожидание видимости элемента выпадающего списка
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(dropDownList));

        // Клик по кнопке статуса заказа, ожидаем появления кнопки и кликаем по ней
        WebElement statusButton = wait.until(ExpectedConditions.elementToBeClickable(statusBtn));
        statusButton.click();

        // Ввод номера заказа, ожидаем видимости поля ввода и отправляем в него номер заказа
        WebElement orderNumberInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(orderNumberInput));
        orderNumberInputField.clear();
        orderNumberInputField.sendKeys(Integer.toString(orderNumber));

        // Клик по кнопке проверки, ожидаем кликабельность кнопки и кликаем по ней
        WebElement checkButtonElement = wait.until(ExpectedConditions.elementToBeClickable(checkButton));
        checkButtonElement.click();
    }
}
