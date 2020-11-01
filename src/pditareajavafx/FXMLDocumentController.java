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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
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
    private Slider BWSlider;
    @FXML
    private Slider brightSlider;
    @FXML
    private Slider brightNSlider;
    @FXML
    private Slider localW;
    @FXML
    private Slider localH;
    
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
    private Button saveButton;
    @FXML
    private Label dataLabel;
    @FXML
    private ToolBar globals;
    @FXML
    private ToolBar borders;
    @FXML
    private Button borderButton;
    @FXML
    private Button localsButton;
    @FXML
    private Button flattenButton;
    @FXML
    private Button doneButton;
    @FXML
    private ToolBar dataBar;
    @FXML
    private ToolBar geometrics;
    @FXML
    private ToolBar locals;
    @FXML
    private ToolBar personalizedKernel;
    @FXML
    private GridPane kernel;
    @FXML
     private Group borderKernels;
    @FXML
     private Group localsKernels;
    @FXML
     private RadioButton matrix3;
    @FXML
     private RadioButton matrix5;
    @FXML
     private RadioButton matrix7;
    @FXML
     private RadioButton matrix2;
    @FXML
     private RadioButton matrix4;
    @FXML
     private RadioButton matrix6;
    @FXML
     private RadioButton vectorX;
    @FXML
     private RadioButton vectorY;
    @FXML
     private RadioButton vectorMagnitud;
    @FXML
     private RadioButton vecindad4;
    @FXML
     private RadioButton vecindad8;
    
    
    
    
    ImageCustom ourImage = new ImageCustom();
    GlobalFilter globalFilter = new GlobalFilter();
    LocalFilter localFilter = new LocalFilter();
    GeometricFilter geometricFilter = new GeometricFilter();
    Histogram histogramFilter = new Histogram();
    int borderKernelSize = 3;
    int robertsSize = 2;
    char vectorDir ='M';
    
    int localsSizeX = 1;
    int localsSizeY = 1;
    
    int vecindad = 4;
    
    
    @FXML
    private void selected3(ActionEvent event) throws IOException {
    matrix3.setSelected(true);
    matrix5.setSelected(false);
    matrix7.setSelected(false);
    borderKernelSize = 3;
    }
    
    @FXML
    private void moveW() throws IOException {
        System.out.println((int)localW.getValue());
    this.localsSizeX = (int) localW.getValue();
    }
    
    @FXML
    private void moveH() throws IOException {
        System.out.println((int)localH.getValue());
    this.localsSizeY = (int) localH.getValue();
    }
    
    @FXML
    private void selected5(ActionEvent event) throws IOException {
    matrix5.setSelected(true);
    matrix3.setSelected(false);
    matrix7.setSelected(false);
    borderKernelSize = 5;
    }
    
    @FXML
    private void selected7(ActionEvent event) throws IOException {
    matrix7.setSelected(true);
    matrix5.setSelected(false);
    matrix3.setSelected(false);
    borderKernelSize = 7;
    }
    
    @FXML
    private void selected2(ActionEvent event) throws IOException {
    matrix2.setSelected(true);
    matrix4.setSelected(false);
    matrix6.setSelected(false);
    robertsSize = 2;
    }
    @FXML
    private void selected4(ActionEvent event) throws IOException {
    matrix4.setSelected(true);
    matrix2.setSelected(false);
    matrix6.setSelected(false);
    robertsSize = 4;
    }
     
    @FXML
    private void selected6(ActionEvent event) throws IOException {
    matrix6.setSelected(true);
    matrix4.setSelected(false);
    matrix2.setSelected(false);
    robertsSize = 6;
    }
    
    @FXML
    private void selectedX(ActionEvent event) throws IOException {
    vectorX.setSelected(true);
    vectorY.setSelected(false);
    vectorMagnitud.setSelected(false);
    vectorDir='X';
    }
    
    @FXML
    private void selectedY(ActionEvent event) throws IOException {
    vectorY.setSelected(true);
    vectorX.setSelected(false);
    vectorMagnitud.setSelected(false);
    vectorDir='Y';
    }
    
    @FXML
    private void selectedMagnitud(ActionEvent event) throws IOException {
    vectorMagnitud.setSelected(true);
    vectorX.setSelected(false);
    vectorY.setSelected(false);
    vectorDir='M';
    }
    
    @FXML
    private void vecindad4Func(ActionEvent event) throws IOException {
    vecindad4.setSelected(true);
    vecindad8.setSelected(false);
    vecindad = 4;
    }
    
    @FXML
    private void vecindad8Func(ActionEvent event) throws IOException {
    vecindad4.setSelected(false);
    vecindad8.setSelected(true);
    vecindad = 8;
    }
    
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
           borderButton.setVisible(false);
           geometricButton.setVisible(false);
           localsButton.setVisible(false);
           globalsButton.setText("Back");
       }else{
           globals.setVisible(false);
           dataButton.setVisible(true);
           borderButton.setVisible(true);
           geometricButton.setVisible(true);
           localsButton.setVisible(true);
           globalsButton.setText("Globales");
       }
    }
    @FXML
    private void showGeometrics(ActionEvent event) throws IOException {
       if (geometrics.isVisible() == false){
           geometrics.setVisible(true);
           dataButton.setVisible(false);
           borderButton.setVisible(false);
           globalsButton.setVisible(false);
           localsButton.setVisible(false);
           geometricButton.setLayoutX(77);
           geometricButton.setText("Back");
       }else{
           geometrics.setVisible(false);
           dataButton.setVisible(true);
           globalsButton.setVisible(true);
           borderButton.setVisible(true);
           localsButton.setVisible(true);
           geometricButton.setLayoutX(235);
           geometricButton.setText("Geometricos");
           
       }
    }
    
    
    
    @FXML
    private void showData(ActionEvent event) throws IOException {
       if (dataBar.isVisible() == false){
           dataBar.setVisible(true);
           globalsButton.setVisible(false);
           geometricButton.setVisible(false);
           localsButton.setVisible(false);
           borderButton.setVisible(false);
           dataButton.setLayoutX(77);
           dataButton.setText("Back");
       }else{
           dataBar.setVisible(false);
           globalsButton.setVisible(true);
           borderButton.setVisible(true);
           geometricButton.setVisible(true);
           localsButton.setVisible(true);
           dataButton.setLayoutX(149);
           dataButton.setText("Image data");
       }
    }
    
     @FXML
    private void showLocals(ActionEvent event) throws IOException {
       if (locals.isVisible() == false){
           locals.setVisible(true);
           dataButton.setVisible(false);
           borderButton.setVisible(false);
           localsKernels.setVisible(true);
           globalsButton.setVisible(false);
           geometricButton.setVisible(false);
           localsButton.setLayoutX(77);
           localsButton.setText("Back");
       }else{
           locals.setVisible(false);
           dataButton.setVisible(true);
           globalsButton.setVisible(true);
           borderButton.setVisible(true);
           localsKernels.setVisible(false);
           geometricButton.setVisible(true);
           localsButton.setLayoutX(385);
           localsButton.setText("Locales");
       }
    }
    @FXML
    private void showBorders(ActionEvent event) throws IOException {
       if (borders.isVisible() == false){
           borders.setVisible(true);
           dataButton.setVisible(false);
           globalsButton.setVisible(false);
           borderKernels.setVisible(true);
           geometricButton.setVisible(false);
           doneButton.setVisible(true);
           saveButton.setVisible(false);
           localsButton.setVisible(false);
           borderButton.setLayoutX(77);
           borderButton.setText("Back");
       }else{
           borders.setVisible(false);
           dataButton.setVisible(true);
           borderKernels.setVisible(false);
           globalsButton.setVisible(true);
           geometricButton.setVisible(true);
           doneButton.setVisible(false);
           saveButton.setVisible(true);
           localsButton.setVisible(true);
           borderButton.setLayoutX(325);
           borderButton.setText("Bordes");
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
    private void blackAndWhiteReal(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        double value = BWSlider.getValue();
        System.out.println("hola");
        //ourImage.setResult();
        Image image = SwingFXUtils.toFXImage(globalFilter.blackAndWhite(ourImage.getOriginal(), value), null);
        imgV.setImage(image);
        saveButton.setVisible(false);
        BWSlider.setVisible(true);
        doneButton.setVisible(true);
    }
    @FXML
    private void brightness(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        double value = brightSlider.getValue();
        
        //ourImage.setResult();
        Image image = SwingFXUtils.toFXImage(globalFilter.brigthness(ourImage.getOriginal(), value), null);
        imgV.setImage(image);
        saveButton.setVisible(false);
        brightSlider.setVisible(true);
        doneButton.setVisible(true);
    }
    @FXML
    private void brightnessN(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        double value = brightNSlider.getValue();
        
        //ourImage.setResult();
        Image image = SwingFXUtils.toFXImage(globalFilter.negativeBrightness(ourImage.getOriginal(), value), null);
        imgV.setImage(image);
        saveButton.setVisible(false);
        brightNSlider.setVisible(true);
        doneButton.setVisible(true);
    }
    
    @FXML
    private void doneUmbral(ActionEvent event){
        Image actual = imgV.getImage();
        BufferedImage newImage = SwingFXUtils.fromFXImage(actual, null);
        ourImage.setResult(newImage);
        if(borders.isVisible() == false){
        doneButton.setVisible(false);
        saveButton.setVisible(true);
        BWSlider.setVisible(false);
        brightSlider.setVisible(false);
        brightNSlider.setVisible(false);
        }
       }
    
    
    @FXML
    public void blackAndWhiteSlider() throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        double value = BWSlider.getValue();
        System.out.println("hola");
        //ourImage.setResult();
        Image image = SwingFXUtils.toFXImage(globalFilter.blackAndWhite(ourImage.getOriginal(), value), null);
        imgV.setImage(image);
    }
     @FXML
    public void brightSlider() throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        double value = brightSlider.getValue();
        
        //ourImage.setResult();
        Image image = SwingFXUtils.toFXImage(globalFilter.brigthness(ourImage.getOriginal(), value), null);
        imgV.setImage(image);
    }
    
    @FXML
    public void brightNeSlider() throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        double value = brightNSlider.getValue();
        
        //ourImage.setResult();
        Image image = SwingFXUtils.toFXImage(globalFilter.negativeBrightness(ourImage.getOriginal(), value), null);
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
    private void Diferential(ActionEvent event) throws IOException {
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
    
    
     static int binomialCoeff(int n, int k) 
    { 
        int res = 1; 
          
        if (k > n - k) 
        k = n - k; 
              
        for (int i = 0; i < k; ++i) 
        { 
            res *= (n - i); 
            res /= (i + 1); 
        } 
        return res; 
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
        
        
        int [][] kernel = new int[localsSizeX][1];
        int [][] kernelY = new int[1][localsSizeY];
        int result =0;
        for (int line = 0; line < localsSizeX; line++) 
    { 
        for (int i = 0; i <= line; i++) {
            result = binomialCoeff 
                        (line, i); 
            if(line == (localsSizeX-1)){
                kernel[i][0] = result; 
            }   
        }
    } 
        //--------------------------
        for (int line = 0; line < localsSizeY; line++) 
    { 
        for (int i = 0; i <= line; i++) {
            result = binomialCoeff 
                        (line, i); 
            if(line == (localsSizeY-1)){
                kernelY[0][i] = result; 
            }   
        }
    } 
        
        
        BufferedImage tempImage = (localFilter.kernelApplier(ourImage.getOriginal(), kernel));
        ourImage.setResult(localFilter.kernelApplier(tempImage, kernelY));
        
        
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
    @FXML
    private void Media(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        
        
        int [][] kernel = new int[localsSizeX][1];
        int [][] kernelY = new int[1][localsSizeY];
        for (int i =0 ; i < localsSizeX; i++){
            kernel[i][0] = 1;
        }
        for (int i =0 ; i < localsSizeY; i++){
            kernelY[0][i] = 1;
        }
        
        
        BufferedImage tempImage = (localFilter.kernelApplier(ourImage.getOriginal(), kernel));
        ourImage.setResult(localFilter.kernelApplier(tempImage, kernelY));
        
        
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
    @FXML
    private void laPlace(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        int kernelSize = localsSizeX > localsSizeY ?  localsSizeX : localsSizeY;
        if(kernelSize%2 == 0){
            kernelSize++;
        }
        
        int [][] kernel = new int[kernelSize][kernelSize];
        
        if(kernelSize ==1){
            kernel[0][0] = 1;
        }else if(kernelSize == 3 && vecindad == 4){
            kernel[0][0] = 0;
            kernel[1][0] = 1;
            kernel[2][0] = 0;
            
            kernel[0][1] = 1;
            kernel[1][1] = -4;
            kernel[2][1] = 1;
            
            kernel[0][2] = 0;
            kernel[1][2] = 1;
            kernel[2][2] = 0;   
        }else if(kernelSize == 3 && vecindad == 8){
            kernel[0][0] = 1;
            kernel[1][0] = 1;
            kernel[2][0] = 1;
            
            kernel[0][1] = 1;
            kernel[1][1] = -8;
            kernel[2][1] = 1;
            
            kernel[0][2] = 1;
            kernel[1][2] = 1;
            kernel[2][2] = 1;   
        }else if(kernelSize == 5 && vecindad == 4){
            kernel[0][0] = 0;
            kernel[1][0] = 0;
            kernel[2][0] = 1;
            kernel[3][0] = 0;
            kernel[4][0] = 0;
            
            kernel[0][1] = 0;
            kernel[1][1] = 1;
            kernel[2][1] = 2;
            kernel[3][1] = 1;
            kernel[4][1] = 0;
            
            kernel[0][2] = 1;
            kernel[1][2] = 2;
            kernel[2][2] = -16;
            kernel[3][2] = 2;
            kernel[4][2] = 1;
            
            kernel[0][3] = 0;
            kernel[1][3] = 1;
            kernel[2][3] = 2;
            kernel[3][3] = 1;
            kernel[4][3] = 0;
            
            kernel[0][4] = 0;
            kernel[1][4] = 0;
            kernel[2][4] = 1;
            kernel[3][4] = 0;
            kernel[4][4] = 0;   
        }else if(kernelSize == 5 && vecindad == 8){
            kernel[0][0] = 1;
            kernel[1][0] = 3;
            kernel[2][0] = 4;
            kernel[3][0] = 3;
            kernel[4][0] = 1;
            
            kernel[0][1] = 3;
            kernel[1][1] = 0;
            kernel[2][1] = -6;
            kernel[3][1] = 0;
            kernel[4][1] = 3;
            
            kernel[0][2] = 4;
            kernel[1][2] = -6;
            kernel[2][2] = -20;
            kernel[3][2] = -6;
            kernel[4][2] = 4;
            
            kernel[0][3] = 3;
            kernel[1][3] = 0;
            kernel[2][3] = -6;
            kernel[3][3] = 0;
            kernel[4][3] = 3;
            
            kernel[0][4] = 1;
            kernel[1][4] = 3;
            kernel[2][4] = 4;
            kernel[3][4] = 3;
            kernel[4][4] = 1;   
        }else if(kernelSize == 7 && vecindad == 4){
            kernel[0][0] = 0;
            kernel[1][0] = 0;
            kernel[2][0] = 1;
            kernel[3][0] = 1;
            kernel[4][0] = 1;
            kernel[5][0] = 0;
            kernel[6][0] = 0;
            
            kernel[0][1] = 0;
            kernel[1][1] = 1;
            kernel[2][1] = 3;
            kernel[3][1] = 3;
            kernel[4][1] = 3;
            kernel[5][1] = 1;
            kernel[6][1] = 0;
            
            kernel[0][2] = 1;
            kernel[1][2] = 3;
            kernel[2][2] = 0;
            kernel[3][2] = -7;
            kernel[4][2] = 0;
            kernel[5][2] = 3;
            kernel[6][2] = 1;
            
            kernel[0][3] = 1;
            kernel[1][3] = 3;
            kernel[2][3] = -7;
            kernel[3][3] = -24;
            kernel[4][3] = -7;
            kernel[5][3] = 3;
            kernel[6][3] = 1;
            
            kernel[0][4] = 1;
            kernel[1][4] = 3;
            kernel[2][4] = 0;
            kernel[3][4] = -7;
            kernel[4][4] = 0;   
            kernel[5][4] = 3;
            kernel[6][4] = 1;
            
            kernel[0][5] = 0;
            kernel[1][5] = 1;
            kernel[2][5] = 3;
            kernel[3][5] = 3;
            kernel[4][5] = 3;   
            kernel[5][5] = 1;
            kernel[6][5] = 0;
            
            kernel[0][6] = 0;
            kernel[1][6] = 0;
            kernel[2][6] = 1;
            kernel[3][6] = 1;
            kernel[4][6] = 1;   
            kernel[5][6] = 0;
            kernel[6][6] = 0;
            
        }else if(kernelSize == 7 && vecindad == 8){
            kernel[0][0] = 2;
            kernel[1][0] = 3;
            kernel[2][0] = 4;
            kernel[3][0] = 6;
            kernel[4][0] = 4;
            kernel[5][0] = 3;
            kernel[6][0] = 2;
            
            kernel[0][1] = 3;
            kernel[1][1] = 5;
            kernel[2][1] = 4;
            kernel[3][1] = 3;
            kernel[4][1] = 4;
            kernel[5][1] = 5;
            kernel[6][1] = 3;
            
            kernel[0][2] = 4;
            kernel[1][2] = 4;
            kernel[2][2] = -9;
            kernel[3][2] = -20;
            kernel[4][2] = -9;
            kernel[5][2] = 4;
            kernel[6][2] = 4;
            
            kernel[0][3] = 3;
            kernel[1][3] = 6;
            kernel[2][3] = -20;
            kernel[3][3] = -36;
            kernel[4][3] = -20;
            kernel[5][3] = 3;
            kernel[6][3] = 6;
            
            kernel[0][4] = 4;
            kernel[1][4] = 4;
            kernel[2][4] = -9;
            kernel[3][4] = -20;
            kernel[4][4] = -9;   
            kernel[5][4] = 4;
            kernel[6][4] = 4;
            
            kernel[0][5] = 3;
            kernel[1][5] = 5;
            kernel[2][5] = 4;
            kernel[3][5] = 3;
            kernel[4][5] = 4;   
            kernel[5][5] = 5;
            kernel[6][5] = 3;
            
            kernel[0][6] = 2;
            kernel[1][6] = 3;
            kernel[2][6] = 4;
            kernel[3][6] = 6;
            kernel[4][6] = 4;   
            kernel[5][6] = 3;
            kernel[6][6] = 2;
            
        }
        
        ourImage.setResult(localFilter.kernelApplier(ourImage.getOriginal(), kernel));
        
        //BufferedImage image2 = (localFilter.kernelApplier(ourImage.getOriginal(), kernel));
        
        
        
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
    
     @FXML
    private void Mediana(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        
        int [][] kernel = new int[localsSizeX][localsSizeY];
        
        ourImage.setResult(localFilter.mediana(ourImage.getOriginal(), kernel));
        
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
    @FXML
    private void Maximo(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        
        
        int [][] kernel = new int[localsSizeX][localsSizeY];
        
        ourImage.setResult(localFilter.maximo(ourImage.getOriginal(), kernel));
        
        Image image = SwingFXUtils.toFXImage(ourImage.getResult(), null);
        imgV.setImage(image);
    }
    @FXML
    private void minimo(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
        
        
        int [][] kernel = new int[localsSizeX][localsSizeY];
        
        ourImage.setResult(localFilter.minimo(ourImage.getOriginal(), kernel));
        
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
        
        int [][] kernel = new int[borderKernelSize][1];
        int [][] kernel2 = new int[1][borderKernelSize];
        int [][]  kernelY = new int[1][borderKernelSize];
        int [][] kernelY2 = new int[borderKernelSize][1];
        
        for (int i=0; i <= borderKernelSize/2; i++ ){
        kernel[i][0] = (int) Math.pow(2, i);
        kernelY[0][i] = (int) Math.pow(2, i);
        kernel[borderKernelSize-(i+1)][0] = (int) Math.pow(2, i);
        kernelY[0][borderKernelSize-(i+1)] = (int) Math.pow(2, i);
        kernel2[0][i] = -1;
        kernelY2[i][0] = -1;
        kernel2[0][borderKernelSize-(i+1)] = 1;
        kernelY2[borderKernelSize-(i+1)][0] = 1;
        }
        
        kernel2[0][borderKernelSize/2 + 1] = 0;
        kernelY2[borderKernelSize/2 + 1][0] = 0;
        
        
        BufferedImage tempImage = (localFilter.kernelApplier(ourImage.getOriginal(), kernel));
        BufferedImage tempImageY = (localFilter.kernelApplier(ourImage.getOriginal(), kernelY));
        
        tempImage = (localFilter.kernelApplier(tempImage, kernel2));
        tempImageY = (localFilter.kernelApplier(tempImageY, kernelY2));
        if(vectorDir == 'M'){
            tempImage = localFilter.gradientCalc(tempImage, tempImageY);
        }else{
            tempImage = vectorDir == 'X' ? tempImage : tempImageY;
        }
        
        
        Image image = SwingFXUtils.toFXImage(tempImage, null);
        
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
        int [][] kernelY = new int[3][3];
        
        
                kernelY[0][0] = -3;
                kernelY[1][0] = -10;
                kernelY[2][0] = -3;
                kernelY[0][1] = 0;
                kernelY[1][1] = 0;
                kernelY[2][1] = 0;
                kernelY[0][2] = 3;
                kernelY[1][2] = 10;
                kernelY[2][2] = 3;
                
                kernel[0][0] = -3;
                kernel[1][0] = 0;
                kernel[2][0] = 3;
                kernel[0][1] = -10;
                kernel[1][1] = 0;
                kernel[2][1] = 10;
                kernel[0][2] = -3;
                kernel[1][2] = 0;
                kernel[2][2] = 3;
                
        
        BufferedImage tempImage = (localFilter.kernelApplier(ourImage.getOriginal(), kernel));
        BufferedImage tempImageY = (localFilter.kernelApplier(ourImage.getOriginal(), kernelY));
        
        if(vectorDir == 'M'){
            tempImage = localFilter.gradientCalc(tempImage, tempImageY);
        }else{
            tempImage = vectorDir == 'X' ? tempImage : tempImageY;
        }
        
        Image image = SwingFXUtils.toFXImage(tempImage, null);
        
        imgV.setImage(image);
    }
    @FXML
    private void roberts(ActionEvent event) throws IOException {
        try {
            if (ourImage.getResult()!=null){
                ourImage.setOriginal(ourImage.getResult());
            }else{
                ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
            }
        } catch (IOException ex) {
            System.out.println("Picture not found");
        }
     
        int [][] kernel = new int[robertsSize][robertsSize];
        int [][] kernelY = new int[robertsSize][robertsSize];
        int half = (int) robertsSize/2;
                for(int y=0; y<robertsSize;y++){
                 for(int x=0; x<robertsSize;x++){
                     if(robertsSize == 2){
                    kernel[x][y] = (x==y) ? (y < half) ? 1 : -1 : 0; 
                    kernelY[x][y] = ((x+y) == (robertsSize - 1)) ? (y < half) ? 1 : -1 : 0;  
                     }else{
                     kernel[x][y] = (x!=y) ? (y < half) ? 1 : -1 : 0; 
                    kernelY[x][y] = ((x+y) != (robertsSize - 1)) ? (y < half) ? 1 : -1 : 0;  
                     }
                    
                }   
                }
        
        BufferedImage tempImage = (localFilter.kernelApplier(ourImage.getOriginal(), kernel));
        BufferedImage tempImageY = (localFilter.kernelApplier(ourImage.getOriginal(), kernelY));
        
        if(vectorDir == 'M'){
            tempImage = localFilter.gradientCalc(tempImage, tempImageY);
        }else{
            tempImage = vectorDir == 'X' ? tempImage : tempImageY;
        }
        
        Image image = SwingFXUtils.toFXImage(tempImage, null);
        
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
        
        int [][]  kernel = new int[borderKernelSize][1];
        int [][] kernel2 = new int[1][borderKernelSize];
        int [][]  kernelY = new int[1][borderKernelSize];
        int [][] kernelY2 = new int[borderKernelSize][1];
        
        for (int i=0; i <= borderKernelSize/2; i++ ){
        kernel[i][0] = 1;
        kernel[borderKernelSize-(i+1)][0] = 1;
        kernelY[0][i] = 1;
        kernel[borderKernelSize-(i+1)][0] = 1;
        kernelY[0][borderKernelSize-(i+1)] = 1;
        kernel2[0][i] = -1;
        kernelY2[i][0] = -1;
        kernel2[0][borderKernelSize-(i+1)] = 1;
        kernelY2[borderKernelSize-(i+1)][0] = 1;
        }
        kernel2[0][borderKernelSize/2 + 1] = 0;
        kernelY2[borderKernelSize/2 + 1][0] = 0;
       
        BufferedImage tempImage = (localFilter.kernelApplier(ourImage.getOriginal(), kernel));
        BufferedImage tempImageY = (localFilter.kernelApplier(ourImage.getOriginal(), kernelY));
        
        tempImage = (localFilter.kernelApplier(tempImage, kernel2));
        tempImageY = (localFilter.kernelApplier(tempImageY, kernelY2));
        
        if(vectorDir == 'M'){
            tempImage = localFilter.gradientCalc(tempImage, tempImageY);
        }else{
            tempImage = vectorDir == 'X' ? tempImage : tempImageY;
        }
        
        Image image = SwingFXUtils.toFXImage(tempImage, null);
        
        
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
           BWSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
               try {
                   if (ourImage.getResult()!=null){
                       ourImage.setOriginal(ourImage.getResult());
                   }else{
                       ourImage.setOriginal(ImageIO.read(new File("./src/images/Desert.bmp")));
                   }
               } catch (IOException ex) {
                   System.out.println("Picture not found");
               }
               double value = BWSlider.getValue();
               System.out.println("hola");
               //ourImage.setResult();
               Image image = SwingFXUtils.toFXImage(globalFilter.blackAndWhite(ourImage.getOriginal(), value), null);
               imgV.setImage(image);
           });
        
    }    
    
}
