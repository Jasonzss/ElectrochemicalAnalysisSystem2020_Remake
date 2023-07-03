package com.bluedot.domain.file.model;

import cn.hutool.core.date.DateUtil;
import com.bluedot.domain.file.AbstractDomainFile;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;

/**
 * @Author Jason
 * @CreationDate 2023/05/29 - 18:40
 * @Description ：class字节码文件类，在使用前最好调用下checkUpdateClass()检查编译文件是否是最新的
 * 比较特殊的一个文件，所以会对File的内容进行部分魔改
 * 此文件的id格式为【class_12345678】
 *
 * TODO 这个文件在业务中是不允许被修改内容的，只有重新编译才可以修改
 */
public class ClassFile extends AbstractDomainFile {
    private static final Logger log = LoggerFactory.getLogger(ClassFile.class);

    private Long compileTime;

    @NotNull
    private final File javaFile;
    @NotNull
    private final String javaId;



    public ClassFile(String javaId, File file) {
        //class文件和java文件的id只有前缀不一样
        super(javaId.replace("java","class"));
        this.javaFile = file;
        this.javaId = javaId;
        setFile(new File(generatePath()+generateName()));
        if(file.exists()){
            //文件存在则进行编译
            checkUpdateClassFile();
        }
    }

    @Override
    protected String generateName() {
        return "JavaAlgorithm.class";
    }

    @Override
    protected String generatePath() {
        return "src/main/resources/algo/java/"+javaId+"/";
    }

    /**
     * 如果java文件发生了变化则重新编译class文件
     */
    public void checkUpdateClassFile(){
        if (compileTime == null || compileTime < javaFile.lastModified()) {
            //当文件还没有编译或者编译的文件已经过时了，就代表需要重新编译
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int compilerResult = compiler.run(null, null, null, javaFile.getAbsolutePath());
            if (compilerResult > 0){
                compileTime = System.currentTimeMillis();
                log.info(javaFile.getPath()+" has recompile on "+ DateUtil.date(compileTime));
            }
            if(compilerResult == 0 && getFile().exists()){
                compileTime = System.currentTimeMillis();
            }
        }
    }
}
