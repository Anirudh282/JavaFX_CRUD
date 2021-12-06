import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;



public class SceneController implements Initializable{

    @FXML
    private TextField Id;
    
    @FXML
    private TextField Name;
    
    @FXML
    private TextField Year;     
    
    @FXML
    private TextField Course;     

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Info, String> colCourse;

    @FXML
    private TableColumn<Info, Integer> colId;

    @FXML
    private TableColumn<Info, String> colName;

    @FXML
    private TableColumn<Info, String> colYear;

    @FXML
    private TextField tfCourse;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfYear;

    @FXML
    private TableView<Info> tvInfo;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if(event.getSource() == btnInsert){
            insertRecord();
        }else if (event.getSource() == btnUpdate){
            updateRecord();
        }else if(event.getSource() == btnDelete){
            deleteButton();    
        }
            
    }        

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        showInfo();
    }

    public Connection getConnection(){
        Connection conn;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/info","root", "root");
            return conn;
        }catch(Exception ex){
            System.out.println("Error" + ex.getMessage());
            return null;
        }
        
    }
    
    public ObservableList<Info> getInfoList(){
        ObservableList<Info> infolist = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM info";
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Info info;
            while(rs.next()){
                info = new Info(rs.getInt("Id"), rs.getString("Name"), rs.getString("Year"), rs.getString("Course"));
                infolist.add(info);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return infolist;
    } 
    
    public void showInfo(){
        ObservableList<Info> list = getInfoList();    
        
        colId.setCellValueFactory(new PropertyValueFactory<Info, Integer>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<Info, String>("Name"));
        colYear.setCellValueFactory(new PropertyValueFactory<Info, String>("Year"));
        colCourse.setCellValueFactory(new PropertyValueFactory<Info, String>("Course"));   

        tvInfo.setItems(list); 
    }

    private void insertRecord(){
        String Query = "INSERT INTO info VALUES (" + "'" + Integer.parseInt(tfId.getText()) + "','" + tfName.getText() 
            + "','" + tfYear.getText() + "','" + tfCourse.getText() + "')";
        executeQuery(Query);
        showInfo();
    }

    private void updateRecord(){
        String Query = "UPDATE info SET Name  = '" + tfName.getText() + "', Year = '" + tfYear.getText() + "', Course = '" +
                tfCourse.getText() + "' WHERE Id = " + Integer.parseInt(tfId.getText()) + "";
        executeQuery(Query);
        showInfo();
    }

    private void deleteButton(){
        String Query = "DELETE FROM info WHERE Id = " + tfId.getText() + "";
        executeQuery(Query);
        showInfo();
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
         }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}               


