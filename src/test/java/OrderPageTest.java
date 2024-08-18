import dto.FirstFormDto;
import dto.SecondFormDto;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class OrderPageTest extends AbstractBaseTest {
    @DataProvider(name = "orderData")
    public Object[][] testData() {
        return new Object[][]{
                {new FirstFormDto("Борис",
                        "Бритва",
                        "г. Санкт-Петербург, ул. Московский проспект, д. 3",
                        "Сокольники",
                        "77773332211"
                ), new SecondFormDto("20.08.2024",
                        "двое суток",
                        "black",
                        "тест1"
                )},
                {new FirstFormDto("Большой",
                        "Лебовский",
                        "г. Санкт-Петербург, ул. Московский проспект, д. 5",
                        "Черкизовская",
                        "77773332210"
                ), new SecondFormDto("20.08.2024",
                        "сутки",
                        "grey",
                        "тест2"
                )}
        };
    }

    @Test(dataProvider = "orderData")
    public void topButtonOrderSuccessfully(FirstFormDto dto, SecondFormDto secondFormDto) {
        this.mainPageService.clickTopOrderBtn();

        this.orderPageService.fillFirstFormAndGoNext(dto);
        this.orderPageService.fillSecondFormAndFinish(secondFormDto);
        WebElement orderSuccess = this.driver.findElement(new By.ByClassName("Order_ModalHeader__3FDaJ"));
        String fullText = orderSuccess.getText();
        String orderedText = fullText.split("\n")[0];
        assertEquals("Заказ оформлен",orderedText);
    }

    @Test(dataProvider = "orderData")
    public void bottomButtonOrderSuccessfully(FirstFormDto dto, SecondFormDto secondFormDto) throws InterruptedException {
        this.mainPageService.clickBottomOrderBtn();

        this.orderPageService.fillFirstFormAndGoNext(dto);
        this.orderPageService.fillSecondFormAndFinish(secondFormDto);
        WebElement orderSuccess = this.driver.findElement(new By.ByClassName("Order_ModalHeader__3FDaJ"));
        String fullText = orderSuccess.getText();
        String orderedText = fullText.split("\n")[0];
        assertEquals("Заказ оформлен",orderedText);
    }

    @Test
    public void validationCheckErrors() throws InterruptedException {
        this.mainPageService.clickBottomOrderBtn();

        Thread.sleep(2000);
        FirstFormDto invalidDto = new FirstFormDto("tes", "tes", "tes", "tes", "test");
        this.orderPageService.fillFirstFormAndGoNext(invalidDto);

        SearchContext form = this.orderPageService.getForm(this.orderPageService.firstStep);
        List<WebElement> errors = form.findElements(new By.ByClassName("Input_ErrorMessage__3HvIb"));

        WebElement nameError = errors.get(0);
        assertEquals("Введите корректное имя", nameError.getText().trim());

        WebElement surnameError = errors.get(1);
        assertEquals("Введите корректную фамилию", surnameError.getText().trim());

        WebElement addressError = errors.get(2);
        assertEquals("Введите корректный адрес", addressError.getText().trim());

        WebElement metroError = form.findElement(new By.ByClassName("Order_MetroError__1BtZb"));
        assertEquals("Выберите станцию", metroError.getText().trim());

        WebElement phoneError = errors.get(3);
        assertEquals("Введите корректный номер", phoneError.getText().trim());
    }
}
