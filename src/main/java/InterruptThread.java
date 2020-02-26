public class InterruptThread extends Object implements Runnable {
    public void run() {
        try {
            System.out.println("in run() before - "+Thread.currentThread().isInterrupted());
            System.out.println("in run() - about to work2()");
            work2();
            System.out.println("in run() - back from work2()");
        } catch (InterruptedException x) {
            System.out.println("in run() - interrupted in work2()");
            return;
        }
        System.out.println("in run() - doing stuff after nap");
        System.out.println("in run() - leaving normally");
    }

    public void work2() throws InterruptedException {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("C isInterrupted()=" + Thread.currentThread().isInterrupted());

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName()+"-> exception");
                }
                System.out.println("D isInterrupted()=" + Thread.currentThread().isInterrupted());
            }
        }
    }

    public void work() throws InterruptedException {
        while (true) {
            for (int i = 0; i < 10000; i++) {
                int j = i * 2;
                System.out.println(System.currentTimeMillis()+"i->"+i);
            }
            System.out.println("A isInterrupted()=" + Thread.currentThread().isInterrupted());
            if (Thread.interrupted()) {
                System.out.println("B isInterrupted()=" + Thread.currentThread().isInterrupted());
                throw new InterruptedException();
            }
        }
    }

    public static void main(String[] args) {
        InterruptThread si = new InterruptThread();
        Thread t = new Thread(si);
        t.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException x) {
            System.out.println("异常处理");
        }

        System.out.println("in main() - interrupting other thread");
        t.interrupt();
        System.out.println("in main() - leaving");
    }
}