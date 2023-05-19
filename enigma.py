def rotor(symbol, n, reverse=False):
    r0 = 'A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z'.replace('  ', '')
    r1 = 'E  K  M  F  L  G  D  Q  V  Z  N  T  O  W  Y  H  X  U  S  P  A  I  B  R  C  J'.replace('  ', '')
    r2 = 'A  J  D  K  S  I  R  U  X  B  L  H  W  T  M  C  Q  G  Z  N  P  Y  F  V  O  E'.replace('  ', '')
    r3 = 'B  D  F  H  J  L  C  P  R  T  X  V  Z  N  Y  E  I  W  G  A  K  M  U  S  Q  O'.replace('  ', '')

    # ротор(номер_диска) с перемешенным алфовитом, где r0 - упорядоченный алфовит, а
    # r(N) - перемешенный алфовит
    rotors = [r0, r1, r2, r3]

    # вернуть символ r(N) диска по порядковому номеру этого же символа в диске который передается
    return rotors[n][r0.index(symbol)] if not reverse else rotors[0][rotors[n].index(symbol)]


def reflector(symbol, n):
    # отражатели - пара символов, чем-то похоже на ротор
    REFLECTORS = {0: 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',
                  1: 'YRUHQSLDPXNGOKMIEBFZCWVJAT',
                  2: 'FVPJIAOYEDRZXWGCTKUQSBNMHL',
                  3: 'ENKQAUYWJICOPBLMDXZVFTHRGS',
                  4: 'RDOBJNTKVEHMLFCWZAXGYIPSUQ', }

    # вернуть отраженный символ
    return REFLECTORS[n][REFLECTORS[0].index(symbol)]

# шифр Цезаря и ничего необычного
def caesar(text, key, alphabet='ABCDEFGHIJKLMNOPQRSTUVWXYZ'):
    # здесь будет результат кодировки
    caesar_text = ''

    # на сколько смещать букву в алфавите
    key0 = key

    # если ключ больше длины алфавита, то оставить его остаток
    if abs(key) > len(alphabet):
        key0 = key % len(alphabet)

    # проходимся по тексту, который нужно зашифровать
    for alph in text:
        if alph.upper() in alphabet:

            # если полученный индекс выйдет больше длины алфавита, то отнять его длину
            if (alphabet.index(alph.upper()) + key0) > (len(alphabet) - 1):
                caesar_text += alphabet[alphabet.index(alph.upper()) + key0 - len(alphabet)]

            # если полученный индекс наоборот отрицательный, то прибавить длину алфавита
            elif (alphabet.index(alph.upper()) + key0) < 0:
                caesar_text += alphabet[alphabet.index(alph.upper()) + key0 + len(alphabet)]

            # если индекс в пределах нормы - просто извлечь символ из алфавита со смещением
            else:
                caesar_text += alphabet[alphabet.index(alph.upper()) + key0]

    # выводим результат
    return caesar_text


# при этих смещениях происходит *магия*
def check_lim(rotN):
    rots = [0, 1, 2, 3]
    lims = [0, 17, 5, 8]
    return lims[rots.index(rotN)]


def commut(sym, pair):
    if sym in pair:
        if pair.index(sym) % 2 == 0:
            return pair[pair.index(sym) + 1]
        else:
            return pair[pair.index(sym) - 1]
    else:
        return sym


# rot3 -> rot2 -> rot1 -> ref -> rot1 -> rot2 -> rot3
# порядок шифровки
def enigma(text, ref, rot1, shift1, rot2, shift2, rot3, shift3, pairs=""):
    for alph_p in pairs:
        if not alph_p.isalpha() and alph_p != ' ':
            return 'Извините, невозможно произвести коммутацию'

    pairs = ''.join([alpha.upper() for alpha in pairs if alpha.upper() in 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'])
    if len(pairs) != len(set(pairs)): return 'Извините, невозможно произвести коммутацию'
    if len(pairs) % 2 != 0: return 'Извините, невозможно произвести коммутацию'

    # здесь будет результат
    enigma_text_res = ''

    # делаем текст валидным, избавляемся от пробелов и прочего
    text = [sym_corr.upper() for sym_corr in text if sym_corr.upper() in 'ABCDEFGHIJKLMNOPQRSTUVWXYZ']

    for i in text:

        if shift2 == check_lim(rot2) - 1:
            shift1 += 1
            shift2 += 1
            shift3 += 1
            if shift1 == check_lim(rot1):
                shift2 += 1
            if shift3 == check_lim(rot3):
                shift2 += 1
        else:
            shift3 += 1
            if shift3 == check_lim(rot3):
                shift2 += 1

        # проверяем на ограничение по длине алфавита
        if shift3 == 26: shift3 = 0
        if shift2 == 26: shift2 = 0
        if shift1 == 26: shift1 = 0

        # будем записывать наш шифруемый символ в эту переменную

        sym_edit = i
        sym_edit = commut(sym_edit, pairs)

        # изначально берем смещение по алфавиту равное смещению ротора(N) - O(x), и берем значение из rotor()
        # где O(x) - прошлая координата, если начали с 0, то O(x) = 0
        sym_edit = rotor(caesar(sym_edit, shift3 - 0), rot3)
        sym_edit = rotor(caesar(sym_edit, shift2 - shift3), rot2)
        sym_edit = rotor(caesar(sym_edit, shift1 - shift2), rot1)
        sym_edit = reflector(caesar(sym_edit, 0 - shift1), ref)
        sym_edit = rotor(caesar(sym_edit, shift1 - 0), rot1, True)
        sym_edit = rotor(caesar(sym_edit, shift2 - shift1), rot2, True)
        sym_edit = rotor(caesar(sym_edit, shift3 - shift2), rot3, True)
        sym_edit = caesar(sym_edit, 0 - shift3)
        sym_edit = commut(sym_edit, pairs)

        enigma_text_res += sym_edit


    return enigma_text_res


print(enigma('RRBWELXP', 1, 1, 0, 2, 0, 3, 0, 'AC'))

