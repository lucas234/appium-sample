# coding=utf-8
# auther：Liul5
# date：2019/7/2 17:22
# tools：PyCharm
# Python：2.7.15
import unittest
import os
from appium import webdriver

PATH = lambda p: os.path.abspath(os.path.join(os.path.dirname(__file__), p))


class AndroidWebviewTest(unittest.TestCase):
    def setUp(self):
        # app_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '../../apps/test-app.apk'))
        desired_caps = {
            'platformName': 'Android',
            'platformVersion': '7.0',
            'deviceName': '520381b347dd148b',
            "app": PATH('../../apps/VodQA.apk'),
            # 声明中文
            "unicodeKeyboard": 'True',
            # 声明中文，否则不支持中文
            "resetKeyboard": 'True',
            # 执行时不重新安装包
            'noReset': 'True',
            # 'automationName': 'UiAutomator2',
        }
        self.driver = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)

    def tearDown(self):
        self.driver.quit()

    def test_seekbar(self):
        self.driver.implicitly_wait(3)
        self.driver.find_element_by_android_uiautomator("new UiSelector().text(\"LOG IN\")").click()
        self.driver.find_element_by_android_uiautomator("new UiSelector().text(\"Slider\")").click()
        slider = self.driver.find_element_by_accessibility_id("slider")
        self.driver.implicitly_wait(2)
        # 移动 100%
        # self.move_seekbar(slider, 1.5)
        # 移动 40%
        self.move_seekbar(slider, 0.4)

    def move_seekbar(self, element, percent):
        # 精度不是很准确，需自己调，当percent为1时可能不会移动到100%，可以尝试1.5或2
        # 获取拖动条的宽
        width = element.size.get("width")
        # 获取坐标
        x = element.location.get("x")
        y = element.location.get("y")
        self.driver.swipe(x, y, int(width*percent), y, 1000)


if __name__ == '__main__':
    unittest.main()
