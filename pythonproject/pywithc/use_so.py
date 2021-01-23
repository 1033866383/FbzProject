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