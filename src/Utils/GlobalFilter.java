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
public class GlobalFilter {
    public BufferedImage negative(BufferedImage original){
        BufferedImage result = new BufferedImage(original.getWidth(),original.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g2 = result.createGraphics();
        
        for (int y = 0; y < original.getHeight(); y++) {
         for (int x = 0; x < original.getWidth(); x++) {
            //Retrieving contents of a pixel
            int pixel = original.getRGB(x,y);
            //Creating a Color object from pixel value
            Color color = new Color(pixel, true);
            
            int red = 255 - color.getRed();
            int green = 255 -  color.getGreen();
            int blue = 255 -  color.getBlue();
            g2.setColor(new Color(red,green,blue));
            g2.fillRect(x, y, x+1, y+1);
            }
        }
        return result;
    }
    
    public BufferedImage blackWhite(BufferedImage original){
        BufferedImage result = new BufferedImage(original.getWidth(),original.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g2 = result.createGraphics();
        
        for (int y = 0; y < original.getHeight(); y++) {
         for (int x = 0; x < original.getWidth(); x++) {
            //Retrieving contents of a pixel
            int pixel = original.getRGB(x,y);
            //Creating a Color object from pixel value
            Color color = new Color(pixel, true);
            
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            int gray = (int) ((red * 0.21 + green * 0.72 + blue*0.07));
            g2.setColor(new Color(gray,gray,gray));
            g2.fillRect(x, y, x+1, y+1);
            }
        }
        return result;
    }
    public BufferedImage borders(BufferedImage original){
        BufferedImage result = new BufferedImage(original.getWidth(),original.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g2 = result.createGraphics();
        
        for (int y = 0; y < original.getHeight(); y++) {
         for (int x = 0; x < original.getWidth(); x++) {
            //Retrieving contents of a pixel
            int pixel1 = original.getRGB(x,y);
            int pixel2;
             try{
            pixel2 = original.getRGB(x-1,y);
            }catch(Exception ex){
                pixel2 = original.getRGB(x,y);
            }
            //Creating a Color object from pixel value
            Color color1 = new Color(pixel1, true);
            Color color2 = new Color(pixel2, true);
            
            int red = Math.abs(color2.getRed() - color1.getRed());
            int green = Math.abs(color2.getGreen() - color1.getGreen());
            int blue = Math.abs(color2.getBlue() - color1.getBlue());
            
            g2.setColor(new Color(red,green,blue));
            g2.fillRect(x, y, x+1, y+1);
            }
        }
        return result;
    }
}
