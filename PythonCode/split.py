import codecs
import os


for k in range(1):
    input_data = codecs.open('F:\\Spring-test\\security-test\\rename\\' + str(k+59) + '\\split\\input.txt', 'r', 'utf-8')

    for line in input_data.readlines():
        line = (line.rstrip('\n'))
        line = (line.rstrip('\r'))
        A = line
        print(A)
        with open('F:\\Spring-test\\security-test\\rename\\' + str(k+59) + '\\split\\dictionary.txt','r') as f:
            word_list = f.read().split()
        print(word_list)
        start = 0
        index = len(A)

        guessed = True

        outfile = open('F:\\Spring-test\\security-test\\rename\\' + str(k + 59) + '\\split\\output.txt', "a")
        outverbfile = open('F:\\Spring-test\\security-test\\rename\\' + str(k + 59) + '\\verb.txt', 'a')

        while guessed:
            if A[start:index].replace(' ', '') in word_list:
                print(A[start:index])
                A = A.replace(A[start:index], '')
                start = 0
                index = len(A)

            else:
                start += 1
                if start == index:
                    guessed = False
        outfile.write(A + '\n')
        outverbfile.write(A + '\n')

        print(k+59)

        print("**********************************")



