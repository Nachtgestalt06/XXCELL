/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import xxcell.Conexion.Conexion;
import xxcell.model.Productos;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author snak0
 */
public class ConsultasController implements Initializable {
    Conexion conn = new Conexion();   

    boolean marc;
    boolean tip;

    String sqlStmt = "SELECT * FROM productos";
    String StmtSq;
    String query;
    
    @FXML
    private JFXComboBox<String> Tipo;

    @FXML
    private JFXTextField buscar;
        
    @FXML
    private JFXComboBox<String> Marca;
    
    //Lista para el autocompletado de JFXTextField buscar
    List <String> possibleWords = new ArrayList<>();
    Set<String> hs = new HashSet<>(); // HashSet Para quitar elementos repetidos de POSSIBLEWORDS
    
    //botones
    @FXML
    private JFXButton filtrar;
    @FXML
    private JFXButton reset;
    
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
    private TableColumn<Productos, String> DesCol; //COLUMNA DESCRIPCIÓN
    @FXML
    private TableColumn<Productos, Number> PrPubCol; //COLUMNA PRECIO PUBLICO ***
    @FXML
    private TableColumn<Productos, Number> DispCol; //COLUMNA DISPONIBILIDAD ****
    @FXML
    private TableColumn<Productos, String> TipoCol; //COLUMNA TIPO
    @FXML
    private TableColumn<Productos, String> NomCol; //COLUMNA NOMBRE
    @FXML
    private TableColumn<Productos, Number> PrDisCol; //COLUMNA PRECIO DISTRIBUIDOR***
    @FXML
    private TableColumn<Productos, Number> L58Col; //COLUMNA DISPONIBILIDAD EN LOCAL 58 ****
    @FXML
    private TableColumn<Productos, Number> L64Col; //COLUMNA DISPONIBILIDAD EN LOCAL 64****
    @FXML
    private TableColumn<Productos, Number> L127Col; //COLUMNA DISPONIBILIDAD EN LOCAL 127****
    
    
    //Lista para Llenar la tabla de productos;
    ObservableList<Productos> productos = FXCollections.observableArrayList();
     
    
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
        query = "SELECT * FROM productos WHERE Marca LIKE '%" + buscar.getText() + "%'";
        if(conn.QueryExecute(query)){
            try {
                while(conn.setResult.next()){
                    possibleWords.add(conn.setResult.getString("Marca"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        query = "SELECT * FROM productos WHERE Modelo LIKE '%" + buscar.getText() + "%'";
        if(conn.QueryExecute(query)){
            try {
                while(conn.setResult.next()){
                    possibleWords.add(conn.setResult.getString("Modelo"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        query = "SELECT * FROM productos WHERE ID LIKE '%" + buscar.getText() + "%'";
        if(conn.QueryExecute(query)){
            try {
                while(conn.setResult.next()){
                    possibleWords.add(conn.setResult.getString("ID"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        hs.addAll(possibleWords);
        possibleWords.clear();
        possibleWords.addAll(hs);
        TextFields.bindAutoCompletion(buscar, possibleWords);
    }    
     //Inicia los elementos de los ComboBox para las Marcas y los tipos de Objetos
    public void iniciarComboBox()
    {
        //inicia el combobox Marca
        hs.clear();
        possibleWords.clear();
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
        //Inicia el Combobox Tipo
        possibleWords.clear();
        hs.clear();
        query = "SELECT * FROM productos";
            if(conn.QueryExecute(query))
            {
                try {
                    while(conn.setResult.next()){
                    possibleWords.add(conn.setResult.getString("Tipo"));
                }
                } catch (SQLException ex) {
                    Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            hs.addAll(possibleWords);
            possibleWords.clear();
            possibleWords.addAll(hs);
        Tipo.getItems().addAll(possibleWords);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarComboBox();
        Tabla.setPlaceholder(new Label("No hay Coincidencias en la busqueda"));
        IDCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        MarcaCol.setCellValueFactory(cellData -> cellData.getValue().marcaProperty());
        ModCol.setCellValueFactory(cellData -> cellData.getValue().modeloProperty());
        DesCol.setCellValueFactory(cellData -> cellData.getValue().descripProperty());
        TipoCol.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
        NomCol.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        PrPubCol.setCellValueFactory(cellData -> cellData.getValue().preciopubProperty());
        PrDisCol.setCellValueFactory(cellData -> cellData.getValue().preciodistProperty());
        DispCol.setCellValueFactory(cellData -> cellData.getValue().disponProperty());
        L58Col.setCellValueFactory(cellData -> cellData.getValue().l58Property());
        L64Col.setCellValueFactory(cellData -> cellData.getValue().l64Property());
        L127Col.setCellValueFactory(cellData -> cellData.getValue().l127Property());
        try {
            Tabla.setItems(ObtenerProd(sqlStmt));
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Función para el comboBox Marca; Esté llenara el TableView dependiendo la Opción que el usuario Ingrese
        Marca.setOnAction(e -> {
            buscar.clear();
            StmtSq = "Select * FROM productos "; 
            StmtSq += "WHERE Marca='"+Marca.getValue()+"'";
            try {   
                Tabla.refresh();
                productos.removeAll(productos);
                Tabla.setItems(ObtenerProd(StmtSq));
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });//FIN DE LAMBDA MARCA       
        
        //Función para el combobox Tipo
        Tipo.setOnAction(e -> {
            buscar.clear();
            StmtSq = "Select * FROM productos "; 
            StmtSq += "WHERE Tipo='"+Tipo.getValue()+"'";
            try {   
                Tabla.refresh();
                productos.removeAll(productos);
                Tabla.setItems(ObtenerProd(StmtSq));
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });//FIN DE LAMBDA   
        //Función para el autocompletado del Textfield Buscar
        buscar.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            Autocompletar();
            StmtSq = "Select * FROM productos ";
            StmtSq += "WHERE Marca='"+buscar.getText()+"' OR Modelo = '"+buscar.getText()+"' OR ID = '"+buscar.getText()+"'";
            try {   
                Tabla.refresh();
                productos.removeAll(productos);
                Tabla.setItems(ObtenerProd(StmtSq));
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });   
        
        filtrar.setOnAction((ActionEvent e) -> { 
            StmtSq = "Select * FROM productos ";
            StmtSq += "WHERE Tipo='"+Tipo.getValue()+"' AND Marca = '"+Marca.getValue()+"'";
            try {   
                Tabla.refresh();
                productos.removeAll(productos);
                Tabla.setItems(ObtenerProd(StmtSq));
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        reset.setOnAction((ActionEvent e) -> { 
            sqlStmt = "SELECT * FROM productos";
            try {
                Tabla.refresh();
                productos.removeAll(productos);
                Tabla.setItems(ObtenerProd(sqlStmt));
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Marca.getSelectionModel().clearSelection();
            Marca.getItems().clear();
            Tipo.getSelectionModel().clearSelection();
            Tipo.getItems().clear();
            iniciarComboBox();
        });//FIN RESET
    }//Fin de INIT    
    
}
