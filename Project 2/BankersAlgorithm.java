// imports
import java.util.ArrayList;
import java.util.Arrays;

// Algorithm Class
public class BankersAlgorithm {
    // default constructor
    public BankersAlgorithm() {
        // no parameters
    }

    // method to calculate the Need matrix
    public void calculateNeedMatrix(int processes, int resourceTypes, int[][] need, int[][] allocation, int[][] max) {
        for(int i = 0; i < processes; i++) {
            for(int j = 0; j < resourceTypes; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
    }

    // method to execute the Safety Algorithm
    public void executeSafetyAlgorithm(int processes, int resourceTypes, int[][] need, int[][] allocation, int[] available, ArrayList<String> safetySequence, String response) {
        ArrayList<Integer> tempAvailable = new ArrayList<>();
        ArrayList<Integer> initialAvailable = new ArrayList<>();
        boolean[] processFinished = new boolean[processes];
        int counter = 0;

        Arrays.fill(processFinished, Boolean.FALSE);

        for (int i = 0; i < available.length; i++) {
            initialAvailable.add(available[i]);
        }

        // loops back to ensure all processes are accounted for
        while (counter != processFinished.length) {
            for (int i = 0; i < processes; i++) {
                for (int j = 0; j < resourceTypes; j++) {
                    if ((!processFinished[i]) && (need[i][j] <= available[j])) {
                        tempAvailable.add(available[j] + allocation[i][j]);
                    }
                }
                updateAvailableAndSafety(tempAvailable, safetySequence, available, processFinished, i);
            }
            counter++;
        }
        systemState(counter, safetySequence, response);

        System.out.println("\nNeed Matrix:");
        for (int i = 0; i < need.length; i++) {
            System.out.print("[");
            for (int j = 0; j < need[i].length; j++) {
                System.out.print(" " + need[i][j] + " ");
            }
            System.out.print("]");
            System.out.println();
        }

        System.out.println("\nAvailable Array:");
        System.out.print("[");
        for (int i = 0; i < initialAvailable.size(); i++) {
            System.out.print(" " + initialAvailable.get(i) + " ");
        }
        System.out.print("]");

        System.out.println();
    }

    // helper method to update Available array and Safety Sequence array
    private void updateAvailableAndSafety(ArrayList<Integer> tempAvailable, ArrayList<String> safetySequence, int[] available, boolean[] processFinished, int process) {
        if (tempAvailable.size() == available.length) {
            for (int i = 0; i < available.length; i++) {
                available[i] = tempAvailable.get(i);
            }
            safetySequence.add("P" + process);
            processFinished[process] = true;
        }
        tempAvailable.clear();
    }

    // method that runs the resource request algorithm
    public void resourceRequestAlgorithm(int resourceTypes, int updateProcess, int[] resourceRequest, int[] available, int[][] need, int[][] allocation) {
        // step 1: if the resource request value is larger than need, request is denied
        for (int i = 0; i < resourceTypes; i++) {
            if (resourceRequest[i] > need[updateProcess][i]) {
                System.out.println("\nError: Cannot immediately grant request. Request exceeds Need.");
                System.exit(0);
            }
        }

        // step 2: if the resource request value is larger than available, request is denied
        for (int i = 0; i < resourceTypes; i++) {
            if (resourceRequest[i] > available[i]) {
                System.out.println("\nError: Cannot immediately grant request. Request exceeds Available resources");
                System.exit(0);
            }
        }

        // step 3: data structures are updated and safety algorithm is called to check if the request leaves the system in a safe state or not
        System.out.println("\nUpdating Data Structures . . .");
        for (int i = 0; i < resourceTypes; i++) {
            available[i] -= resourceRequest[i];
            allocation[updateProcess][i] += resourceRequest[i];
            need[updateProcess][i] -= resourceRequest[i];
        }
    }

    // method that determines if the state of the system is safe or unsafe. prints safety sequence if in a safe state
    private void systemState(int counter, ArrayList<String> safetySequence, String response) {
        if (counter == safetySequence.size()) {
            if (response.equals("Y") || response.equals("y")) {
                System.out.println("\nSystem left in a safe state. The request can be immediately granted");
                System.out.print("Safety Sequence: ");
            } else {
                System.out.println("\nThe system was left in a safe state");
                System.out.print("Safety Sequence: ");
            }

            for (int i = 0; i < safetySequence.size(); i++) {
                System.out.print(safetySequence.get(i) + " ");
            }
            System.out.print("\n");
        } else {
            if (response.equals("Y") || response.equals("y")) {
                System.out.println("\nSystem not left in a safe state. The request was denied");
            } else {
                System.out.println("\nThe system was not left in a safe state");
            }
        }
    }
}
