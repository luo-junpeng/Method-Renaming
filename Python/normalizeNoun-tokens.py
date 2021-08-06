
##去除文件内容的标点符号，并逐个单词读取到列表，去除重复元素


import string
from string import digits

for k in range(67):
    path = r'F:\\Apache-test\\hbase-test\\lexical\\' + str(k+30) +'\\original-java.txt'
    outpath = r'F:\\Apache-test\\hbase-test\\lexical\\' + str(k+30) +'\\middle.txt'

    with open(path, encoding='utf-8', errors='ignore') as file:
        lines = file.readlines()

    a = ""
    for line in lines:
        a += line.strip()
        c = a.split()
        b = "".join(c)

    for w in string.punctuation:
        a = a.replace(w, ' ')
        res = a.translate(str.maketrans('', '', digits))

    print(a)

    outfile = open(outpath, 'w',encoding='utf-8')  # 在使用write()函数的时候，如果文件打开模式带 b，那写入文件内容时，str (参数)要用 encode 方法转为 bytes 形式，否则报错。
    outfile.write(a)  # 写入train_output.txt(此处是一股脑的全写进去，并没有做任何的分行处理)
    outfile.close()

    with open(outpath) as file:
        outlines = file.readlines()

    for i in range(0, outlines.__len__(), 1):  # (开始/左边界, 结束/右边界, 步长)

        list = []  ## 空列表, 将第i行数据存入list中
        for word in outlines[i].split():
            word = word.strip(string.whitespace)
            list.append(word);
        print(list)
        print('**************************')

    list4 = []  # 创建空的列表
    for i in list:  # 使用for in遍历出列表
        if not i in list4:  # 将遍历好的数字存储到控的列表中，因为使用了if not ，只有为空的的字符才会存里面，如果llist4里面已经有了，则不会存进去，这就起到了去除重复的效果！！
            list4.append(i)  # 把i存入新的列表中
    print(str(list4))

    a = ' '.join(list4)
    print(a)
    outpath = r'F:\\Apache-test\\hbase-test\\lexical\\' + str(k+30) + '\\methodbodywords.txt'
    outfile = open(outpath, 'w',encoding='utf-8')  # 在使用write()函数的时候，如果文件打开模式带 b，那写入文件内容时，str (参数)要用 encode 方法转为 bytes 形式，否则报错。
    outfile.write(a)  # 写入train_output.txt(此处是一股脑的全写进去，并没有做任何的分行处理)
    outfile.close()











