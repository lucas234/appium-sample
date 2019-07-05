import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class AndroidMoveSeekbarTest {
    private static AndroidDriver<MobileElement> driver;
    private static WebDriverWait wait;
    private static Logger log= LogManager.getLogger("appium-log");

    @BeforeTest
    public void beforeTest() throws Exception{
        log.info("######################### begin test #########################");
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appPath = new File(classpathRoot, "../../apps/VodQA.apk");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName","'Android'");
        cap.setCapability("platformVersion","7.0");
        cap.setCapability("deviceName","520381b347dd148b");
        cap.setCapability("noReset",true);
//        cap.setCapability("automationName","UiAutomator2");
        cap.setCapability("app",appPath.getCanonicalPath());
//        cap.setCapability("appPackage","io.selendroid.testapp");
//        cap.setCapability("appActivity","io.selendroid.testapp.HomeScreenActivity");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
//        wait = new WebDriverWait(driver, 30);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }

    @Test
    public void testSeekbar() throws Exception{
        Thread.sleep(3000);
//        By content = By.id("content");
//        wait.until(ExpectedConditions.elementToBeClickable(content));
//        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"LOG IN\")").click();
        driver.findElementByAndroidUIAutomator("new UiSelector().text(\"Slider\")").click();
        MobileElement slider =driver.findElementByAccessibilityId("slider");
        //精度不是很准确，需自己调，当percent为1时可能不会移动到100%，可以尝试1.5或2
        // 拖动100%
        moveSeekbar(slider, 1.5);
        // 拖动40%
//        moveSeekbar(slider, 0.6);
    }

    public void moveSeekbar(MobileElement element, double percent){
        // 获取拖动条的宽
        int width = element.getSize().getWidth();
        // 获取坐标
        int x = element.getLocation().getX();
        int y = element.getLocation().getY();
        TouchAction action = new TouchAction(driver);
        action.press(x,y).moveTo((int)(width*percent),y).release().perform();
    }

    public static boolean isElementVisible(MobileElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            log.info("Exist verification -- cannot find the element with by: " + element.toString());
            return false;
        }
    }
}


