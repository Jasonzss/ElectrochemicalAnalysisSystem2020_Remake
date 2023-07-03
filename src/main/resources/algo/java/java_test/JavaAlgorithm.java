package algo.java.java_test;

public class JavaAlgorithm{
    public String algorithmMethodName = "helloParam";

    public JavaAlgorithm() {
    }

    public void hello() {
        System.out.println("Hello Class");
    }

    public int helloReturn(){
        return 5;
    }

    public void helloParam(int a){
        System.out.println("this is "+a);
    }

    public void helloNewMethod(){
        System.out.println("this is an new method");
    }
}