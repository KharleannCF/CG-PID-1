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
    
       public BufferedImage zoomVecino(BufferedImage original, int m, int n){
            BufferedImage result = new BufferedImage((original.getWidth()*2),(original.getHeight()*2), BufferedImage.TYPE_INT_RGB);
            Graphics g2 = result.createGraphics();

            Color colors[][] = new Color[(original.getHeight()*2)][(original.getWidth()*2)];
            
            int k = 0, l = 0;
            System.out.println("ENTRE A LA PUTA FUNCION");
            //Esta solucion es la mas rapida que se me ocurrio, no se si se pueda implementar de forma mas rapida

            for (int y = 0; y < result.getHeight(); y++) {
                for (int x = 0; x < result.getWidth(); x++) {
                   try{
                       
                    g2.setColor(Vecino(original, y/2, x/2));
                    g2.fillRect(y,x, y+1, x+1);
                    
                   }catch(Exception exp){
                       
                   }
                }
            }

        System.out.println("RETORNO");
        return result;
       }
       
       public Color Vecino(BufferedImage Original, int j, int i){
           Color color;
           int minDistancia = 9999999;
           int minY = 0, minX = 0;
           for (int y = j-1; y < j+1; y++) {
                for (int x = i-1; x < i+1; x++) {
                  if(Math.sqrt( (Math.pow(y-j,2) + Math.pow(x-i,2))) < minDistancia ){
                      minY = y;
                      minX = x;
                  }
                }
            }
           
           int pixel = Original.getRGB(minY,minX);
           int  red = (pixel & 0x00ff0000) >> 16;  
           int  green = (pixel & 0x0000ff00) >> 8;
           int  blue = pixel & 0x000000ff;
           color = new Color(red,green,blue);
           
           return color;
       }
       
    public BufferedImage deZoomVecino(BufferedImage original, int m, int n){
            BufferedImage result = new BufferedImage((original.getWidth()/2),(original.getHeight()/2), BufferedImage.TYPE_INT_RGB);
            Graphics g2 = result.createGraphics();

            Color colors[][] = new Color[(original.getHeight()/2)][(original.getWidth()/2)];
            
            int k = 0, l = 0;
            System.out.println("ENTRE A LA PUTA FUNCION");
            //Esta solucion es la mas rapida que se me ocurrio, no se si se pueda implementar de forma mas rapida

            for (int y = 0; y < result.getHeight(); y++) {
                for (int x = 0; x < result.getWidth(); x++) {
                   try{
                       
                    g2.setColor(Vecino(original, y*2, x*2));
                    g2.fillRect(y,x, y+1, x+1);
                    
                   }catch(Exception exp){
                       
                   }
                }
            }

        System.out.println("RETORNO");
        return result;
       }
}