public class Producer implements Runnable {
    // variables
    ThreadArray threadArray;

    // constructor
    Producer(ThreadArray threadArray) {
        this.threadArray = threadArray;
    }

    // run method that executes the code for calling produceTasks() method
    public void run() {
        threadArray.produceTasks();
    }
}
