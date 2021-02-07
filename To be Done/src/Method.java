import java.io.*;
import java.util.ArrayList;
class Method {
    ArrayList<Data> TaskList = new ArrayList<>();

    /////////////////////////////// DISPLAYING TASKS //////////////////////////////////
    public String DisplayTasks() {
        StringBuilder view = new StringBuilder();
        for (int i = 0; i < TaskList.size(); i++) {
            if (TaskList.get(i).getFinished().equals("Not Finished"))
                view.append(i + 1).append("-").append(TaskList.get(i).get_Description()).append("                       ").append(TaskList.get(i).get_Type()).append("                          ").append(" Not Finished\n");

            if (TaskList.get(i).getFinished().equals("Finished"))
                view.append(i + 1).append("-").append(TaskList.get(i).get_Description()).append("                     ").append(TaskList.get(i).get_Type()).append("                             ").append(" Finished\n");

        }


        return view.toString();
    }

    /////////////////// METHOD TO ADD NEW TASK //////////////////////
    public void AddTask(Data Task) {
        if (Task != null) {
            TaskList.add(Task);// adding  the new task to array

        }
    }
    /////////////////////////////////////////////////////////////////////////
//                                      REMOVING TASK
    public void RemoveData(int index) {
        TaskList.remove(index);

    }
    ////////////////////////////////////////////////////////////////
    //     دي المفروض تضبط الجزء بتاع التعديل(عشان اما تعدل جزء متظطرش تكتب الباقي تاني اللي انت مش محتاج تعدله)
    public String Return_1_Task_Elment(int i, String case_status){
        String Element_willnt_be_edited =TaskList.get(i).getFinished();
        switch (case_status){
            case "type":
                Element_willnt_be_edited = TaskList.get(i).get_Type();
                break;
            case "description":
                Element_willnt_be_edited = TaskList.get(i).get_Description();
                break;
        }
       return Element_willnt_be_edited; }

    ////////////////////////EDIT DESCRIPTION//////////////////////
    public void EditDescription(int index, Data UpdateDescription) {
        TaskList.set(index, UpdateDescription);

    }
    ////////////////////////EDIT TYPE//////////////////////
    public void EditType(int index, Data UpdateDescription) {
        TaskList.set(index, UpdateDescription);

    }
    public void finished(int index,Data update){

        TaskList.set(index, update);
    }

    /////////////////////// SAVING DATA /////////////////////
    public  void savaData(int index){
        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter("save.txt"));
            for(index-=1; index>=0;index--){
                bw.write(TaskList.get(index).get_Description());
                bw.newLine();
                bw.write(TaskList.get(index).get_Type());
                bw.newLine();
                bw.write(TaskList.get(index).getFinished());
                bw.newLine();

            }
            bw.close();
        }
        catch (Exception e){
            //
        }
    }

    ///////////// to load data ////////////////////////
    public int[] loadData(){
        int[] TaskCounter= new int[3];
        int index =0;
        try { // to read data from save file
            BufferedReader br = new BufferedReader(new FileReader("save.txt"));
            String description= br.readLine();
            for (TaskCounter[0]=1; TaskCounter[0]>=0;TaskCounter[0]++) {
                if(description.isEmpty()){
                    description = br.readLine();
                }

                String type = br.readLine();
                String finished= br.readLine();
                Data loadingData= new Data(description,type);
                if (finished.equals("Finished")){
                    loadingData.set_Finished();
                    TaskList.add(index,loadingData);
                    TaskCounter[1]+=1;
                }
                else if (finished.equals("Not Finished")){
                    TaskList.add(index,loadingData);
                    TaskCounter[2]+=1;
                }
                if((description =br.readLine()) == null){
                    break;
                }
            }
            br.close();

        }
        catch (IOException e){
            /////
        }

        return TaskCounter;
    }

    //////////////////////////////FILTER TASK BY TYPE/////////////////////////////
    public String filter_Task_and_Display(String filter_Type , String type){
        StringBuilder view_filtered_tasks = new StringBuilder();
        int counter=1;
        switch (filter_Type) {
            case "Finished":
                for (int i = 0; i < TaskList.size(); i++) {
                    if (TaskList.get(i).getFinished().equals("Finished")) {
                        view_filtered_tasks.append(counter).append("-").append(TaskList.get(i).get_Description()).append("             ").append(TaskList.get(i).get_Type()).append("             ").append(" Finished\n");
                        counter += 1;
                    }
                }
                break;
            case "Not Finished":
                for (int i = 0; i < TaskList.size(); i++) {
                    if (TaskList.get(i).getFinished().equals("Not Finished")){
                        view_filtered_tasks.append(counter).append("-").append(TaskList.get(i).get_Description()).append("             ").append(TaskList.get(i).get_Type()).append("             ").append(" Not Finished\n");
                        counter+=1;
                    }
                }
                break;
            case "type":
                for (int i = 0; i < TaskList.size(); i++) {
                    if (TaskList.get(i).get_Type().equals(type)) {
                        view_filtered_tasks.append(counter).append("-").append(TaskList.get(i).get_Description()).append("             ").append(TaskList.get(i).get_Type()).append("             ").append(TaskList.get(i).getFinished());
                        counter+=1;
                    }
                }
                break;
        }


        return view_filtered_tasks.toString();
    }


}
