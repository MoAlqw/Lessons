# any

from functools import lru_cache


@lru_cache()
def factorial(n):
    if n > 0:
        return n * factorial(n-1)
    else:
        return 1


@lru_cache()
def sf(n):
    res = [factorial(i) for i in range(1, n+1)]
    a = 1
    for b in res:
        a *= b
    return a


def test(n, fr=0, sfr=0):
    print(n, factorial(n), sf(n), end=' ')
    print(('passed' if fr == factorial(n) else 'not passed', 'passed' if sfr == sf(n) else 'not passed'))


test(0, 1, 1)
test(1, 1, 1)
test(2, 2, 2)
test(3, 6, 12)
test(4, 24, 288)
test(5, 120, 34560)
