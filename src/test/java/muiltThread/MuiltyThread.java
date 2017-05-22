package muiltThread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ellen on 2017/5/21.
 */
public class MuiltyThread {
    static int k = 0;
    static int i1 = 0;
    static AtomicInteger j = new AtomicInteger(0);

    public static void testTherad() {
        long start = System.currentTimeMillis();
        int z = 0;
        StringBuilder sb = new StringBuilder();
        ExecutorService service = Executors.newFixedThreadPool(10);
        while (z < 10) {
            int t = z;
            service.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100000; i++) {
                        sb.append(i);
//                        System.out.println(t + ": " + j);
                    }
                }
            });
            z++;
        }
        service.shutdown();
        boolean shutdown = service.isShutdown();
        if (shutdown) {
            long end = System.currentTimeMillis();
            System.out.println(end - start);
            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args) {
        testTherad();
    }


    public static void singleThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("s");
            }
        }).start();


    }

}
