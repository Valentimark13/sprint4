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
    public final By nextButton = new By.ByCssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");
    private final By finishButton = new By.ByCssSelector(".Button_Button__ra12g.Button_Middle__1CSJM:not([class*='Button_Inverted__3IF-i'])");
    private final By acceptOrderButton = new By.ByCssSelector("div.Order_Modal__YZ-d3>div.Order_Buttons__1xGrp>button.Button_Button__ra12g.Button_Middle__1CSJM:not([class*='Button_Inverted__3IF-i'])");
    private final By metroStationInput = new By.ByClassName("select-search__input");
    private final By stationElement = new By.ByClassName("select-search__row");
    private final By dayPickerElement = new By.ByClassName("react-datepicker__day");
    private final By dueDateInput = new By.ByCssSelector(".react-datepicker__input-container>input");
    private final By duration = new By.ByXPath("//div[@class='Dropdown-placeholder'][contains(text(), '* Срок аренды')]");
    private final By durationOptions =  new By.ByClassName("Dropdown-option");
    private final By orderModalHeader = new By.ByClassName("Order_ModalHeader__3FDaJ");

    private final By orderListError = By.className("Input_ErrorMessage__3HvIb");
    private final By metroElementError = By.className("Order_MetroError__1BtZb");

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

        waitDriver.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
        waitDriver.until(ExpectedConditions.elementToBeClickable(acceptOrderButton)).click();
    }

    public SearchContext getForm(By form) {
        return driver.findElement(form);
    }

    public String getOrderSuccessText()
    {
        return driver.findElement(this.orderModalHeader).getText().split("\n")[0];
    }

    public String getMetroTextError(SearchContext form) {
        return form.findElement(this.metroElementError).getText().trim();
    }

    public String getFormInputsErrorText(SearchContext form, Integer index) {
        return form.findElements(orderListError).get(index).getText().trim();
    }
}
