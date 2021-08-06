import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.YamlPrinter;

import java.io.File;
import java.io.FileNotFoundException;
public class ASTtest {
    public static void main(String[] args) throws FileNotFoundException {
        //Parse the code you want to inspect:
        File file=new File("C:\\Users\\Administrator\\Desktop\\Test.java");
        CompilationUnit cu = JavaParser.parse(file);
        // Now comes the inspection code:
        System.out.println(cu);
        // Now comes the inspection code:
        YamlPrinter printer = new YamlPrinter(true);
        System.out.println(printer.output(cu));

    }
}