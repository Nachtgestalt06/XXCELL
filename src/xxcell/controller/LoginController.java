package xxcell.controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import xxcell.Conexion.Conexion;

public class LoginController implements Initializable {
    
    @FXML private JFXButton btnLogin;
    @FXML private JFXPasswordField password_box;
    @FXML private JFXTextField username_box;
    @FXML private Label lblMensaje;
    
    static Scene scene;
    
    Conexion conn = new Conexion();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnLogin.setDefaultButton(true);
        
    }
    
    @SuppressWarnings("empty-statement")
    private boolean isValidCredentials() throws SQLException
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        boolean let_in = false;
        String user = username_box.getText();
        String pass = password_box.getText();
        String sqlStmt;
        sqlStmt = "SELECT * FROM empleado";
	sqlStmt += " WHERE usuario='"+user+"'";
	sqlStmt += " AND contra='"+pass+"'";

               
        if(!conn.ConnectionOpen()) {
            alert.setTitle("Error");
            alert.setHeaderText("No hay conexion con la base de datos");
            alert.setContentText("Lo sentimos, no se ha podido establecer la conexion con la base de datos");
            alert.showAndWait();        
        }
        else {
            conn.QueryExecute(sqlStmt);
            try {
                if(conn.setResult.first()) {
                    let_in = true;
                    Variables_Globales.Rol = conn.setResult.getString("nivel");
                } else {
                    username_box.clear();
                    password_box.clear();
                    lblMensaje.setText("Usuario y/o Contraseña incorrectos"); 
                    username_box.requestFocus();
                }
            } catch (SQLException e) {
                
                //No pondré nada aquí, me piro.
            }
        }//fin else
        return let_in;
    }
    
    @FXML
    void actionLogin(ActionEvent e) throws IOException {
            String mensaje = "";
            if(username_box.getText().length() == 0) {
                mensaje = "Usuario";
                username_box.requestFocus();
            }

            if(password_box.getText().length() == 0) {
                if(mensaje.length()==0)
                    password_box.requestFocus();
                mensaje = mensaje+"\n"+"Password";
            }

            if(mensaje.length() > 0){
                    mensaje = "Faltan los siguientes datos: \n"+mensaje;
                    Alert incompleteAlert = new Alert(Alert.AlertType.INFORMATION);
                    incompleteAlert.setTitle("Datos incompletos");
                    incompleteAlert.setHeaderText(null);
                    incompleteAlert.setContentText(mensaje);
                    incompleteAlert.showAndWait();
            } else try {
                
                if(isValidCredentials()){
                    construirScene(e);
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }    

    }
    
    private void construirScene(ActionEvent e) throws IOException {
        PrincipalController controller = new PrincipalController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/xxcell/view/Principal.fxml"));
        loader.setController(controller);
        
        Parent principal;
        principal = FXMLLoader.load(getClass().getResource("/xxcell/view/Principal.fxml"));
        Stage principalStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(principal);
        principalStage.setScene(scene);
        principalStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        principalStage.setFullScreen(true);
        principalStage.show();
    }
    
    public static Scene getScene() {
        return scene;
    }
    
}
