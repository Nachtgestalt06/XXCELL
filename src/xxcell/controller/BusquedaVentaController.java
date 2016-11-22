package xxcell.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import xxcell.Conexion.Conexion;
import xxcell.model.Productos;

public class BusquedaVentaController implements Initializable {
    
    Conexion conn = new Conexion();
    String sqlStmt;
    String query;
    
    //Variables para la Tabla creada
    @FXML
    private TableView<Productos> Tabla;
    @FXML
    private TableColumn<Productos, String> IDCol; //COLUMNA ID
    @FXML
    private TableColumn<Productos, String> ModCol; //COLUMNA MODELO
    @FXML
    private TableColumn<Productos, String> MarcaCol; //COLUMNA MARCA
    @FXML
    private TableColumn<Productos, String> TipoCol; //COLUMNA TIPO
    @FXML
    private TableColumn<Productos, String> NomCol; //COLUMNA NOMBRE
    
    //Lista para Llenar la tabla de productos;
    ObservableList<Productos> productos = FXCollections.observableArrayList();
    
    @FXML
    private JFXComboBox<String> Producto;

    @FXML
    private JFXComboBox<String> Marca;

    @FXML
    private JFXTextField Modelo;

    @FXML
    private JFXButton Filtrar;

    @FXML
    private JFXButton Reset;
    
    //Lista para el autocompletado de JFXTextField buscar
    List <String> possibleWords = new ArrayList<>();
    Set<String> hs = new HashSet<>(); // HashSet Para quitar elementos repetidos de POSSIBLEWORDS
    
    //Función para llenar el TableView con los datos que el usuario indique
    public ObservableList<Productos> ObtenerProd(String STSQL) throws SQLException{
        String Mod, Marc, DI, Nom, Tip, Dep;   
        int Disp, l58, l64, l127;
        double PPub, PDist;
        if(conn.QueryExecute(STSQL))
        {
            while (conn.setResult.next())
            {
                DI = conn.setResult.getString("ID");
                Marc = conn.setResult.getString("Marca");
                Mod = conn.setResult.getString("Modelo");
                Nom = conn.setResult.getString("Nombre");
                PPub = conn.setResult.getDouble("PrecPub");
                PDist = conn.setResult.getDouble("PrecDist");
                Tip = conn.setResult.getString("Tipo");
                Dep = conn.setResult.getString("Descrip");
                Disp = conn.setResult.getInt("CantidadActual");
                l58 = conn.setResult.getInt("L58");
                l64 = conn.setResult.getInt("L64");
                l127 = conn.setResult.getInt("L127");
                productos.add(new Productos(DI,Marc,Mod,Nom,PPub,PDist,Tip,Dep,Disp,l58,l64,l127));
            }    
        }
        return productos;
    }
    
     public void Autocompletar()
    {
        possibleWords.clear();
        hs.clear();
        query = "SELECT * FROM productos WHERE Modelo LIKE '%" + Modelo.getText() + "%'";
        if(conn.QueryExecute(query)){
            try {
                while(conn.setResult.next()){
                    possibleWords.add(conn.setResult.getString("Modelo"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        hs.addAll(possibleWords);
        possibleWords.clear();
        possibleWords.addAll(hs);
        TextFields.bindAutoCompletion(Modelo, possibleWords);
    }    
    
    public void iniciarComboBox()
    {
        //inicia el combobox Marca
        possibleWords.clear();
        hs.clear();
        query = "SELECT * FROM productos";
            if(conn.QueryExecute(query))
            {
                try {
                    while(conn.setResult.next()){
                    possibleWords.add(conn.setResult.getString("Marca"));
                }
                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            hs.addAll(possibleWords);
            possibleWords.clear();
            possibleWords.addAll(hs);
        Marca.getItems().addAll(possibleWords);//Añade las diferentes marcas al combobox
        
        //inicia el combobox Producto
        possibleWords.clear();
        hs.clear();
        query = "SELECT * FROM productos";
            if(conn.QueryExecute(query))
            {
                try {
                    while(conn.setResult.next()){
                    possibleWords.add(conn.setResult.getString("Nombre"));
                }
                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            hs.addAll(possibleWords);
            possibleWords.clear();
            possibleWords.addAll(hs);
        Producto.getItems().addAll(possibleWords);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        iniciarComboBox();
        IDCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        MarcaCol.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
        ModCol.setCellValueFactory(cellData -> cellData.getValue().modeloProperty());
        TipoCol.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
        NomCol.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        try {
            sqlStmt = "SELECT * FROM productos";
            Tabla.setItems(ObtenerProd(sqlStmt));
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Función para el comboBox Marca; Esté llenara el TableView dependiendo la Opción que el usuario Ingrese
        Marca.setOnAction(e -> {
            query = "Select * FROM productos "; 
            query += "WHERE Marca='"+Marca.getValue()+"'";
            try {   
                Tabla.refresh();
                productos.removeAll(productos);
                Tabla.setItems(ObtenerProd(query));
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });//FIN DE LAMBDA MARCA   
        
        Modelo.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            Autocompletar();
            query = "Select * FROM productos ";
            query += "WHERE Modelo='"+Modelo.getText()+"'";
            try {   
                Tabla.refresh();
                productos.removeAll(productos);
                Tabla.setItems(ObtenerProd(query));
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });   

        //Función para el combobox Tipo
        Producto.setOnAction(e -> {
            query = "Select * FROM productos "; 
            query += "WHERE Nombre='"+Producto.getValue()+"'";
            try {   
                Tabla.refresh();
                productos.removeAll(productos);
                Tabla.setItems(ObtenerProd(query));
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });//FIN DE LAMBDA
        
        Filtrar.setOnAction((ActionEvent e) -> { 
            query = "Select * FROM productos ";
            if(Marca.getValue() != null && Producto.getValue() != null && Modelo.getText().length()==0){
                System.out.println("No hay nada en textfield");                
                query += "WHERE Marca = '"+Marca.getValue()+"' AND Nombre = '"+Producto.getValue()+"'";
                System.out.println(query);
                try {   
                    Tabla.refresh();
                    productos.removeAll(productos);
                    Tabla.setItems(ObtenerProd(query));
                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println(Modelo.getText() + " Modelo");
            System.out.println(Marca.getValue() + " Marca");
            System.out.println(Producto.getValue() + " Producto");
            if(Marca.getValue() != null && Producto.getValue() != null && Modelo.getText().length()>0){
                query += "WHERE Modelo='"+Modelo.getText()+"' AND Marca = '"+Marca.getValue()+"' AND Nombre = '"+Producto.getValue()+"'";
                System.out.println(query);
                try {   
                    Tabla.refresh();
                    productos.removeAll(productos);
                    Tabla.setItems(ObtenerProd(query));
                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
        Reset.setOnAction((ActionEvent e) -> { 
            query = "SELECT * FROM productos";
            try {
                Tabla.refresh();
                productos.removeAll(productos);
                Tabla.setItems(ObtenerProd(query));
            } catch (SQLException ex) {
                Logger.getLogger(BusquedaVentaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Marca.getSelectionModel().clearSelection();
            Marca.getItems().clear();
            Producto.getSelectionModel().clearSelection();
            Producto.getItems().clear();
            Modelo.clear();
            iniciarComboBox();
        });//FIN RESET
        
        Tabla.setOnMousePressed(new EventHandler<MouseEvent>() {
             @Override 
             public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Variables_Globales.BusquedaVenta = Tabla.getSelectionModel().getSelectedItem();
                    Stage stage;
                    stage = (Stage) Tabla.getScene().getWindow();
                    stage.close();
                }
            }
        });
        
        Tabla.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    Variables_Globales.BusquedaVenta = Tabla.getSelectionModel().getSelectedItem();
                    Stage stage;
                    stage = (Stage) Tabla.getScene().getWindow();
                    stage.close();
                }
            }
            
        });
    }    
}
