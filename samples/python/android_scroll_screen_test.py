# coding=utf-8
# auther：Liul5
# date：2019/7/1 10:50
# tools：PyCharm
# Python：2.7.15
# reference
# http://www.automationtestinghub.com/appium-scroll-examples/
# https://developer.android.com/reference/android/support/test/uiautomator/UiScrollable.html

import unittest
import os
from appium import webdriver
from appium.webdriver.common.touch_action import TouchAction

PATH = lambda p: os.path.abspath(os.path.join(os.path.dirname(__file__), p))


class AndroidScrollScreenTest(unittest.TestCase):

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

    def test_scroll_by_UIScrollable(self):
        self.driver.find_element_by_accessibility_id("Views").click()
        self.scroll_by_UIScrollable()
        # 尝试下来该方法只支持js script，但官网显示支持。。。。
        # self.driver.execute_script('mobile: scroll', {'direction': 'down'})
        # self.driver.execute_script('mobile: swipe', {'direction': 'down'})
        self.assertTrue(self.driver.find_element_by_android_uiautomator("new UiSelector().textContains(\"Hello World\")").is_displayed())

    def test_scroll_by_touch_actions(self):
        self.driver.find_element_by_accessibility_id("Views").click()
        el = self.driver.find_element_by_accessibility_id("Animation")
        el2 = self.driver.find_element_by_accessibility_id("Grid")
        # 1. by TouchAction
        actions = TouchAction(self.driver)
        actions.press(el2).move_to(el).release().perform()
        # 2.或者直接用scroll方法，基于TouchAction的封装
        # self.driver.scroll(el2, el)
        # 3.用drag_and_drop（基于TouchAction的封装），但是要执行多次，执行一次不会到最底层
        # self.driver.drag_and_drop(el2, el)
        # 4. 用swipe、flick （基于TouchAction的封装），但是要执行多次，执行一次不会到最底层
        # self.driver.swipe(1100, 1100, 300, 300)
        # self.driver.flick(1100, 1100, 300, 300)
        self.driver.find_element_by_accessibility_id("WebView").click()
        self.assertTrue(self.driver.find_element_by_android_uiautomator("new UiSelector().textContains(\"Hello World\")").is_displayed())

    def test_scroll_by_adb_command(self):
        self.driver.find_element_by_accessibility_id("Views").click()
        # 自行调整执行次数
        self.scroll_by_adb_command()
        self.scroll_by_adb_command()
        self.driver.find_element_by_accessibility_id("WebView").click()
        self.assertTrue(self.driver.find_element_by_android_uiautomator("new UiSelector().textContains(\"Hello World\")").is_displayed())

    def test_scroll_horizontal(self):
        self.driver.find_element_by_accessibility_id("Views").click()
        el = self.driver.find_element_by_accessibility_id("Animation")
        el2 = self.driver.find_element_by_accessibility_id("Grid")
        self.driver.scroll(el2, el)
        self.driver.find_element_by_accessibility_id("Tabs").click()
        self.driver.find_element_by_accessibility_id("5. Scrollable").click()
        self.scroll_by_UIScrollable(horizontal=True)
        self.assertTrue(self.driver.find_element_by_android_uiautomator("new UiSelector().text(\"Content for tab with tag Tab 24\")").is_displayed())

    def scroll_by_UIScrollable(self, horizontal=None):
        # 滚动定位到要找的元素
        # 1.通过scrollIntoView方法
        by_scroll_into_view = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"WebView\").instance(0))"

        # 2.通过getChildByText方法
        get_child_by_text = "new UiScrollable(new UiSelector().resourceId(\"android:id/list\")).getChildByText(new UiSelector().text(\"WebView\"),\"WebView\")"
        get_child_by_text2 = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).getChildByText(new UiSelector().text(\"WebView\"),\"WebView\")"

        # 3.通过getChildByDescription方法
        get_child_by_description = "new UiScrollable(new UiSelector().resourceId(\"android:id/list\")).getChildByDescription(new UiSelector().text(\"WebView\"),\"WebView\")"

        # 水平滑动 setAsHorizontalList()
        scroll_by_horizontal = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).setAsHorizontalList().getChildByText(new UiSelector().text(\"TAB 24\"),\"TAB 24\")"
        if horizontal:
            self.driver.find_element_by_android_uiautomator(scroll_by_horizontal).click()
        else:
            self.driver.find_element_by_android_uiautomator(by_scroll_into_view).click()

    @staticmethod
    def scroll_by_adb_command():
        # adb -s 1b686a shell input swipe 300 300 250 250
        os.popen("adb shell input swipe 1100 1100 300 300")

    def scroll_by_coordinate(self):
        # 1.by TouchAction
        # actions = TouchAction(self.driver)
        # actions.press(startX, startY).moveTo(endX, endY).release().perform()
        # 2. swipe、flick 封装TouchAction
        self.driver.swipe(1100, 1100, 300, 300)
        # self.driver.flick(1100, 1100, 300, 300)


if __name__ == '__main__':
    unittest.main()
