/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kharleann
 */
public class ImageCustom {
    public BufferedImage original;
    public BufferedImage result;
    public Pixel[][] pixels;
    public int width;
    public int height;
    public int totalColors;
    
    
    public ImageCustom(){
        width = 0;
        height = 0;
        totalColors = 0;
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
