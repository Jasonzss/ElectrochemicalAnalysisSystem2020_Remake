package com.bluedot.domain.file.model;

import com.bluedot.BaseTest;
import com.bluedot.domain.file.FileCreator;
import com.bluedot.domain.file.exception.JavaCodeException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 22:20
 * @Description ：
 */
public class JavaFileTest extends BaseTest {
    JavaFile file1;
    JavaFile file2;
    JavaFile file3;
    File dir;

    FileCreator fileCreator = new FileCreator();

    @Before
    public void newJavaFile() throws FileNotFoundException {
        file1 = new JavaFile("src/test/resources/algo/java/java_javaFile/JavaFile.java");
        try {
            file2 = new JavaFile("D:/Git/gitCodes/ElectrochemicalAnalysisSystem2020_Remake/src/test/resources/algo/java/java_javaFile/JavaFile.java");
        }catch (Exception e){
            //do nothing
            //因为绝对路径各有不同
        }

        file3 = new JavaFile("src/main/resources/algo/java/java_12345678/JavaAlgorithm.java");
        fileCreator.createFile(file3);
    }

    @After
    public void deleteFile() throws InterruptedException {
        file3.deleteOnExit();
        Thread.sleep(1000);
        dir = new File("src/main/resources/algo/java/java_12345678");
        dir.delete();
    }

    @Test
    public void testGetFullClassName() {
        System.out.println(file1.getFullClassName());
    }

    @Test(expected = JavaCodeException.class)
    public void testGetFullClassName_() {
        System.out.println(file3.getFullClassName());
    }
}
