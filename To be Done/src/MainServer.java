import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class MainServer {
    public static void main(String[] args) {

        try {
            int port = 2772;

            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Waiting for connection...");
            Socket connectionSocket = serverSocket.accept();
            System.out.println("connected");
            DataInputStream input = new DataInputStream(connectionSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(connectionSocket.getOutputStream());
            Method Methods = new Method();
            loop: while (true) {
                int selectService = input.readInt();

                String Not_Finished_For_Description = null;
                switch (selectService) {
                    ////////////////////////ADDING TASK////////////////
                    case 1:
                        System.out.println("Add Task");
                        String description = input.readUTF();
                        String type = input.readUTF();
                        Data newTask = new Data(description, type);
                        Methods.AddTask(newTask);
                        outputStream.writeUTF("Successfully added");
                        break;
                        ////////////////////DISPLAY TASKS///////////////////////
                    case 2:
                        System.out.println("Display Tasks");
                        String Tasks = Methods.DisplayTasks();
                        outputStream.writeUTF(Tasks);
                        break;
                    ////////////////////EDIT TASK/////////////////
                    case 3:
                        selectService = input.readInt();
                        switch (selectService){
                            case 1:
                                System.out.println("Remove Task");
                                int removed_task = input.readInt()-1;
                                String finished_Value= Methods.Return_1_Task_Elment(removed_task,"Finished");
                                Methods.RemoveData(removed_task);
                                outputStream.writeUTF(finished_Value);
                                break;
                            /////////////////////EDITING DESCRIPTION////////////////////
                            case 2:
                                System.out.println("Edit description");

                                //outputStream.writeUTF(task);  // عرض التاسك لليوزر
                                int Index = input.readInt() - 1;    // ناخد رقم التاسك اللي محتاجه تعديل
                                String UpdatedData = input.readUTF(); // الوصف الجديد للتاسك
                                String Not_Edited_For_Type = Methods.Return_1_Task_Elment(Index, "type");
                                Data NewTask = new Data(UpdatedData, Not_Edited_For_Type);
                                //Methods.AddTask(NewTask);
                                Methods.EditDescription(Index, NewTask);
                                break;
                            ////////////////////// FINISHING TASK //////////////////
                            case 3:
                                System.out.println("FINISHING TASK");
                                int index = input.readInt() - 1;
                                String Not_Edited_For_type = Methods.Return_1_Task_Elment(index, "type");
                                Not_Finished_For_Description = Methods.Return_1_Task_Elment(index, "description");
                                Data task = new Data(Not_Finished_For_Description, Not_Edited_For_type);
                                task.set_Finished();
                                Methods.finished(index, task);
                                break;
                            ////////////////////////EDITING TYPE/////////////////////////////
                            case 4:
                                System.out.println("Edit Type");
                                index = input.readInt() - 1;
                                String UpdatedType = input.readUTF();
                                Not_Finished_For_Description = Methods.Return_1_Task_Elment(index, "description");

                                Data newtask = new Data(Not_Finished_For_Description, UpdatedType);
                                Methods.EditType(index,newtask);

                                break ;
                        }
                        break ;
                        /////////////////////SAVING TASK///////////////
                    case 4:
                        System.out.println("SAVING TASK");
                        Methods.savaData(Methods.TaskList.size());
                        break loop;
                    ////////////////////LOADING DATA ///////////////////
                    case 0:
                        System.out.println("LOADING TASK");
                        int[] TaskCounter= Methods.loadData();
                        outputStream.writeInt(TaskCounter[0]);
                        outputStream.writeInt(TaskCounter[1]);
                        outputStream.writeInt(TaskCounter[2]);
                        break ;
                    ////////////////////FILTER TASK ///////////////////
                    case 5:
                        System.out.println("FILTER TASK:");
                        StringBuilder type_element = new StringBuilder();
                        int index = input.readInt();
                        /////////////////SHOW FINISHED TASK ONLY///////////////////
                        if (index == 1 ) {
                            System.out.println("            Finished");
                            for (index = 0;index <Methods.TaskList.size();index++){
                                if(Methods.TaskList.get(index).getFinished().equals("Finished")){
                                type_element.append(Methods.filter_Task_and_Display("Finished",null)).append("\n");
                                break ;
                            }
                                }
                            outputStream.writeUTF(type_element.toString());
                        }

                        /////////////////SHOW NOT FINISHED TASK ONLY///////////////////

                        else if (index == 2 ) {
                            System.out.println("            Not Finished");
                            for (index = 0;index <Methods.TaskList.size();index++){
                                if(Methods.TaskList.get(index).getFinished().equals("Not Finished")){
                                    type_element.append(Methods.filter_Task_and_Display("Not Finished",null)).append("\n");
                                    break ;
                                }
                            }

                            outputStream.writeUTF(type_element.toString());
                        }
                        /////////////////SHOW TASK TYPE ONLY///////////////////
                        else if (index==3){
                            ArrayList<String> removing_repeated_element_In_Type = new ArrayList();
                            System.out.println("            Type");
                            for (index = 0;index <Methods.TaskList.size();index++) { // adding type value to arraylist
                                removing_repeated_element_In_Type.add(index, Methods.Return_1_Task_Elment(index, "type"));
                            }
                            // removing repeated element
                            for (index=0;index<removing_repeated_element_In_Type.size();index++){
                                for (int j=index+1;j<removing_repeated_element_In_Type.size();j++){
                                    if(removing_repeated_element_In_Type.get(index).equals(removing_repeated_element_In_Type.get(j))){
                                        removing_repeated_element_In_Type.remove(index);
                                    }
                                }
                            }
                            if(removing_repeated_element_In_Type.get(0).equals(removing_repeated_element_In_Type.get(1))){// check if there more repeated element
                                removing_repeated_element_In_Type.remove(0);
                            }
                            for (index = 0;index <removing_repeated_element_In_Type.size();index++) {//adding type element to String to sent it to user
                                type_element.append(index+1).append("-").append(removing_repeated_element_In_Type.get(index)).append("\n");
                            }
                            outputStream.writeUTF(type_element.toString());//sending type element after removing repeated element
                            index =  input.readInt();// taking number of type element that user want to filter
                            String filtered_tasks=Methods.filter_Task_and_Display("type", removing_repeated_element_In_Type.get(index - 1));//starting filter them
                            outputStream.writeUTF(filtered_tasks);//sending type element to user after cleaning it
                        }
                        break ;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}