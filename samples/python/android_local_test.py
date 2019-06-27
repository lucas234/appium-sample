# coding=utf-8
import unittest
from appium import webdriver


class AndroidLocalTest(unittest.TestCase):
    def setUp(self):
        self.driver = None

    def tearDown(self):
        self.driver.quit()

    def test_chrome(self):
        # 需要下载chrome，并已安装
        web_desired_caps = {
            'platformName': 'Android',
            'platformVersion': '7.0',
            'deviceName': '520381b347dd148b',
            'browserName': 'Chrome',  # 想启动内置浏览器则将值改为"Browser"即可
        }
        self.driver = webdriver.Remote('http://localhost:4723/wd/hub', web_desired_caps)
        self.driver.get("https://www.baidu.com/")
        self.assertEqual(self.driver.current_url, "https://www.baidu.com/")
        self.assertEqual(self.driver.title, u"百度一下")

    def test_calculator(self):
        calculator_desired_caps = {
            'platformName': 'Android',
            'platformVersion': '7.0',
            'deviceName': '520381b347dd148b',
            # 如果想要运行其他的内置APP，则将package、activity替换即可
            # 获取计算器的package、activity即可
            # 可通过 adb shell dumpsys window windows | findstr "Current" 获取，（首先打开计算器，然后运行命令即可获取到）
            'appPackage': 'com.sec.android.app.popupcalculator',
            'appActivity': 'com.sec.android.app.popupcalculator.Calculator'
        }
        self.driver = webdriver.Remote('http://localhost:4723/wd/hub', calculator_desired_caps)
        self.driver.find_element_by_accessibility_id("5").click()
        self.driver.find_element_by_accessibility_id("Plus").click()
        self.driver.find_element_by_accessibility_id("6").click()
        self.driver.find_element_by_accessibility_id("Equal").click()
        self.assertEqual(self.driver.find_element_by_id("txtCalc").text, "11")


if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(AndroidLocalTest)
    unittest.TextTestRunner(verbosity=2).run(suite)
    # unittest.main()
