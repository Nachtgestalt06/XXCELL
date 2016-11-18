package xxcell.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import xxcell.Conexion.Conexion;
import xxcell.model.Productos;
import org.controlsfx.control.textfield.TextFields;

public class ModificarAlmacenController implements Initializable {
    Conexion conn = new Conexion();   

    Productos aux;

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
    @FXML
    private JFXButton Modificar;
    @FXML
    private JFXButton Eliminar;
    
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
    
    //Datos para Editar los algún campo
     //TEXTFIELDS
    @FXML
    private JFXTextField IDTxt;
    @FXML
    private JFXTextField MarcaTxt;
    @FXML
    private JFXTextField ModeloTxt;    
    @FXML
    private JFXTextField Disptxt;
    @FXML
    private JFXTextField PreDtxt;
    @FXML
    private JFXTextField PrecPtxt;
    @FXML
    private JFXTextField Tipotxt;
    @FXML
    private JFXTextField Nomtxt;
    @FXML
    private TextField TFL127;
    @FXML
    private TextField TFL64;
    @FXML
    private TextField TFL58;
    //FIN TEXTFIELD
    //Area para la Descripcion
    @FXML
    private JFXTextArea DescTxt;
    //LABEL DE ERROR PARA LA VALIDACIÓN DE LOS TEXTFIELDS
    @FXML
    private Label lblEID;
    @FXML
    private Label lblEModelo;
    @FXML
    private Label lblEMarca;
    @FXML
    private Label lblESum;
    @FXML
    private Label lblEnumero;
    @FXML
    private Label lbl127Dispon;
    @FXML
    private Label lbl64Dispon;
    @FXML
    private Label lbl58Dispon;
    @FXML
    private Label lblDispon;
    
    //Listas para los automecompleatos en los textfields de Marca, Tipo y Modelo
    
    //Lista para el autocompletado de JFXTextField buscar
    List <String> possibleWords_Marca = new ArrayList<>();
    Set<String> hs_Marca = new HashSet<>(); // HashSet Para quitar elementos repetidos de POSSIBLEWORDS
    List <String> possibleWords_Tipo = new ArrayList<>();
    Set<String> hs_Tipo = new HashSet<>(); // HashSet Para quitar elementos repetidos de POSSIBLEWORDS
    List <String> possibleWords_Modelo = new ArrayList<>();
    Set<String> hs_Modelo = new HashSet<>(); // HashSet Para quitar elementos repetidos de POSSIBLEWORDS
    List <String> possibleWords_Nom = new ArrayList<>();
    Set<String> hs_Nom = new HashSet<>(); // HashSet Para quitar elementos repetidos de POSSIBLEWORDS
     
    boolean ban;
    
     //Función para llenar el TableView con los datos que el usuario indique
    public ObservableList<Productos> ObtenerProd(String STSQL) throws SQLException{
        String Mod, Marc, DI, Nom, Tip, Dep;   
        int Disp, L58,L64,L127;
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
                Disp = conn.setResult.getInt("CantidadInicial");
                L58 = conn.setResult.getInt("L58");
                L64 = conn.setResult.getInt("L64");
                L127 = conn.setResult.getInt("L127");
                productos.add(new Productos(DI,Marc,Mod,Nom,PPub,PDist,Tip,Dep,Disp, L58,L64,L127));
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
                Logger.getLogger(ModificarAlmacenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        query = "SELECT * FROM productos WHERE Modelo LIKE '%" + buscar.getText() + "%'";
        if(conn.QueryExecute(query)){
            try {
                while(conn.setResult.next()){
                    possibleWords.add(conn.setResult.getString("Modelo"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModificarAlmacenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        query = "SELECT * FROM productos WHERE ID LIKE '%" + buscar.getText() + "%'";
        if(conn.QueryExecute(query)){
            try {
                while(conn.setResult.next()){
                    possibleWords.add(conn.setResult.getString("ID"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModificarAlmacenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        hs.addAll(possibleWords);
        possibleWords.clear();
        possibleWords.addAll(hs);
        TextFields.bindAutoCompletion(buscar, possibleWords);
    }    
    
    public void AutocompletarEdit()
    {
        //AUTOCOMPLETADO PARA EL TEXTFIELD DE MARCA
        query = "SELECT * FROM productos WHERE Marca LIKE '%" + MarcaTxt.getText() + "%'";
        if(conn.QueryExecute(query)){
            try {
                while(conn.setResult.next()){
                    possibleWords_Marca.add(conn.setResult.getString("Marca"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(AgregarProductoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        hs_Marca.addAll(possibleWords_Marca);
        possibleWords_Marca.clear();
        possibleWords_Marca.addAll(hs_Marca);
        TextFields.bindAutoCompletion(MarcaTxt, possibleWords_Marca);
        //AUTOCOMPLETADO PARA EL TEXTFIEL DE MODELO
        query = "SELECT * FROM productos WHERE Modelo LIKE '%" + ModeloTxt.getText() + "%'";
        if(conn.QueryExecute(query)){
            try {
                while(conn.setResult.next()){
                    possibleWords_Modelo.add(conn.setResult.getString("Modelo"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(AgregarProductoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        hs_Modelo.addAll(possibleWords_Modelo);
        possibleWords_Modelo.clear();
        possibleWords_Modelo.addAll(hs_Modelo);
        TextFields.bindAutoCompletion(ModeloTxt, possibleWords_Modelo);    
        //AUTOCOMPLETADO PARA EL TEXTFIELD TIPO
        query = "SELECT * FROM productos WHERE Tipo LIKE '%" + Tipotxt.getText() + "%'";
        if(conn.QueryExecute(query)){
            try {
                while(conn.setResult.next()){
                    possibleWords_Tipo.add(conn.setResult.getString("Tipo"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(AgregarProductoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        hs_Tipo.addAll(possibleWords_Tipo);
        possibleWords_Tipo.clear();
        possibleWords_Tipo.addAll(hs_Tipo);
        TextFields.bindAutoCompletion(Tipotxt, possibleWords_Tipo);
        //AUTOCOMPLETADO PARA EL TEXTFIELD Nombre
        query = "SELECT * FROM productos WHERE Nombre LIKE '%" + Nomtxt.getText() + "%'";
        if(conn.QueryExecute(query)){
            try {
                while(conn.setResult.next()){
                    possibleWords_Nom.add(conn.setResult.getString("Nombre"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(AgregarProductoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        hs_Nom.addAll(possibleWords_Nom);
        possibleWords_Nom.clear();
        possibleWords_Nom.addAll(hs_Nom);
        TextFields.bindAutoCompletion(Nomtxt, possibleWords_Nom);
        
    }
     //Inicia los elementos de los ComboBox para las Marcas y los tipos de Objetos
    public void iniciarComboBox()
    {
        //inicia el combobox Marca
        possibleWords.clear();
        query = "SELECT * FROM productos";
        if(conn.QueryExecute(query))
        {
            try {
                while(conn.setResult.next()){
                    possibleWords.add(conn.setResult.getString("Marca"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ModificarAlmacenController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ModificarAlmacenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        hs.addAll(possibleWords);
        possibleWords.clear();
        possibleWords.addAll(hs);
        Tipo.getItems().addAll(possibleWords);
    }
    //Funcion para eliminar un producto
    void EliminarProd(){
        aux = Tabla.getSelectionModel().getSelectedItem();
        StmtSq = "DELETE FROM productos WHERE ID='"+aux.getID()+"'";                  
        if(conn.QueryUpdate(StmtSq))
        {
            String mensaje = "Producto Eliminado \n";
            Alert incompleteAlert = new Alert(Alert.AlertType.INFORMATION);
            incompleteAlert.setTitle("Eliminar Producto");
            incompleteAlert.setHeaderText(null);
            incompleteAlert.setContentText(mensaje);
            incompleteAlert.initOwner(Eliminar.getScene().getWindow());
            incompleteAlert.showAndWait();
        }
        else
        {
            String mensaje = "Producto No se ha podido eliminar \n";
            Alert incompleteAlert = new Alert(Alert.AlertType.INFORMATION);
            incompleteAlert.setTitle("Eliminar Producto");
            incompleteAlert.setHeaderText(null);
            incompleteAlert.setContentText(mensaje);
            incompleteAlert.initOwner(Eliminar.getScene().getWindow());
            incompleteAlert.showAndWait();
        }
   }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tabla.setOnMousePressed(new EventHandler<MouseEvent>() {
             @Override 
             public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    aux = Tabla.getSelectionModel().getSelectedItem();
                    llenado(aux);
                }
            }
        });
    //INICIAR LOS TEXFIELDS****
        Tipotxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            Autocompletar();
        });
        MarcaTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            Autocompletar();
        });
        ModeloTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            Autocompletar();
        });
        Nomtxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            Autocompletar();
        });
    //FIN DE TEXFIELDS******
 //**********************************************BOTON EDITAR*******************************
        Modificar.setOnAction((ActionEvent e) -> {
            if(Tabla.getSelectionModel().isEmpty()){
                    String mensaje = "No se ha seleccionado ningun producto. \n";
                    Alert incompleteAlert = new Alert(Alert.AlertType.INFORMATION);
                    incompleteAlert.setTitle("Eliminar Producto");
                    incompleteAlert.setHeaderText(null);
                    incompleteAlert.setContentText(mensaje);
                    incompleteAlert.initOwner(Modificar.getScene().getWindow());
                    incompleteAlert.showAndWait();
            }
            else
            {
                int x, y, z, w;
                int suma, conta = 0;    
                ban=true;
//*******************Validación de ID *************************
                if(IDTxt.getText().length() == 0 || IDTxt.getText().length() > 13) {
                    lblEID.setText("ID debe ser mayor a 0 y Menor a 14 caracteres");
                    IDTxt.requestFocus();
                    ban = false;
                }
                else{
                    lblEID.setText("");
                    if(IDTxt.getText().equals(aux.getID())){
                        conta++;
                        lblEID.setText("Sin Modificaciones");
                    }
                }
//*******************Validación de Modelo***************************
                if(ModeloTxt.getText().length() == 0 || ModeloTxt.getText().length() > 15) {
                    lblEModelo.setText("Modelo debe ser mayor a 0 y Menor a 15 caracteres");
                    ModeloTxt.requestFocus();
                    ban = false;
                }
                else{
                    lblEModelo.setText("");
                    if(ModeloTxt.getText().equals(aux.getModelo())){
                        conta++;
                        lblEModelo.setText("Sin Modificaciones");
                    }
                }
//*******************Validación para Marca************************************
                if(MarcaTxt.getText().length() == 0 || MarcaTxt.getText().length() > 15) {
                    lblEMarca.setText("Marca debe ser mayor a 0 y Menor a 15 caracteres");
                    MarcaTxt.requestFocus();
                    ban = false;
                }
                else{
                    lblEMarca.setText("");
                    if(MarcaTxt.getText().equals(aux.getMarca()))
                    {
                        lblEMarca.setText("Sin Modificaciones");
                        conta++;
                    }
                }
//*******************Validación para el local 64**************************************
                if(esnumero(TFL64.getText()))
                {
                    lblESum.setText("");
                    if(TFL64.getText().length() == 0) {
                        TFL64.setText("0");
                        w=0;
                    }
                    else
                        w = Integer.parseInt(TFL64.getText());
                    
                    if(w == 0)
                        conta++;
                }
                else
                {
                    ban=false;
                    lblEnumero.setText("Los datos deben ser números (Error en local 64)");
                    w=0;
                }
 //*******************Validación para el local 127**********************************
                if(esnumero(TFL127.getText()))
                {
                    if(TFL127.getText().length() == 0) {
                        TFL127.setText("0");
                        y=0;
                    }
                    else
                        y = Integer.parseInt(TFL127.getText());
                    
                    if(y==0)
                        conta++;
                }
                else
                {
                    ban=false;
                    lblEnumero.setText("Los datos deben ser números (Error en local 127)");
                    y=0;
                }
//*******************Validación para el local 58************************************
                if(esnumero(TFL58.getText()))
                {
                    if(TFL58.getText().length() == 0) {
                        TFL58.setText("0");
                        z=0;
                    }
                    else
                        z = Integer.parseInt(TFL58.getText());
                    if(z==0)
                        conta++;
                }
                else
                {
                    ban=false;
                    lblEnumero.setText("Los datos deben ser números (Error en local 58)");
                    z=0;
                }
//*******************Validación para el Textfield de Disponibilidad******************************
                if(esnumero(Disptxt.getText()))
                {
                    if(Disptxt.getText().length() == 0) {
                        Disptxt.setText("0");
                        x=0;
                    }
                    else
                        x = Integer.parseInt(Disptxt.getText());
                    if(x==0)
                        conta++;
                }
                else
                {
                    ban=false;
                    lblEnumero.setText("Los datos deben ser números (Error en Mercancia entrante)");
                    x=0;
                }
//*******************Validación para La sumas de los locales con respecto al total de productos*********************
                x = x + aux.getDispon();
                z = z + aux.getL58();
                y = y + aux.getL127();
                w = w + aux.getL64();
                suma = y+z+w;
                if(suma>x)
                {
                    lblEnumero.setText("");
                    lblESum.setText("La suma de los locales no debe ser mayor a la mercancia entrante");
                    ban=false;
                }
                else
                    lblESum.setText("");
                if(ban)
                {
                    if(conta==7){
                        String mensaje = "¡Debe hacer al menos una modificacion! \n";
                        Alert incompleteAlert = new Alert(Alert.AlertType.INFORMATION);
                        incompleteAlert.setTitle("Gestión de Productos");
                        incompleteAlert.setHeaderText(null);
                        incompleteAlert.setContentText(mensaje);
                        incompleteAlert.initOwner(Modificar.getScene().getWindow());
                        incompleteAlert.showAndWait();
                    }else{
                        AgregarSQL(aux);
                        ReinicioTxtFields();
                        conta = 0;
                    }
                }   
            }//FIN ELSE
        });//*********************************************** FIN BOTON MODIFICAR **********************************
        iniciarComboBox();
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
            Logger.getLogger(ModificarAlmacenController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ModificarAlmacenController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ModificarAlmacenController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ModificarAlmacenController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ModificarAlmacenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        reset.setOnAction((ActionEvent e) -> { 
            sqlStmt = "SELECT * FROM productos";
            try {
                Tabla.refresh();
                productos.removeAll(productos);
                Tabla.setItems(ObtenerProd(sqlStmt));
                ReinicioTxtFields();
            } catch (SQLException ex) {
                Logger.getLogger(ModificarAlmacenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });//FIN RESET
        
        Eliminar.setOnAction((ActionEvent e) -> { 
            String resetStmt;
            if(Tabla.getSelectionModel().isEmpty()){
                    String mensaje = "No se ha seleccionado ningun producto. \n";
                    Alert incompleteAlert = new Alert(Alert.AlertType.INFORMATION);
                    incompleteAlert.setTitle("Eliminar Producto");
                    incompleteAlert.setHeaderText(null);
                    incompleteAlert.setContentText(mensaje);
                    incompleteAlert.initOwner(Eliminar.getScene().getWindow());
                    incompleteAlert.showAndWait();
            }else{
                EliminarProd();
                try {
                    resetStmt = "SELECT * FROM productos";
                    Tabla.refresh();
                    productos.removeAll(productos);
                    Tabla.setItems(ObtenerProd(sqlStmt));
                    ReinicioTxtFields();
                } catch (SQLException ex) {
                    Logger.getLogger(ModificarAlmacenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });//FIN ELIMINAR
    }//Fin de INIT  
    
    //FUNCIÓN PARA VALIDAR SI LOS DATOS INGRESADOS EN "MERCANCIA ENTRANTE, Y LOS 3 LOCALES SON NUMEROS
    boolean esnumero (String x)
    {
        
        double number = 0;
        if(x.isEmpty())
        {
            return true;
        }
        else
        {
            try {
                number = Double.parseDouble(x);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
    }
    //FIN DE FUNCION ESNUMERO
    
    //fUNCION AGREGARSQL QUE SERÁ LA ENCARGADA DE EDITAR LA BASE DE DATOS SI HAY ALGUNA MODIFICACIÓN EN LOS CAMPOS.
    public void AgregarSQL( Productos Prod)
    {
        String qry;
        boolean bandera = true;
        int x = Integer.parseInt(Disptxt.getText());
        double y = Double.parseDouble(PrecPtxt.getText());
        double z = Double.parseDouble(PreDtxt.getText());
        int l58 = Integer.parseInt(TFL58.getText());
        int l64 = Integer.parseInt(TFL64.getText());
        int l127 = Integer.parseInt(TFL127.getText());

        qry = "UPDATE productos SET ID='"+IDTxt.getText()+"', Modelo='"+ModeloTxt.getText()+"', Nombre='"+Nomtxt.getText()+"', PrecPub='"+y+"', Marca='"+MarcaTxt.getText()+"', Tipo='"+Tipotxt.getText()+"', PrecDist='"+z+"', Descrip='"+DescTxt.getText()+"', CantidadInicial='"+x+"', L58='"+l58+"', L64='"+l64+"', L127='"+l127+"' WHERE ID='"+Prod.getID()+"'";
        if(bandera==false)
        {
            String mensaje = "No se ha modificado ningun dato \n";
            Alert incompleteAlert = new Alert(Alert.AlertType.INFORMATION);
            incompleteAlert.setTitle("Modificar Almacen");
            incompleteAlert.setHeaderText(null);
            incompleteAlert.setContentText(mensaje);
            incompleteAlert.initOwner(Modificar.getScene().getWindow());
            incompleteAlert.showAndWait();
        }
        else
        {
            if(conn.QueryUpdate(qry)) {
                String mensaje = "Producto Modificado \n";
                Alert incompleteAlert = new Alert(Alert.AlertType.INFORMATION);
                incompleteAlert.setTitle("Gestión de Productos");
                incompleteAlert.setHeaderText(null);
                incompleteAlert.setContentText(mensaje);
                incompleteAlert.initOwner(Modificar.getScene().getWindow());
                incompleteAlert.showAndWait();
                try {
                    Tabla.refresh();
                    productos.removeAll(productos);
                    Tabla.setItems(ObtenerProd(sqlStmt));
                } catch (SQLException ex) {
                    Logger.getLogger(ModificarAlmacenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                String mensaje = "Producto No se ha podido añadir \n";
                Alert incompleteAlert = new Alert(Alert.AlertType.INFORMATION);
                incompleteAlert.setTitle("Gestión de Productos");
                incompleteAlert.setHeaderText(null);
                incompleteAlert.setContentText(mensaje);
                incompleteAlert.initOwner(Modificar.getScene().getWindow());
                incompleteAlert.showAndWait();
                conn.RollBack();
            }     
        }
    }
    
    //FUNCION QUE AUTOCOMPLETA LOS TEXFIELDS CUANDO SE DA DOBLE CLICK A UNA FILA
    public void llenado(Productos Prod)
    {
        String cadena;
        IDTxt.setText(Prod.getID());
        MarcaTxt.setText(Prod.getMarca());
        ModeloTxt.setText(Prod.getModelo());
        Nomtxt.setText(Prod.getNombre());
        Tipotxt.setText(Prod.getTipo());
        DescTxt.setText(Prod.getDescrip());
        cadena = String.valueOf(Prod.getPrecioPub());
        PrecPtxt.setText(cadena);
        cadena = String.valueOf(Prod.getPrecioDist());
        PreDtxt.setText(cadena);
        cadena = String.valueOf(Prod.getDispon());
        lblDispon.setText(" "+cadena);
        Disptxt.setText("0");
        cadena = String.valueOf(Prod.getL127());
        lbl127Dispon.setText(" "+cadena);
        TFL127.setText("0");
        cadena = String.valueOf(Prod.getL58());
        lbl58Dispon.setText(" "+cadena);
        TFL58.setText("0");
        cadena = String.valueOf(Prod.getL64());
        lbl64Dispon.setText(" "+cadena);
        TFL64.setText("0");
    }
    
    public void ReinicioTxtFields(){
        IDTxt.setText("");
        MarcaTxt.setText("");
        ModeloTxt.setText("");
        Nomtxt.setText("");
        Tipotxt.setText("");
        DescTxt.setText("");
        PrecPtxt.setText("");
        PreDtxt.setText("");
        Disptxt.setText("");
        TFL127.setText("");
        TFL58.setText("");
        TFL64.setText("");
        lblEID.setText("");
        lblEModelo.setText("");
        lblEMarca.setText("");
        lblESum.setText("");
        lblEnumero.setText("");
        lbl127Dispon.setText("");
        lbl64Dispon.setText("");
        lbl58Dispon.setText("");
        lblDispon.setText("");
    }
}
