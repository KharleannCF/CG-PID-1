/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pditareajavafx;



import Utils.GeometricFilter;
import Utils.GlobalFilter;
import Utils.Histogram;
import Utils.ImageCustom;
import Utils.LocalFilter;
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
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
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
    private Button geometricButton;
    @FXML
    private Button dataButton;
    @FXML
    private Button totalColorsButton;
    @FXML
    private Label dataLabel;
    @FXML
    private ToolBar globals;
    @FXML
    private ToolBar locals;
    @FXML
    private Button localButton;
    @FXML
    private Button flattenButton;
    @FXML
    private ToolBar dataBar;
    @FXML
    private ToolBar geometrics;
    @FXML
    private ToolBar personalizedKernel;
    @FXML
    private GridPane kernel;
    
    
    
    
    
    ImageCustom ourImage = new ImageCustom();
    GlobalFilter globalFilter = new GlobalFilter();
    LocalFilter localFilter = new LocalFilter();
    GeometricFilter geometricFilter = new GeometricFilter();
    Histogram histogramFilter = new Histogram();
    
    
    @FXML
    private void loadImage(ActionEvent event) throws IOException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        File selectedFile = jfc.getSelectedFile();
        BufferedImage preImage = ImageIO.read(selectedFile);
        ourImage.setOriginal(preImage);
        ourImage.setWidth(ourImage.getOriginal().getWidth());
        ourImage.setHeight(ourImage.getOriginal().getHeight());        
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
           localButton.setVisible(false);
           geometricButton.setVisible(false);
           globalsButton.setText("Back");
       }else{
           globals.setVisible(false);
           dataButton.setVisible(true);
           localButton.setVisible(true);
           geometricButton.setVisible(true);
           globalsButton.setText("Globales");
       }
    }
    
    @FXML
    private void showData(ActionEvent event) throws IOException {
       if (dataBar.isVisible() == false){
           dataBar.setVisible(true);
           globalsButton.setVisible(false);
           geometricButton.setVisible(false);
           localButton.setVisible(false);
           dataButton.setLayoutX(77);
           dataButton.setText("Back");
       }else{
           dataBar.setVisible(false);
           dataLabel.setVisible(false);
           globalsButton.setVisible(true);
           localButton.setVisible(true);
           geometricButton.setVisible(true);
           dataButton.setLayoutX(149);
           dataButton.setText("Image data");
       }
    }
     @FXML
    private void showGeometrics(ActionEvent event) throws IOException {
       if (geometrics.isVisible() == false){
           geometrics.setVisible(true);
           dataButton.setVisible(false);
           localButton.setVisible(false);
           globalsButton.setVisible(false);
           geometricButton.setLayoutX(77);
           geometricButton.setText("Back");
       }else{
           geometrics.setVisible(false);
           dataButton.setVisible(true);
           globalsButton.setVisible(true);
           localButton.setVisible(true);
           geometricButton.setLayoutX(235);
           geometricButton.setText("Geometricos");
       }
    }
    @FXML
    private void showLocal(ActionEvent event) throws IOException {
       if (locals.isVisible() == false){
           locals.setVisible(true);
           dataButton.setVisible(false);
           globalsButton.setVisible(false);
           geometricButton.setVisible(false);
           localButton.setLayoutX(77);
           localButton.setText("Back");
       }else{
           locals.setVisible(false);
           dataButton.setVisible(true);
           globalsButton.setVisible(true);
           geometricButton.setVisible(true);
           localButton.setLayoutX(325);
           localButton.setText("Locales");
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
    private void createHistogram(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        ourImage.setResult(histogramFilter.histogram(ourImage.getOriginal()));
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
        flattenButton.setVisible(true);
    }
    @FXML
    private void flattenHistogram(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        ourImage.setResult(histogramFilter.histogramFlatten(ourImage.getOriginal()));
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
        int [][] kernel = new int[2][1];
        kernel[0][0]= -1;
        kernel[1][0]= 1;
        

        ourImage.setResult(localFilter.kernelApplier(ourImage.getOriginal(), kernel));
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
    @FXML
    private void Gaussiano(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        int [][] kernel = new int[3][3];
        kernel[0][0]= 1;
        kernel[1][0]= 2;
        kernel[2][0]= 1;
        kernel[0][1]= 2;
        kernel[1][1]= 4;
        kernel[2][1]= 2;
        kernel[0][2]= 1;
        kernel[1][2]= 2;
        kernel[2][2]= 1;
        

        ourImage.setResult(localFilter.kernelApplier(ourImage.getOriginal(), kernel));
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
    @FXML
    private void Sobel(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        int [][] kernel = new int[3][3];
        kernel[0][0]= -1;
        kernel[1][0]= 0;
        kernel[2][0]= 1;
        kernel[0][1]= -2;
        kernel[1][1]= 0;
        kernel[2][1]= 2;
        kernel[0][2]= -1;
        kernel[1][2]= 0;
        kernel[2][2]= 1;
        

        ourImage.setResult(localFilter.kernelApplier(ourImage.getOriginal(), kernel));
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
     @FXML
    private void scharr(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        int [][] kernel = new int[3][3];
        kernel[0][0]= -3;
        kernel[1][0]= 0;
        kernel[2][0]= 3;
        kernel[0][1]= -10;
        kernel[1][1]= 0;
        kernel[2][1]= 10;
        kernel[0][2]= -3;
        kernel[1][2]= 0;
        kernel[2][2]= 3;
        ourImage.setResult(localFilter.kernelApplier(ourImage.getOriginal(), kernel));
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
         @FXML
    private void prewitt(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        int [][] kernel = new int[3][3];
        kernel[0][0]= -1;
        kernel[1][0]= 0;
        kernel[2][0]= 1;
        kernel[0][1]= -1;
        kernel[1][1]= 0;
        kernel[2][1]= 1;
        kernel[0][2]= -1;
        kernel[1][2]= 0;
        kernel[2][2]= 1;
        ourImage.setResult(localFilter.kernelApplier(ourImage.getOriginal(), kernel));
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
@FXML
    private void kernelRedondo(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        int [][] kernel = new int[5][5];
        kernel[0][0]= 0;
        kernel[1][0]= 1;
        kernel[2][0]= 2;
        kernel[3][0]= 1;
        kernel[4][0]= 0;
        kernel[0][1]= 1;
        kernel[1][1]= 1;
        kernel[2][1]= -4;
        kernel[3][1]= 1;
        kernel[4][1]= 3;
        kernel[0][2]= 4;
        kernel[1][2]= 1;
        kernel[2][2]= -2;
        kernel[3][2]= 5;
        kernel[4][2]= 1;
        kernel[0][3]= 0;
        kernel[1][3]= 1;
        kernel[2][3]= -5;
        kernel[3][3]= -4;
        kernel[4][3]= 5;
        kernel[0][4]= 0;
        kernel[1][4]= 1;
        kernel[2][4]= 1;
        kernel[3][4]= 1;
        kernel[4][4]= 0;
        
        ourImage.setResult(localFilter.kernelApplier(ourImage.getOriginal(), kernel));
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
     @FXML
    private void getColorsTotal(ActionEvent event) throws IOException {
         try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        dataLabel.setVisible(true);
        
                ArrayList colors = new ArrayList();
         for (int y = 0; y < ourImage.getOriginal().getHeight(); y++) {
         for (int x = 0; x < ourImage.getOriginal().getWidth(); x++) {
            //Retrieving contents of a pixel
            int pixel = ourImage.getOriginal().getRGB(x,y);
            if(!(colors.contains(pixel))){
                colors.add(pixel);
            }
            }
        }
         ourImage.setTotalColors(colors.size());
        
        dataLabel.setText("Total de colores: " + ourImage.getTotalColors());
    }
     @FXML
    private void getDimensions(ActionEvent event) throws IOException {
         try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        dataLabel.setVisible(true);
        dataLabel.setText("Dimensiones de la imagen: " + ourImage.getWidth() +  " x " + ourImage.getHeight());
    }
     @FXML
    private void leftToRight(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        ourImage.setResult(geometricFilter.rotateLToR(ourImage.getOriginal()));
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
    @FXML
    private void rightToLeft(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        ourImage.setResult(geometricFilter.rotateRToL(ourImage.getOriginal()));
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
    
     @FXML
    private void save(ActionEvent event) throws IOException {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showSaveDialog(null);
        BufferedImage bImage = ourImage.getResult();
        File selectedFile = jfc.getSelectedFile();
        String fileName = selectedFile.toString();
        int index = fileName.lastIndexOf('.');
        String extension = fileName.substring(index + 1);
        ImageIO.write(bImage, extension, selectedFile);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
