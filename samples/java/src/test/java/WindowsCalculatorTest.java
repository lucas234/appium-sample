import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.util.concurrent.TimeUnit;
import java.net.URL;
import io.appium.java_client.windows.WindowsDriver;

import org.testng.Assert;
import org.testng.annotations.*;

public class WindowsCalculatorTest {

    private static WindowsDriver CalculatorSession = null;
    private static WebElement CalculatorResult = null;

    @BeforeClass
    public static void setup() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
//            capabilities.setCapability("platformName", "Windows");
//            capabilities.setCapability("deviceName", "WindowsPC");
            CalculatorSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            CalculatorSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            CalculatorResult = CalculatorSession.findElementByAccessibilityId("CalculatorResults");

        }catch(Exception e){
            e.printStackTrace();
        } finally {}
    }

    @AfterClass
    public static void TearDown()
    {
        CalculatorResult = null;
        if (CalculatorSession != null) {
            CalculatorSession.quit();
        }
        CalculatorSession = null;
    }

    @Test
    public void Addition()
    {
        CalculatorSession.findElementByName("七").click();
        CalculatorSession.findElementByName("七").click();
        CalculatorSession.findElementByName("加").click();
        CalculatorSession.findElementByName("八").click();
        CalculatorSession.findElementByName("等于").click();
        Assert.assertEquals("显示为 85", CalculatorResult.getText());
    }

    @Test
    public void Combination()
    {
        CalculatorSession.findElementByName("七").click();
        CalculatorSession.findElementByName("乘以").click();
        CalculatorSession.findElementByName("九").click();
        CalculatorSession.findElementByName("加").click();
        CalculatorSession.findElementByName("一").click();
        CalculatorSession.findElementByName("等于").click();
        CalculatorSession.findElementByName("除以").click();
        CalculatorSession.findElementByName("八").click();
        CalculatorSession.findElementByName("等于").click();
        Assert.assertEquals("显示为 8", CalculatorResult.getText());
    }

    @Test
    public void Division()
    {
        CalculatorSession.findElementByName("六").click();
        CalculatorSession.findElementByName("四").click();
        CalculatorSession.findElementByName("除以").click();
        CalculatorSession.findElementByName("八").click();
        CalculatorSession.findElementByName("等于").click();
        Assert.assertEquals("显示为 8", CalculatorResult.getText());
    }

    @Test
    public void Multiplication()
    {
        CalculatorSession.findElementByName("六").click();
        CalculatorSession.findElementByName("乘以").click();
        CalculatorSession.findElementByName("八").click();
        CalculatorSession.findElementByName("等于").click();
        Assert.assertEquals("显示为 48", CalculatorResult.getText());
    }

    @Test
    public void Subtraction()
    {
        CalculatorSession.findElementByName("九").click();
        CalculatorSession.findElementByName("减").click();
        CalculatorSession.findElementByName("一").click();
        CalculatorSession.findElementByName("等于").click();
        Assert.assertEquals("显示为 8", CalculatorResult.getText());
    }
}