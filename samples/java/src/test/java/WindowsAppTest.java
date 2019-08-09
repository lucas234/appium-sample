import io.appium.java_client.windows.WindowsDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class WindowsAppTest {
    private static WindowsDriver driver;
    private static WebDriverWait wait;
    private static Logger log= LogManager.getLogger("appium-log");

    @BeforeTest
    public void beforeTest() throws Exception{
        log.info("######################### begin test #########################");
        // Launch Notepad
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("app", "LexisNexisAPAC.LexisRed_wsek3cqrhvvz2!App");
        driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), cap);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }

    @Test
    public void  justTest(){
            driver.findElementByAccessibilityId("EmailTextBox").sendKeys("alan.liu@lexisnexis.com");
            driver.findElementByAccessibilityId("PasswordBox").sendKeys("1234");
            driver.findElementByAccessibilityId("LoginButton").click();
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

}


