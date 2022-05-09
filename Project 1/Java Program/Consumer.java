public class Consumer implements Runnable {
    // variables
    ThreadArray threadArray;

    // constructor
    Consumer(ThreadArray threadArray) {
        this.threadArray = threadArray;
    }

    // run method that executes the code for calling consumeTasks() method
    public void run() {
        threadArray.consumeTasks();
    }
}
