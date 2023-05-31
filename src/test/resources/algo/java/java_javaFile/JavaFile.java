public class JavaFile{
    public JavaFile() {
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

    public void helloException(){
        int a = 1/0;
    }

    public void helloNewMethod(){
        System.out.println("this is an new method");
    }
}