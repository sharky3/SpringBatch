import random as r
import string

N = 100010

print("id, name, value")

def degenerate(i):
    f1 = r.choice(string.ascii_letters) + r.choice(string.ascii_letters) + r.choice(string.ascii_letters) + r.choice(string.ascii_letters)
    f2 = round(i + r.random(), 2)
    print("{},{},{}".format(i+1, f1, f2))

for i in range(10, N):
    degenerate(i)
