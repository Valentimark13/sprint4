
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;

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

