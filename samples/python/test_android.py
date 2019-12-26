# coding=utf-8
import unittest
from appium import webdriver


class AndroidSimpleTest(unittest.TestCase):
    def setUp(self):
        calculator_desired_caps = {
            'platformName': 'Android',
            'platformVersion': '9.0',
            # emulator
            'deviceName': 'emulator-5554',
            'appPackage': 'com.android.calculator2',
            'appActivity': 'com.android.calculator2.Calculator'
            # real device
            # 'deviceName': '520381b347dd148b',
            # 'appPackage': 'com.sec.android.app.popupcalculator',
            # 'appActivity': 'com.sec.android.app.popupcalculator.Calculator'
        }
        self.driver = webdriver.Remote('http://localhost:4723/wd/hub', calculator_desired_caps)

    def tearDown(self):
        self.driver.quit()

    def test_calculator(self):
        # real device
        # self.driver.find_element_by_accessibility_id("5").click()
        # self.driver.find_element_by_accessibility_id("Plus").click()
        # self.driver.find_element_by_accessibility_id("6").click()
        # self.driver.find_element_by_accessibility_id("Equal").click()
        # self.assertEqual(self.driver.find_element_by_id("txtCalc").text, "11")

        # ************
        # emulator
        self.driver.find_element_by_id("digit_5").click()
        self.driver.find_element_by_accessibility_id("plus").click()
        self.driver.find_element_by_id("digit_6").click()
        self.driver.find_element_by_accessibility_id("equals").click()
        self.assertEqual(self.driver.find_element_by_id("result").text, "11")

    @unittest.skip("skip")
    def test_something(self):
        pass


if __name__ == '__main__':
    unittest.main()