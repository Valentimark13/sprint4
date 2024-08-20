import dto.PersonalDetailsForm;
import dto.DeliveryDetailsForm;
import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.SearchContext;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class OrderPageTest extends AbstractBaseTest {
    private final PersonalDetailsForm personalDetailsForm;
    private final DeliveryDetailsForm deliveryDetailsForm;

    public OrderPageTest(PersonalDetailsForm personalDetailsForm, DeliveryDetailsForm deliveryDetailsForm) {
        this.personalDetailsForm = personalDetailsForm;
        this.deliveryDetailsForm = deliveryDetailsForm;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {new PersonalDetailsForm("Борис", "Бритва", "г. Санкт-Петербург, ул. Московский проспект, д. 3", "Сокольники", "77773332211"),
                        new DeliveryDetailsForm("20.08.2024", "двое суток", "black", "тест1")},
                {new PersonalDetailsForm("Большой", "Лебовский", "г. Санкт-Петербург, ул. Московский проспект, д. 5", "Черкизовская", "77773332210"),
                        new DeliveryDetailsForm("20.08.2024", "сутки", "grey", "тест2")}
        });
    }

    @Test
    public void topButtonOrderSuccessfully() {
        mainPageService.clickTopOrderBtn();
        orderPageService.fillFirstFormAndGoNext(personalDetailsForm);
        orderPageService.fillSecondFormAndFinish(deliveryDetailsForm);
        WebElement orderSuccess = driver.findElement(By.className("Order_ModalHeader__3FDaJ"));
        String fullText = orderSuccess.getText();
        String orderedText = fullText.split("\n")[0];
        assertEquals("Заказ оформлен", orderedText);
    }

    @Test
    public void bottomButtonOrderSuccessfully() throws InterruptedException {
        mainPageService.clickBottomOrderBtn();
        orderPageService.fillFirstFormAndGoNext(personalDetailsForm);
        orderPageService.fillSecondFormAndFinish(deliveryDetailsForm);
        WebElement orderSuccess = driver.findElement(By.className("Order_ModalHeader__3FDaJ"));
        String fullText = orderSuccess.getText();
        String orderedText = fullText.split("\n")[0];
        assertEquals("Заказ оформлен", orderedText);
    }

    @Test
    public void validationCheckErrors() throws InterruptedException {
        mainPageService.clickBottomOrderBtn();
        PersonalDetailsForm invalidDto = new PersonalDetailsForm("tes", "tes", "tes", "tes", "test");
        orderPageService.fillFirstFormAndGoNext(invalidDto);
        SearchContext form = orderPageService.getForm(orderPageService.firstStep);
        List<WebElement> errors = form.findElements(By.className("Input_ErrorMessage__3HvIb"));
        assertEquals("Введите корректное имя", errors.get(0).getText().trim());
        assertEquals("Введите корректную фамилию", errors.get(1).getText().trim());
        assertEquals("Введите корректный адрес", errors.get(2).getText().trim());
        WebElement metroError = form.findElement(By.className("Order_MetroError__1BtZb"));
        assertEquals("Выберите станцию", metroError.getText().trim());
        assertEquals("Введите корректный номер", errors.get(3).getText().trim());
    }
}
