import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;


public class WindowsAppTest {
    private static AppiumDriver driver;
    private static WebDriverWait wait;
    private static Robot myRobot;
    private static Logger log= LogManager.getLogger("appium-log");

    @BeforeClass
    public void beforeTest() throws Exception{
        log.info("######################### begin test #########################");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("app", "LexisNexisAPAC.LexisRed_wsek3cqrhvvz2!App");
        try {
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), cap);
        }catch (Exception e){
//            e.printStackTrace();
            log.info("first init driver failed!");
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), cap);
        }
        log.info("wait 9 seconds");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        myRobot = new Robot();

    }

    @AfterClass
    public void afterTest() {
        driver.close();
    }

    @Test
    public void  login() throws Exception{
        WebElement test = driver.findElementByAccessibilityId("EmailTextBox");
        test.clear();
        driver.findElementByAccessibilityId("EmailTextBox").clear();
        myRobot.keyPress(KeyEvent.VK_SHIFT);
        myRobot.keyRelease(KeyEvent.VK_SHIFT);
        driver.findElementByAccessibilityId("EmailTextBox").sendKeys("lucas.liu@lexisnexis.com");
        driver.findElementByAccessibilityId("PasswordBox").clear();
        driver.findElementByAccessibilityId("PasswordBox").sendKeys("1234567a");
        driver.findElementByAccessibilityId("LoginButton").click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElementByName("LSA999").click();
        driver.findElementByName("Open").click();
    }

//    @Test
    public void logout(){
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElementByAccessibilityId("SignOut").click();
        driver.findElementByName("Logout").click();
        driver.findElementByName("Confirm").click();
    }

}


