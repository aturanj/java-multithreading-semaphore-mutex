package io.aturanj.multithreading.mutex;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Mutex --> Mutex is the Semaphore with an access count of 1
 *
 * Resource: https:// mkyong.com/java/java-thread-mutex-and-semaphore-example/
 *
 */
public class MutexExample {

    private static final int SEMAPHORE_LIMIT = 1;

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

                        Thread.sleep(1000); //   1 sec
                    }

                } finally {

                    System.out.println(nameOfThread + " releasing lock...");

                    semaphore.release();

                    System.out.println(nameOfThread + " available semaphore permits now --> " + semaphore.availablePermits());
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(MutexExample.class.getName()).log(Level.SEVERE, null, ex);
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

//  OUTPUT (Only 1 thread can acquire the resource at the same time)
//
//  A acquiring lock...
//  B acquiring lock...
//  E acquiring lock...
//  D acquiring lock...
//  D available semaphore permits now --> 1
//  C available semaphore permits now --> 1
//  E available semaphore permits now --> 1
//  A available semaphore permits now --> 1
//  B available semaphore permits now --> 1
//  D got the permit!
//  D is performing operation 0 avaible semaphore --> 0
//  D is performing operation 1 avaible semaphore --> 0
//  D releasing lock...
//  E got the permit!
//  E is performing operation 0 avaible semaphore --> 0
//  D available semaphore permits now --> 1
//  E is performing operation 1 avaible semaphore --> 0
//  E releasing lock...
//  E available semaphore permits now --> 1
//  C got the permit!
//  C is performing operation 0 avaible semaphore --> 0
//  C is performing operation 1 avaible semaphore --> 0
//  C releasing lock...
//  C available semaphore permits now --> 1
//  A got the permit!
//  A is performing operation 0 avaible semaphore --> 0
//  A is performing operation 1 avaible semaphore --> 0
//  A releasing lock...
//  A available semaphore permits now --> 1
//  B got the permit!
//  B is performing operation 0 avaible semaphore --> 0
//  B is performing operation 1 avaible semaphore --> 0
//  B releasing lock...
//  B available semaphore permits now --> 1
