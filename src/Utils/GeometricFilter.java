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
 * @author Kharleann
 */
public class GeometricFilter {
    public BufferedImage rotateIzqToDer(BufferedImage original){
        BufferedImage result = new BufferedImage(original.getHeight(),original.getWidth(), BufferedImage.TYPE_INT_RGB);
        Graphics g2 = result.createGraphics();
        
        for (int y = 0; y < original.getHeight(); y++) {
         for (int x = 0; x < original.getWidth(); x++) {
            //Retrieving contents of a pixel
            int pixel = original.getRGB(x,y);
            //Creating a Color object from pixel value
            Color color = new Color(pixel, true);
            g2.setColor(color);
            g2.fillRect(y,original.getHeight()-1-x,y+1, original.getHeight()-x);
            }
        }
        System.out.println(original.getHeight());
        System.out.println(result.getWidth());
        return result;
    }
}
