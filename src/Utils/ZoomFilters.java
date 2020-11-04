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
    
    public BufferedImage zoomInBilineal(BufferedImage original, int m, int n){
        
            BufferedImage result = new BufferedImage((original.getWidth()*2),(original.getHeight()*2), BufferedImage.TYPE_INT_RGB);
            Graphics g2 = result.createGraphics();
            
            //Esta solucion es la mas rapida que se me ocurrio, no se si se pueda implementar de forma mas rapida

            for (int y = 0; y < result.getHeight(); y++) {
                for (int x = 0; x < result.getWidth(); x++) {
                   try{
                       
                    g2.setColor(APrima(original, y/2, x/2, y, x));
                    g2.fillRect(y,x, y+1, x+1);
                    
                   }catch(Exception exp){
                       
                   }
                }
            }

        System.out.println("RETORNO");
        return result;
       }
    
    public Color APrima(BufferedImage original, float j, float i, int l, int k){
           Color color;
           double b = 0, unoMenosB = 0, a = 0, unoMenosA = 0; 
           int minDistancia = 9999999;
           double red1, green1, blue1, red2, green2, blue2 ,red3, green3, blue3, red4, green4, blue4;
           int red, green, blue;
                    
           a = Math.abs(i-k);
           unoMenosA = 1 - a;
           b = Math.abs(j-l);
           unoMenosB = 1 - b;
           
           red4 = (((unoMenosA*unoMenosB)*Ared(original, l, k)) + ((a*unoMenosB)*Ared(original, l+1, k)) + ((unoMenosA*b)*Ared(original,l,k+1)) + ((a*b)*Ared(original,l+1,k+1)));
           green4 = (((unoMenosA*unoMenosB)*Agreen(original, l, k)) + ((a*unoMenosB)*Agreen(original, l+1, k)) + ((unoMenosA*b)*Agreen(original,l,k+1)) + ((a*b)*Agreen(original,l+1,k+1)));
           blue4 = (((unoMenosA*unoMenosB)*Ablue(original, l, k)) + ((a*unoMenosB)*Ablue(original, l+1, k)) + ((unoMenosA*b)*Ablue(original,l,k+1)) + ((a*b)*Ablue(original,l+1,k+1)));
        
           System.out.println(red4);
           System.out.println(green4);
           System.out.println(blue4);
           
           color = new Color(0,0,0); 
           
        return color;
    }
    
    public int Ared(BufferedImage Original, int j, int i){
           
           int pixel = Original.getRGB(j,i);
           int  red = (pixel & 0x00ff0000) >> 16;  
           
           System.out.println(red);
           
           return red;
    }
    
    
    
    public int Agreen(BufferedImage Original, int j, int i){
           
           int pixel = Original.getRGB(j,i);
           int  green = (pixel & 0x0000ff00) >> 8;
           
           System.out.println(green);
           
           return green;
    }
    
    public int Ablue(BufferedImage Original, int j, int i){
        
           int pixel = Original.getRGB(j,i);
           int blue = pixel & 0x000000ff;
           
           System.out.println(blue);
           
           return blue;
    }
   
    
    
}