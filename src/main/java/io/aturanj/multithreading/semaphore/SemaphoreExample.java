package io.aturanj.multithreading.semaphore;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Semaphores --> Restrict the number of threads that can access a resource.
 *
 * Resource: https://mkyong.com/java/java-thread-mutex-and-semaphore-example/
 *
 */
public class SemaphoreExample {

    private static final int SEMAPHORE_LIMIT = 4;

    static Semaphore semaphore = new Semaphore(SEMAPHORE_LIMIT);

    static class MyThread extends Thread {

        String nameOfThread = "";

        MyThread(String name) {
            this.nameOfThread = name;
        }

        @Override
        public void run() {

            try {

                System.out.println(nameOfThread + " acquiring lock...");

                System.out.println(nameOfThread + " available semaphore permits now --> " + semaphore.availablePermits());

                semaphore.acquire(); // in case not permitted, the thread waits for the other threads to be released

                System.out.println(nameOfThread + " got the permit!");

                try {

                    for (int i = 0; i <= SEMAPHORE_LIMIT; i++) {

                        System.out.println(nameOfThread + " is performing operation " + i + " avaible semaphore --> " + semaphore.availablePermits());

                        Thread.sleep(1000); // 1 sec
                    }

                } finally {

                    System.out.println(nameOfThread + " releasing lock...");

                    semaphore.release();

                    System.out.println(nameOfThread + " available semaphore permits now --> " + semaphore.availablePermits());
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(SemaphoreExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {

        MyThread myThread0 = new MyThread("A");
        myThread0.start();

        MyThread myThread1 = new MyThread("B");
        myThread1.start();

        MyThread myThread2 = new MyThread("C");
        myThread2.start();

        MyThread myThread3 = new MyThread("D");
        myThread3.start();

        MyThread myThread4 = new MyThread("E");
        myThread4.start();
    }
}

// OUTPUT (Thread E waits for the others to be released)
//
//  B acquiring lock...
//  D acquiring lock...
//  E acquiring lock...
//  C acquiring lock...
//  A acquiring lock...
//  E available semaphore permits now --> 4
//  A available semaphore permits now --> 4
//  D available semaphore permits now --> 4
//  B available semaphore permits now --> 4
//  C available semaphore permits now --> 4
//  D got the permit!
//  B got the permit!
//  C got the permit!
//  A got the permit!
//  B is performing operation 0 avaible semaphore --> 0
//  A is performing operation 0 avaible semaphore --> 0
//  D is performing operation 0 avaible semaphore --> 0
//  C is performing operation 0 avaible semaphore --> 0
//  D is performing operation 1 avaible semaphore --> 0
//  B is performing operation 1 avaible semaphore --> 0
//  A is performing operation 1 avaible semaphore --> 0
//  C is performing operation 1 avaible semaphore --> 0
//  D is performing operation 2 avaible semaphore --> 0
//  A is performing operation 2 avaible semaphore --> 0
//  B is performing operation 2 avaible semaphore --> 0
//  C is performing operation 2 avaible semaphore --> 0
//  D is performing operation 3 avaible semaphore --> 0
//  A is performing operation 3 avaible semaphore --> 0
//  B is performing operation 3 avaible semaphore --> 0
//  C is performing operation 3 avaible semaphore --> 0
//  C is performing operation 4 avaible semaphore --> 0
//  D is performing operation 4 avaible semaphore --> 0
//  A is performing operation 4 avaible semaphore --> 0
//  B is performing operation 4 avaible semaphore --> 0
//  A releasing lock...
//  B releasing lock...
//  C releasing lock...
//  A available semaphore permits now --> 1
//  B available semaphore permits now --> 1
//  C available semaphore permits now --> 2
//  D releasing lock...
//  D available semaphore permits now --> 3
//  E got the permit!
//  E is performing operation 0 avaible semaphore --> 3
//  E is performing operation 1 avaible semaphore --> 3
//  E is performing operation 2 avaible semaphore --> 3
//  E is performing operation 3 avaible semaphore --> 3
//  E is performing operation 4 avaible semaphore --> 3
//  E releasing lock...
//  E available semaphore permits now --> 4
