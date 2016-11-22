package xxcell.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import xxcell.Conexion.Conexion;
import xxcell.model.Detalles;
import xxcell.model.Empleados;

public class NominaEmpleadosController implements Initializable {

    //Varibales para las conexiones y los querys
    Conexion conn = new Conexion();
    String qry = "SELECT * FROM empleado";
    
    float TotalVenta, totalvendido = 0, sueldo=0;
    
    Date fechaHoy = new Date();
    
    Empleados aux = new Empleados();
    int cantidadproductos = 0;
    
    //Buttons
    @FXML
    private JFXButton ObtenerSalario;
    @FXML
    private JFXButton ImprimirNomina;
    
    // Labels
    @FXML
    private Label lblCantidad;
    @FXML
    private Label lblPorcentaje;
    @FXML
    private Label NombreEmpleado;
    @FXML
    private Label NumeroEmpleado;
    @FXML
    private Label lblSueldo;
    @FXML
    private Label lblFechaFin;
    @FXML
    private Label lblError;
    
    //CheckBox
    @FXML
    private JFXCheckBox Lunes;
    @FXML
    private JFXCheckBox Martes;
    @FXML
    private JFXCheckBox Miercoles;
    @FXML
    private JFXCheckBox Jueves;
    @FXML
    private JFXCheckBox Viernes;
    @FXML
    private JFXCheckBox Sabado;
    @FXML
    private JFXCheckBox Domingo;
    
    //DatePickers
    
    @FXML
    private JFXDatePicker FechaInicial;
    @FXML
    private JFXDatePicker FechaFinal;
    
    @FXML
    private TableView<Detalles> TablaVentas;
    @FXML
    private TableColumn<Detalles, Number> Folio;
    @FXML
    private TableColumn<Detalles, String> Producto;
    @FXML
    private TableColumn<Detalles, Number> Cantidad;
    @FXML
    private TableColumn<Detalles, Number> Total;
    
    //Lista para Llenar la tabla de Detalles;
    ObservableList<Detalles> detalles = FXCollections.observableArrayList();
    
    //Variables de TableView para la Tabla Empleados
    @FXML
    private TableView<Empleados> TablaEmpleados;
    @FXML
    private TableColumn<Empleados, String> Nombre;
    @FXML
    private TableColumn<Empleados, String> Apellido;  
    
    //Lista para Llenar la tabla de Empleados;
    ObservableList<Empleados> empleados = FXCollections.observableArrayList();
    
     //Función que obtendrá los objetos empleados para ingresarlos al tableview
    public ObservableList<Empleados> ObtenerEmpleados() throws SQLException{
        String nom, apel, usuar;
        int numEmp;
                
        if(conn.QueryExecute(qry))
        {
            while(conn.setResult.next())
            {
                usuar = conn.setResult.getString("usuario");
                nom = conn.setResult.getString("Nombre");
                apel = conn.setResult.getString("Apellido");
                numEmp = conn.setResult.getInt("NumEmpleado");
                empleados.add(new Empleados(usuar, nom, apel, numEmp, conn.setResult.getInt("Nivel")));
            }
        }
        return empleados;
    }
    //Función que obtendrá los objetos detalle de Ventas para ingresarlos al tableview
    public ObservableList<Detalles> ObtenerDetalles() throws SQLException{
        String codigo, producto, cadena;
        int folio, cantidad;
        LocalDate FechaInit, FinalFecha;
        FechaInit = FechaInicial.getValue();
        FinalFecha = FechaFinal.getValue();
        float total;
        String STSQL = "SELECT * FROM tblventas INNER JOIN tblventadetalle ON tblventas.ventaFolio = tblventadetalle.ventaFolio AND NumEmpleado = '"+aux.getNumEmp()+"' ";
        STSQL += "WHERE tblventas.ventaFecha BETWEEN '"+FechaInit+"' and '"+FinalFecha+"'";
        System.out.println(STSQL);
        if(conn.QueryExecute(STSQL))
        {
            while (conn.setResult.next())
            {
                folio = conn.setResult.getInt("ventaFolio");
                codigo = conn.setResult.getString("productoCodigo");
                producto = conn.setResult.getString("productoNombre");
                cantidad = conn.setResult.getInt("ventaFolio");
                total = (conn.setResult.getFloat("productoPrecio"))*cantidad;
                cantidadproductos = cantidadproductos + cantidad;
                totalvendido = totalvendido + total;
                cadena = aux.getNombre();
                cadena += " " + aux.getApellido();
                lblCantidad.setText(String.valueOf(cantidadproductos));
                TotalVenta = totalvendido;
                NombreEmpleado.setText(cadena);
                NumeroEmpleado.setText(String.valueOf(aux.getNumEmp()));
                detalles.add(new Detalles(folio, codigo, producto, cantidad, total));
            } 
        }  
        return detalles;
    }
    
    public void Salario() throws SQLException{
        int cantidadMeta, faltas;
        String cadena;
        float descuento = 0;
        
        String qrySueldo = "SELECT * FROM sueldos where Nivel = '"+aux.getNivel()+"'";
        conn.QueryExecute(qrySueldo);
        if(conn.setResult.first()){
            cantidadMeta = conn.setResult.getInt("NumVentas");
            if(cantidadproductos > cantidadMeta){
                lblPorcentaje.setText(".04%");
                faltas = obtenerFaltas();
                if(faltas>1){
                    faltas--;
                    descuento = faltas * conn.setResult.getFloat("DescuentoFalta");
                    System.out.println("Descuento = "+descuento+" Faltas = "+ faltas +" * Descuento Falta ="+ conn.setResult.getFloat("DescuentoFalta"));
                }
                System.out.println(descuento);
                sueldo = totalvendido * conn.setResult.getFloat("ComisionMeta");
                System.out.println("Total Vendido * Comision "+ sueldo);
                sueldo = sueldo + conn.setResult.getFloat("Base");
                System.out.println("Sueldo + Base "+ sueldo);
                sueldo = sueldo - descuento;
                System.out.println("Sueldo - Descuento "+ sueldo);
                lblSueldo.setText(Float.toString(sueldo));    
            }
            else{
                faltas = obtenerFaltas();
                if(faltas>1){
                    faltas--;
                    descuento = faltas * conn.setResult.getFloat("DescuentoFalta");
                }
                lblPorcentaje.setText(".02%");               
                sueldo = totalvendido * conn.setResult.getFloat("ComisionBase");
                System.out.println("Total Vendido * Comision "+ sueldo);
                sueldo = sueldo + conn.setResult.getFloat("Base");
                System.out.println("Sueldo + Base "+ sueldo);
                sueldo = sueldo - descuento;
                System.out.println("Sueldo - Descuento "+ sueldo);
                lblSueldo.setText(Float.toString(sueldo));    
             }
        }
    }
    
    private int obtenerFaltas() {
        int faltas=0;
        if(Lunes.selectedProperty().get() == true){
            faltas++;
        }
        if(Martes.selectedProperty().get() == true){
            faltas++;
        }
        if(Miercoles.selectedProperty().get() == true){
            faltas++;
        }
        if(Jueves.selectedProperty().get() == true){
            faltas++;
        }
        if(Viernes.selectedProperty().get() == true){
            faltas++;
        }
        if(Sabado.selectedProperty().get() == true){
            faltas++;
        }
        if(Domingo.selectedProperty().get() == true){
            faltas++;
        }
        return faltas;
    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        lblFechaFin.setText(formato.format(fechaHoy));

        Nombre.setCellValueFactory(cellData -> cellData.getValue().NombreProperty());
        Apellido.setCellValueFactory(cellData -> cellData.getValue().ApellidoProperty());
        try {
            TablaEmpleados.setItems(ObtenerEmpleados());
        } catch (SQLException ex) {
            Logger.getLogger(ModificarEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Folio.setCellValueFactory(cellData -> cellData.getValue().FolioProperty());
        Producto.setCellValueFactory(cellData -> cellData.getValue().ProductoProperty());
        Cantidad.setCellValueFactory(cellData -> cellData.getValue().CantidadProperty());
        Total.setCellValueFactory(cellData -> cellData.getValue().TotalProperty());
        
        //Seleccionar un objeto con doble click
        TablaEmpleados.setOnMousePressed(new EventHandler<MouseEvent>() {
             @Override 
             public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    if(TablaEmpleados.getSelectionModel().getSelectedItem() != null ){
                        aux = TablaEmpleados.getSelectionModel().getSelectedItem();
                        try {
                            
                            if(FechaInicial.getValue() == null && FechaFinal.getValue() == null){
                                lblError.setText("Seleccione las fechas");
                            }
                            else{
                                lblError.setText("");
                                TablaVentas.refresh();
                                detalles.removeAll(detalles);
                                TablaVentas.setItems(ObtenerDetalles());
                               Salario(); 
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        
        ObtenerSalario.setOnAction((ActionEvent e) -> {       
            try {
                Salario();
            } catch (SQLException ex) {
                Logger.getLogger(NominaEmpleadosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }//FIN INITIALIZE      
}
