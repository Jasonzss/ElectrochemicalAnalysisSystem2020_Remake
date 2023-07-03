from service import algorithm_executor, algorithm_invoke_exception
from test import test_exception

# 先不上测试框架
# import unittest

print(
    algorithm_executor.execute_algo("D:/Git/gitCodes//ElectrochemicalAnalysisSystem2020_Remake/src/main/resources/algo"
                                    "/python/test_hello", "hello", None))

print(
    algorithm_executor.execute_algo("D:/Git/gitCodes//ElectrochemicalAnalysisSystem2020_Remake/src/main/resources/algo"
                                    "/python/test_hello", "hello_sb", "jason"))
try:
    print(
        algorithm_executor.execute_algo(
            "D:/Git/gitCodes//ElectrochemicalAnalysisSystem2020_Remake/src/main/resources/algo"
            "/python/test_hello", "hello_sb", None))
except algorithm_invoke_exception.AlgorithmInvokeException as e:
    print("raise expected exception success")
else:
    raise test_exception.TestException("没有抛出预期异常")

try:
    print(
        algorithm_executor.execute_algo(
            "D:/Git/gitCodes//ElectrochemicalAnalysisSystem2020_Remake/src/main/resources/algo"
            "/python/test_hello", "hello", "jason"))
except algorithm_invoke_exception.AlgorithmInvokeException:
    print("raise expected exception success")
else:
    raise test_exception.TestException("没有抛出预期异常")
