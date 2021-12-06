public class Info {
    public Integer Id;
    public String Name;
    public String Year;
    public String Course;
    
    public Info(Integer Id, String Name, String Year, String Course) {
       this.Id = Id;
       this.Name = Name;
       this.Year = Year;  
       this.Course = Course;
    }

    public Integer getId(){
        return Id;
    }

    public String getName(){
        return Name;
    }

    public String getYear(){
        return Year;
    }

    public String getCourse(){
        return Course;
    }


} 
