/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pditareajavafx;



import Utils.GeometricFilter;
import Utils.GlobalFilter;
import Utils.ImageCustom;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

/**
 *
 * @author Kharleann
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ImageView imgV;
    @FXML
    private Button cargar;
    @FXML
    private Button globalsButton;
    @FXML
    private Button dataButton;
    @FXML
    private Button totalColorsButton;
    @FXML
    private Label dataLabel;
    @FXML
    private ToolBar globals;
    @FXML
    private ToolBar dataBar;
    
    ImageCustom ourImage = new ImageCustom();
    GlobalFilter globalFilter = new GlobalFilter();
    GeometricFilter geometricFilter = new GeometricFilter();
    
    @FXML
    private void loadImage(ActionEvent event) throws IOException {
        BufferedImage preImage = ImageIO.read(new File("./src/images/Desert.bmp"));
        ourImage.setOriginal(preImage);
        ourImage.setWidth(ourImage.getOriginal().getWidth());
        ourImage.setHeight(ourImage.getOriginal().getHeight());
        
        ArrayList colors = new ArrayList();
         for (int y = 0; y < preImage.getHeight(); y++) {
         for (int x = 0; x < preImage.getWidth(); x++) {
            //Retrieving contents of a pixel
            int pixel = preImage.getRGB(x,y);
            if(!(colors.contains(pixel))){
                colors.add(pixel);
            }
            }
        }
         ourImage.setTotalColors(colors.size());
         
        
        
        ourImage.reSize(ourImage.getWidth(), ourImage.getHeight());
        ourImage.setResult(preImage);
        
        Image image = SwingFXUtils.toFXImage(ourImage.getOriginal(), null);
        imgV.setImage(image);
    }
    @FXML
    private void showGlobals(ActionEvent event) throws IOException {
       if (globals.isVisible() == false){
           globals.setVisible(true);
           dataButton.setVisible(false);
           globalsButton.setText("Back");
       }else{
           globals.setVisible(false);
           dataButton.setVisible(true);
           globalsButton.setText("Globales");
       }
    }
    
    @FXML
    private void showData(ActionEvent event) throws IOException {
       if (dataBar.isVisible() == false){
           dataBar.setVisible(true);
           globalsButton.setVisible(false);
           dataButton.setLayoutX(77);
           dataButton.setText("Back");
       }else{
           dataBar.setVisible(false);
           dataLabel.setVisible(false);
           globalsButton.setVisible(true);
           dataButton.setLayoutX(149);
           dataButton.setText("Image data");
       }
    }
    
    @FXML
    private void negative(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        ourImage.setResult(globalFilter.negative(ourImage.getOriginal()));
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
    @FXML
    private void blackAndWhite(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        ourImage.setResult(globalFilter.blackWhite(ourImage.getOriginal()));
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
    @FXML
    private void Borders(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        ourImage.setResult(globalFilter.borders(ourImage.getOriginal()));
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
     @FXML
    private void getColorsTotal(ActionEvent event) throws IOException {
        int totals = ourImage.getTotalColors();
        dataLabel.setVisible(true);
        dataLabel.setText("Total de colores: " + totals);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
