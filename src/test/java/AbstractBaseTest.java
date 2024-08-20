import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pom.MainPageModel;
import pom.OrderPageModel;

public abstract class AbstractBaseTest {
    protected static final String MAIN_URL = "https://qa-scooter.praktikum-services.ru/";
    protected WebDriver driver;
    protected MainPageModel mainPageService;
    protected OrderPageModel orderPageService;

    @Before
    public void setUp() {
        if (Math.random() < 0.5) {
            this.driver = new ChromeDriver();
        } else {
            this.driver = new FirefoxDriver();
        }

        this.driver.get(MAIN_URL);

        this.mainPageService = new MainPageModel(this.driver);
        this.orderPageService = new OrderPageModel(this.driver);
    }

    @After
    public void tearDown() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}
