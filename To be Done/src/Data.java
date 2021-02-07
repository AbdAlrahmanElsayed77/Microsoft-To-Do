 class Data{
    private String description;
    private String finished;
    private String type;
    public Data(String description,String type){
        set_Not_Finished();
        set_Description(description);
        set_Type(type);
    }
    public String getFinished() {
        return finished;
    } // returning finished value
    public void set_Not_Finished() {
        this.finished = "Not Finished";
    }// setting finished value= not finished

     public void set_Finished(){ // making finished value = finished
         this.finished ="Finished";
     }

    public void set_Description(String description){
        if(!description.isEmpty()) {
            this.description = description;
        }else{
            System.out.println("description is empty");
        }
    }


    public String get_Type(){
        return this.type;
    }

     public void set_Type(String type){
         if(!type.isEmpty()) {
             this.type = type;
         }else{
             System.out.println("type is empty");
         }
     }

     public String get_Description(){
         return this.description;
    }
 }