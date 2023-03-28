/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project3;

//import com.mysql.jdbc.PreparedStatement;
import java.sql.PreparedStatement;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Neon
 */
public class FXMLDocumentController implements Initializable {
    
     @FXML
    private Hyperlink create_acc;

    @FXML
    private Hyperlink login_acc;
    
     @FXML
    private TextField feedback;

    @FXML
    private Button login_btn;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Label m;

    @FXML
    private Label m1;

    @FXML
    private Label marco;

    @FXML
    private Label marco1;

    @FXML
    private PasswordField password;

    @FXML
    private Button signup_btn;

    @FXML
    private AnchorPane signup_form;

    @FXML
    private TextField su_email;

    @FXML
    private PasswordField su_password;

    @FXML
    private TextField su_username;

    @FXML
    private TextField username;
    // FXML Ended quotes -----------------------------
    
// DATABASE TOOLS 

    private Connection connect ;
    private PreparedStatement statement ;
    private PreparedStatement statement2 ;
    private ResultSet result ;
    
    public Connection connectdb(){
        try{
            connect = DriverManager.getConnection("jdbc:mysql://localhost/admin", "root", "") ;
            
//            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/admin?zeroDateTimeBehavior=convertToNull", "root", "") ;
            return connect ;
        }catch(Exception e){e.printStackTrace();}
        
        return null ;
    }
 public void login(ActionEvent event) {
    connect = connectdb();
    
    try {
        String sql = "SELECT * FROM data WHERE username = ? AND password = ?";
        String sql2 = "INSERT INTO data(username, password, email, feedback) VALUES(?, ?, ?, ?)";
        
        statement = connect.prepareStatement(sql);
        statement.setString(1, username.getText());
        statement.setString(2, password.getText());
        result = statement.executeQuery();
        if (result.next()) {
            String username = result.getString("username");
            if (!feedback.getText().isEmpty()) {
                statement2 = connect.prepareStatement(sql2);
                statement2.setString(1, username);
                statement2.setString(2, password.getText()); // set the value for the "password" column
                statement2.setString(3, su_email.getText()); // set the value for the "password" column
                statement2.setString(4, feedback.getText());
                statement2.executeUpdate();

            }
            JOptionPane.showMessageDialog(null, "Successfully Login", "Admin Message", JOptionPane.INFORMATION_MESSAGE);
            login_btn.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Simple Dashboard");
            stage.show();
        } else {
            JOptionPane.showMessageDialog(null, "Wrong Username/Password!", "Admin Message", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    
public void signup(ActionEvent event){
    connect = connectdb();
    try{
        String sql = "INSERT INTO data(username, password, email, feedback) VALUES(?,?,?, '0')" ;
        
        statement = (PreparedStatement) connect.prepareStatement(sql);
        statement.setString(1, su_username.getText());
        statement.setString(2, su_password.getText());
        statement.setString(3, su_email.getText());
        statement.execute();
        
        JOptionPane.showMessageDialog(null,"Successfully Created New Account !", "System Message", JOptionPane.ERROR_MESSAGE);
        
    }catch(Exception e){
        e.printStackTrace();
    }
}

    
    public void changeForm(ActionEvent event){
        if(event.getSource()== login_acc){
            signup_form.setVisible(false);
            login_form.setVisible(true);
            
            
        }else if(event.getSource()== create_acc){
            signup_form.setVisible(true);
            login_form.setVisible(false);
        }
    }
    
    public void exit(){
        System.exit(0);
    }
    //
    public void textfield(MouseEvent event){
        if(event.getSource()== username){
            username.setStyle("-fx-background-color: #fff;" + "-fx-border-width: 3px;");
            password.setStyle("-fx-background-color: #eef3ff;"+ "-fx-border-width: 1px");
            
        }else if(event.getSource()==password){
            username.setStyle("-fx-background-color: #eef3ff;"+"-fx-border-width: 1px");
            password.setStyle("-fx-background-color: #fff;" + "-fx-border-width: 3px");
        }
    }
    // after last check scene
    
    public void su_textfield(MouseEvent event){
        if(event.getSource() == su_username){
            su_username.setStyle("-fx-background-color: #fff;" + "-fx-border-width: 3px;");
            su_password.setStyle("-fx-background-color: #eef3ff;" + "-fx-border-width: 1px");
            su_email.setStyle("-fx-background-color: #eef3ff;" + "-fx-border-width: 1px");
        }else if(event.getSource() == su_password){
            
            su_username.setStyle("-fx-background-color: #eef3ff;" + "-fx-border-width: 1px;");
            su_password.setStyle("-fx-background-color: #fff;" + "-fx-border-width: 3px");
            su_email.setStyle("-fx-background-color: #eef3ff;" + "-fx-border-width: 1px");
         
        }else if(event.getSource()== su_email){
            su_username.setStyle("-fx-background-color: #eef3ff;" + "-fx-border-width: 1px;");
            su_password.setStyle("-fx-background-color: #eef3ff;" + "-fx-border-width: 3px");
            su_email.setStyle("-fx-background-color: #fff;" + "-fx-border-width: 1px");
        }
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        su_email.setStyle("-fx-background-color: #fff;" + "-fx-border-width: 1px");
        // change 
        username.setStyle("-fx-background-color: #fff;" + "-fx-border-width: 3px;");
        
        DropShadow original = new DropShadow(20, Color.valueOf("#6a9ae7")) ;
        m.setEffect(original);
        marco.setEffect(original);
        
        m1.setEffect(original);
        marco1.setEffect(original);
        
     
        m.setOnMouseEntered((MouseEvent event)->{
            
            DropShadow shadow = new DropShadow(50, Color.valueOf("#6a9ae7")) ;
            
            m.setStyle("-fx-text-fill: #fff");
            m.setEffect(original);
            marco.setEffect(shadow);
        });
        m.setOnMouseExited((MouseEvent event)->{
            
            DropShadow shadow = new DropShadow(50, Color.valueOf("#6a9ae7"));
            m.setStyle("-fx-text-fill: #6a9ae7");
            m.setEffect(shadow);
            marco.setEffect(shadow);
            
        });
        
        marco.setOnMouseEntered((MouseEvent event)->{
            
            DropShadow shadow = new DropShadow(50, Color.valueOf("#6a9ae7")) ;
            
            m.setStyle("-fx-text-fill: #fff");
            m.setEffect(original);
            marco.setEffect(shadow);
        });
        marco.setOnMouseExited((MouseEvent event)->{
            
            DropShadow shadow = new DropShadow(50, Color.valueOf("#6a9ae7"));
            m.setStyle("-fx-text-fill: #6a9ae7");
            m.setEffect(shadow);
            marco.setEffect(shadow);
            
        });
        
        // xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        m1.setOnMouseEntered((MouseEvent event)->{
            
            DropShadow shadow = new DropShadow(50, Color.valueOf("#6a9ae7")) ;
            
            m1.setStyle("-fx-text-fill: #fff");
            m1.setEffect(original);
            marco1.setEffect(shadow);
        });
        m1.setOnMouseExited((MouseEvent event)->{
            
            DropShadow shadow = new DropShadow(50, Color.valueOf("#6a9ae7"));
            m1.setStyle("-fx-text-fill: #6a9ae7");
            m1.setEffect(shadow);
            marco1.setEffect(shadow);
            
        });
        
        marco1.setOnMouseEntered((MouseEvent event)->{
            
            DropShadow shadow = new DropShadow(50, Color.valueOf("#6a9ae7")) ;
            
            m1.setStyle("-fx-text-fill: #fff");
            m1.setEffect(original);
            marco1.setEffect(shadow);
        });
        marco1.setOnMouseExited((MouseEvent event)->{
            
            DropShadow shadow = new DropShadow(50, Color.valueOf("#6a9ae7"));
            m1.setStyle("-fx-text-fill: #6a9ae7");
            m1.setEffect(shadow);
            marco1.setEffect(shadow);
            
        });
        
    }    
    
}
