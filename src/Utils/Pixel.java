/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Color;

/**
 *
 * @author Kharleann
 */
public class Pixel {
    public Color color;
    public int ubiX;
    public int ubiY;
    
    public Pixel(){
        ubiX = 0;
        ubiY = 0;
        
    }
    
    public Color getColor(){
        return color;
    }
    
    public void setColor(Color NewColor){
        this.color = NewColor;
    }
    
    public int getUbiX(){
        return ubiX;
    }
    
    public int getUbiY(){
        return ubiY;
    }
    
    public void setUbiX(int newUbiX){
        this.ubiX = newUbiX;
    }
    
    public void setUbiY(int newUbiY){
        this.ubiY = newUbiY;
    }
}
