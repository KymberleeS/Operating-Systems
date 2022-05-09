public class Main {
    public static void main(String[] args) {
        // Program Title
        System.out.println("Producer and Consumer Threads Task Simulator");
        System.out.println("**Simulate the Producer-Consumer Problem by choosing the array size and thread speeds**");
        System.out.println("----------------------------------------------------------------------------------------------");

        // creating a new thread array object
        ThreadArray threadArray = new ThreadArray();

        // calling method for user to input parameters such as array size and thread speed
        threadArray.inputParameters();

        System.out.println("\nSimulation Started\n");

        // creating consumer/producer objects that implement runnable and access the same array object
        Producer producer = new Producer(threadArray);
        Consumer consumer = new Consumer(threadArray);

        // creating producer and consumer threads
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        // starting the threads
        producerThread.start();
        consumerThread.start();
    }
}
