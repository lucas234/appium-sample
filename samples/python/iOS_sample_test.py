# coding=utf-8
import unittest
from appium import webdriver
import time


class AndroidSimpleTest(unittest.TestCase):
    def setUp(self):
        desired_caps = {
            "platformName": "iOS",
            "platformVersion": "12.1",
            "deviceName": "iPad Pro",
            "noReset": True,
            "udid": "1A7A0E9D-98AE-4230-BC92-13F66901FCBA",
            "orientation": "LANDSCAPE",
            "connectHardwareKeyboard": True,
            "app": "/Users/lexisred/Downloads/TestApp.app"
        }
        self.driver = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)

    def tearDown(self):
        self.driver.quit()

    def test_something(self):
        input_field_A = self.driver.find_element_by_accessibility_id("IntegerA")
        input_field_A.clear()
        input_field_A.send_keys('8')
        input_field_B = self.driver.find_element_by_accessibility_id("IntegerB")
        input_field_B.clear()
        input_field_B.send_keys('6')
        self.driver.find_element_by_accessibility_id("ComputeSumButton").click()
        answer = self.driver.find_element_by_accessibility_id("Answer")
        # self.assertEqual(answer.text, "14")
        self.assertEqual(answer.get_attribute("value"), "14")
        time.sleep(5)


if __name__ == '__main__':
    unittest.main()
