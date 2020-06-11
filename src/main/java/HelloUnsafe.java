import sun.misc.Unsafe;

public class HelloUnsafe {
    static class M{
        private M(){}

        int m = 0;
    }

    public static void main(String[] args) throws InstantiationException {
        Unsafe unsafe = Unsafe.getUnsafe();
        M o = (M)unsafe.allocateInstance(M.class);
        (o).m = 10;
        System.out.println(o.m);
    }
}
