package com.bluedot.domain.algorithm.model;

import com.bluedot.domain.algorithm.AbstractAlgorithm;
import com.bluedot.domain.algorithm.AlgoInvokeException;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author Jason
 * @CreationDate 2023/05/28 - 1:12
 * @Description ：java算法类
 * 约定用户上传的算法文件中的public方法只能是算法的输入，可以有参数重载
 */
public class JavaAlgorithm extends AbstractAlgorithm {
    public static final String SUPPORT_LANGUAGE_JAVA = "java";

    /**
     * 算法的源代码
     */
    private OutputStream sourceCode;
    /**
     * 算法对应的类对象
     */
    private Class<?> clazz;

    @Override
    public Object execute(Object... input) throws AlgoInvokeException {
        Method targetMethod = getTargetMethod(input);

        if (targetMethod == null){
            throw new AlgoInvokeException("can't find the public algo method matched the input parameter");
        }

        try {
            return targetMethod.invoke(clazz.newInstance(), input);
        } catch (IllegalAccessException e) {
            throw new AlgoInvokeException("the algo method is not public:"+targetMethod,e);
        } catch (InvocationTargetException e) {
            throw new AlgoInvokeException("fail to invoke target algo method"+targetMethod,e);
        } catch (InstantiationException e) {
            throw new AlgoInvokeException("fail to instant algo class:"+clazz,e);
        }
    }

    @Override
    public boolean support(Object... input) {
        return getTargetMethod(input) != null;
    }

    /**
     * 根据参数获取对应的公开方法
     * @param input 输入参数
     * @return 公开的目标方法
     */
    private Method getTargetMethod(Object... input){
        Method[] methods = clazz.getMethods();

        for(Method m : methods){
            Class<?>[] parameterTypes = m.getParameterTypes();
            if(input.length != parameterTypes.length){
                continue;
            }
            for(int i = 0; i < input.length; i++){
                parameterTypes[i].isAssignableFrom(input[i].getClass());
            }
            return m;
        }

        return null;
    }

    public OutputStream getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(OutputStream sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected String getLanguage() {
        return SUPPORT_LANGUAGE_JAVA;
    }

    public static class JavaAlgorithmBuilder{
        public JavaAlgorithm build(AbstractAlgorithm algorithm){
            JavaAlgorithm javaAlgorithm = new JavaAlgorithm();

            javaAlgorithm.setAlgoId(algorithm.getAlgoId());
            javaAlgorithm.setAlgoName(algorithm.getAlgoName());
            javaAlgorithm.setCreator(algorithm.getCreator());
            javaAlgorithm.setCreateDate(algorithm.getCreateDate());
            javaAlgorithm.setModifyDate(algorithm.getModifyDate());
            javaAlgorithm.setInfo(algorithm.getInfo());
            javaAlgorithm.setStatus(algorithm.getStatus());

            return javaAlgorithm;
        }
    }
}
