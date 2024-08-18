
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainPageTest extends AbstractBaseTest {

    @Test
    public void accordionPressSuccessfully() throws InterruptedException {

        // Первый элемент
        this.mainPageService.clickDropDownList(0);
        WebElement firstAccordionText = new WebDriverWait(this.driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-accordion-component='AccordionItemPanel'][1]//p")));
        assertEquals("Сутки — 400 рублей. Оплата курьеру — наличными или картой.", firstAccordionText.getText());

        // Второй элемент
        this.mainPageService.clickDropDownList(1);
        WebElement secondAccordionText = new WebDriverWait(this.driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='accordion__panel-1']//p")));
        assertEquals("Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.", secondAccordionText.getText().trim());

        // Третий элемент
        this.mainPageService.clickDropDownList(2);
        WebElement thirdAccordionText = new WebDriverWait(this.driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='accordion__panel-2']//p")));
        assertEquals("Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.", thirdAccordionText.getText().trim());

        // Четвертый элемент
        this.mainPageService.clickDropDownList(3);
        WebElement fourthAccordionText = new WebDriverWait(this.driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='accordion__panel-3']//p")));
        assertEquals("Только начиная с завтрашнего дня. Но скоро станем расторопнее.", fourthAccordionText.getText().trim());

        // Пятый элемент
        this.mainPageService.clickDropDownList(4);
        WebElement fifthAccordionText = new WebDriverWait(this.driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='accordion__panel-4']//p")));
        assertEquals("Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.", fifthAccordionText.getText().trim());

        // Шестой элемент
        this.mainPageService.clickDropDownList(5);
        WebElement sixthAccordionText = new WebDriverWait(this.driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='accordion__panel-5']//p")));
        assertEquals("Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.", sixthAccordionText.getText().trim());

        // Седьмой элемент
        this.mainPageService.clickDropDownList(6);
        WebElement seventhAccordionText = new WebDriverWait(this.driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='accordion__panel-6']//p")));
        assertEquals("Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.", seventhAccordionText.getText().trim());

        // Восьмой элемент
        this.mainPageService.clickDropDownList(7);
        WebElement eighthAccordionText = new WebDriverWait(this.driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='accordion__panel-7']//p")));
        assertEquals("Да, обязательно. Всем самокатов! И Москве, и Московской области.", eighthAccordionText.getText().trim());

    }

    @Test
    public void logoClickAndGoToMainPageSuccessfully() {
        this.mainPageService.clickScooterLogo();
        String currentUrl = this.driver.getCurrentUrl();
        String expectedUrl = "https://qa-scooter.praktikum-services.ru/";
        assertEquals(
                "URL текущей страницы должен быть 'https://qa-scooter.praktikum-services.ru/'" +
                        " после клика на логотип",
                expectedUrl,
                currentUrl
        );
    }

    @Test
    public void logoClickAndGoToMainPageNewTabSuccessfully() {
        WebElement link = this.mainPageService.getYandexLogoElement();
        link.sendKeys(Keys.chord(Keys.COMMAND, Keys.RETURN));

        for (String tab : driver.getWindowHandles()) {
            driver.switchTo().window(tab);
        }

        String currentUrl = driver.getCurrentUrl();
        // Ожидаемый URL
        String expectedUrl = "https://yandex.ru";
        // Проверяем, что текущий URL совпадает с ожидаемым
        assertEquals(
                "URL текущей страницы должен быть '" + expectedUrl + "'" +
                        " после клика на логотип",
                expectedUrl,
                currentUrl
        );
    }

    @Test
    public void invalidOrderNumberNotFoundHandledSuccessfully() throws InterruptedException {
        this.mainPageService.checkOrderStatus(1);

        // Используем явное ожидание для появления изображения с классом Track_NotFound__6oaoY
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement notFoundImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Track_NotFound__6oaoY img")));

        // Проверяем, что элемент отображен(тут вообще было сложно с картинкой этой)
        assertTrue("Изображение с alt='Not found' должно быть отображено", notFoundImage.isDisplayed());
        String actualSrc = notFoundImage.getAttribute("src");
        String expectedSrc = "/assets/not-found.png";
        String normalizedActualSrc = actualSrc.substring(actualSrc.indexOf("/assets"));
        assertEquals("Src изображения должно быть равно ожидаемому", expectedSrc, normalizedActualSrc);
    }
}

