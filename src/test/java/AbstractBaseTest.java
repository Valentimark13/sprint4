import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import pom.MainPageModel;
import pom.OrderPageModel;

public abstract class AbstractBaseTest {
    protected WebDriver driver;
    protected MainPageModel mainPageService;
    protected OrderPageModel orderPageService;

    @BeforeMethod
    @org.testng.annotations.Parameters("browser")
    public void setUp(@Optional("firefox") String browser) {
        if (browser.equals("chrome")) {
            this.driver = new ChromeDriver();
        } else {
            this.driver = new FirefoxDriver();
        }

        this.driver.get("https://qa-scooter.praktikum-services.ru/");

        this.mainPageService = new MainPageModel(this.driver);
        this.orderPageService = new OrderPageModel(this.driver);
    }

    @AfterMethod
    public void teardown() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}
