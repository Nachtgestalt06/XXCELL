package xxcell.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import xxcell.Conexion.Conexion;


public class SueldosController implements Initializable {

    boolean ban;
    Alert succesAlert = new Alert(Alert.AlertType.INFORMATION);
    Alert alert = new Alert(Alert.AlertType.ERROR);
    
    Conexion conn = new Conexion();
    String qry;
    //Botones
    @FXML
    private JFXButton Actualizar;
    @FXML
    private JFXButton Cancelar;
    
    //TextFields
    @FXML
    private JFXTextField SueldoBase;

    @FXML
    private JFXTextField VentasMeta;

    @FXML
    private JFXTextField ComisionBase;

    @FXML
    private JFXTextField ComisionMeta;

    @FXML
    private JFXTextField Faltas;

    @FXML
    private JFXTextField DiaExtra;
    
    //Combobox
    @FXML
    private JFXComboBox<String> Nivel;
    
    int x;
    
    public void iniciarComboBox()
    {
        //inicia el combobox Nivel
        ObservableList<String> options = FXCollections.observableArrayList("1. Tiempo Completo","2. Fin de Semana");
        Nivel.getItems().addAll(options);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        iniciarComboBox();
        Nivel.setOnAction(e -> {
            if("1. Tiempo Completo".equals(Nivel.getValue()))
                x=1;
            else
                x=2;
            qry = "SELECT * FROM sueldos WHERE Nivel = '"+x+"'";
            conn.QueryExecute(qry);
            try{
                if(conn.setResult.first()){
                    ban = true;
                    SueldoBase.setText(String.valueOf(conn.setResult.getFloat("Base")));
                    VentasMeta.setText(String.valueOf(conn.setResult.getInt("NumVentas")));
                    ComisionBase.setText(String.valueOf(conn.setResult.getFloat("ComisionBase")));
                    ComisionMeta.setText(String.valueOf(conn.setResult.getFloat("ComisionMeta")));
                    Faltas.setText(String.valueOf(conn.setResult.getFloat("DescuentoFalta")));
                    DiaExtra.setText(String.valueOf(conn.setResult.getFloat("DiaExtra")));
               }
                else{
                    ban = false;
                }
            } catch (SQLException ex) {
                Logger.getLogger(SueldosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Actualizar.setOnAction((ActionEvent e) -> {
            if(ban){
                if("1. Tiempo Completo".equals(Nivel.getValue()))
                    x=1;
                else
                    x=2;
                float decimal;
                int Ventas;
                qry = "UPDATE sueldos SET Base=?, NumVentas=?, DescuentoFalta=?, DiaExtra=?, ComisionBase=?, ComisionMeta=? ";
                qry += "WHERE Nivel = '"+x+"'";
                try {
                    conn.preparedStatement(qry);
                    //Se inserta el sueldo Base
                    decimal = Float.parseFloat(SueldoBase.getText());                    
                    conn.stmt.setFloat(1, decimal);
                    //Inserta Numero de Ventas para llegar a la comisión
                    Ventas = Integer.parseInt(VentasMeta.getText());
                    conn.stmt.setInt(2, Ventas);
                    //Se inserta el descuento por falta
                    decimal = Float.parseFloat(Faltas.getText());
                    conn.stmt.setFloat(3, decimal);
                    //Se inserta el precio por día extra trabajado
                    decimal = Float.parseFloat(DiaExtra.getText());
                    conn.stmt.setFloat(4, decimal);
                    //Se inserta la comisión base, cuando NO se llega a la meta de ventas
                    decimal = Float.parseFloat(ComisionBase.getText());
                    conn.stmt.setFloat(5, decimal);
                    //Se inserta la comisión meta, cuando SI llegan a la meta de ventas
                    decimal = Float.parseFloat(ComisionMeta.getText());
                    conn.stmt.setFloat(6, decimal);
                    
                    conn.stmt.executeUpdate();
                    conn.Commit();
                    
                    succesAlert.setTitle("Alta satisfactoria");
                    succesAlert.setHeaderText(null);
                    succesAlert.initOwner(Actualizar.getScene().getWindow());
                    succesAlert.setContentText("Actualización correcta");
                } catch (SQLException ex) {
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en la actualizacion");
                    alert.setContentText("Error en la actualizaciion, verifique los datos ingresados.");
                    alert.showAndWait();
                    Logger.getLogger(SueldosController.class.getName()).log(Level.SEVERE, null, ex);
                }  
            }
            else{
                if("1. Tiempo Completo".equals(Nivel.getValue()))
                    x=1;
                else
                    x=2;
                float decimal;
                int Ventas;
                qry = "Insert into sueldos (Nivel, Base, NumVentas, DescuentoFalta, DiaExtra, ComisionBase, ComisionMeta) ";
                qry += "values (?, ?, ?, ?, ?, ?, ?)";
                try {
                    conn.preparedStatement(qry);
                    //Se inserta a Nivel
                    conn.stmt.setInt(1, x);
                    //Se inserta el sueldo Base
                    decimal = Float.parseFloat(SueldoBase.getText());                    
                    conn.stmt.setFloat(2, decimal);
                    //Inserta Numero de Ventas para llegar a la comisión
                    Ventas = Integer.parseInt(VentasMeta.getText());
                    conn.stmt.setInt(3, Ventas);
                    //Se inserta el descuento por falta
                    decimal = Float.parseFloat(Faltas.getText());
                    conn.stmt.setFloat(4, decimal);
                    //Se inserta el precio por día extra trabajado
                    decimal = Float.parseFloat(DiaExtra.getText());
                    conn.stmt.setFloat(5, decimal);
                    //Se inserta la comisión base, cuando NO se llega a la meta de ventas
                    decimal = Float.parseFloat(ComisionBase.getText());
                    conn.stmt.setFloat(6, decimal);
                    //Se inserta la comisión meta, cuando SI llegan a la meta de ventas
                    decimal = Float.parseFloat(ComisionMeta.getText());
                    conn.stmt.setFloat(7, decimal);
                    
                    conn.stmt.executeUpdate();
                    conn.Commit();
                    succesAlert.setTitle("Alta satisfactoria");
                    succesAlert.setHeaderText(null);
                    succesAlert.initOwner(Actualizar.getScene().getWindow());
                    succesAlert.setContentText("Actualización correcta");
                } catch (SQLException ex) {
                    alert.setTitle("Error");
                    alert.setHeaderText("Error en la actualizacion");
                    alert.setContentText("Error en la actualizaciion, verifique los datos ingresados.");
                    alert.showAndWait();
                    Logger.getLogger(SueldosController.class.getName()).log(Level.SEVERE, null, ex);
                }  
            }
        });
        
        Cancelar.setOnAction((ActionEvent e) -> {       
            Stage stage;
            stage = (Stage) Cancelar.getScene().getWindow();
            stage.close();
        });
    }    
    
}
