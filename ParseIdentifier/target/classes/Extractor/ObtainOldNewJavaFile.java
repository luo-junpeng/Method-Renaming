package classes.Extractor;

import com.csvreader.CsvReader;
import detectId.Trace.SyncPipe;

import java.io.*;

public class ObtainOldNewJavaFile {
    public static void main(String[] args) throws Exception {
        //传入项目名称
        ProjectCommit("dubbo");
    }

    private static void ProjectCommit(String project) throws Exception {
        String projectpath="C:\\project\\IdentifierStyle\\data\\GitProject\\"+project;
        String csvPath="C:\\project\\IdentifierStyle\\log\\dump\\"+project+".csv";
        String LogOutput = "C:\\Users\\Administrator\\Desktop\\log.txt";
        System.out.println(ExecuteCommand(projectpath, "git rev-parse HEAD ",LogOutput));
        String finalCom=readCurCommit("C:\\Users\\Administrator\\Desktop\\log.txt");

        try {
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(csvPath);
            while (csvReader.readRecord()){
                // 读一整行
                // System.out.println(csvReader.getRawRecord());
                // 打印标识符变化情况
                System.out.println(csvReader.get(3));
                //生成对比的两个文件
                String location=csvReader.get(2);
                String his=csvReader.get(3);
                String[] hisId=his.split("<-");
                //第一个版本比对

                //String curCom=finalCom;
//                String oldCom=csvReader.get(4);


                for(int i=0;i<hisId.length-1;i++){
                    String change=hisId[i]+"<-"+hisId[i+1];
                    System.out.println(change);
//                    System.out.println(curCom+","+oldCom);
                    String curCom=csvReader.get(4+i);
                    //生成两个文件传入ProjectDiff
                    genFile(location,curCom,change);
                    ProjectDiff(change);
                    //删除新旧文件夹中的对比文件
                    curCom=csvReader.get(4+(i+1));


                }
                //结束后把old文件夹和new文件夹中的内容清空



            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void ProjectDiff(String change) {
        //读取旧文件，新文件，对比
        //读取变化的标识符
        String changeId=change;
    }

    private static void genFile(String location, String curCom,String change) throws Exception {
        change=change.replace("<-","_");
        String[] data=location.split("\\\\");
        String fileName=data[data.length-1];

        //newCom读取当前的代码
        generateNew(location,curCom,fileName,"C:\\project\\IdentifierStyle\\data\\VersionDB\\ver_new\\"+curCom+"_"+change+"_"+fileName);

        //oldcam读取记录代码的上一个版本
        generateOld(location,fileName,"C:\\project\\IdentifierStyle\\data\\VersionDB\\ver_old\\"+curCom+"_"+change+"_"+fileName);

    }

    private static void generateOld(String location,String fileName, String output) throws Exception {
        int index=location.length()-fileName.length();
        String proj=location.substring(0,index-2);

        //先回退到版本old,再回退到上一个版本
//        ExecuteCommand(proj,"git reset --hard "+oldCom,"C:\\Users\\delll\\IdeaProjects\\DatasetCreate\\src\\main\\resources\\result.txt");
//        copyTo(location,output);
        ExecuteCommand(proj,"git reset --hard \"HEAD^\"","C:\\Users\\Administrator\\Desktop\\result.txt");
        copyTo(location,output);

    }

    private static void generateNew(String location, String curCom,String fileName,String output) throws Exception {
        int index=location.length()-fileName.length();
        String proj=location.substring(0,index-2);
        ExecuteCommand(proj,"git reset --hard "+curCom,"C:\\Users\\Administrator\\Desktop\\result.txt");
        copyTo(location,output);
    }

    private static void copyTo(String location, String output) throws IOException {
        //读取
        File in=new File(location);
        //写入
        File out=new File(output);

        BufferedReader br = new BufferedReader(new FileReader(in));
        BufferedWriter bw=new BufferedWriter(new FileWriter(out,true));

        String line = "";
        while ((line = br.readLine()) != null) {
            bw.write(line);
            bw.newLine();
        }

        br.close();
        bw.close();
    }

    private static String readCurCommit(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = br.readLine();

        br.close();
        return line;
    }

    public static String ExecuteCommand(String projectdir,String cmd,String output) throws Exception
    {
        String final_com=null;
        System.out.println("projectdir:"+projectdir);

        String[] command =
                {
                        "cmd",
                };
        Process p = Runtime.getRuntime().exec(command);
        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
        new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
        PrintWriter stdin = new PrintWriter(p.getOutputStream());
        stdin.println("c:");
        stdin.println("cd "+projectdir);

        stdin.println(cmd + " > " + output);



        stdin.close();
        int returnCode = p.waitFor();
        System.out.println("Return code = " + returnCode);

        try (FileReader reader = new FileReader(output);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                //System.out.println(line);
                final_com=line;
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return final_com;
    }
}
