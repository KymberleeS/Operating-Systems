// imports
import java.util.ArrayList;
import java.util.Scanner;

public class ThreadArray {
    // object variables
    ArrayList<Integer> taskArray = new ArrayList<>();
    Scanner userInput = new Scanner(System.in);

    // variables
    boolean simulationRunning = true;

    int producedTask = 1;
    int consumedTask = 1;

    int taskArraySize = 0;
    double producerThreadSpeed = 0;
    double consumerThreadSpeed = 0;

    // method that takes input from user concerning size of array and speed of the threads
    public void inputParameters() {
        System.out.print("Please enter a size for the Task Thread Array: ");
        taskArraySize = userInput.nextInt();

        System.out.print("Please enter the producer thread speed (in seconds): ");
        producerThreadSpeed = userInput.nextDouble();

        System.out.print("Please enter the consumer thread speed (in seconds): ");
        consumerThreadSpeed = userInput.nextDouble();
    }

    // method that adds tasks to the array while the simulation is running
    public void produceTasks() {
        while (simulationRunning) {
            // adding tasks to the array and notifying the other thread afterwards
            synchronized (this) {
                System.out.println("Producer has produced task number " + producedTask);
                taskArray.add(producedTask++);
                notify();
            }

            // rate at which tasks are being added to the array
            try {
                Thread.sleep(Double.valueOf(producerThreadSpeed * 1000).longValue());
            } catch(InterruptedException e) {
                System.out.println("Error occurred. Please try again");
            }

            // if the array is full, print this message
            if (taskArray.size() == taskArraySize) {
                System.out.println("\nThe task Array has become full. Waiting for the Consumer to consume tasks.\n");
            }

            // while the array is full, wait until consumer has removed tasks from the array
            while(taskArray.size() == taskArraySize) {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch(InterruptedException e) {
                    System.out.println("Error occurred. Please try again.");
                }
            }
        }
    }

    // method that removes tasks to the array while the simulation is running
    public void consumeTasks() {
        while (simulationRunning) {
            // removing tasks to the array and notifying the other thread afterwards
            synchronized (this) {
                consumedTask = taskArray.remove(0);
                System.out.println("Consumer has consumed task number " + consumedTask);
                notify();
            }

            // rate at which tasks are being removed from the array
            try {
                Thread.sleep(Double.valueOf(consumerThreadSpeed * 1000).longValue());
            } catch(InterruptedException e) {
                System.out.println("Error occurred. Please try again");
            }

            // if the array is empty, print this message
            if (taskArray.size() == 0) {
                System.out.println("\nThe task Array has become empty. Waiting for the Producer to produce tasks.\n");
            }

            // while the array is empty, wait until producer has added tasks to the array
            while(taskArray.size() == 0) {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch(InterruptedException e) {
                    System.out.println("Error occurred. Please try again.");
                }
            }
        }
        // closing user input
        userInput.close();
    }
}
