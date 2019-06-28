import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import java.net.URL;


public class AndroidSimpleTest {
    private static AndroidDriver<?> driver;
    private static Logger log= LogManager.getLogger("appium-log");

    @BeforeTest
    public void beforeTest() throws Exception{
        log.info("######################### begin test #########################");
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appPath = new File(classpathRoot, "../../apps/test-app.apk");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName","'Android'");
        cap.setCapability("platformVersion","7.0");
        cap.setCapability("deviceName","520381b347dd148b");
        cap.setCapability("noReset",true);
        cap.setCapability("app",appPath.getCanonicalPath());
//        cap.setCapability("appPackage","io.selendroid.testapp");
//        cap.setCapability("appActivity","io.selendroid.testapp.HomeScreenActivity");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }

    @Test
    public void testSomething() throws Exception{
        driver.findElementByAccessibilityId("buttonStartWebviewCD").click();
        WebElement input_field = driver.findElementById("name_input");
        input_field.clear();
        input_field.sendKeys("test User");
//        driver.wait(3000);
        log.info(driver.getContextHandles());
        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"Send me your name!\")").click();
        Assert.assertTrue(driver.findElementByAndroidUIAutomator("new UiSelector().text(\"This is my way of saying hello\")").isDisplayed());
        driver.findElementById("goBack").click();
    }
}
