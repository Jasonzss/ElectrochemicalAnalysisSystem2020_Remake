from flask import request, json
from werkzeug.exceptions import HTTPException

from core.utils import jsonify


class APIException(HTTPException):
    code = 500  # http 状态码
    msg = '服务器未知错误'  # 异常信息
    error_code = 999  # 约定的异常码

    def __init__(self, code=None, error_code=None, msg=None, headers=None):
        if code:
            self.code = code
        if error_code:
            self.error_code = error_code
        if msg:
            self.msg = msg
        super(APIException, self).__init__()

    def get_body(self, environ=None):
        body = dict(
            msg=self.msg,
            error_code=self.error_code,
            request_url=request.method + ' ' + self.get_url_no_param()
        )
        text = json.dumps(body)  # 返回文本
        return text

    def get_headers(self, environ=None):
        return [('Content-type', 'application/json; charset=utf-8')]

    @staticmethod
    def get_url_no_param():
        full_path = str(request.full_path)
        main_path = full_path.split('?')[0]
        return main_path

    def to_dict(self):
        return {"error_code" : self.error_code, "msg" : self.msg}


class Success(APIException):
    code = 200
    error_code = 0
    data = None
    msg = '成功'

    def __init__(self, data=None, code=None, error_code=None, msg=None):
        if data:
            self.data = jsonify(data)
        if error_code == 1:
            code = code if code else 201
            msg = msg if msg else '创建 | 更新成功'
        if error_code == 2:
            code = code if code else 202
            msg = msg if msg else '删除成功'
        super(Success, self).__init__(code, error_code, msg)

    def get_body(self, environ=None):
        body = dict(
            error_code=self.error_code,
            msg=self.msg,
            data=self.data
        )
        text = json.dumps(body)  # 返回文本
        return text


class ServerError(APIException):
    code = 500
    error_code = 999
    msg = '服务器端异常'


class Failed(APIException):
    code = 400
    error_code = 9999
    msg = '失败'


class EmptyPath(APIException):
    code = 400
    error_code = 1001
    msg = '文件路径不能为空'


class ModuleNotExist(APIException):
    code = 400
    error_code = 1002
    msg = '失败'


class AlgorithmFileNotFound(APIException):
    code = 400
    error_code = 1003
    msg = '找不到算法文件'


class NoInvokeTarget(APIException):
    code = 400
    error_code = 1004
    msg = '未指定调用对象'


class InvokeTargetNotExist(APIException):
    code = 400
    error_code = 1005
    msg = '调用对象不存在'


class WrongParam(APIException):
    code = 400
    error_code = 1006
    msg = '输入了错误的参数'


class ExceptionOccursWhenAlgorithmInvoke(APIException):
    code = 400
    error_code = 1007
    msg = 'python函数/方法调用时发生异常'


