package pageObject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WindowsFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LoginPage {
    private AppiumDriver driver;
    private Robot myRobot;
    public LoginPage() {
    }
    public LoginPage(AppiumDriver driver)throws Exception {
        myRobot = new Robot();
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @WindowsFindBy(accessibility = "EmailTextBox")
    private WebElement emailElement;

    @WindowsFindBy(accessibility = "PasswordBox")
    private WebElement passwordElement;

    @WindowsFindBy(accessibility = "LoginButton")
    private WebElement loginElement;

    public boolean isDisplayed() {
        return loginElement.isDisplayed();
    }

    public void typeName(String name) {
        emailElement.clear();
        myRobot.keyPress(KeyEvent.VK_SHIFT);
        myRobot.keyRelease(KeyEvent.VK_SHIFT);
        emailElement.sendKeys(name);
    }
    public void typePassword(String password) {
        passwordElement.clear();
        passwordElement.sendKeys(password);
    }
    public void clickLogin() {
        loginElement.click();
    }

    public void login (String name, String password) {
        typeName(name);
        typePassword(password);
        clickLogin();
    }
}
