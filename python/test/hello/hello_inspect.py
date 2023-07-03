import inspect


class User:
    name = ""
    age = 0

    def __init__(self, name, age):
        self.name = name
        self.age = age

    def to_string(self):
        print("name : " + self.name
              + ", age : " + self.age)

    def speak(self, s):
        print(self.name + " say " + s)


def func(a: str):
    print("this is a function : " + a)


def func1():
    print("this is a function")


# 数据定义
u = User("jason", 24)
list1 = ['Google', 'jason', 1997, 2000]
tup = ('Google', 'jason', 1997, 2000)
tiny_dict = {'name': 'jason', 'likes': 123, 'url': 'www.jason.com'}

# 反射获取对象中的方法
speak = getattr(u, "speak")
init = getattr(u, "__init__")
# <bound method User.speak of <__main__.User object at 0x0000013389E98A90>>
print(speak)

# 1.获取所有成员（包括成员方法、
print(inspect.getmembers(u))

# 2.返回内部属性 (name, age)
print(inspect.signature(User))
# 返回方法的参数列表：(s)
print(inspect.signature(speak))
# 返回函数的参数列表：(a: str)
print(inspect.signature(func))
# (a: str)
print(inspect.signature(func).__str__())
print(inspect.signature(func).__str__())
# ()
print(inspect.signature(func1).__str__())

# 3.返回对象所在的模块
print(inspect.getmodule(u))

# 4.返回该类的源代码
print(inspect.getsource(User))

# 5.返回该类的源代码每行组成的元组
# 源代码中的第一行：class User:
print(inspect.getsourcelines(User)[0][0])
# 源代码第一行所在的行数：4
print(inspect.getsourcelines(User)[1])

# 6.判断对象是否为方法
# True
print(inspect.ismethod(speak))
# True
print(inspect.ismethod(init))
# False
print(inspect.ismethod(list1))
# False: 函数和方法不是一个概念
print(inspect.ismethod(func))
# True
print(inspect.isfunction(func))
# <class 'function'>
print(func.__class__)

# 7.判断是否有目标属性
# True
print(hasattr(u, "name"))
# False
print(hasattr(u, "unknown"))

# 8.是否为用户自定义或者built-in函数或方法
# True
print(inspect.isroutine(speak))
# True
print(inspect.isroutine(func))

# 9.获取方法的参数名称：('self', 's')
print(speak.__code__.co_varnames)

