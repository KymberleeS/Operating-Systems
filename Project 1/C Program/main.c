#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>

int main() {
    // variables
    char DOSCommandsList[6][5] = {"cd", "dir", "type", "del", "ren", "copy"};

    char command[1028];
    char arg1[1028];
    char arg2[1028];

    char input[1028];

    int index;
    int result;

    // continuously loops DOS Interpreter Program until the user types in "Ctrl-C (case sensitive)"
    while (strcmp(command, "Ctrl-C") != 0) {
        // providing a brief description of the commands for user reference
        printf("DOS Command Interpreter\n\n");
        printf("cd - allows you to change directories\n");
        printf("dir - allows you to list files within the current directory\n");
        printf("type - allows you to view the contents of a file\n");
        printf("del - allows you to delete a file\n");
        printf("ren - allows you to rename a file\n");
        printf("copy - allows you to copy a file from the current location to another location\n\n");

        printf("**NOTE: HIT 'Enter' after typing a command, BEFORE entering command parameters\n\n");

        // prompting the user to enter a command or exit based on the commands given above
        printf("Enter a Command or Type Ctrl-C to Exit: ");
 
        scanf("%s", &command);
        getchar();
        fflush(stdin);

        // handles exiting the program and invalid commands
        if (strcmp(command, "Ctrl-C") == 0) {
             printf("^C\n\n");
             break;
        } else {
            for (int i = 0; i < 6; i++) {
               if (strcmp(command, DOSCommandsList[i]) == 0) {
                   index = i;   
                   break; 
                } else if (strcmp(command, DOSCommandsList[i]) != 0) {
                   index = 6;    
                }    
            }
        } 

        // switch cases to handle DOS command conversions to UNIX
        switch(index) {
           // cd -> cd
           case 0:
               printf("\nEnter Directory Path (format: <arg1>): ");
               fgets(arg1, sizeof(arg1), stdin);       
               printf("\n\n");

               strtok(arg1, "\n");
               result = chdir(arg1); 

               if (result == 0) { 
                  printf("Successfully Changed Directories. Current Path: \n");

                  char currentPath[100];
                  printf("%s\n", getcwd(currentPath, 100));
               } else {
                  printf("No such directory found. Please try again\n\n");
               }

               printf("\n\n");
               break;    
           // dir -> ls    
           case 1:
               printf("\n\n");
               system("ls");
               printf("\n\n");
               break;
           // type -> cat    
           case 2:
               printf("\nEnter File to View (format: <arg1>): ");
               fgets(arg1, sizeof(arg1), stdin);
               printf("\n\n");

               strtok(arg1, "\n");
               strcpy(input, "cat ");

               system(strcat(input, arg1));

               printf("\n\n");
               break;
           // del -> rm    
           case 3:
               printf("\nEnter File to Delete (format: <arg1>): ");
               fgets(arg1, sizeof(arg1), stdin);
               printf("\n");
               fflush(stdin);

               strtok(arg1, "\n");
               strcpy(input, "rm ");

               result = system(strcat(input, arg1));

               if (result == 0) {
                   printf("File was successfully deleted");
               }

               printf("\n\n\n");
               break;
           // ren -> mv    
           case 4:
               printf("\nEnter File to Rename (format: <arg1>): ");
               fgets(arg1, sizeof(arg1), stdin);
               fflush(stdin);

               printf("Enter New File Name (format: <arg2>): ");
               fgets(arg2, sizeof(arg2), stdin);
               printf("\n");
               fflush(stdin);

               strtok(arg1, "\n");
               strtok(arg2, "\n");

               strcpy(input, "mv ");
               strcpy(arg1, strcat(arg1, " "));
               result = system(strcat(input, strcat(arg1, arg2)));

               if (result == 0) {
                   printf("File was successfully renamed");
               }

               printf("\n\n\n");
               break;
           // copy -> cp    
           case 5:
               printf("\nEnter Path of File to Copy (format: <arg1>): ");
               fgets(arg1, sizeof(arg1), stdin);
               fflush(stdin);

               printf("Enter Destination Path (format: <arg2>): ");
               fgets(arg2, sizeof(arg2), stdin);
               printf("\n");
               fflush(stdin);

               strtok(arg1, "\n");
               strtok(arg2, "\n");

               strcpy(input, "cp ");
               strcpy(arg1, strcat(arg1, " "));
               result = system(strcat(input, strcat(arg1, arg2)));

               if (result == 0) {
                   printf("File was successfully copied");
               }

               printf("\n\n\n");
               break;
           // invalid command entered    
           case 6:
               printf("Invalid Command, Please try again\n\n\n");
               break;            
        }
    }
    // exits the program upon breaking from the loop
    return 0;
}
