/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xxcell.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static xxcell.controller.LoginController.scene;

public class AlmacenController implements Initializable {
    
    @FXML
    private JFXButton AgregProd;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    void AddAction(ActionEvent event) throws IOException {
        Parent principal;
        principal = FXMLLoader.load(getClass().getResource("/xxcell/view/Agregar Producto.fxml"));
        Stage principalStage = new Stage();
        scene = new Scene(principal);
        principalStage.setScene(scene);
        principalStage.initModality(Modality.APPLICATION_MODAL);
        principalStage.setResizable(false);
        principalStage.initOwner(AgregProd.getScene().getWindow());
        principalStage.showAndWait(); 
    }
    
    /*/@FXML
    void AddActionModificar(ActionEvent event) throws IOException {
        
        /*Parent principal;
        principal = FXMLLoader.load(getClass().getResource("/xxcell/view/ModificarAlmacen.fxml"));
        Stage principalStage = new Stage();
        scene = new Scene(principal);
        principalStage.setScene(scene);
        principalStage.initModality(Modality.APPLICATION_MODAL);
        principalStage.initOwner(AgregProd.getScene().getWindow());
        principalStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        principalStage.setFullScreen(true);
        principalStage.showAndWait();
        
    }*/
}
