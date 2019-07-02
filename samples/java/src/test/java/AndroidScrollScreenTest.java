import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class AndroidScrollScreenTest {
    private static AndroidDriver<MobileElement> driver;
    private static Logger log= LogManager.getLogger("appium-log");
    // reference
    // http://www.automationtestinghub.com/appium-scroll-examples/
    // https://developer.android.com/reference/android/support/test/uiautomator/UiScrollable.html#scrollBackward()

    @BeforeTest
    public void beforeTest() throws Exception{
        log.info("######################### begin test #########################");
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appPath = new File(classpathRoot, "../../apps/api-demo.apk");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName","'Android'");
        cap.setCapability("platformVersion","7.0");
        cap.setCapability("deviceName","520381b347dd148b");
        cap.setCapability("noReset",true);
        cap.setCapability("app",appPath.getCanonicalPath());
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }

//    @Test
    public void testScrollScreen(){
        driver.findElementByAccessibilityId("Views").click();
//        driver.executeScript("mobile:scroll", ImmutableMap.of("direction", "down"));
        scrollAndClick("WebView");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        Assert.assertTrue(driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"Hello World\")").isDisplayed());
    }

//    @Test
    public void testScrollByTouchActions(){
        driver.findElementByAccessibilityId("Views").click();
        MobileElement el1 = driver.findElementByAccessibilityId("Animation");
        MobileElement el2 = driver.findElementByAccessibilityId("Grid");
//        driver.executeScript("mobile:scroll", ImmutableMap.of("direction", "down"));
        //想滚动多次，替换不同的元素即可
        scrollUsingTouchActions(el2, el1);
        driver.findElementByAccessibilityId("WebView").click();
        Assert.assertTrue(driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"Hello World\")").isDisplayed());
    }

//    @Test
    public void testScrollByUIScrollable(){
        driver.findElementByAccessibilityId("Views").click();
//        driver.executeScript("mobile:scroll", ImmutableMap.of("direction", "down"));
        scrollUsingUIScrollable();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        Assert.assertTrue(driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"Hello World\")").isDisplayed());
    }

    @Test
    public void testScrollHorizontal(){
        // 水平滑动
        driver.findElementByAccessibilityId("Views").click();
        MobileElement el1 = driver.findElementByAccessibilityId("Animation");
        MobileElement el2 = driver.findElementByAccessibilityId("Grid");
        scrollUsingTouchActions(el2, el1);
        driver.findElementByAccessibilityId("Tabs").click();
        driver.findElementByAccessibilityId("5. Scrollable").click();
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).setAsHorizontalList().getChildByText(new UiSelector().text(\"TAB 24\"),\"TAB 24\")").click();
        Assert.assertTrue(driver.findElementByAndroidUIAutomator("new UiSelector().text(\"Content for tab with tag Tab 24\")").isDisplayed());
    }

    public void scrollAndClick(String visibleText) {
        // 通过scrollIntoView方法
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+visibleText+"\").instance(0))").click();
    }

    public void scrollUsingTouchActions(MobileElement startElement, MobileElement endElement) {
        TouchAction actions = new TouchAction(driver);
        // waitAction(Duration.ofSeconds(2)只是使滚动慢一点，可以去掉
        //actions.press(startElement).waitAction(Duration.ofSeconds(0)).moveTo(endElement).release().perform()
        actions.press(startElement).moveTo(endElement).release().perform();
    }

    public void scrollUsingAdbCommand(){
        //adb -s 1b686a shell input swipe 300 300 250 250
    }

    public void scrollUsingCoordinate() {
        // TouchAction actions = new TouchAction(driver);
        // actions.press(startX, startY).moveTo(endX, endY).release().perform();
    }

    public void scrollUsingUIScrollable(){
        // 通过getChildByText方法
//        MobileElement element = driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"android:id/list\")).getChildByText(new UiSelector().text(\"WebView\"),\"WebView\")"));
//        element.click();
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"android:id/list\")).getChildByText(new UiSelector().text(\"WebView\"),\"WebView\")").click();
//        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).getChildByText(new UiSelector().text(\"WebView\"),\"WebView\")").click();

        // 通过getChildByDescription方法
//        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"android:id/list\")).getChildByDescription(new UiSelector().text(\"WebView\"),\"WebView\")").click();

        //水平滑动 setAsHorizontalList()
//        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).setAsHorizontalList().getChildByText(new UiSelector().text(\"WebView\"),\"WebView\")").click();
    }

}
