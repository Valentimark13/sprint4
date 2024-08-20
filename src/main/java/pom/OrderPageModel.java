package pom;

import dto.PersonalDetailsForm;
import dto.DeliveryDetailsForm;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;

public class OrderPageModel extends AbstractPageModel {
    public final By firstStep = new By.ByClassName("Order_Form__17u6u");
    public final By secondStep = new By.ByClassName("Order_Form__17u6u");
    public final By formItem = new By.ByTagName("input");
    public final By nextButton = new By.ByXPath("//*[@id=\"root\"]/div/div[2]/div[3]/button");
    private final By finishButton = new By.ByXPath("//*[@id=\"root\"]/div/div[2]/div[3]/button[2]");

    private final By acceptOrderButton = new By.ByXPath("//*[@id=\"root\"]/div/div[2]/div[5]/div[2]/button[2]");

    private final By metroStationInput = new By.ByXPath("//input[@placeholder='* Станция метро']");

    private final By stationElement = new By.ByClassName("select-search__row");

    private final By dayPickerElement = new By.ByClassName("react-datepicker__day");

    private final By dueDateInput = new By.ByXPath("//input[@placeholder='* Когда привезти самокат']");

    private final By duration = new By.ByXPath("//*[@id=\"root\"]/div/div[2]/div[2]/div[2]");

    private final By durationOptions =  new By.ByClassName("Dropdown-option");
    public OrderPageModel(WebDriver driver) {
        super(driver);
    }

    public void fillFirstFormAndGoNext(PersonalDetailsForm dto) {
        List<WebElement> inputs = this.getForm(firstStep).findElements(formItem);

        inputs.get(0).sendKeys(dto.name);
        inputs.get(1).sendKeys(dto.surName);
        inputs.get(2).sendKeys(dto.address);

        waitDriver.until(ExpectedConditions.elementToBeClickable(metroStationInput)).click();

        List<WebElement> stations = driver.findElements(stationElement);

        for (WebElement station : stations) {
            if (station.getText().contains(dto.station)) {
                waitDriver.until(ExpectedConditions.elementToBeClickable(station)).click();
                break;
            }
        }

        inputs.get(4).sendKeys(dto.phone);

        driver.findElement(nextButton).click();
    }

    public void fillSecondFormAndFinish(DeliveryDetailsForm dto) {
        List<WebElement> inputs = getForm(secondStep).findElements(formItem);

        waitDriver.until(ExpectedConditions.elementToBeClickable(dueDateInput)).sendKeys(dto.dueDate);

        WebElement day = driver.findElements(dayPickerElement).get((new Random().nextInt(36)));

        waitDriver.until(ExpectedConditions.elementToBeClickable(day)).click();

        waitDriver.until(ExpectedConditions.elementToBeClickable(duration)).click();

        List<WebElement> stations = driver.findElements(this.durationOptions);
        for (WebElement element : stations) {
            if (element.getText().contains(dto.duration)) {
                waitDriver.until(ExpectedConditions.elementToBeClickable(element)).click();
                break;
            }
        }

        waitDriver.until(ExpectedConditions.elementToBeClickable(new By.ById(dto.color))).click();

        inputs.get(3).sendKeys(dto.comment);

        driver.findElement(finishButton).click();
        waitDriver.until(ExpectedConditions.elementToBeClickable(acceptOrderButton)).click();
    }

    public SearchContext getForm(By form) {
        return driver.findElement(form);
    }
}
