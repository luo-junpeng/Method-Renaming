
import numpy as np
import heapq


class SimilarModel:

    def cos_similar(self ,sen_a_vec, sen_b_vec):
        '''
        计算两个句子的余弦相似度
        :param sen_a_vec:
        :param sen_b_vec:
        :return:
        '''
        vector_a = np.mat(sen_a_vec)
        vector_b = np.mat(sen_b_vec)
        num = float(vector_a * vector_b.T)
        denom = np.linalg.norm(vector_a) * np.linalg.norm(vector_b)
        cos = num / denom
        return cos


bert_client = SimilarModel()

with open ('F:\\Apache-test\\hbase-test\\methodtoken\\train\\CNN\\DLoutput_1\\1_CNNoutput.csv') as tf:
    renamedvec = tf.readlines()
    print(len(renamedvec))

for l in range(len(renamedvec)):
    with open('F:\Apache-test\hbase-test\\VersionDB\\ver_new\\train\\CNN\\DLoutput_1\\1_CNNoutput.csv') as tf:
        newvec = tf.readlines()

    cos_sim = [0] * len(newvec)
    line = 0 * len(newvec)
    for j in range(len(newvec)):
        cos_sim[j] = bert_client.cos_similar(renamedvec[l], newvec[j])
        # line[j] = j

    with open('F:\Apache-test\hbase-test\\VersionDB\\ver_new\\token\\ParsedMethodNames\\onlyMethodNames.txt') as tf:
        name = tf.readlines()
    re1 = map(cos_sim.index, heapq.nlargest(20, cos_sim))  # 求最大的三个索引    nsmallest与nlargest相反，求最小
    re2 = heapq.nlargest(20, cos_sim)  # 求最大的三个元素
    xre1 = list(re1)

    outverfile = open('F:\Apache-test\hbase-test\\rename\\' + str(l + 1) + '\\Top10Structure\\verb.txt', 'a')
    outsinfile = open('F:\Apache-test\hbase-test\\rename\\' + str(l + 1) + '\\Top10Structure\\sin.txt', 'a')

    print('相似度前10的句子,相似度为：')
    for i in range(20):
        j = xre1[i]
        # print(line[j],re2[i])
        print(j + 1, name[j], re2[i])
        outverfile.write(name[j])
        outsinfile.write(str(re2[i]) + '\n')








