# > $env:FLASK_APP = "hello"
# > flask run
#  * Running on http://127.0.0.1:5000/

import sys
import os

# 把 api module 导入到python.path中，不然会找不到自己写的内容
sys.path.append(os.path.dirname(os.path.abspath(__file__))+"\\api")
sys.path.append(os.path.dirname(os.path.abspath(__file__))+"\\service")
# from python import *
