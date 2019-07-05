import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class SwipeScreenTest {
    private static AndroidDriver<MobileElement> driver;
    private static Logger log = LogManager.getLogger("appium-log");

    public enum DIRECTION {DOWN, UP, LEFT, RIGHT;}

    @BeforeTest
    public void beforeTest() throws Exception {
        log.info("######################### begin test #########################");
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appPath = new File(classpathRoot, "../../apps/api-demo.apk");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName", "'Android'");
        cap.setCapability("platformVersion", "7.0");
        cap.setCapability("deviceName", "520381b347dd148b");
        cap.setCapability("noReset", true);
//        cap.setCapability("automationName","UiAutomator2");
        cap.setCapability("app", appPath.getCanonicalPath());
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
    public void testSwipes() throws Exception {
        // 你可以看到界面的滑动，具体精度可自行调整
        driver.findElementByAccessibilityId("Graphics").click();
        String by_scroll_into_view = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Touch Paint\").instance(0))";
        driver.findElementByAndroidUIAutomator(by_scroll_into_view).click();
        Thread.sleep(2000);
        log.info("swipe to left");
        swipe(DIRECTION.LEFT, 1000);
        Thread.sleep(2000);
        log.info("swipe to right");
        swipe(DIRECTION.RIGHT, 1000);
        Thread.sleep(2000);
        log.info("swipe to up");
        swipe(DIRECTION.UP, 1000);
        Thread.sleep(2000);
        log.info("swipe to down");
        swipe(DIRECTION.DOWN, 1001);
    }

    private void swipe(DIRECTION direction, long duration) {
        Dimension size = driver.manage().window().getSize();
        int startX;
        int endX;
        int startY;
        int endY;

        switch (direction) {
            case RIGHT:
                startY = (size.height / 2);
                startX = (int) (size.width * 0.05);
                endX = (int) (size.width * 0.9);
                swipe(startX, startY, endX, startY, duration);
                break;
            case LEFT:
                startY = (size.height / 2);
                startX = (int) (size.width * 0.9);
                endX = (int) (size.width * 0.05);
                swipe(startX, startY, endX, startY, duration);
                break;
            case UP:
                endY = (int) (size.height * 0.30);
                startY = (int) (size.height * 0.70);
                startX = (size.width / 2);
                swipe(startX, startY, startX, endY, duration);
                break;
            case DOWN:
                startY = (int) (size.height * 0.30);
                endY = (int) (size.height * 0.70);
                startX = (size.width / 2);
                swipe(startX, startY, startX, endY, duration);
                break;
        }
    }

    private void swipe(int startX, int startY, int endX, int endY, long duration) {
        new TouchAction(driver)
                .press(startX, startY)
                .waitAction(Duration.ofMillis(duration))
                .moveTo(endX, endY)
                .release()
                .perform();
    }

}
