from importlib import machinery, util
from inspect import isroutine

from api.api_exception import EmptyPath, ModuleNotExist, AlgorithmFileNotFound, NoInvokeTarget, InvokeTargetNotExist, \
    WrongParam, ExceptionOccursWhenAlgorithmInvoke


def execute_algo_by_path(path, params):
    # 1. 判断入参是否合法
    if path is None or path.strip() == "":
        raise EmptyPath

    # 2. 导入目标文件模块
    python_algorithm = load_module_from_file(path, "python_algorithm")
    return do_execute(python_algorithm, params)


def execute_algo_by_input_stream(stream, params):
    python_algorithm = load_module_from_stream(stream, "python_algorithm")
    return do_execute(python_algorithm, params)


def do_execute(python_algorithm, params):
    # 与java端的算法管理系统存在一定的耦合
    # 3. 获取文件中指定的算法名
    try:
        algo_name = getattr(python_algorithm, "algo_name")
    except ModuleNotFoundError as e:
        raise ModuleNotExist from e
    except AttributeError as e:
        raise AlgorithmFileNotFound from e

    # 4. 反射获取到对应的算法
    try:
        f = getattr(python_algorithm, algo_name)
    except AttributeError as e:
        # AttributeError: module 'PythonAlgorithm' has no attribute 'hell0o'. Did you mean: 'hello'?
        # python是怎么做到根据异常分析出可能出错的原因的？这有点牛逼【Did you mean: 'hello'?】
        raise NoInvokeTarget from e

    # 5. 判断拿到的是否是可运行的函数或方法
    if not isroutine(f):
        raise InvokeTargetNotExist

    # 6. 执行
    try:
        if params is None or len(params) == 0:
            return f()
        else:
            # 将参数列表放入参数中执行
            return f(*params)
    except TypeError as e:
        raise WrongParam from e
    except Exception as e:
        raise ExceptionOccursWhenAlgorithmInvoke from e


def load_module_from_file(file_path, module_name):
    loader = machinery.SourceFileLoader(module_name, file_path)
    spec = util.spec_from_loader(loader.name, loader)
    module = util.module_from_spec(spec)
    loader.exec_module(module)
    return module


def load_module_from_stream(stream, module_name):
    code = compile(stream.read(), module_name, 'exec')
    module = util.module_from_spec(util.spec_from_loader(module_name, loader=None))
    exec(code, module.__dict__)
    return module
