# coding=utf-8
# auther：Liul5
# date：2019/8/8 11:05
# tools：PyCharm
# Python：3.7.3

import unittest
from appium import webdriver


class WindowsCalculatorTest(unittest.TestCase):

    @classmethod
    def setUpClass(self):
        # set up appium
        desired_caps = {}
        desired_caps["app"] = "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App"
        self.driver = webdriver.Remote(command_executor='http://127.0.0.1:4723', desired_capabilities=desired_caps)

    @classmethod
    def tearDownClass(self):
        self.driver.quit()

    def getresults(self):
        displaytext = self.driver.find_element_by_accessibility_id("CalculatorResults").text
        displaytext = displaytext.strip("显示为 ")
        displaytext = displaytext.rstrip(' ')
        displaytext = displaytext.lstrip(' ')
        return displaytext

    def test_addition(self):
        self.driver.find_element_by_name("一").click()
        self.driver.find_element_by_name("加").click()
        self.driver.find_element_by_name("七").click()
        self.driver.find_element_by_name("等于").click()
        self.assertEqual(self.getresults(), "8")

    def test_combination(self):
        self.driver.find_element_by_name("七").click()
        self.driver.find_element_by_name("乘以").click()
        self.driver.find_element_by_name("九").click()
        self.driver.find_element_by_name("加").click()
        self.driver.find_element_by_name("一").click()
        self.driver.find_element_by_name("等于").click()
        self.driver.find_element_by_name("除以").click()
        self.driver.find_element_by_name("八").click()
        self.driver.find_element_by_name("等于").click()
        self.assertEqual(self.getresults(), "8")

    def test_division(self):
        self.driver.find_element_by_name("八").click()
        self.driver.find_element_by_name("八").click()
        self.driver.find_element_by_name("除以").click()
        self.driver.find_element_by_name("一").click()
        self.driver.find_element_by_name("一").click()
        self.driver.find_element_by_name("等于").click()
        self.assertEqual(self.getresults(), "8")

    def test_multiplication(self):
        self.driver.find_element_by_name("九").click()
        self.driver.find_element_by_name("乘以").click()
        self.driver.find_element_by_name("九").click()
        self.driver.find_element_by_name("等于").click()
        self.assertEqual(self.getresults(), "81")

    def test_subtraction(self):
        self.driver.find_element_by_name("九").click()
        self.driver.find_element_by_name("减").click()
        self.driver.find_element_by_name("一").click()
        self.driver.find_element_by_name("等于").click()
        self.assertEqual(self.getresults(), "8")


if __name__ == '__main__':
    unittest.main()
