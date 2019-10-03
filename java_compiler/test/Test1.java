package test;

// import structure.*;

public class Test1 {
    public static class A {
        // 垃圾 Java
        public static void main(String[] args) {
            System.out.println("dddd");
        }
    }
    
    public static void main(String[] args) {
        A.main(args);
    }

    public <T> T createInstance(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }
}
