
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainPageTest extends AbstractBaseTest {
    private static final String YANDEX_DOMAIN = "https://yandex.ru/";

    @Test
    public void logoClickAndGoToMainPageSuccessfully() {
        this.mainPageService.clickScooterLogo();
        String currentUrl = this.driver.getCurrentUrl();
        assertEquals(
                "URL текущей страницы должен быть '" + MAIN_URL + "'" +
                        " после клика на логотип",
                MAIN_URL,
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
        assertEquals(
                "URL текущей страницы должен быть '" + YANDEX_DOMAIN + "'" +
                        " после клика на логотип",
                YANDEX_DOMAIN,
                currentUrl
        );
    }

    @Test
    public void invalidOrderNumberNotFoundHandledSuccessfully() {
        this.mainPageService.checkOrderStatus(1);

        // Проверяем, что элемент отображен(тут вообще было сложно с картинкой этой)
        assertTrue("Изображение с alt='Not found' должно быть отображено", this.mainPageService.getNotFoundOrderElement().isDisplayed());
        String actualSrc = this.mainPageService.getNotFoundOrderElement().getAttribute("src");
        String expectedSrc = "/assets/not-found.png";
        String normalizedActualSrc = actualSrc.substring(actualSrc.indexOf("/assets"));
        assertEquals("Src изображения должно быть равно ожидаемому", expectedSrc, normalizedActualSrc);
    }
}

