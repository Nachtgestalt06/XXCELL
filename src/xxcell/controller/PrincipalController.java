package xxcell.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import static javafx.scene.input.KeyCode.F1;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xxcell.XCELL;

public class PrincipalController implements Initializable {
    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXHamburger titleBurger;

    @FXML
    private StackPane root;
    
    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXButton btnAlmacen;

    @FXML
    private JFXButton btnRecursosHumanos;
    
    @FXML
    private Label lblBottom;
    
    private Node Venta = null;
    private Node Consultas = null;
    private AnchorPane Almacen = null;
    private AnchorPane RecursosHumanos = null;
    private Node ModificarAlmacen = null;
    private Node ModificarEmpleado = null;
    private Node MostrarNomina = null;
    private Node Login = null;
    
    Node principal = null;
    
    private Scene pscene;
    private Stage primaryStage;
    private static boolean flagVentas = false;
    private static boolean flagConsultas = false;
    private StackPane mainLayout;
    
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(()-> {
            root.requestFocus();
            //Lambda 
            borderPane.getScene().getWindow().setOnCloseRequest(event -> {
                event.consume();
                cerrar();
            }); 

        });
        
        
        VBox sideMenu = null;
        try {
            sideMenu = FXMLLoader.load(getClass().getResource("/xxcell/view/SideMenu.fxml"));
            mostrarBienvenida();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        drawer.setOnDrawerOpening((e) -> {
            titleBurger.getAnimation().setRate(1);
            titleBurger.getAnimation().play();
        });
        
        drawer.setOnDrawerClosing((e) -> {
            titleBurger.getAnimation().setRate(-1);
            titleBurger.getAnimation().play();
            });
        
        titleBurger.setOnMouseClicked((e)->{
            if (drawer.isHidden() || drawer.isHidding()) drawer.open();
            else drawer.close();
        });
        
        System.out.println("Rol = "+Variables_Globales.Rol);
        drawer.setDefaultDrawerSize(150);
        drawer.setSidePane(sideMenu);
        
        for(Node node : sideMenu.getChildren()){
            if(node.getAccessibleText()!= null){
                node.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)-> {
                    switch(node.getAccessibleText()){
                        case "Venta" : {
                            try {
                                mostrarVenta();
                            } catch (IOException ex) {
                                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        
                        case "Consultas"  : {
                            try {
                                mostrarConsultas();
                            } catch (IOException ex) {
                                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        
                        case "Almacen"  : {
                            try {
                                mostrarAlmacen();
                            } catch (IOException ex) {
                                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        
                        case "RecursosHumanos"  : {
                            try {
                                mostrarRecursosHumanos();
                            } catch (IOException ex) {
                                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        
                        case "VentasDia" : {
                            try {
                                mostrarVentasDia();
                            } catch (IOException ex) {
                                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                        
                        case "CerrarSesion":{
                            try {
                                mostrarLogin();
                            } catch (IOException ex) {
                                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }
                    }
                });
            }
        }
                               
        
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()){
                    case F1 : {
                        if(!flagVentas)
                        try {
                            mostrarVenta();
                        } catch (IOException ex) {
                            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    
                    case F2 : {
                        if(!flagConsultas)
                        try {
                            mostrarConsultas();
                        } catch (IOException ex) {
                            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    
                    case F3: {
                        if(Variables_Globales.Rol.equals("0"))
                        try {
                            mostrarAlmacen();
                        } catch (IOException ex) {
                            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    
                    case F4: {
                        if(Variables_Globales.Rol.equals("0"))
                        try {
                            mostrarRecursosHumanos();
                        } catch (IOException ex) {
                            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    
                    case SPACE : {
                        if (drawer.isHidden() || drawer.isHidding()) drawer.open();
                        else drawer.close();
                    }
                    break;
                    
                    case ESCAPE : {
                        try {
                            mostrarBienvenida();
                        } catch (IOException ex) {
                            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                }
            }
        });
        
    }
      
    private void mostrarVenta() throws IOException {   
        flagVentas = true;
        Venta = FXMLLoader.load(getClass().getResource("/xxcell/view/Venta.fxml"));
        drawer.setContent(Venta);
        if(flagConsultas)
            flagConsultas = false;
    }
    
    private void mostrarConsultas() throws IOException {   
        flagConsultas = true;
        Consultas = FXMLLoader.load(getClass().getResource("/xxcell/view/Consultas.fxml"));
        drawer.setContent(Consultas);
        if(flagVentas)
            flagVentas = false;
    }
    
    private void mostrarBienvenida() throws IOException {
        root.requestFocus();
        flagVentas = false;
        flagConsultas = false;
        principal = FXMLLoader.load(getClass().getResource("/xxcell/view/Bienvenida.fxml"));
        drawer.setContent(principal);
    }
    
    private void mostrarAlmacen() throws IOException {   
        Almacen = FXMLLoader.load(getClass().getResource("/xxcell/view/Almacen.fxml"));        
        drawer.setContent(Almacen);
        for(Node node : Almacen.getChildren()){
            if(node.getAccessibleText()!= null){
                node.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)-> {
                    switch(node.getAccessibleText()){
                        case "modificar" : {
                        try {
                            mostrarModificarAlmacen();
                        } catch (IOException ex) {
                            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                        break;
                    }
                });
            }
        }
    }
    
    private void mostrarRecursosHumanos() throws IOException {   
        RecursosHumanos = FXMLLoader.load(getClass().getResource("/xxcell/view/Recursos Humanos.fxml"));
        drawer.setContent(RecursosHumanos);
        for(Node node : RecursosHumanos.getChildren()){
            if(node.getAccessibleText()!= null){
                node.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)-> {
                    switch(node.getAccessibleText()){
                        case "ModificarEmpleado" : {
                        try {
                            mostrarModificarEmpleado();
                        } catch (IOException ex) {
                            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                        break;
                        case "Nomina" : {
                        try {
                            mostrarNomina();
                        } catch (IOException ex) {
                            Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                        break;
                    }
                });
            }
        }
    }
    
    private void mostrarModificarAlmacen() throws IOException {   
        ModificarAlmacen = FXMLLoader.load(getClass().getResource("/xxcell/view/ModificarAlmacen.fxml"));
        drawer.setContent(ModificarAlmacen);
    }
    
    private void mostrarModificarEmpleado() throws IOException{
        ModificarEmpleado = FXMLLoader.load(getClass().getResource("/xxcell/view/ModificarEmpleado.fxml"));
        drawer.setContent(ModificarEmpleado);
    }
    
    private void mostrarVentasDia() throws IOException{
        Almacen = FXMLLoader.load(getClass().getResource("/xxcell/view/VentasDia.fxml"));
        drawer.setContent(Almacen);
    }
 
    private void mostrarLogin() throws IOException{
        Variables_Globales.Rol = null;
        Stage stage;
        stage = (Stage) drawer.getScene().getWindow();
        stage.close();
        
        this.primaryStage = new Stage();
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(XCELL.class.getResource("/xxcell/view/Login.fxml"));
        mainLayout = loader.load();
        JFXDecorator decorator = new JFXDecorator(primaryStage, mainLayout);
        Scene scene = new Scene(decorator);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void mostrarNomina() throws IOException{
        MostrarNomina = FXMLLoader.load(getClass().getResource("/xxcell/view/NominaEmpleados.fxml"));
        drawer.setContent(MostrarNomina);
    }

    private void cerrar() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Salir del sistema");
                //alert.setHeaderText("¿Desea realmente salir del sistema?");
        alert.setContentText("¿Desea realmente salir del sistema?");
        alert.initOwner(borderPane.getScene().getWindow());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            borderPane.getScene().getWindow().hide();
        }         
    }
    
}
