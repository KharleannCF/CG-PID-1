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
public class LocalFilter {
     public BufferedImage borders(BufferedImage original, int[][] kernel){
         int totalX = kernel.length;
         int totalY = kernel[0].length;
         int halfX = kernel.length / 2;
         int halfY = kernel[0].length / 2;
         
        
         
        BufferedImage result = new BufferedImage(original.getWidth(),original.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g2 = result.createGraphics();
        
        for (int y = 0; y < original.getHeight(); y++) {
         for (int x = 0; x < original.getWidth(); x++) {
            //Retrieving contents of a pixel
            int pixel = 0;
            int red = 0;
            int green = 0;
            int blue = 0;
            for (int actY = 0; actY < totalY; actY++){
                int moveY = y;
                if (actY < halfY){
                        moveY = moveY - (halfY -actY);
                 }else if (actY > halfY){
                        moveY = moveY + (actY - halfY);
                 }
                for (int actX = 0; actX < totalX; actX++){
                  int moveX = x;
                if (actX < halfX){
                        moveX = moveX - (halfX -actX);
                 }else if (actX > halfX){
                        moveX = moveX + (actX - halfX);
                 }
                  try{
                      pixel = original.getRGB(moveX, moveY);
                  } catch(Exception exp){
                      pixel = original.getRGB(x,y);
                  }
                  Color temp = new Color(pixel, true);
                  int redTemp = temp.getRed();
                  int greenTemp = temp.getGreen();
                  int blueTemp = temp.getBlue();
                  red = red + redTemp * kernel[actX][actY];
                  green = green + greenTemp * kernel[actX][actY];
                  blue = blue + blueTemp * kernel[actX][actY];
                } 
            }
            red = Math.abs(red)/16;
            green = Math.abs(green)/16;
            blue = Math.abs(blue)/16;
            g2.setColor(new Color(red,green,blue));
            g2.fillRect(x, y, x+1, y+1);
            }
        }

        return result;
    }
}
