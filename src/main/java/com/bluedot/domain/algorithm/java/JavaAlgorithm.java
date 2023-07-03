package com.bluedot.domain.algorithm.java;

import com.bluedot.domain.algorithm.AbstractAlgorithm;
import com.bluedot.domain.algorithm.exception.AlgoInvokeException;
import com.bluedot.domain.algorithm.exception.AlgorithmException;
import com.bluedot.infrastructure.exception.CommonErrorCode;
import com.bluedot.infrastructure.utils.PrimitiveUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Jason
 * @CreationDate 2023/05/28 - 1:12
 * @Description ：java算法类
 *
 */
public class JavaAlgorithm extends AbstractAlgorithm {
    public static final String SUPPORT_LANGUAGE_JAVA = "java";

    /**
     * 算法对应的类对象
     */
    private Object target;

    private Long fileUpdateTimeStamp;

    @Override
    public Object execute(Object... input) throws AlgoInvokeException {
        try {
            if(target == null){
                throw new AlgoInvokeException(CommonErrorCode.E_3018);
            }
            Method targetMethod = getTargetMethod(input);

            if(targetMethod == null){
                throw new AlgoInvokeException(CommonErrorCode.E_3007);
            }
            return targetMethod.invoke(target, input);
        } catch (IllegalAccessException e) {
            throw new AlgoInvokeException(e, CommonErrorCode.E_3008);
        } catch (InvocationTargetException e) {
            throw new AlgoInvokeException(e, CommonErrorCode.E_3009);
        }
    }

    /**
     * 旧方案：根据参数获取对应的公开方法，如果用户的类里编写了多个参数相同的公开方法，返回的可能是他们其中随机一个
     * 新方案：在算法文件中的存在一个公开字段，该字段为目标算法的对应方法的方法名称。根据名称和入参即可确定调用的方法
     * @param input 输入参数
     * @return 公开的目标方法
     * TODO 参数判断不应该是用户该去操心的事情，应该让用户只是输入字符串，而后端尝试将这些字符串转换为目标类型数据
     */
    private Method getTargetMethod(Object... input){
        try {
            String algorithmMethodName = String.valueOf(target.getClass().getField("algorithmMethodName").get(target));
            Method[] methods = target.getClass().getMethods();
            for(Method method : methods){
                if(method.getName().equals(algorithmMethodName)){
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if(input.length == parameterTypes.length){
                        for(int i = 0; i < input.length; i++){
                            if(!PrimitiveUtil.isSameType(input[i], parameterTypes[i])){
                                break;
                            }
                        }
                        //所有参数类型都一致
                        return method;
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            throw new AlgorithmException(CommonErrorCode.E_3014);
        } catch (IllegalAccessException e) {
            throw new AlgorithmException(CommonErrorCode.E_3015);
        }

        return  null;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Long getFileUpdateTimeStamp() {
        return fileUpdateTimeStamp;
    }

    public void setFileUpdateTimeStamp(Long fileUpdateTimeStamp) {
        this.fileUpdateTimeStamp = fileUpdateTimeStamp;
    }

    public void destroy(){
        target = null;
    }

    @Override
    protected String getLanguage() {
        return SUPPORT_LANGUAGE_JAVA;
    }
}
