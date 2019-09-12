package test;
import structure.*;

public class Test1 {
    public static void main(String[] args) {
        LocTree t = new LocTree();

        // Class<Tree> C = ;
        // Class<LocTree> C = LocTree.class;
        // C a = C.newInstance();

        Class<? extends FakeTree> c1[] = new Class[3];

        c1[0] = FakeTree.class;

        c1[1] = Tree.class;

        c1[2]= Tree.class;
    }

    public <T> T createInstance(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }
}
