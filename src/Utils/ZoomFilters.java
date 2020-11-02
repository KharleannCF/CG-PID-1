/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Danielito
 */
public class ZoomFilters {
    
    
    
       public BufferedImage zoomVecino(BufferedImage original, int i, int j){
        BufferedImage result = new BufferedImage(original.getHeight(),original.getWidth(), BufferedImage.TYPE_INT_RGB);
        Graphics g2 = result.createGraphics();
        
        int pixelPivot = original.getRGB(i,j);
        Color colorPivot = new Color(pixelPivot, true);
        g2.setColor(colorPivot);
        g2.fillRect(result.getWidth()/2,result.getHeight()/2, (result.getWidth()/2)+1, (result.getHeight()/2)+1);
        
        for (int y = result.getHeight()/2; y < result.getHeight(); y = y + 2) {
         for (int x = result.getWidth()/2; x < result.getWidth(); x = x + 2) {
            //Retrieving contents of a pixel
            int pixel = original.getRGB(y,x);
            //Creating a Color object from pixel value
            Color color = new Color(pixel, true);
            g2.setColor(color);
            g2.fillRect(x,y, x+1, y+1);
            }
        }
        
        
        
        return result;
    }
}
