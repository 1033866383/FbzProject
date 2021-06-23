import json

import requests

import literacy_test_project.interface.config as config
from literacy_test_project.interface.auto_test_app import read_data


@read_data
class auto:
   
    #Here, the results of all methods can be obtained if one method depends on another method.
    res_func = dict()

    #run order function name, if not have property will use default hash order
    run_order = ["send_code", "login_shizi", "user_info"]

    hosts = {"dev": "dev", "test": "test"}

    URL = hosts.get(config.env)

    if not URL:
            print("环境缺失")
            exit()


    def send_code(self, data):
        url = "sendCode"
        return requests.post(url= self.URL + url, data=data).text


    def login_shizi(self, data):
        url = "login"
        res = requests.post(url = self.URL + url, data=data).text
        return res

    def version(self, data):
        url = "data"
        return requests.get(url= self.URL + url, params=data).text


    def user_info(self, data):
        # 用户信息
        url = "info"
        return requests.get(url=self.URL + url, params=data).text




if __name__ == '__main__':
    auto()
