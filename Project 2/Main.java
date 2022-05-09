// imports
import java.util.ArrayList;
import java.util.Scanner;

// Driver Class
public class Main {
    public static void main(String[] args) {
        // description
        System.out.println("Banker's Algorithm Simulation");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Simulator for deadlock prevention");
        System.out.println("1. Values entered starting from Process 1, Resource 1, going left to right. (*NOTE: Useful to have " +
                           "the Allocation matrix, Max matrix, and Available written ahead of time)");
        System.out.println("2. Please enter only positive values");
        System.out.println("3. Please ensure that values entered in the Max matrix is larger than/equal to values " +
                           "entered in the Allocation matrix\n");

        // creating scanner object to take in user input
        Scanner userInput = new Scanner(System.in);

        // user inputs number of processes
        System.out.print("Enter the number of processes: ");
        int processes = userInput.nextInt();

        // user inputs number of resource types
        System.out.print("Enter the number of resource types: ");
        int resourceTypes = userInput.nextInt();

        // initializing Banker's Algorithm Data Structures
        int[][] allocation = new int[processes][resourceTypes];
        int[][] max = new int[processes][resourceTypes];
        int[][] need = new int[processes][resourceTypes];
        int[] available = new int[resourceTypes];
        ArrayList<String> safetySequence = new ArrayList<>();

        System.out.print("\n");

        // filling in Allocation matrix
        for (int i = 0; i < processes; i++) {
            for (int j = 0; j < resourceTypes; j++) {
                System.out.print("Enter Allocation Value for Process " + i + ", Resource Type " + j + ": ");
                allocation[i][j] = userInput.nextInt();
            }
        }
        System.out.print("\n");

        // filling in Max matrix
        for (int i = 0; i < processes; i++) {
            for( int j = 0; j < resourceTypes; j++) {
                System.out.print("Enter Max Value for Process " + i + ", Resource Type " + j + ": ");
                max[i][j] = userInput.nextInt();
            }
        }
        System.out.print("\n");

        // filling in available array
        for (int i = 0; i < resourceTypes; i++) {
            System.out.print("Enter Available Value for Resource Type " + i + ": ");
            available[i] = userInput.nextInt();
        }

        // clearing scanner buffer
        userInput.nextLine();

        // creating BankersAlgorithm object
        BankersAlgorithm bankersAlgorithm = new BankersAlgorithm();

        // calling method to calculate and fill Need matrix
        System.out.println("\nCalculating Need matrix values . . .");
        bankersAlgorithm.calculateNeedMatrix(processes, resourceTypes, need, allocation, max);

        // asking user if they would like make a resource request
        System.out.print("\nWould you like to make a Resource Request? (Y/N): ");
        String response = userInput.nextLine();

        // resource request variables
        int updateProcess;
        int[] resourceRequest = new int[resourceTypes];

        // calling resource request algorithm if user's response is yes
        if (response.equals("Y") || response.equals("y")) {
            // asking user which process they would like to make a request for
            System.out.print("\nFor which Process would you like to make a request? (Choose from 0 - " + (processes - 1) + "): ");
            updateProcess = userInput.nextInt();

            // catches invalid processes that exist outside the available range
            while (updateProcess > processes - 1 || updateProcess < 0) {
                System.out.print("Please enter a valid Process. (Choose from 0 - " + (processes - 1) + "): ");
                updateProcess = userInput.nextInt();
            }

            System.out.print("\n");

            // prompts the user to enter the new resource request values
            for (int i = 0; i < resourceTypes; i++) {
                System.out.print("Enter Resource Request value " + i + " for P" + updateProcess + ": ");
                resourceRequest[i] = userInput.nextInt();
            }

            // calling method to execute resource request algorithm
            bankersAlgorithm.resourceRequestAlgorithm(resourceTypes, updateProcess, resourceRequest, available, need, allocation);
        }

        // calling method to execute safety algorithm
        System.out.println("\nExecuting the Safety Algorithm . . .");
        bankersAlgorithm.executeSafetyAlgorithm(processes, resourceTypes, need, allocation, available, safetySequence, response);

        // closing Scanner
        userInput.close();
    }
}
