/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rawtojpegconverter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author I14746
 */
public class RawToJPEGconverter extends Application {
    private Stage mainStage;
    
    @Override
    public void start(Stage primaryStage) {
        showMainStage();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void showMainStage()
    {
         Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(RawToJPEGconverter.class.getName()).log(Level.SEVERE, null, ex);
        }    
        Scene scene = new Scene(root);   
        mainStage = new Stage(StageStyle.DECORATED);
        mainStage.setTitle("Raw file converter");
        mainStage.setScene(scene);
        mainStage.show();
//        mainStage.getIcons().add(new Image(RawToJPEGconverter.class.getResourceAsStream("logo.png")));
    }
    
    
}
