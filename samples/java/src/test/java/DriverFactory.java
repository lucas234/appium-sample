//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//import java.util.logging.Level;
//
//import org.openqa.selenium.Proxy.ProxyType;
//import org.openqa.selenium.UnexpectedAlertBehaviour;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeDriverService;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.firefox.FirefoxBinary;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.logging.LogType;
//import org.openqa.selenium.logging.LoggingPreferences;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriverService;
//import org.openqa.selenium.remote.CapabilityType;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.github.noraui.exception.TechnicalException;
//import com.github.noraui.utils.Context;
//import com.github.noraui.utils.Messages;
//import com.github.noraui.utils.Utilities;
//import com.github.noraui.utils.Utilities.OperatingSystem;
//import com.github.noraui.utils.Utilities.SystemArchitecture;
//
//public class DriverFactory {
//
//    /**
//     * Specific logger
//     */
//    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
//
//    /** Default web drivers implicit wait **/
//    public static final long IMPLICIT_WAIT = 500;
//
//    public static final String PHANTOM = "phantom";
//    public static final String IE = "ie";
//    public static final String CHROME = "chrome";
//    public static final String HTMLUNIT = "htmlunit";
//    public static final String FIREFOX = "firefox";
//    public static final String DEFAULT_DRIVER = PHANTOM;
//
//    /** Selenium drivers. **/
//    private final Map<String, WebDriver> drivers;
//
//    public DriverFactory() {
//        drivers = new HashMap<>();
//    }
//
//    /**
//     * Get selenium driver. Drivers are lazy loaded.
//     *
//     * @return selenium driver
//     */
//    public WebDriver getDriver() {
//        // Driver's name is retrieved by system properties
//        String driverName = Context.getBrowser();
//        driverName = driverName != null ? driverName : DEFAULT_DRIVER;
//        WebDriver driver = null;
//        if (!drivers.containsKey(driverName)) {
//            try {
//                driver = generateWebDriver(driverName);
//            } catch (final TechnicalException e) {
//                logger.error("error DriverFactory.getDriver()", e);
//            }
//        } else {
//            driver = drivers.get(driverName);
//        }
//        return driver;
//    }
//
//    /**
//     * Clear loaded drivers
//     */
//    public void clear() {
//        for (final WebDriver wd : drivers.values()) {
//            wd.quit();
//        }
//        drivers.clear();
//    }
//
//    /**
//     * Generates a phantomJs webdriver.
//     *
//     * @return
//     *         A phantomJs webdriver
//     * @throws TechnicalException
//     *             if an error occured when Webdriver setExecutable to true.
//     */
//    private WebDriver generatePhantomJsDriver() throws TechnicalException {
//        final String pathWebdriver = DriverFactory.getPath(Driver.PHANTOMJS);
//        if (!new File(pathWebdriver).setExecutable(true)) {
//            throw new TechnicalException(Messages.getMessage(TechnicalException.TECHNICAL_ERROR_MESSAGE_WEBDRIVER_SET_EXECUTABLE));
//        }
//        logger.info("Generating Phantomjs driver ({}) ...", pathWebdriver);
//
//        final DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "Accept-Language", "fr-FR");
//        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, pathWebdriver);
//        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
//        capabilities.setJavascriptEnabled(true);
//
//        setLoggingLevel(capabilities);
//
//        // Proxy configuration
//        String proxy = "";
//        if (Context.getProxy().getProxyType() != ProxyType.UNSPECIFIED && Context.getProxy().getProxyType() != ProxyType.AUTODETECT) {
//            proxy = Context.getProxy().getHttpProxy();
//        }
//        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
//                new String[] { "--proxy=" + proxy, "--web-security=no", "--ignore-ssl-errors=true", "--ssl-protocol=tlsv1", "--webdriver-loglevel=NONE" });
//        return new PhantomJSDriver(capabilities);
//    }
//
//    /**
//     * Generates an ie webdriver. Unable to use it with a proxy. Causes a crash.
//     *
//     * @return
//     *         An ie webdriver
//     * @throws TechnicalException
//     *             if an error occured when Webdriver setExecutable to true.
//     */
//    private WebDriver generateIEDriver() throws TechnicalException {
//        final String pathWebdriver = DriverFactory.getPath(Driver.IE);
//        if (!new File(pathWebdriver).setExecutable(true)) {
//            throw new TechnicalException(Messages.getMessage(TechnicalException.TECHNICAL_ERROR_MESSAGE_WEBDRIVER_SET_EXECUTABLE));
//        }
//        logger.info("Generating IE driver ({}) ...", pathWebdriver);
//
//        System.setProperty(Driver.IE.getDriverName(), pathWebdriver);
//
//        final DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
//        capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
//        capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
//        capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
//        capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
//        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
//        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//        capabilities.setCapability("disable-popup-blocking", true);
//        capabilities.setJavascriptEnabled(true);
//
//        setLoggingLevel(capabilities);
//
//        // Proxy configuration
//        if (Context.getProxy().getProxyType() != ProxyType.UNSPECIFIED && Context.getProxy().getProxyType() != ProxyType.AUTODETECT) {
//            capabilities.setCapability(CapabilityType.PROXY, Context.getProxy());
//        }
//
//        return new InternetExplorerDriver(capabilities);
//    }
//
//    /**
//     * Generates a chrome webdriver.
//     *
//     * @param headlessMode
//     *            Enable headless mode ?
//     * @return
//     *         A chrome webdriver
//     * @throws TechnicalException
//     *             if an error occured when Webdriver setExecutable to true.
//     */
//    private WebDriver generateGoogleChromeDriver(boolean headlessMode) throws TechnicalException {
//        final String pathWebdriver = DriverFactory.getPath(Driver.CHROME);
//        if (!new File(pathWebdriver).setExecutable(true)) {
//            throw new TechnicalException(Messages.getMessage(TechnicalException.TECHNICAL_ERROR_MESSAGE_WEBDRIVER_SET_EXECUTABLE));
//        }
//        logger.info("Generating Chrome driver ({}) ...", pathWebdriver);
//
//        System.setProperty(Driver.CHROME.getDriverName(), pathWebdriver);
//
//        final ChromeOptions chromeOptions = new ChromeOptions();
//        final DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//        capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
//        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
//
//        setLoggingLevel(capabilities);
//
//        if (Context.isHeadless()) {
//            chromeOptions.addArguments("--headless");
//        }
//
//        // Proxy configuration
//        if (Context.getProxy().getProxyType() != ProxyType.UNSPECIFIED && Context.getProxy().getProxyType() != ProxyType.AUTODETECT) {
//            capabilities.setCapability(CapabilityType.PROXY, Context.getProxy());
//        }
//
//        setChromeOptions(capabilities, chromeOptions);
//
//        String withWhitelistedIps = Context.getWebdriversProperties("withWhitelistedIps");
//        if (withWhitelistedIps != null && !"".equals(withWhitelistedIps)) {
//            ChromeDriverService service = new ChromeDriverService.Builder().withWhitelistedIps(withWhitelistedIps).withVerbose(false).build();
//            return new ChromeDriver(service, capabilities);
//        } else {
//            return new ChromeDriver(capabilities);
//        }
//    }
//
//    /**
//     * Sets the target browser binary path in chromeOptions if it exists in configuration.
//     *
//     * @param capabilities
//     *            The global DesiredCapabilities
//     */
//    private void setChromeOptions(final DesiredCapabilities capabilities, ChromeOptions chromeOptions) {
//
//        // Set custom downloaded file path. When you check content of downloaded file by robot.
//        HashMap<String, Object> chromePrefs = new HashMap<>();
//        chromePrefs.put("download.default_directory", System.getProperty("user.dir") + File.separator + "downloadFiles");
//        chromeOptions.setExperimentalOption("prefs", chromePrefs);
//
//        // Set custom chromium (if you not use default chromium on your target device)
//        String targetBrowserBinaryPath = Context.getWebdriversProperties("targetBrowserBinaryPath");
//        if (targetBrowserBinaryPath != null && !"".equals(targetBrowserBinaryPath)) {
//            chromeOptions.setBinary(targetBrowserBinaryPath);
//        }
//
//        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
//    }
//
//    /**
//     * Generates a firefox webdriver.
//     *
//     * @return
//     *         A firefox webdriver
//     * @throws TechnicalException
//     *             if an error occured when Webdriver setExecutable to true.
//     */
//    private WebDriver generateFirefoxDriver() throws TechnicalException {
//        final String pathWebdriver = DriverFactory.getPath(Driver.FIREFOX);
//        if (!new File(pathWebdriver).setExecutable(true)) {
//            throw new TechnicalException(Messages.getMessage(TechnicalException.TECHNICAL_ERROR_MESSAGE_WEBDRIVER_SET_EXECUTABLE));
//        }
//        logger.info("Generating Firefox driver ({}) ...", pathWebdriver);
//
//        System.setProperty(Driver.FIREFOX.getDriverName(), pathWebdriver);
//
//        final FirefoxOptions firefoxOptions = new FirefoxOptions();
//        final FirefoxBinary firefoxBinary = new FirefoxBinary();
//
//        final DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//        capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
//        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
//
//        setLoggingLevel(capabilities);
//
//        // Proxy configuration
//        if (Context.getProxy().getProxyType() != ProxyType.UNSPECIFIED && Context.getProxy().getProxyType() != ProxyType.AUTODETECT) {
//            capabilities.setCapability(CapabilityType.PROXY, Context.getProxy());
//        }
//
//        if (Context.isHeadless()) {
//            firefoxBinary.addCommandLineOptions("--headless");
//            firefoxOptions.setBinary(firefoxBinary);
//        }
//        firefoxOptions.setLogLevel(Level.OFF);
//
//        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
//
//        return new FirefoxDriver(capabilities);
//    }
//
//    /**
//     * Generates a htmlunit webdriver.
//     *
//     * @return
//     *         A htmlunit webdriver
//     */
//    private WebDriver generateHtmlUnitDriver() {
//        logger.info("Generating HtmlUnit driver...");
//        final DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
//        capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
//        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
//        capabilities.setJavascriptEnabled(true);
//
//        setLoggingLevel(capabilities);
//
//        // Proxy configuration
//        if (Context.getProxy().getProxyType() != ProxyType.UNSPECIFIED && Context.getProxy().getProxyType() != ProxyType.AUTODETECT) {
//            capabilities.setCapability(CapabilityType.PROXY, Context.getProxy());
//        }
//        return new HtmlUnitDriver(capabilities);
//    }
//
//    /**
//     * Generates a selenium webdriver following a name given in parameter.
//     * By default a phantomJS driver is generated.
//     *
//     * @param driverName
//     *            The name of the web driver to generate
//     * @return
//     *         An instance a web driver whose type is provided by driver name given in parameter
//     * @throws TechnicalException
//     *             if an error occured when Webdriver setExecutable to true.
//     */
//    private WebDriver generateWebDriver(String driverName) throws TechnicalException {
//        WebDriver driver;
//        if (IE.equals(driverName)) {
//            driver = generateIEDriver();
//        } else if (CHROME.equals(driverName)) {
//            driver = generateGoogleChromeDriver(false);
//        } else if (HTMLUNIT.equals(driverName)) {
//            driver = generateHtmlUnitDriver();
//        } else if (FIREFOX.equals(driverName)) {
//            driver = generateFirefoxDriver();
//        } else {
//            driver = generatePhantomJsDriver();
//        }
//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.MILLISECONDS);
//        drivers.put(driverName, driver);
//        return driver;
//
//    }
//
//    /**
//     * Sets the logging level of the generated web driver.
//     *
//     * @param caps
//     *            The web driver's capabilities
//     * @param level
//     *            The logging level
//     */
//    private void setLoggingLevel(DesiredCapabilities caps) {
//        final LoggingPreferences logPrefs = new LoggingPreferences();
//        logPrefs.enable(LogType.BROWSER, Level.ALL);
//        logPrefs.enable(LogType.CLIENT, Level.OFF);
//        logPrefs.enable(LogType.DRIVER, Level.OFF);
//        logPrefs.enable(LogType.PERFORMANCE, Level.OFF);
//        logPrefs.enable(LogType.PROFILER, Level.OFF);
//        logPrefs.enable(LogType.SERVER, Level.OFF);
//        caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
//    }
//
//    /**
//     * Get the path of the driver given in parameters.
//     *
//     * @param currentDriver
//     *            The driver to get the path of
//     * @return
//     *         A String representation of the path
//     */
//    public static String getPath(Driver currentDriver) {
//        final OperatingSystem currentOperatingSystem = OperatingSystem.getCurrentOperatingSystem();
//        String format = "";
//        if ("webdriver.ie.driver".equals(currentDriver.driverName)) {
//            format = Utilities.setProperty(Context.getWebdriversProperties(currentDriver.driverName), "src/test/resources/drivers/%s/internetexplorer/%s/IEDriverServer%s");
//        } else if ("phantomjs.binary.path".equals(currentDriver.driverName)) {
//            format = Utilities.setProperty(Context.getWebdriversProperties(currentDriver.driverName), "src/test/resources/drivers/%s/phantomjs/%s/phantomjs%s");
//        } else if ("webdriver.chrome.driver".equals(currentDriver.driverName)) {
//            format = Utilities.setProperty(Context.getWebdriversProperties(currentDriver.driverName), "src/test/resources/drivers/%s/googlechrome/%s/chromedriver%s");
//        } else if ("webdriver.gecko.driver".equals(currentDriver.driverName)) {
//            format = Utilities.setProperty(Context.getWebdriversProperties(currentDriver.driverName), "src/test/resources/drivers/%s/firefox/%s/geckodriver%s");
//        }
//        return String.format(format, currentOperatingSystem.getOperatingSystemDir(), SystemArchitecture.getCurrentSystemArchitecture().getSystemArchitectureName(),
//                currentOperatingSystem.getSuffixBinary());
//    }
//
//    /**
//     * List of external non-java web drivers.
//     *
//     * @author Nicolas HALLOUIN
//     */
//    public enum Driver {
//        IE("webdriver.ie.driver"), PHANTOMJS("phantomjs.binary.path"), CHROME("webdriver.chrome.driver"), FIREFOX("webdriver.gecko.driver");
//        private String driverName;
//
//        Driver(String driverName) {
//            this.driverName = driverName;
//        }
//
//        String getDriverName() {
//            return driverName;
//        }
//    }
//}