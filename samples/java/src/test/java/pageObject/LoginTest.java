package pageObject;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class LoginTest {
    public static void main(String[] args) throws Exception{
        DesiredCapabilities cap = new DesiredCapabilities();
        WindowsDriver driver;
        cap.setCapability("app", "LexisNexisAPAC.LexisRed_wsek3cqrhvvz2!App");
        try {
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), cap);
        }catch (Exception e){
//            e.printStackTrace();
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), cap);
        }

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("test@163.com","123456");
    }
}
