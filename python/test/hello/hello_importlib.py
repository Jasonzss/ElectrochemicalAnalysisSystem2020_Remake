from importlib import util, machinery
from io import BytesIO


# 导入文件模块
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


path = "../../../src/main/resources/algo/python/python_test_hello/python_algorithm.py"

python_algorithm = load_module_from_file(path, 'python_algorithm')

print(python_algorithm.hello())
print(python_algorithm.hello_sb("jason"))


# ---------------------------------------------------------------

io = BytesIO(open(path, 'br').read())

mymodule = load_module_from_stream(io, 'mmodule')

print(mymodule.hello())
print(mymodule.hello_sb("fuck"))


# ---------------------------------------------------------------
s = b'algo_name = "helloddd"\n\n\ndef hello():\n    return "hello import"\n\n\ndef hello_sb(sb):\n    return "hello ' \
    b'"+sb\n '

m = load_module_from_stream(BytesIO(s), "m")
print(m.hello())
print(m.hello_sb("d"))

# ---------------------------------------------------------------
# test a wrong python file
path = "../../../src/main/resources/algo/python/python_test_error/python_algorithm.py"

aaa = load_module_from_file(path, 'python_algorithm')
aaa.hello()