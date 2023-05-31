package com.bluedot.domain.file.model;

import cn.hutool.core.date.DateUtil;
import com.bluedot.domain.file.AbstractDomainModelFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.net.URI;

/**
 * @Author Jason
 * @CreationDate 2023/05/29 - 18:40
 * @Description ：class字节码文件类，在使用前最好调用下checkUpdateClass()检查编译文件是否是最新的
 * 比较特殊的一个文件，所以会对File的内容进行部分魔改
 *
 * TODO 这个文件在业务中是不允许被修改内容的，只有重新编译才可以修改
 */
public class ClassFile extends AbstractDomainModelFile {
    private static final Logger log = LoggerFactory.getLogger(ClassFile.class);

    private Long compileTime;
    private final JavaFile javaFile;

    public ClassFile(JavaFile javaFile) {
        //class文件和java文件放在同一文件夹下，所以只需要替换下后缀就行了
        super(javaFile.getPath().replace(".java",".class"));
        this.javaFile = javaFile;
        if(exists()){
            //文件存在则读取上次编译时间
            this.compileTime = lastModified();
        }
    }

    /**
     * 禁用File自带的构造器
     */
    public ClassFile(String parent, String child) {
        super(parent,child);
        throw new RuntimeException("constructor has been disabled");
    }

    public ClassFile(File parent, String child) {
        super(parent, child);
        throw new RuntimeException("constructor has been disabled");
    }

    public ClassFile(URI uri) {
        super(uri);
        throw new RuntimeException("constructor has been disabled");
    }

    @Override
    public String getSuffix() {
        return ".class";
    }

    /**
     * 如果java文件发生了变化则重新编译class文件
     */
    public void checkUpdateClass(){
        if (compileTime == null || compileTime < javaFile.lastModified()) {
            //当文件还没有编译或者编译的文件已经过时了，就代表需要重新编译
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int compilerResult = compiler.run(null, null, null, javaFile.getAbsolutePath());
            if (compilerResult > 0){
                compileTime = System.currentTimeMillis();
                log.info(javaFile.getPath()+"has recompile on "+ DateUtil.date(compileTime));
            }
        }
    }
}
