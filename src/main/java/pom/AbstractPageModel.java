package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class AbstractPageModel {
    protected WebDriver driver;
    protected WebDriverWait waitDriver;

    public AbstractPageModel(WebDriver driver) {
        this.driver = driver;
        this.waitDriver = new WebDriverWait(this.driver, Duration.ofSeconds(2000));
    }
}
