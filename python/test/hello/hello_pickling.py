import pickle

d = dict(name='Bob', age=20, score=88)

# b'\x80\x04\x95$\x00\x00\x00\x00\x00\x00\x00}\x94(\x8c\x04name\x94\x8c\x03Bob\x94\x8c\x03age\x94K\x14\x8c\x05score\x94KXu.'
print(pickle.dumps(d))


class Student(object):
    def __init__(self, age, name, uid):
        self.name = name
        self.age = age
        self.uid = uid


jason = Student(11, "jason", 123456)
# <__main__.Student object at 0x000002425E0A8410>
print(jason)
# b'\x80\x04\x95A\x00\x00\x00\x00\x00\x00\x00\x8c\x08__main__\x94\x8c\x07Student\x94\x93\x94)\x81\x94}\x94(\x8c\x04name\x94\x8c\x05jason\x94\x8c\x03age\x94K\x0b\x8c\x02id\x94J@\xe2\x01\x00ub.'
print(pickle.dumps(jason))
