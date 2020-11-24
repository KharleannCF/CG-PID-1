/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Stack;
import org.opencv.core.Mat;

/**
 *
 * @author Kharleann
 */
public class ImageCustom {
    public BufferedImage original;
    public BufferedImage result;
    public Stack<BufferedImage> undoStack;
    public Stack<BufferedImage> redoStack;
    public Pixel[][] pixels;
    public int width;
    public int height;
    public int totalColors;
    public List<Mat> allPlanes;
    public Mat complexImage;
    
    public ImageCustom(){
        width = 0;
        height = 0;
        totalColors = 0;
        undoStack = new Stack<>();
        redoStack = new Stack<>();  
    }
    
    public BufferedImage redo(BufferedImage actual){
        BufferedImage result;
        if(!this.redoStack.empty()){
            result = this.redoStack.pop();
            this.undoStack.push(actual);
        }else{
            result = actual;
        }
        return result;
    }
    
    public BufferedImage undo(BufferedImage actual){
        BufferedImage result;
        if(!this.undoStack.empty()){
            result = this.undoStack.pop();
            this.redoStack.push(actual);
        }else{
            result = actual;
        }
        return result;
    }
    
    public void newAction(BufferedImage actual){
        
        this.undoStack.push(actual);
        
        while( !this.redoStack.empty() ){
            this.redoStack.pop();
        }
        
    }
    
    public void addPlanes(List<Mat> planes){
        this.allPlanes = planes;
    }
    public List<Mat>  getPlanes(){
        return this.allPlanes;
    }
    public void addImage(Mat planes){
        this.complexImage = planes;
    }
    public Mat getImage(){
        return this.complexImage;
    }
    public void setOriginal(BufferedImage img){
        this.original = img;
    }
    
    public void setResult(BufferedImage img){
       this. result = img;
    }
    
    public void reSize(int width, int height){
        this.pixels = new Pixel[width][height];
    }
    
    public void setPixelColor(Color value, int axisX, int axisY){
        this.pixels[axisX][axisY].setColor(value);  
    }
    
    public void setPixelUbi(int valueX, int valueY, int axisX, int axisY){
        this.pixels[axisX][axisY].setUbiX(valueX);
        this.pixels[axisX][axisY].setUbiY(valueY);
    }
    
    public void setWidth(int newWidth){
        this.width = newWidth;
    }
    
    public void setHeight(int newHeight){
        this.height = newHeight;
    }
    
    public void setTotalColors(int newColors){
        this.totalColors = newColors;
    }
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }
    public int getTotalColors(){
        return this.totalColors;
    }
    
    public BufferedImage getOriginal(){
        return original;
    }
    
    public BufferedImage getResult(){
        return result;
    }
}
