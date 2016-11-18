/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xxcell;

import com.jfoenix.controls.JFXDecorator;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class XCELL extends Application {
    private Stage primaryStage;

    private StackPane mainLayout;
    
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        this.primaryStage.setTitle("XXCELL");

        showLoginView();        

    }
    
    private void showLoginView() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(XCELL.class.getResource("/xxcell/view/Login.fxml"));
        mainLayout = loader.load();
        JFXDecorator decorator = new JFXDecorator(primaryStage, mainLayout);
        Scene scene = new Scene(decorator);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
}
