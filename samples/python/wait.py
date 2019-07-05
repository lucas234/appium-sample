# coding=utf-8
# auther：Liul5
# date：2019/7/4 9:43
# tools：PyCharm
# Python：2.7.15
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
# reference
# https://docs.seleniumhq.org/docs/04_webdriver_advanced.jsp#explicit-and-implicit-waits


WebDriverWait("your driver", 10, ignored_exceptions=True).until(lambda x: x.find_element_by__id(""))
# driver.implicitly_wait(3)
wait = WebDriverWait("your driver", 10)
element = wait.until(EC.element_to_be_clickable((By.ID, 'someid')))
element1 = wait.until(EC.presence_of_element_located((By.ID, "myDynamicElement")))


def comment():
    """Constructor, takes a WebDriver instance and timeout in seconds.

       :Args:
        - driver - Instance of WebDriver (Ie, Firefox, Chrome or Remote)
        - timeout - Number of seconds before timing out
        - poll_frequency - sleep interval between calls
          By default, it is 0.5 second.
        - ignored_exceptions - iterable structure of exception classes ignored during calls.
          By default, it contains NoSuchElementException only.

       Example:
        from selenium.webdriver.support.ui import WebDriverWait \n
        element = WebDriverWait(driver, 10).until(lambda x: x.find_element_by_id("someId")) \n
        is_disappeared = WebDriverWait(driver, 30, 1, (ElementNotVisibleException)).\ \n
                    until_not(lambda x: x.find_element_by_id("someId").is_displayed())
    """
