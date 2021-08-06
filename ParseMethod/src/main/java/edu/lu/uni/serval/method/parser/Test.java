package edu.lu.uni.serval.method.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ljp
 * @Time: 2021-04-12 19:51
 */
public class Test {
    public static void main(String[] args) throws IOException {
        MethodParser parser = new MethodParser();

        List<String> list = new ArrayList<String>();
        list.add("F:\\Apache-test\\jmeter-test-non\\VersionDB\\ver_new\\javaFile");

        String outpath = "F:\\Apache-test\\jmeter-test-non\\VersionDB\\ver_new\\token\\";

        parser.parseProjects(list,outpath);
    }
}
