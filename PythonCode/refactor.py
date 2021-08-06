for k in range(67):

    with open('F:\\Apache-test\\hbase-test\\rename\\' + str(k+1) + '\\verb.txt') as tf:
        verb = tf.readlines()
        for i in range(len(verb)):
            verb[i] = verb[i].replace('\n', '')
        print(verb)
    print('***********************')
    with open('F:\\Apache-test\\hbase-test\\rename\\' + str(k+1) + '\\verbsin.txt') as tf:
        verbsin = tf.readlines()
        for i in range(len(verbsin)):
            verbsin[i] = verbsin[i].replace('\n', '')
            verbsin[i] = float(verbsin[i])
        print(verbsin)
    print('***********************')
    with open('F:\\Apache-test\\hbase-test\\rename\\' + str(k+1) + '\\noun.txt') as tf:
        noun = tf.readlines()
        for i in range(len(noun)):
            noun[i] = noun[i].replace('\n', '')
        print(noun)
    print('***********************')
    with open('F:\\Apache-test\\hbase-test\\rename\\' + str(k+1) + '\\nounsin.txt') as tf:
        nounsin = tf.readlines()
        for i in range(len(nounsin)):
            nounsin[i] = nounsin[i].replace('\n', '')
            nounsin[i] = float(nounsin[i])
        print(nounsin)
    print('***********************')

    sinlist = [0] * (len(verb) * len(noun))
    outfile = open('F:\\Apache-test\\hbase-test\\rename\\' + str(k+1) + '\\RenamedMethods.txt', 'a')
    outsinfile = open('F:\\Apache-test\\hbase-test\\rename\\' + str(k+1) + '\\outsin.txt', 'a')
    outnamefile = open('F:\\Apache-test\\hbase-test\\rename\\' + str(k+1) + '\\outname.txt', 'a')
    for i in range(len(verb)):
        for j in range(len(noun)):
            voab = verb[i] + noun[j]
            sin = verbsin[i] + nounsin[j]
            sinlist = sin
            print(voab)
            print(sin)
            outfile.write(voab + ' ' + str(sin) + '\n')
            outsinfile.write(str(sin) + ' ')
            outnamefile.write(voab + ' ')

    print(k+1)
    print('***********************')

    # re1 = map(cos_sim.index, heapq.nlargest(10, cos_sim))  # 求最大的三个索引    nsmallest与nlargest相反，求最小
    # re2 = heapq.nlargest(10, cos_sim)  # 求最大的三个元素
    # xre1 = list(re1)
    # print('相似度前10的句子,相似度为：')
    # for i in range(10):
    # j = xre1[i]
    # print(newcondinates[j], re2[i])