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
    public BufferedImage brigthness(BufferedImage original, double sum){
        BufferedImage result = new BufferedImage(original.getWidth(),original.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g2 = result.createGraphics();
        
        for (int y = 0; y < original.getHeight(); y++) {
         for (int x = 0; x < original.getWidth(); x++) {
            //Retrieving contents of a pixel
            int pixel = original.getRGB(x,y);
            //Creating a Color object from pixel value
            Color color = new Color(pixel, true);
            
           int red = (int) (sum + color.getRed());
            int green = (int) (sum + color.getGreen());
            int blue = (int) (sum + color.getBlue());
            
            red = red > 255 ? 255 : red;
            green = green > 255 ? 255 : green;
            blue = blue > 255 ? 255 : blue;
            
            g2.setColor(new Color(red, green, blue));
            g2.fillRect(x, y, x+1, y+1);
            }
        }
        return result;
    }
    public BufferedImage negativeBrightness(BufferedImage original, double sum){
        BufferedImage result = new BufferedImage(original.getWidth(),original.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g2 = result.createGraphics();
        
        for (int y = 0; y < original.getHeight(); y++) {
         for (int x = 0; x < original.getWidth(); x++) {
            //Retrieving contents of a pixel
            int pixel = original.getRGB(x,y);
            //Creating a Color object from pixel value
            Color color = new Color(pixel, true);
            
           int red = (int) (color.getRed() - sum);
            int green = (int) (color.getGreen() - sum);
            int blue = (int) (color.getBlue() - sum);
            
            red = red < 0 ? 0 : red;
            green = green < 0 ? 0 : green;
            blue = blue < 0 ? 0 : blue;
            
            g2.setColor(new Color(red, green, blue));
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
    public BufferedImage blackAndWhite(BufferedImage original, double value){
        BufferedImage result = new BufferedImage(original.getWidth(),original.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g2 = result.createGraphics();
        
        for (int y = 0; y < original.getHeight(); y++) {
         for (int x = 0; x < original.getWidth(); x++) {
            //Retrieving contents of a pixel
            int pixel1 = original.getRGB(x,y);
        
            //Creating a Color object from pixel value
            Color color = new Color(pixel1, true);
            
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            int total = red + green + blue;
            total = total/3;
             
            if (total < value){
                color = new Color(0,0,0);
            }else {
                color = new Color(255,255,255);
            }
           g2.setColor(color);
           g2.fillRect(x, y, x+1, y+1);
            }
        }
        return result;
    }
}
