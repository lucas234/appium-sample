# coding=utf-8
import unittest
from appium import webdriver
import time
import os


class IosSimpleTest(unittest.TestCase):
    def setUp(self):
        desired_caps = {
            "platformName": "iOS",
            "platformVersion": "11.2",
            "deviceName": "Red’s iPad (2)",
            "noReset": True,
            "udid": "34551df52360e9243cfd7acb75801e1d58d6746f",
            "orientation": "LANDSCAPE",
            "xcodeOrgId": "TEST INTERNATIONAL",
            "xcodeSigningId": "3847P22J7L",
            "app": "/Users/XXXXX/Desktop/test_ios/your.app"
        }
        self.driver = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)

    def tearDown(self):
        self.driver.quit()

    def test_something(self):
        input_field = self.driver.find_elements_by_ios_predicate('type == "XCUIElementTypeTextField"')[1]
        input_field.clear()
        time.sleep(2)
        input_field.send_keys('123456@test.com')
        password = self.driver.find_element_by_ios_predicate('type=="XCUIElementTypeSecureTextField"')
        password.clear()
        time.sleep(2)
        password.send_keys('123456@test.com')
        password.send_keys('\n')
        time.sleep(10)


if __name__ == '__main__':
    unittest.main()
