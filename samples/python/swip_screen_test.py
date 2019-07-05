# coding=utf-8
# auther：Liul5
# date：2019/7/4 11:25
# tools：PyCharm
# Python：2.7.15
import unittest
import os
from appium import webdriver
import time

PATH = lambda p: os.path.abspath(os.path.join(os.path.dirname(__file__), p))


class SwipeScreenTest(unittest.TestCase):
    def setUp(self):
        # app_path = os.path.abspath(os.path.join(os.path.dirname(__file__), '../../apps/test-app.apk'))
        desired_caps = {
            'platformName': 'Android',
            'platformVersion': '7.0',
            'deviceName': '520381b347dd148b',
            "app": PATH('../../apps/api-demo.apk'),
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

    @unittest.skip("skipped")
    def test_swipe(self):
        # 是VodQA.apk下的
        self.driver.implicitly_wait(3)
        self.driver.find_element_by_android_uiautomator("new UiSelector().text(\"LOG IN\")").click()
        self.driver.find_element_by_android_uiautomator("new UiSelector().text(\"Carousel\")").click()
        self.swipe_right(self.get_size())

    def test_swipes(self):
        # 你可以看到界面的滑动，具体精度可自行调整
        self.driver.find_element_by_accessibility_id("Graphics").click()
        by_scroll_into_view = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Touch Paint\").instance(0))"
        self.driver.find_element_by_android_uiautomator(by_scroll_into_view).click()
        time.sleep(2)
        self.swipe_left(self.get_size())
        time.sleep(2)
        self.swipe_right(self.get_size())
        time.sleep(2)
        self.swipe_up(self.get_size())
        time.sleep(2)
        self.swipe_down(self.get_size())

    def get_size(self):
        """
        获取屏幕尺寸（width,height）
        :param self:
        :return:
        """
        size = self.driver.get_window_size()
        width = size.get('width')
        height = size.get('height')
        return width, height

    def swipe_left(self, sizes):
        """
        向左滑动
        :param sizes:屏幕尺寸（width,height）
        :return:
        """
        x1 = int(sizes[0] * 0.9)
        y1 = int(sizes[1] * 0.5)
        x2 = int(sizes[0] * 0.1)
        self.driver.swipe(x1, y1, x2, y1, 1000)

    def swipe_up(self, sizes):
        """
        向上滑动
        :param sizes:屏幕尺寸（width,height）
        :return:
        """
        x1 = int(sizes[0] * 0.5)
        y1 = int(sizes[1] * 0.95)
        y2 = int(sizes[1] * 0.35)
        self.driver.swipe(x1, y1, x1, y2, 1000)

    def swipe_down(self, sizes):
        """
        向下滑动
        :param sizes:屏幕尺寸（width,height）
        :return:
        """
        x1 = int(sizes[0] * 0.5)
        y1 = int(sizes[1] * 0.35)
        y2 = int(sizes[1] * 0.85)
        self.driver.swipe(x1, y1, x1, y2, 1000)

    def swipe_right(self, sizes):
        """
        向右滑动
        :param sizes:屏幕尺寸（width,height）
        :return:
        """
        y1 = int(sizes[1] * 0.5)
        x1 = int(sizes[0] * 0.1)
        x2 = int(sizes[0] * 0.9)
        self.driver.swipe(x1, y1, x2, y1, 1000)


if __name__ == '__main__':
    unittest.main()
