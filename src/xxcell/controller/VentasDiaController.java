/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xxcell.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import xxcell.Conexion.Conexion;
import xxcell.model.Detalles;
import xxcell.model.Productos;


public class VentasDiaController implements Initializable {

    Conexion conn = new Conexion();
    String STSQL = "SELECT * FROM tblventadetalle";
    
    @FXML
    private TableView<Detalles> Tabla;
    @FXML
    private TableColumn<Detalles, Number> Folio;
    @FXML
    private TableColumn<Detalles, String> Codigo;

    @FXML
    private TableColumn<Detalles, String> Producto;

    @FXML
    private TableColumn<Detalles, Number> Cantidad;

    @FXML
    private TableColumn<Detalles, Number> Total;

    @FXML
    private Label lblCantidad;

    @FXML
    private Label lblTotal;
    
    ObservableList<Detalles> detalles = FXCollections.observableArrayList();
    
    public ObservableList<Detalles> ObtenerDetalles() throws SQLException{
        String codigo, producto;
        int folio, cantidad, cantidadproductos = 0;
        float total, totalvendido = 0;
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
                detalles.add(new Detalles(folio, codigo, producto, cantidad, total));
            }
            lblCantidad.setText(String.valueOf(cantidadproductos));
            lblTotal.setText(String.valueOf("$ "+totalvendido));
        }
        return detalles;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Folio.setCellValueFactory(cellData -> cellData.getValue().FolioProperty());
        Codigo.setCellValueFactory(cellData -> cellData.getValue().CodigoProperty());
        Producto.setCellValueFactory(cellData -> cellData.getValue().ProductoProperty());
        Cantidad.setCellValueFactory(cellData -> cellData.getValue().CantidadProperty());
        Total.setCellValueFactory(cellData -> cellData.getValue().TotalProperty());
        try {
            Tabla.setItems(ObtenerDetalles());
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }      
}
