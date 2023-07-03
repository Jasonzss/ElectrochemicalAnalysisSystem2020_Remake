import sys

print(sys.path)

sys.path.append("D:/Git/gitCodes//ElectrochemicalAnalysisSystem2020_Remake/src/main/resources/algo/python/test_hello")


print("-----------------")
print(sys.path)
print("-----------------")

import python_algorithm

print(python_algorithm.hello())

del python_algorithm
