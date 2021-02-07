
import java.lang.Thread;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
public class Client {
    public static  String RED = "\u001B[31m"; // ANSI code for colors
    public static  String RESET_COLOR = "\u001B[0m";
    public static  String GREEN = "\u001B[32m";
    public static void main(String[] args) {
        int finished_task=0;
        int unfinished_task=0;
        int Total =0;
        try {

        Socket socket = new Socket("localhost",2772);
        DataOutputStream output_stream = new DataOutputStream(socket.getOutputStream());
        DataInputStream input_stream = new DataInputStream(socket.getInputStream());

        Scanner scanner = new Scanner(System.in);


        loop: while (true) {
            if (Total ==0 & unfinished_task==0 & finished_task==0){
                System.out.print("                        Main Menu\n" +
                        "                        =========\n" +
                        "You have " + RED + unfinished_task +" task " + RESET_COLOR + "\n" + RESET_COLOR +
                        "Choice:\n"+
                        "0-LOAD task\n"+
                        "1-Add new task\n"
                      );
            }
            else if (Total >0 | unfinished_task > 0 | finished_task> 0) {
                System.out.print("                        Main Menu\n" +
                        "                        =========\n" +

                        "You have " + RED + unfinished_task +" task To be Done " + RESET_COLOR + "and " + GREEN + finished_task+" Completed task.\n" + RESET_COLOR +
                        "Choice an option:\n" +
                        "1-Add new task\n" +
                        "2-Show the task.\n" +
                        "3-Edit task. (remove ,mark task as finished ,edit task description ,edit type task)\n" +
                        "4-Save and Exit\n"+
                        "5-Show specific task\n"
                         );
            }
            int choice = scanner.nextInt();
            output_stream.writeInt(choice);
            switch (choice) {
                //////////////////////// ADD TASK //////////////////////////////
                case 1:
                    System.out.println("Enter description: ");
                    scanner.nextLine();// this line is something like "\n" in print
                    // cuz nextInt read only the number and not making new line
                    String description =scanner.nextLine();
                    System.out.println("Enter type: ");

                    String type =scanner.nextLine();
                    output_stream.writeUTF(description);
                    output_stream.writeUTF(type);
                    String added = input_stream.readUTF();
                    System.out.println("\n"+added);
                    Thread.sleep(500);
                    Total +=1;
                    unfinished_task+=1;

                    break;
                    ///////////////SHOW TASKS
                case 2:

                    System.out.print("finished ="+finished_task+"           unfinished=" +unfinished_task + "           Total=" + Total+"\n");
                    String view =input_stream.readUTF();
                    System.out.print("--------              ----------             -----  \n" +"--------------------------------------------------  \n"
                            +"# "+" Description                 " +"Type                           "+"Status \n"
                            +"  "+"           -                 " +"   -                           "+"     -\n"
                            + view);
                    Thread.sleep(1000);
                    break;

                case 3:
                    System.out.print(
                            "1-Removing task\n"+
                            "2-EDITING DESCRIPTION\n"+
                            "3-FINISHING TASK\n"+
                            "4-EDITING TYPE\n"
                    );
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    output_stream.writeInt(choice);
                    switch (choice) {
                        ////////////////////REMOVING TASK
                        case 1 :
                            System.out.print("enter task number you want to remove:");
                            int removed_task = scanner.nextInt();
                            output_stream.writeInt(removed_task);
                            System.out.print("task number " + removed_task + " deleted\n");
                            String finished_Value = input_stream.readUTF();
                            if (Total > 0 & finished_Value.equals("Not Finished")) {
                                Total -= 1;
                                unfinished_task -= 1;
                            } else if (Total > 0 & finished_Value.equals("Finished")) {
                                Total -= 1;
                                finished_task -= 1;

                            }
                            break;
                        //////////////////////////////___EDITING DESCRIPTION___////////////////////////////////
                        case 2:
                            System.out.print("enter task number you want to edit:");
                            int task_num= scanner.nextInt();
                            scanner.nextLine();
                            output_stream.writeInt(task_num);
                            System.out.print("Enter new description:");
                            String new_description = scanner.nextLine();
                            output_stream.writeUTF(new_description);
                            Thread.sleep(500);
                            break ;
                        //////////////////MARK AS FINISHED TASK////////////////////////////////
                        case 3:
                            System.out.print("task number that you finished:");
                            task_num=scanner.nextInt();
                            output_stream.writeInt(task_num);
                            System.out.print("task number "+task_num+" finished\n");
                            finished_task+=1;
                            unfinished_task-=1;
                            Thread.sleep(600);
                            break ;
                        //////////////////EDITING TYPE //////////////////
                        case 4:
                            System.out.print("enter task number you want to edit:");
                            task_num= scanner.nextInt();
                            scanner.nextLine();
                            output_stream.writeInt(task_num);
                            System.out.print("Enter new Type:");
                            String new_type = scanner.nextLine();
                            output_stream.writeUTF(new_type);
                            Thread.sleep(500);
                            break ;
                        }
                        break ;

                    /////////////////////SAVING TASK///////////////
                case 4:
                    break loop;

                ////////////////////LOADING DATA ///////////////////
                case 0:
                    Total= input_stream.readInt();
                    finished_task= input_stream.readInt();
                    unfinished_task= input_stream.readInt();
                    System.out.println("Loading tasks done");

                    break ;
                case 5:
                    String resevied_filtered_task;
                    System.out.println("What type of data u want to filter:");
                    System.out.println("1-Finished\n2-Not finished\n3-Type");
                    choice = scanner.nextInt();
                    if (choice == 1 | choice == 2){
                        output_stream.writeInt(choice);
                        resevied_filtered_task=input_stream.readUTF();
                        System.out.println(resevied_filtered_task);
                    }
                    else if (choice ==3) {
                        output_stream.writeInt(choice);
                        String type_elemnt =input_stream.readUTF();
                        System.out.print(type_elemnt+"\nChoose:");
                        choice = scanner.nextInt();
                        output_stream.writeInt(choice);
                        resevied_filtered_task=input_stream.readUTF();
                        System.out.println(resevied_filtered_task);
                    }
                    break ;
            }
        }
    }
    catch(Exception e){
        System.out.println(e.getMessage());
    }
    }
}
