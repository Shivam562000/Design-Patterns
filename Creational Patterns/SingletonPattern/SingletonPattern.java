package SingletonPattern;

// Double Checked Locking based Java implementation of
// singleton design pattern
import java.io.*;

class Singleton {
    /*
     * We have declared the obj volatile which ensures that multiple threads offer
     * the obj variable correctly when it is being initialized to the Singleton
     * instance. This method drastically reduces the overhead of calling the
     * synchronized method every time.
     */
    private static volatile Singleton obj = null;

    private Singleton() {
        System.out.println("Singleton is Instantiated.");
    }

    public static Singleton getInstance() {
        if (obj == null) {
            // To make thread safe
            synchronized (Singleton.class) {
                // check again as multiple threads
                // can reach above step
                if (obj == null)
                    obj = new Singleton();
            }
        }
        return obj;
    }

    public static void doSomething() {
        System.out.println("Somethong is Done.");
    }
}

class SingletonPattern {
    public static void main(String[] args) {
        Singleton.getInstance().doSomething();
    }
}
