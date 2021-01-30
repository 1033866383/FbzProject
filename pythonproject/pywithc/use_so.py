# -*- coding: utf-8 -*-
from ctypes import *
# gcc -shared -Wl,-install_name,adder.so -o put.so -fPIC put.c
lib = CDLL("put.so")
i = 100000
d = c_int(i)
print(d.value)
poi = pointer(d)
print(poi)
i = lib.putInt(poi, 100)
print(d.value)
print(i)



i = 10
ad = id(i)
print(hex(ad))
res = lib.putAddr(c_long(ad), 10000000)
print(res)
#可以确定的是我们的确改变了 i显示的内存的值
suc = cast(ad, POINTER(c_int)).contents.value
print(suc)
print(i)
print(hex(id(i)))