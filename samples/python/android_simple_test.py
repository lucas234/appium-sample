# coding=utf-8
import unittest
from appium import webdriver
import time
import os


class AndroidSimpleTest(unittest.TestCase):
    def setUp(self):
        app_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '../../apps/test-app.apk'))
        driver_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '../../drivers'))
        # print os.path.abspath("../../apps/test-app.apk")
        desired_caps = {
            'platformName': 'Android',
            'platformVersion': '7.0',
            'deviceName': '520381b347dd148b',
            "app": app_path,
            # 指定Chromedriver地址，
            # "chromedriverExecutableDir": driver_path,
            # "chromedriverExecutable": r"../drivers/chromedriver.exe",
            # 声明中文
            "unicodeKeyboard": 'True',
            # 声明中文，否则不支持中文
            "resetKeyboard": 'True',
            # 执行时不重新安装包
            'noReset': 'True',
            # 'automationName': 'UiAutomator2',
            'appPackage': 'io.selendroid.testapp',
            'appActivity': 'io.selendroid.testapp.HomeScreenActivity'
        }
        self.driver = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)

    def tearDown(self):
        self.driver.quit()

    def test_something(self):
        self.driver.find_element_by_accessibility_id("buttonStartWebviewCD").click()
        input_field = self.driver.find_element_by_id('name_input')
        time.sleep(1)
        input_field.clear()
        input_field.send_keys('Appium User')
        self.driver.find_element_by_android_uiautomator("new UiSelector().text(\"Send me your name!\")").click()
        self.driver.implicitly_wait(4)
        self.assertTrue(self.driver.find_element_by_android_uiautomator("new UiSelector().text(\"This is my way of saying hello\")").is_displayed())
        self.driver.find_element_by_id("goBack").click()


if __name__ == '__main__':
    unittest.main()
