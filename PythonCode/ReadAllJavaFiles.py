import os
import  sys
import chardet

allpath = []
allname = []
testname = ""

def getallfile(path):
    allfilelist=os.listdir(path)
    # 遍历该文件夹下的所有目录或者文件
    for file in allfilelist:
        filepath=os.path.join(path,file)
        # 如果是文件夹，递归调用函数
        if os.path.isdir(filepath):
            getallfile(filepath)
        # 如果不是文件夹，保存文件路径及文件名
        elif os.path.isfile(filepath):
            testname = file
            if testname.endswith(".java"):
              filepath1 = filepath.replace('/','\\')
              filepath2 = filepath1.replace('\\','\\' +'\\')
              #filepath3 = filepath2.replace(' ','\\')
              allpath.append(filepath2)
              allname.append(file)
    return allpath, allname


if __name__ == "__main__":
    projectname = 'zeppelin'
    rootdir = "C:\\project\\IdentifierStyle\\data\\GitProject\\" + projectname
    file_handle = open('C:\\project\\IdentifierStyle\\data\\JavaFileIndex\\' + projectname +'.txt', mode='w')
    files, names = getallfile(rootdir)
    for file in files:
        print(file)
        file_handle.write(file + '\n')
    print("-------------------------")

#if __name__ == "__main__":
    #file_handle = open('C:\\Users\\Administrator\\Desktop\\Project\\GitProject\\dubbo2.6.9\\JavaFileIndex.txt', #mode='r',encoding='gbk',errors='ignore')
    #lines = file_handle.readlines()
    #with open('C:\\Users\\Administrator\\Desktop\\Project\\GitProject\\dubbo2.6.9\\JavaFile.txt', 'w') as tf:
        #for line in lines:
             #line = line.replace('\n','')
             #print(line)
             #file_handle1 = open(line, mode='r', encoding='gbk',errors='ignore')
            # lines1 = file_handle1.read()
             #tf.write(lines1)

    #for name in names:
        #print(name)
