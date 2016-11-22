package xxcell.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;
import xxcell.Conexion.Conexion;

public class AgregarProductoController implements Initializable {
    
    //Conexión a MYSQL
    Conexion conn = new Conexion(); 
    
    //Funciones
    Funciones func = new Funciones();
    
    //BOTONES 
    @FXML
    private JFXButton Agregar;
        
    @FXML
    private JFXButton Cancelar;
    //FIN BOTONES
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
    private Label lblVistaActual;
    @FXML
    private Label lblVistaEntradas;
    @FXML
    private Label lblVistaSalidas;
    
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
    List <String> possibleWords_ID = new ArrayList<>();
    Set<String> hs_ID = new HashSet<>(); // HashSet Para quitar elementos repetidos de POSSIBLEWORDS


    boolean ban; //Bandera para validar los datos, algún campo falla éste se pone en falso
    
    //Autocompletados en los textfields
    public void Autocompletar()
    {
        String query;
        //AUTOCOMPLETADO PARA EL TEXTFIEL DE MARCA
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
        
         hs_Tipo.addAll(possibleWords_Tipo);
        possibleWords_Tipo.clear();
        possibleWords_Tipo.addAll(hs_Tipo);
        TextFields.bindAutoCompletion(Tipotxt, possibleWords_Tipo);
        
        //AUTOCOMPLETADO PARA EL TEXTFIELD ID
        query = "SELECT * FROM productos WHERE Nombre LIKE '%" + Nomtxt.getText() + "%'";
        if(conn.QueryExecute(query)){
            try {
                while(conn.setResult.next()){                  
                    possibleWords_ID.add(conn.setResult.getString("ID"));
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(AgregarProductoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        hs_ID.addAll(possibleWords_ID);
        possibleWords_ID.clear();
        possibleWords_ID.addAll(hs_ID);
        TextFields.bindAutoCompletion(IDTxt, possibleWords_ID);
    }   
    
    //Funcion para agregar a la base de datos el producto que desee
    public void AgregarSQL() {
        int x = Integer.parseInt(Disptxt.getText());
        double y = Double.parseDouble(PrecPtxt.getText());
        double z = Double.parseDouble(PreDtxt.getText());
        int l58 = Integer.parseInt(TFL58.getText());
        int l64 = Integer.parseInt(TFL64.getText());
        int l127 = Integer.parseInt(TFL127.getText());
        int entradas = Integer.parseInt(lblVistaEntradas.getText());
        int salidas = Integer.parseInt(lblVistaSalidas.getText());
        
        String query = "SELECT * FROM productos WHERE usuario='"+IDTxt.getText()+"'";
        conn.QueryExecute(query);
            try {
                //SI EL ID YA EXISTE EN LA BASE DE DATOS cancela el alta.
                if(conn.setResult.first()) {
                    String mensaje = "¡El ID de Producto ya existe! \nSi tiene mercancia entrante vaya al modulo 'Modificar Productos' para añadirlos \n";
                    Alert incompleteAlert = new Alert(Alert.AlertType.INFORMATION);
                    incompleteAlert.setTitle("Gestión de Productos");
                    incompleteAlert.setHeaderText(null);
                    incompleteAlert.setContentText(mensaje);
                    incompleteAlert.initOwner(Agregar.getScene().getWindow());
                    incompleteAlert.showAndWait();                   
                } 
                //Sino Existe, agregará uno Nuevo
                else {
                    String qry = "Insert into productos(ID, Modelo, Nombre, PrecPub,";
                    qry += "Marca, Tipo, PrecDist, Descrip, CantidadInicial, CantidadActual, ";
                    qry += "Entradas, Salidas, L58, L64, L127)";
                    qry += "values ('"+IDTxt.getText()+"', '"+ModeloTxt.getText()+"', '"+Nomtxt.getText()+"',";
                    qry += "'"+y+"', '"+MarcaTxt.getText()+"', '"+Tipotxt.getText()+"', '"+z+"', '"+DescTxt.getText()+"',";
                    qry += "'"+x+"', '"+x+"', '"+entradas+"', '"+salidas+"', '"+l58+"', '"+l64+"', '"+l127+"')";
                    if(conn.QueryUpdate(qry)) {
                        String mensaje = "Producto Añadido \n";
                        Alert incompleteAlert = new Alert(Alert.AlertType.INFORMATION);
                        incompleteAlert.setTitle("Gestión de Productos");
                        incompleteAlert.setHeaderText(null);
                        incompleteAlert.setContentText(mensaje);
                        incompleteAlert.initOwner(Agregar.getScene().getWindow());
                        incompleteAlert.showAndWait();
                    }
                    else {
                        String mensaje = "Producto No se ha podido añadir \n";
                        Alert incompleteAlert = new Alert(Alert.AlertType.INFORMATION);
                        incompleteAlert.setTitle("Gestión de Productos");
                        incompleteAlert.setHeaderText(null);
                        incompleteAlert.setContentText(mensaje);
                        incompleteAlert.initOwner(Agregar.getScene().getWindow());
                        incompleteAlert.showAndWait();
                        conn.RollBack();
                    }        
                }
            } catch (SQLException e) {
                //No pondré nada aquí, me piro.
            }     
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        StringConverter<String> formatter;
        //TextFormatter para permitir solo el uso de numeros en los campos Telefono y Numero de empleado
        formatter = new StringConverter<String>() {
            @Override
            public String fromString(String string) {
                if (string.length() == 13)
                   return string;
                else
                if (string.length() == 12 && string.indexOf('-') == -1)
                   return string.substring(0, 4) + "-" + 
                          string.substring(4);
                else
                   return "";
             }

            @Override
            public String toString(String object) {
               if (object == null)   // only null when called from 
                  return ""; // TextFormatter constructor 
                                     // without default
               return object;
            }
         };
        
        UnaryOperator<TextFormatter.Change> filter;
        filter = (TextFormatter.Change change) -> {
            String text = change.getText();
            for (int i = 0; i < text.length(); i++)
                if (!Character.isDigit(text.charAt(i)))
                    return null;
            return change;
        };
        
        
        //Agrega a los texfields la lista de posibles resultados
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
        IDTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            Autocompletar();
            });
        Disptxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            txtEjemploJtextFieldChanged();
        });
        //TextFormater para solo poder teclear numeros
        TFL127.setTextFormatter(new TextFormatter<String>(formatter,"",filter));
        TFL64.setTextFormatter(new TextFormatter<String>(formatter,"",filter));
        TFL58.setTextFormatter(new TextFormatter<String>(formatter,"",filter));
        Disptxt.setTextFormatter(new TextFormatter<String>(formatter,"",filter));

        //Boton Agregar
        Agregar.setOnAction((ActionEvent e) -> {
            int x, y, z, w;
            int suma;    
            ban=true;
//*******************Validación de ID *************************
            if(IDTxt.getText().length() == 0 || IDTxt.getText().length() > 13) {
                lblEID.setText("ID debe ser mayor a 0 y Menor a 14 caracteres");
                IDTxt.requestFocus();
                ban = false;
            }
            else
                lblEID.setText("");
//*******************Validación de Modelo***************************
            if(ModeloTxt.getText().length() == 0 || ModeloTxt.getText().length() > 15) {
                lblEModelo.setText("Modelo debe ser mayor a 0 y Menor a 15 caracteres");
                ModeloTxt.requestFocus();
                ban = false;
            }
            else
                lblEModelo.setText("");
//*******************Validación para Marca************************************
            if(MarcaTxt.getText().length() == 0 || MarcaTxt.getText().length() > 15) {
                lblEMarca.setText("Marca debe ser mayor a 0 y Menor a 15 caracteres");
                MarcaTxt.requestFocus();
                ban = false;
            }
            else
                lblEMarca.setText("");
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
            }
            else
            {
                ban=false;
                lblEnumero.setText("Los datos deben ser números (Error en Mercancia entrante)");
                x=0;
            }
//*******************Validación para La sumas de los locales con respecto al total de productos*********************
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
                AgregarSQL();
                Stage stage;
                stage = (Stage) Agregar.getScene().getWindow();
                stage.close();
            }           
        });
        
        Cancelar.setOnAction((ActionEvent e) -> {       
            Stage stage;
            stage = (Stage) Cancelar.getScene().getWindow();
            stage.close();
        });      
    } //FIN INIT
    
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
    
    private void txtEjemploJtextFieldChanged(){
	int suma;
	int inicial;
	int entradas;
	int salidas;
		
	if(!func.IsInteger(Disptxt.getText()))
            this.lblVistaActual.setText("Error");
	else{
            inicial = Integer.valueOf(Disptxt.getText());
            entradas = Integer.valueOf(lblVistaEntradas.getText());
            salidas = Integer.valueOf(lblVistaSalidas.getText());
            suma = inicial+entradas-salidas;
            this.lblVistaActual.setText(String.valueOf(suma));
	}
    }
}
