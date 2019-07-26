import java.io.IOException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;

public class AppiumServerJava {
    // 代码启动 appium
    public void startServer() {
        CommandLine cmd = new CommandLine("C:\\Program Files\\nodejs\\node.exe");
        cmd.addArgument("C:\\Users\\liul5\\AppData\\Local\\Programs\\Appium\\resources\\app\\node_modules\\appium\\build\\lib\\main.js");
        cmd.addArgument("-a");
        cmd.addArgument("127.0.0.1");
        cmd.addArgument("-p");
        cmd.addArgument("4723");

        DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(1);
        try {
            executor.execute(cmd, handler);
            Thread.sleep(1000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("taskkill /F /IM node.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        AppiumServerJava appiumServer = new AppiumServerJava();
        appiumServer.startServer();
//        appiumServer.stopServer();
    }
}