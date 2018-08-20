/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rawtojpegconverter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * FXML Controller class
 *
 * @author I14746
 */
public class MainFXMLController implements Initializable {

    @FXML
    private Label statuslabel;
    @FXML
    private TextField PathImg;
    @FXML
    private Button Browse;
    @FXML
    private TextField widthtext;
    @FXML
    private TextField heighttext;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
        
    @FXML
    public void BrowseRawFile(){
            statuslabel.setText("");
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File(System.getProperty("user.home")));
            chooser.setTitle("Choose the RAW image file");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("RAW files (*.raw)", "*.raw");
            chooser.getExtensionFilters().add(extFilter);
            File file = chooser.showOpenDialog(Browse.getParent().getScene().getWindow());
            try {
                if (file != null) {
                    PathImg.setText(file.getPath());
                    String imagepath = null;
                    try {
                        imagepath = file.toURI().toURL().toString();
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(RawToJPEGconverter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Image image = new Image(imagepath);
                }
            } catch (NullPointerException npe) {
            }        
    }
    
    
    public boolean rawToJpeg(byte[] rawBytes, int width, int height, File outputFile){
        boolean retval = false;
        try{
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            byte b = 0;
            int count = 0; 
            for(int h=0;h<height;h++){
                for(int w=0;w<width;w++){
                    b = rawBytes[count++];
                    bi.setRGB(w, h, b);
                }
            }
            Iterator imageWriters = ImageIO.getImageWritersByFormatName("jpeg");
            ImageWriter imageWriter = (ImageWriter) imageWriters.next(); 
            ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile);
            imageWriter.setOutput(ios);
            imageWriter.write(bi);

            retval = true;
        }catch(Exception ex){
            ex.printStackTrace();
            retval = false;
        }
        return retval;

    }
    
    @FXML
    private boolean ConvertAction(){
        File f = new File(PathImg.getText());
        byte[] fileContent = null;
        try {
            fileContent = Files.readAllBytes(f.toPath());
        } catch (IOException ex) {
            Logger.getLogger(RawToJPEGconverter.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        File f_out = new File(f.getParent()+"\\converted.jpg");
        try {
            f_out.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(RawToJPEGconverter.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        int width = Integer.parseInt(widthtext.getText());
        int height = Integer.parseInt(heighttext.getText());
        if(rawToJpeg(fileContent,width,height,f_out)){
            statuslabel.setText("Status: Image converted successfully.");
            return true;
        }else{
            statuslabel.setText("Status: Failed to convert the image.");
            return false;
        }
    }
}
