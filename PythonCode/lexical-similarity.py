from gensim.models import word2vec
import numpy as np

import heapq


class SimilarModel:

    def cos_similar(self, sen_a_vec, sen_b_vec):
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


if __name__ == '__main__':
    model = word2vec.Word2Vec.load('F:\\Apache-test\\hbase-test\\lexical\\w2v_model')
    bert_client = SimilarModel()
    # print(model2['ReturnStatement'])


    # 从候选集condinates 中选出与sentence_a 最相近的句子
    for k in range(67):
        with open('F:\\Apache-test\\hbase-test\\lexical\\oldNames.txt') as tf:
            sentence = tf.readlines()
        sentence_vec = model.wv[str(sentence[k]).replace('\n','')]

        with open('F:\\Apache-test\\hbase-test\\lexical\\' +str(k+1) + '\\methodbodywords.txt') as tf:
            voab = tf.read().split()
        print(voab)

        voab_vec = [0] * len(voab)
        cos_sim = [0] * len(voab)
        for i in range(len(voab)):
            voab_vec[i] = model.wv[voab[i]]
            cos_sim[i] = bert_client.cos_similar(sentence_vec, voab_vec[i])
        # outnounfile = open('C:\\Users\\Administrator\\Desktop\\cassandra-test\\rename\\method1\\noun.txt', 'a')
        # outsinfile = open('C:\\Users\\Administrator\\Desktop\\cassandra-test\\rename\\method1\\nounsin.txt', 'a')
        print('余弦相似度为：', cos_sim)
        re1 = map(cos_sim.index, heapq.nlargest(len(voab), cos_sim))  # 求最大的三个索引    nsmallest与nlargest相反，求最小
        re2 = heapq.nlargest(len(voab), cos_sim)  # 求最大的三个元素
        xre1 = list(re1)

        outnounfile = open('F:\\Apache-test\\hbase-test\\rename\\' + str(k+1) + '\\noun.txt', 'a')
        outsinfile = open('F:\\Apache-test\\hbase-test\\rename\\' + str(k+1) + '\\nounsin.txt', 'a')
        #outsourcenoun = open('F:\\nomulus-test\\rename\\' + str(k+1) + '\\sourcenoun.txt', 'a')

        print('相似度前'+ str(len(voab))+ '的句子,相似度为：')
        for i in range(len(voab)):
            j = xre1[i]
            print(voab[j], re2[i])
            outnounfile.write(voab[j] + '\n')
            outsinfile.write(str(re2[i]) + '\n')
            #outsourcenoun.write(voab[j] + '\n')
        print(k+1)
        print('****************')


