import json
"""
simple auto test
"""

#读取参数数据
def read_json(data="data.json") -> dict:
        with open(data) as f:
            res = f.read()
            res = json.loads(res)
        return res

def read_data(clazz):
    funs = {}
    def find_func():
        fieds = dir(clazz)
        for i in fieds:
            if "__" not in i and callable(getattr(clazz, i)):
                funs[i] = getattr(clazz, i)
        data = read_json()
        if "run_order" in fieds:
            for k in funs:
                if k not in clazz.run_order:
                    clazz.run_order.append(k)
            print("=========任务队列===========")
            print(clazz.run_order)
            print("=========任务开始===========")
            for f in clazz.run_order:
                paramlist = data.get(f)
                print("=========开始执行函数：" + f + "===========")
                if paramlist:
                    for p in paramlist:
                        print("case入参：" + str(p))
                        if funs.get(f):
                            res = funs.get(f)(clazz, p)
                            clazz.res_func[f] = res
                            print("结果：", res)
                        else:
                            print("无函数：", f)
                else:
                    if funs.get(f):
                        res = funs.get(f)(clazz)
                        clazz.res_func[f] = res
                        print("结果：", res)
                    else:
                        print("无函数：", f)
        else:
            print("=========任务队列===========")
            print(funs)
            print("=========任务开始===========")
            for f in funs:
                paramlist = data.get(f)
                print("=========开始执行函数："+f+"===========")
                if paramlist:
                    for p in paramlist:
                        print("case入参：" + str(p))
                        res = funs.get(f)(clazz, p)
                        clazz.res_func[f] = res
                        print("结果：", res)
                else:
                    res = funs.get(f)(clazz)
                    clazz.res_func[f] = res
                    print("结果：", res)
    return find_func

