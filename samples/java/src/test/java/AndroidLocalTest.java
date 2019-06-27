import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class AndroidLocalTest {
    private static AndroidDriver<?> driver;
    private static Logger log= LogManager.getLogger("appium-log");

    @BeforeTest
    public void beforeTest( ) {
        log.info("######################### begin test #########################");
    }

    @AfterTest
    public void afterTest( ) {
        driver.quit();
    }

    @Test
    public void localCalculator() throws MalformedURLException{
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName","'Android'");
        cap.setCapability("platformVersion","7.0");
        cap.setCapability("deviceName","520381b347dd148b");
        // 获取计算器的package、activity即可
        // （可通过adb shell dumpsys window windows | findstr "Current"获取，首先打开计算器，然后运行命令即可获取到）
        // 如果想要运行其他的内置APP，则将package、activity替换即可
        cap.setCapability("appPackage","com.sec.android.app.popupcalculator");
        cap.setCapability("appActivity","com.sec.android.app.popupcalculator.Calculator");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
        driver.findElementByAccessibilityId("5").click();
        driver.findElementByAccessibilityId("Plus").click();
        driver.findElementByAccessibilityId("6").click();
        driver.findElementByAccessibilityId("Equal").click();
        Assert.assertEquals(driver.findElementById("txtCalc").getText(),"11");
    }

    @Test
    public void launchChrome() throws MalformedURLException {
        //需要下载chrome，并已安装
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName","'Android'");
        cap.setCapability("platformVersion","7.0");
        cap.setCapability("deviceName","520381b347dd148b");
        // 想启动内置浏览器则将值改为"Browser"即可
        cap.setCapability("browserName","chrome");
        // 该步骤和下边注释的两条任意选择一个都可启动
        //chrome -- com.android.chrome/com.google.android.apps.chrome.Main
//            cap.setCapability("appPackage","com.android.chrome");
//            cap.setCapability("appActivity","com.google.android.apps.chrome.Main");
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
        driver.get("https://www.baidu.com/");
        log.info("############################"+driver.getCurrentUrl()+"##########################");
        log.info("############################"+driver.getTitle()+"##########################");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.baidu.com/", "URL Mismatch");
        Assert.assertEquals(driver.getTitle(), "百度一下", "Title Mismatch");
    }
}
