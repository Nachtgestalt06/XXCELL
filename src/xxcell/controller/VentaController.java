package xxcell.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import xxcell.Conexion.Conexion;
import static xxcell.controller.LoginController.scene;
import xxcell.model.ProductoVenta;

public class VentaController implements Initializable {
    
    @FXML
    private StackPane rootVenta;
    
    @FXML
    private BorderPane paneBotones;
    //*****************Botones del view ***************
    @FXML
    private JFXButton btnSearch;
    
    @FXML
    private JFXButton btnCancelar;
    
    @FXML
    private JFXButton btnCobrar;

    @FXML
    private JFXButton btnIniciar;
    
    @FXML
    private JFXButton btnVentaEspera;
    //****************Etiquetas del view *************
    @FXML
    private Label lblImporteLetras;
    
    @FXML
    private Label lblUsuario;
    
    @FXML
    private Label lblFecha;
    
    @FXML
    private Label lblDescuento;
    
    //Lista para Llenar la tabla de productos;
    ObservableList<ProductoVenta> productosVenta = FXCollections.observableArrayList();
    ObservableList<ObservableList<ProductoVenta>> auxProductosVenta = FXCollections.observableArrayList();
    
    @FXML
    private TableView<ProductoVenta> tblProductos;
   
    @FXML
    private TableColumn<ProductoVenta, String> tblColCodigo;

    @FXML
    private TableColumn<ProductoVenta, String> tblColNombre;

    @FXML
    private TableColumn<ProductoVenta, String> tblColModelo;

    @FXML
    private TableColumn<ProductoVenta, String> tblColPrecio;

    @FXML
    private TableColumn<ProductoVenta, String> tblColCantidad;

    @FXML
    private TableColumn<ProductoVenta, String> tblColImporte;
    
    @FXML
    private TableColumn<ProductoVenta, String> tblColDescuento;
    
    @FXML
    private JFXTextField txtCodigoBarras;
    
    @FXML
    private JFXTextField txtDescuento;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblProductosCant;

    @FXML
    private Spinner<Integer> spnFolio;
    
    boolean agruparProductos;
    boolean flagVentaEspera = false;
    Conexion conn = new Conexion();
    
    int usuario;
    
    private Alert alert;
    private Dialog dialog;
    
    Funciones fun = new Funciones();
    
    @FXML
    void BusquedaTabla(ActionEvent event) throws IOException, SQLException {
        Parent principal;
        principal = FXMLLoader.load(getClass().getResource("/xxcell/view/BusquedaVenta.fxml"));
        Stage principalStage = new Stage();
        scene = new Scene(principal);
        principalStage.setScene(scene);
        principalStage.initModality(Modality.APPLICATION_MODAL);
        principalStage.initOwner(btnSearch.getScene().getWindow());
        principalStage.showAndWait(); 
        System.out.println(Variables_Globales.BusquedaVenta.getID());
         if(Variables_Globales.BusquedaVenta.getID() != null){
            txtCodigoBarras.setText(Variables_Globales.BusquedaVenta.getID());
            if(!ProductoEnVenta()){
                try {
                    tblProductos.setItems(ObtenerProd());
                } catch (SQLException ex) {
                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            txtCodigoBarras.requestFocus();
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(()-> {
            paneBotones.requestFocus();
            btnSearch.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN), new Runnable(){
                @Override
                public void run() {
                    btnSearch.fire();
                }
            });
            
            btnIniciar.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN), new Runnable(){
                @Override
                public void run() {
                    btnIniciar.fire();
                }            
            });
            
            btnCobrar.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN), new Runnable(){
                @Override
                public void run() {
                    btnCobrar.fire();
                }            
            }); 
            
            txtDescuento.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN), new Runnable(){
                @Override
                public void run() {
                    txtDescuento.requestFocus();
                }            
            });
            
            btnCancelar.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN), new Runnable(){
                @Override
                public void run() {
                    btnCancelar.fire();
                }            
            });
        });
        
        tblProductos.setPlaceholder(new Label(""));
        
        tblColCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
        tblColNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        tblColModelo.setCellValueFactory(cellData -> cellData.getValue().modeloProperty());
        tblColCantidad.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty());
        tblColPrecio.setCellValueFactory(cellData -> cellData.getValue().precioProperty());
        tblColDescuento.setCellValueFactory(cellData -> cellData.getValue().descuentoProperty());
        tblColImporte.setCellValueFactory(cellData -> cellData.getValue().importeProperty());
        
        //Vuelve editable la columna Cantidad
        tblColCantidad.setCellFactory(TextFieldTableCell.forTableColumn());
        tblColCantidad.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductoVenta, String>>() {
            //Cambia los valores de los label Total y Productos al editar la celda
            @Override
            public void handle(TableColumn.CellEditEvent<ProductoVenta, String> event) {
                double importe;
                int productos;
                double total;
                String cantidad;
                String precio;
                String preimporte;
                String precantidad;
                
                DecimalFormat formateador = new DecimalFormat("####.00");
                
                precantidad = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getCantidad();
                preimporte = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getImporte();
                
                ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).setCantidadProperty(event.getNewValue());
                
                cantidad = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getCantidad();
                precio = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getPrecio();
                
                
                importe = Integer.valueOf(cantidad) * Double.valueOf(precio);
                productos = (Integer.valueOf(lblProductosCant.getText()) + Integer.valueOf(cantidad))-Integer.valueOf(precantidad);
                
                lblProductosCant.setText(String.valueOf(productos));
                
                ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).setImporteProperty(Double.toString(importe));
                
                total = (Double.valueOf(lblTotal.getText()) + Double.valueOf(importe)) - Double.valueOf(preimporte);
                lblTotal.setText(formateador.format(total));
                
                tblProductos.refresh();
            }
        });
        
        //Vuelve editable la columna Costo
        tblColPrecio.setCellFactory(TextFieldTableCell.forTableColumn());
        tblColPrecio.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductoVenta, String>>() {
            //Cambia los valores de los label Total y Productos al editar la celda
            @Override
            public void handle(TableColumn.CellEditEvent<ProductoVenta, String> event) {
                double importe;
                double total;
                String cantidad;
                String precio;
                String preciobase;
                String preimporte;
                String descuento;
                String importeS;
                double descontado;
                
                DecimalFormat formateadorPorc = new DecimalFormat("####.##");
                DecimalFormat formateador = new DecimalFormat("####.00");
                
                preimporte = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getImporte();
                preciobase = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getPreciobase();
                                
                ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).setPrecioProperty(event.getNewValue());
                
                precio = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getPrecio();
                cantidad = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getCantidad();
                precio = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getPrecio();
                
                descontado = 100 - ((Double.valueOf(precio)/Double.valueOf(preciobase))*100);
                System.out.println(descontado);
                
                importe = Integer.valueOf(cantidad) * Double.valueOf(precio);
                importeS = formateador.format(importe);
                descuento = formateadorPorc.format(descontado);
                                
                ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).setImporteProperty(importeS);
                ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).setDescuentoProperty(descuento);
                
                
                total = (Double.valueOf(lblTotal.getText()) + Double.valueOf(importe)) - Double.valueOf(preimporte);
                lblTotal.setText(formateador.format(total));
                
                tblProductos.refresh();
            }
        });
        
        //Vuelve editable la columna Descuento
        tblColDescuento.setCellFactory(TextFieldTableCell.forTableColumn());
        tblColDescuento.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductoVenta, String>>() {
            //Cambia los valores de los label Total y Productos al editar la celda
            @Override
            public void handle(TableColumn.CellEditEvent<ProductoVenta, String> event) {
                double importe;
                int productos;
                double total;
                double descontado;
                String descuento;
                String cantidad;
                String precio;
                String preimporte;
                String precantidad;
                String importeS;
                
                DecimalFormat formateador = new DecimalFormat("####.00");
                
                precantidad = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getCantidad();
                preimporte = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getImporte();
                
                ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).setDescuentoProperty(event.getNewValue());
                descuento = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getDescuento();
                
                cantidad = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getCantidad();
                precio = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getPreciobase();
                
                descontado = Integer.valueOf(descuento);
                productos = (Integer.valueOf(lblProductosCant.getText()) + Integer.valueOf(cantidad))-Integer.valueOf(precantidad);
                importe = Double.valueOf(precio) - ((descontado/100) * Double.valueOf(precio));
                
                importeS = formateador.format(importe);
                                
                ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).setImporteProperty(importeS);
                
                total = (Double.valueOf(lblTotal.getText()) + importe) - Double.valueOf(preimporte);
                lblTotal.setText(formateador.format(total));
                
                tblProductos.refresh();
            }
        });
        
        //Vuelve editable la columna Importe
        tblColImporte.setCellFactory(TextFieldTableCell.forTableColumn());
        tblColImporte.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductoVenta, String>>() {
            //Cambia los valores de los label Total y Productos al editar la celda
            @Override
            public void handle(TableColumn.CellEditEvent<ProductoVenta, String> event) {
                String importe;
                int productos;
                double total;
                String cantidad;
                String precio;
                String preimporte;
                String precantidad;
                
                DecimalFormat formateador = new DecimalFormat("####.00");
                
                precantidad = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getCantidad();
                preimporte = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getImporte();
                
                ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).setImporteProperty(event.getNewValue());
                importe = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getImporte();
                                
                total = (Double.valueOf(lblTotal.getText()) + Double.valueOf(importe))- Double.valueOf(preimporte);
                lblTotal.setText(formateador.format(total));
                
                tblProductos.refresh();
            }
        });
        
        tblProductos.setEditable(true);
        
        inicializa();
        
        Date fechaHoy = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        
        lblFecha.setText(formato.format(fechaHoy));
                
        txtCodigoBarras.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode() == KeyCode.ENTER){
                if(!ProductoEnVenta()){
                    try {
                        tblProductos.setItems(ObtenerProd());
                    } catch (SQLException ex) {
                        Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });  
        
        txtDescuento.setOnKeyPressed((KeyEvent event) -> {
            double importe;
            int productos;
            double total;
            double descontado;
            String descuento;
            String cantidad;
            String precio;
            String preimporte;
            String precantidad;
            String importeS;
            
            DecimalFormat formateadorPorc = new DecimalFormat("####.##");
            DecimalFormat formateador = new DecimalFormat("####.00");
            
            if(event.getCode() == KeyCode.ENTER){
                for(int fila = 0; fila < productosVenta.size(); fila++){
                    precantidad = productosVenta.get(fila).getCantidad();
                    preimporte = productosVenta.get(fila).getImporte();
                    
                    productosVenta.get(fila).setDescuentoProperty(txtDescuento.getText());

                    descuento = productosVenta.get(fila).getDescuento();

                    //cantidad = ((ProductoVenta) event.getTableView().getItems().get(event.getTablePosition().getRow())).getCantidad();
                    precio = productosVenta.get(fila).getPreciobase();

                    descontado = Integer.valueOf(descuento);
                    //productos = (Integer.valueOf(lblProductosCant.getText()) + Integer.valueOf(cantidad))-Integer.valueOf(precantidad);
                    importe = Double.valueOf(precio) - ((descontado/100) * Double.valueOf(precio));

                    importeS = formateador.format(importe);

                    productosVenta.get(fila).setImporteProperty(importeS);

                    total = (Double.valueOf(lblTotal.getText()) + importe) - Double.valueOf(preimporte);
                    lblTotal.setText(formateador.format(total));
                    
                    
                }
                tblProductos.refresh();
                txtCodigoBarras.requestFocus();
            }
        });        
        
        lblTotal.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String sEntero;
            String decimal;
            int longitud;
            int iEntero;

            longitud = lblTotal.getText().length();

            sEntero = lblTotal.getText().substring(0, longitud-3);
            iEntero = Integer.valueOf(sEntero);

            decimal = lblTotal.getText().substring(longitud - lblTotal.getText().length())+"/100 M.N";
            lblImporteLetras.setText(fun.getStringofNumber(iEntero)+" PESOS "+decimal);
        });
    }    
        
    @FXML
    void ActionIniciar(ActionEvent event) {
        String qry;
        String cadena;
        TextInputDialog dialog = new TextInputDialog("Numero de Empleado");
        dialog.setTitle("Punto de Venta XXCELL");
        dialog.initOwner(btnIniciar.getScene().getWindow());
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.initOwner(btnIniciar.getScene().getWindow());
        
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) {
            dialog.setHeaderText("Inicio de Venta");
            dialog.setContentText("Ingrese su número de empleado:");
            System.out.println("Your name: " + result.get());
            if(esnumero(result.get())){
                System.out.println("Es número");
                usuario = Integer.parseInt(result.get());
                qry = "SELECT * FROM empleado where NumEmpleado = '"+usuario+"'";
                conn.QueryExecute(qry);
                try {
                    if(conn.setResult.first()) {
                        cadena = conn.setResult.getString("Nombre");
                        cadena = cadena + " " + conn.setResult.getString("Apellido");
                        lblUsuario.setText(cadena);
                        HabilitaVenta(true);
                        //txtCodigoBarras.requestFocus();
                        txtDescuento.requestFocus();
                    } else {
                        alert.setHeaderText("Error");
                        alert.setContentText("Debe ingresar su Numero de Empleado");
                        alert.showAndWait();
                    }
                } catch (Exception ex){

                }     
            } else
                System.out.println("No es número");
        }
        
    }
    
    @FXML
    void ActionCancelar(ActionEvent event) {
        System.out.println("Cancelar");
        if(!btnCancelar.isDisabled())
            inicializa();
    }
    
    @FXML
    void ActionCobrar(ActionEvent event) throws IOException {
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.initOwner(btnIniciar.getScene().getWindow());
        if(productosVenta.isEmpty()){
            alert.setHeaderText("Error");
            alert.setContentText("Para cobrar es necesario introducir al menos un producto");
            alert.showAndWait();
            txtCodigoBarras.requestFocus();
        }
        else {
            Variables_Globales.totalVenta = lblTotal.getText();
            Variables_Globales.ventaRealizada = false;
            
            mostrarCobrar();
            
            if(Variables_Globales.ventaRealizada)
                GrabarVenta();
        }

    }
    
    @FXML
    void ActionVentaEspera(ActionEvent event) throws IOException {
        Parent principal;
        principal = FXMLLoader.load(getClass().getResource("/xxcell/view/Venta.fxml"));
        Stage principalStage = new Stage();
        scene = new Scene(principal);
        principalStage.setScene(scene);
        principalStage.setMaximized(true);
        principalStage.initModality(Modality.APPLICATION_MODAL);
        principalStage.initOwner(btnCancelar.getScene().getWindow());
        principalStage.showAndWait();
        
    }    
    
    //Accion para suprimir la fila seleccionada con la tecla DELETE
    @FXML
    void ActionDelete(KeyEvent event) {
        ObservableList<ProductoVenta> productoSeleccionado, todosProductos;
        ProductoVenta producto;
        double importe;
        int productos;
        double total;
        String cantidad;
        String precio;
        String preimporte;
        String precantidad;
        DecimalFormat formateador = new DecimalFormat("####.00");
               
        if(event.getCode() == KeyCode.DELETE){
            //Si se presiona la tecla DELETE se deben actualizar los label Total y Productos y eliminar la fila seleccionada
            producto = tblProductos.getSelectionModel().getSelectedItem();
            todosProductos = tblProductos.getItems();
            productoSeleccionado = tblProductos.getSelectionModel().getSelectedItems();
            
            precantidad = producto.getCantidad().toString();
            preimporte = producto.getImporte().toString();
            cantidad = producto.getCantidad().toString();

            productos = (Integer.valueOf(lblProductosCant.getText()) - Integer.valueOf(cantidad));

            lblProductosCant.setText(String.valueOf(productos));

            total = Double.valueOf(lblTotal.getText()) - Double.valueOf(preimporte);
            lblTotal.setText(formateador.format(total));
                        
            productoSeleccionado.forEach(todosProductos::remove);
            txtCodigoBarras.requestFocus();
        }
        
        if(event.getCode() == KeyCode.ADD){
            producto = tblProductos.getSelectionModel().getSelectedItem();
            cantidad = producto.getCantidad();
            precio = producto.getPrecio();
            System.out.println(producto.getCodigo());
            
            //precio = productosVenta.get(fila).getPrecio().toString();

            productos = Integer.valueOf(cantidad) + 1;
            producto.setCantidadProperty(Integer.toString(productos));
            //productosVenta.get(fila).setCantidadProperty(Integer.toString(productos));

            productos = Integer.valueOf(lblProductosCant.getText()) + 1;
            lblProductosCant.setText(String.valueOf(productos));

            //Importe de la venta
            total  = Double.valueOf(precio)*(Integer.valueOf(cantidad)+1);
            producto.setImporteProperty(Double.toString(total));
            //productosVenta.get(fila).setImporteProperty(Double.toString(total));

            total = Double.valueOf(lblTotal.getText()) + Double.valueOf(precio);
            lblTotal.setText(formateador.format(total));

            tblProductos.refresh();
            txtCodigoBarras.setText("");
        }
        
        if(event.getCode() == KeyCode.SUBTRACT){
            producto = tblProductos.getSelectionModel().getSelectedItem();
            cantidad = producto.getCantidad();
            if(Integer.valueOf(cantidad) < 1){
                precio = producto.getPrecio();

                productos = Integer.valueOf(cantidad) - 1;
                producto.setCantidadProperty(Integer.toString(productos));

                lblProductosCant.setText(String.valueOf(productos));

                //Importe de la venta
                total  = Double.valueOf(precio)*(Integer.valueOf(cantidad)-1);
                producto.setImporteProperty(Double.toString(total));

                total = Double.valueOf(lblTotal.getText()) - Double.valueOf(precio);
                lblTotal.setText(formateador.format(total));

                tblProductos.refresh();
                txtCodigoBarras.setText("");
            }
        }
    }
    
    public void inicializa(){
        Date fechaHoy = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        lblFecha.setText(formato.format(fechaHoy));
        spnFolio.setValueFactory((new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000000, getFolioVenta())));
        spnFolio.getEditor().setStyle("-fx-font: 20pt 'verdana'; "
                + "-fx-text-fill: black; "
                + "-fx-alignment: CENTER_RIGHT;");    
        lblProductosCant.setText("0");
        lblTotal.setText("0.00");
        productosVenta.clear();
        lblUsuario.setText(null);
        lblDescuento.setVisible(false);
        lblImporteLetras.setText(null);
        txtDescuento.setText("0");
        txtDescuento.setVisible(false);
        btnVentaEspera.setDisable(true);
        btnVentaEspera.setVisible(false);
                
        HabilitaVenta(false);
    }
    
    private void HabilitaVenta(boolean venta){
            //Habilitados en venta
            tblProductos.setDisable(!venta);
            btnCobrar.setDisable(!venta);
            txtCodigoBarras.setDisable(!venta);
            btnIniciar.setDisable(venta);
            btnSearch.setDisable(!venta);
            spnFolio.setValueFactory((new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000000, getFolioVenta())));
            txtDescuento.setDisable(!venta);
            txtDescuento.setVisible(venta);
            lblDescuento.setVisible(venta);
            btnVentaEspera.setDisable(!venta);
            btnVentaEspera.setVisible(venta);
            
    }
    
    private boolean ProductoEnVenta(){
        int fila;
        String dato = "";
        String precio;
        String cantidad;
        String costo;
        boolean result = false;
        int productos;
        double total;
        double utilidad;

        DecimalFormat formateador = new DecimalFormat("####.00");
        
        dato = txtCodigoBarras.getText().trim().toUpperCase();        
        
        if(!dato.isEmpty()){
            for(fila = 0; fila < productosVenta.size(); fila++) {
                if(productosVenta.get(fila).getCodigo().toUpperCase().equals(dato)){
                    cantidad = productosVenta.get(fila).getCantidad().toString();
                    precio = productosVenta.get(fila).getPrecio().toString();
                    
                    productos = Integer.valueOf(cantidad) + 1;
                    productosVenta.get(fila).setCantidadProperty(Integer.toString(productos));
                    
                    productos = Integer.valueOf(lblProductosCant.getText()) + 1;
                    lblProductosCant.setText(String.valueOf(productos));
                    
                    //Importe de la venta
                    total  = Double.valueOf(precio)*(Integer.valueOf(cantidad)+1);
                    productosVenta.get(fila).setImporteProperty(Double.toString(total));
                    
                    total = Double.valueOf(lblTotal.getText()) + Double.valueOf(precio);
                    lblTotal.setText(formateador.format(total));
                                        
                    tblProductos.refresh();
                    txtCodigoBarras.setText("");
                    result = true;
                }             
            }
        }
        return result;
    }
    
    public ObservableList<ProductoVenta> ObtenerProd() throws SQLException {
        double total;
        double costo;
        double precio2;
        double utilidad;
        boolean continuar = true;
        int productos;
        String query;
        String dato = null;

        String modelo, codigo, nombre, cantidad, preciobase, precio, importe, descuento;   
        
        dato = txtCodigoBarras.getText().trim().toUpperCase();

        DecimalFormat formateador = new DecimalFormat("####.00");
        
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.initOwner(btnIniciar.getScene().getWindow());

        if(!dato.isEmpty()){
            query = "Select * from productos Where ID = '"+txtCodigoBarras.getText()+"'";
            conn.QueryExecute(query);
            try{
                if(conn.setResult.next()){
                    if(conn.setResult.getInt("CantidadActual")<=0){
                        alert.setHeaderText("Error");
                        alert.setContentText("Productosin existencias");
                        alert.showAndWait();
                        continuar = false;
                    }
                    if(continuar){
                        codigo = conn.setResult.getString("ID");
                        nombre = conn.setResult.getString("Nombre");
                        modelo = conn.setResult.getString("Modelo");
                        cantidad = "1";
                        descuento = txtDescuento.getText();
                        preciobase = conn.setResult.getString("PrecPub");
                        importe = Double.toString(Double.valueOf(preciobase) - ((Double.valueOf(descuento)/100) * Double.valueOf(preciobase)));
                        precio = conn.setResult.getString("PrecPub");
                        //importe = conn.setResult.getString("PrecPub");
                        
                        precio2 = conn.setResult.getDouble("PrecPub");
                        productosVenta.add(new ProductoVenta(codigo,nombre,modelo,cantidad,preciobase,precio,descuento,importe));

                        txtCodigoBarras.setText("");
                        
                        productos = Integer.valueOf(lblProductosCant.getText()) + 1;
                        lblProductosCant.setText(String.valueOf(productos));

                        total = Double.valueOf(lblTotal.getText()) + Double.valueOf(importe);
                        lblTotal.setText(formateador.format(total));
                    }
                } else{
                        //JOptionPane.showMessageDialog(rootPane, "Producto no existe");
                                //txtCodigo.setForeground(Color.red);
                }
                conn.setResult.close();
            } catch(SQLException e){
                        //JOptionPane.showMessageDialog(rootPane, "Producto agrega: "+e.getMessage().toString() );
            }
            return productosVenta;
        }
        return null;   
    }
    
    boolean esnumero (String x) {
        double number = 0;
        if(x.isEmpty()) {
            return true;
        } else {
            try {
                number = Double.parseDouble(x);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
    }
    
    private int getFolioVenta(){
        int result = 0;
        String query;
        query = "Select folioventa from tblfolios";

        conn.QueryExecute(query);

        try {
            if(conn.setResult.next())
                result = conn.setResult.getInt("folioventa");			
        } catch (SQLException e){
                //JOptionPane.showMessageDialog(rootPane, "getFolioVenta: "+e.getMessage().toString());
        }

        return result;
    }
    
    private int getFolioInventario(){
        int result = 0;
        String query;
        query = "Select folioinventario from tblfolios";

        conn.QueryExecute(query);

        try {
            if(conn.setResult.next())
                result = conn.setResult.getInt("folioinventario");			
        } catch (SQLException e){
                //JOptionPane.showMessageDialog(rootPane, "getFolioInventario: "+e.getMessage().toString());
        }

        return result;
    }

    private void GrabarVenta() {
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Venta realizada");
        alert.initOwner(btnIniciar.getScene().getWindow());
        Date fechaHoy = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        lblFecha.setText(formato.format(fechaHoy));
        
        boolean commit = false;
        
        conn.AutoCommit(false);
        if(GrabaVentaEncabezado())
            if(GrabarVentaDetalle())
                if(ActualizaFolioVenta()){
                    alert.setContentText("Venta realizada con exito");
                    alert.showAndWait();
                    commit = true;
        }
        if(commit)
            conn.Commit();
        else
            conn.RollBack();
        inicializa();
    }
    
    private void mostrarCobrar() throws IOException{
        Parent principal;
        principal = FXMLLoader.load(getClass().getResource("/xxcell/view/Cobrar.fxml"));
        Stage principalStage = new Stage();
        scene = new Scene(principal);
        principalStage.setScene(scene);
        principalStage.initModality(Modality.APPLICATION_MODAL);
        principalStage.setResizable(false);
        principalStage.initOwner(btnCancelar.getScene().getWindow());
        principalStage.showAndWait(); 
    }
    
    private boolean GrabaVentaEncabezado(){
        boolean result = false;
        String sqlStmt;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date fechaHoy = new Date();
        sqlStmt = "Insert into tblventas ";
        sqlStmt += "(ventafolio,";
        sqlStmt += "ventafecha,";
        sqlStmt += "ventaproductos,";
        sqlStmt += "ventaimporte,";
        sqlStmt += "ventacancelada,";
        sqlStmt += "NumLocal,";
        sqlStmt += "NumEmpleado)";
        sqlStmt += " Values ";
        sqlStmt += "("+String.valueOf(getFolioVenta())+",";
        sqlStmt += "'"+lblFecha.getText()+"',";
        sqlStmt += lblProductosCant.getText()+",";
        sqlStmt += lblTotal.getText()+",";
        sqlStmt += "'',";
        sqlStmt += "'"+Variables_Globales.local+"',";
        sqlStmt += usuario+")";
        
        if(conn.QueryUpdate(sqlStmt))
                result = true;
        return result;
    }
    
    private boolean GrabarVentaDetalle(){
        boolean result = true;
        int fila;
        String codigo;
        String nombre;
        String precio;
        String cantidad;
        String costo;

        String sqlStmt;

        for(fila = 0; fila < productosVenta.size(); fila++) {
            codigo = productosVenta.get(fila).getCodigo().toString();
            nombre = productosVenta.get(fila).getNombre().toString();
            precio = productosVenta.get(fila).getPrecio().toString();
            cantidad = productosVenta.get(fila).getCantidad().toString();

            if(ActualizaExistenciaSalida(codigo,cantidad,false)){
                if(RegistraMovimiento(codigo,cantidad,false)) { 
                    sqlStmt = "Insert into tblventadetalle ";
                    sqlStmt += "(ventafolio, ";
                    sqlStmt += " productocodigo,";
                    sqlStmt += " productonombre,";
                    sqlStmt += " ventacantidad,";
                    sqlStmt += " productoprecio,";
                    sqlStmt += " NumLocal)";
                    sqlStmt += " Values ";
                    sqlStmt +=  "("+spnFolio.getValue().toString()+",";
                    sqlStmt += "'"+codigo+"',";
                    sqlStmt += "'"+nombre+"',";
                    sqlStmt += cantidad+",";
                    sqlStmt += precio+",";
                    sqlStmt += "'"+Variables_Globales.local+"')";

                    if(!conn.QueryUpdate(sqlStmt)){ 
                        result = false;
                        break;
                    }
                }
            }
        }
        return result;
    }
    
    private boolean ActualizaExistenciaSalida(String cod, String can, boolean cancel){
        boolean result = false;
        String query;
        if(!cancel){
                query = "Update productos set Salidas = Salidas + " +can +",";
                query += " CantidadActual = CantidadActual - "+can+",";
                query += " L127 = L127 - "+can;
        }
        else {
                query = "Update productos set Salidas = Salidas - " +can +",";
                query += " CantidadActual = CantidadActual + "+can+",";
                query += " L127 = L127 + "+can;
        }

        query += " Where ID='"+cod+"'";
        if(conn.QueryUpdate(query))
                result = true;
        return result;
    }
    
    private boolean RegistraMovimiento(String cod, String can, boolean cancel){
        boolean result = false;
        String folioInventario;

        String query;

        folioInventario = String.valueOf(getFolioInventario());

        query = "Insert into tblinventario (invfolio,invmovimiento,";
        query += "invfecha,invcantidad,productocodigo,ventafolio,invdescripcion)";
        query += "Values ("+folioInventario+",";

        if(!cancel)
                query += "'Salida',";
        else
                query += "'Entrada',";

        query += "'"+lblFecha.getText()+"',";
        query += can+",";
        query += "'"+cod+"',";
        query += spnFolio.getValue().toString()+",";
        if(!cancel)
                query += "'Salida por venta')";
        else
                query += "'Entrada por cancelacion')";

        if(conn.QueryUpdate(query))
                if(ActualizaFolioInventario())
                        result = true;
        return result;
    }
    
    private boolean ActualizaFolioInventario(){
        boolean result = false;
        String sqlStmt;


        sqlStmt = "Update tblfolios set folioinventario = folioinventario + '1'";
        if(conn.QueryUpdate(sqlStmt))
                result = true;

        return result;
    }
	
    private boolean ActualizaFolioVenta(){
        boolean result = false;
        String sqlStmt;

        sqlStmt = "Update tblfolios set folioventa = folioventa + 1";
        if(conn.QueryUpdate(sqlStmt))
                result = true;

        return result;
    }   
    
}
