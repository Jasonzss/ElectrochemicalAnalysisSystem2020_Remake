# 导包
from importlib import util

from flask import Flask, request, json, make_response
from werkzeug.exceptions import HTTPException

from api.api_exception import ServerError, APIException
from service import algorithm_executor

# 创建flask实例
app = Flask(__name__)
app.config['JSON_AS_ASCII'] = False


@app.errorhandler(Exception)
def framework_error(e):
    if isinstance(e, APIException):
        response = make_response(json.dumps(e.to_dict(), ensure_ascii=False))
        response.status_code = e.code
        return response
    if isinstance(e, HTTPException):
        response = make_response(json.dumps(e.__dict__, ensure_ascii=False))
        response.status_code = e.code
        return response
    else:
        if not app.config['DEBUG']:
            return ServerError()
        else:
            return e


# 路由注解：触发这个函数的url
# 调用执行算法
@app.route("/invoke", methods=['POST'])
def invoke():
    request.get_data(True, True, True)
    params = request.form["params"]
    p = json.loads(params)

    if request.form.__contains__("path"):
        res = algorithm_executor.execute_algo_by_path(request.form["path"], p)
    else:
        # 调用io流执行器
        res = algorithm_executor.execute_algo_by_input_stream(request.files.get("is").stream, p)

    # dumps方法操作中文时会出现编码问题，所以需要设置 ensure_ascii=False
    # 调用文件路径执行器
    return json.dumps(res, ensure_ascii=False)


# 判断
# @app.route("/support", methods=['POST'])
# def support():
#     algo_id = request.form["algo_id"]
#     base_path = request.form["base_path"]
#     params = request.form["params"]


# 路由注解：触发这个函数的url
@app.route("/hello")
def hello_world():
    # 返回html
    return "<p>Hello, World!</p>"


# 用于测试文本实体传输
@app.route("/test1", methods=['POST'])
def test1():
    data = request.json
    params = data.get("params")
    path = data.get("path")
    print("---------------------")
    print(params)
    clazz = params.__class__
    print(clazz)

    if isinstance(params, list):
        for i in params:
            print(str(i.__class__) + " : " + str(i))

    print("---------------------")
    print(path)
    print(path.__class__)

    return "<p>success</p>"


def load_module_from_stream(stream, module_name):
    code = compile(stream.read(), module_name, 'exec')
    module = util.module_from_spec(util.spec_from_loader(module_name, loader=None))
    exec(code, module.__dict__)
    return module


# 用于测试多媒体表单传输
@app.route("/test2", methods=['POST'])
def test2():
    request.get_data(True, True, True)
    print(request.form["jason"])
    print(request.form.__contains__("jason"))
    print(request.form.__contains__("jasd"))

    a = load_module_from_stream(request.files.get("is").stream, "a")
    print(a.hello())
    print(a.hello_sb("傻逼"))

    return "<p>success</p>"


# 用于文本表单传输
@app.route("/test3", methods=['POST'])
def test3():
    request.get_data(True, True, True)
    form = request.form

    print(form["params"].__class__)
    print(form["params"])
    p = json.loads(form["params"])
    print(p)
    print(p.__class__)

    return "<p>成功</p>"


if __name__ == '__main__':
    app.run()
