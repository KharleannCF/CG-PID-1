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
    
       public BufferedImage zoomVecino(BufferedImage original, int m, int n, int mult){
            BufferedImage result = new BufferedImage((original.getWidth()*mult),(original.getHeight()*mult), BufferedImage.TYPE_INT_RGB);
            Graphics g2 = result.createGraphics();
            
            int k = 0, l = 0;
            //System.out.println("ENTRE A LA PUTA FUNCION");
            //Esta solucion es la mas rapida que se me ocurrio, no se si se pueda implementar de forma mas rapida

            int Height = result.getHeight();
            int Width = result.getWidth();
            for (int y = 0; y < Height; y++) {
                for (int x = 0; x < Width; x++) {
                   try{
                       
                    g2.setColor(Vecino(original,(float) y/mult,(float) x/mult));
                    g2.fillRect(y,x, y+1, x+1);
                    
                   }catch(Exception exp){
                       Color color = new Color(0,0,0);
                       g2.setColor(color);
                       g2.fillRect(y,x,y+1,x+1);
                   }
                }
            }

        //System.out.println("RETORNO");
        return result;
       }
       
       public Color Vecino(BufferedImage Original, float j, float i){
           Color color;
           int minDistancia = 9999999;
           int minY = 0, minX = 0;
           for (int y = (int)j-1; y < (int)j+1; y++) {
                for (int x = (int)i-1; x < (int)i+1; x++) {
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
       
    public BufferedImage deZoomVecino(BufferedImage original, int m, int n, int mult){
            BufferedImage result = new BufferedImage((original.getWidth()/mult),(original.getHeight()/mult), BufferedImage.TYPE_INT_RGB);
            Graphics g2 = result.createGraphics();
            
            int k = 0, l = 0;
            System.out.println("ENTRE A LA PUTA FUNCION");
            //Esta solucion es la mas rapida que se me ocurrio, no se si se pueda implementar de forma mas rapida

            for (int y = 0; y < result.getHeight(); y++) {
                for (int x = 0; x < result.getWidth(); x++) {
                   try{
                       
                    g2.setColor(Vecino(original, y*mult, x*mult));
                    g2.fillRect(y,x, y+1, x+1);
                    
                   }catch(Exception exp){
                       
                   }
                }
            }

        System.out.println("RETORNO");
        return result;
       }
    
    public BufferedImage zoomInBilineal(BufferedImage original, int m, int n, int mult){
        
            BufferedImage result = new BufferedImage((original.getWidth()*mult),(original.getHeight()*mult), BufferedImage.TYPE_INT_RGB);
            Graphics g2 = result.createGraphics();
            
            //Esta solucion es la mas rapida que se me ocurrio, no se si se pueda implementar de forma mas rapida

            for (int y = 0; y < result.getHeight(); y++) {
                for (int x = 0; x < result.getWidth(); x++) {
                   try{
                       
                    g2.setColor(APrima(original, (float) y/mult, (float) x/mult));
                    g2.fillRect(y,x, y+1, x+1);
                    
                   }catch(Exception exp){
                       
                   }
                }
            }

        System.out.println("RETORNO");
        return result;
       }
    
    public BufferedImage deZoomInBilineal(BufferedImage original, int m, int n, int mult){
        
            BufferedImage result = new BufferedImage((original.getWidth()/mult),(original.getHeight()/mult), BufferedImage.TYPE_INT_RGB);
            Graphics g2 = result.createGraphics();
            
            //Esta solucion es la mas rapida que se me ocurrio, no se si se pueda implementar de forma mas rapida

            for (int y = 0; y < result.getHeight(); y++) {
                for (int x = 0; x < result.getWidth(); x++) {
                   try{
                       
                    g2.setColor(APrima(original, (float) y*mult, (float) x*mult));
                    g2.fillRect(y,x, y+1, x+1);
                    
                   }catch(Exception exp){
                       
                   }
                }
            }

        System.out.println("RETORNO");
        return result;
       }
    
    public Color APrima(BufferedImage original, float j, float i){
           Color color;
           float b = 0, unoMenosB = 0, a = 0, unoMenosA = 0; 
           int col = (int) j;
           int fil = (int) i;
           double red1, green1, blue1, red2, green2, blue2 ,red3, green3, blue3, red4, green4, blue4;
           int red, green, blue;
                    
           a = (float)(Math.abs(i-fil));
           unoMenosA = (float) Math.abs(1 - a);
           b = (float) Math.abs(j-col);
           unoMenosB = (float) Math.abs(1 - b);
           
           red = (int)(((unoMenosA*unoMenosB)*Ared(original, col, fil)) + ((a*unoMenosB)*Ared(original, col+1, fil)) + ((unoMenosA*b)*Ared(original,col,fil+1)) + ((a*b)*Ared(original,col+1,fil+1)));
           green = (int)(((unoMenosA*unoMenosB)*Agreen(original, col, fil)) + ((a*unoMenosB)*Agreen(original, col+1, fil)) + ((unoMenosA*b)*Agreen(original,col,fil+1)) + ((a*b)*Agreen(original,col+1,fil+1)));
           blue = (int)(((unoMenosA*unoMenosB)*Ablue(original, col, fil)) + ((a*unoMenosB)*Ablue(original, col+1, fil)) + ((unoMenosA*b)*Ablue(original,col,fil+1)) + ((a*b)*Ablue(original,col+1,fil+1)));
           
           /*System.out.println(a);
           System.out.println(b);
           System.out.println(unoMenosA);
           System.out.println(unoMenosB);*/
           //System.out.println(red4);
           //System.out.println(green4);
           //System.out.println(blue4);
           
           color = new Color(red,green,blue); 
           
        return color;
    }
    
    public int Ared(BufferedImage Original, int j, int i){
           
           int pixel = Original.getRGB(j,i);
           int  red = (pixel & 0x00ff0000) >> 16;  
           
           //System.out.println(red);
           
           return red;
    }
    
    
    
    public int Agreen(BufferedImage Original, int j, int i){
           
           int pixel = Original.getRGB(j,i);
           int  green = (pixel & 0x0000ff00) >> 8;
           
           //System.out.println(green);
           
           return green;
    }
    
    public int Ablue(BufferedImage Original, int j, int i){
        
           int pixel = Original.getRGB(j,i);
           int blue = pixel & 0x000000ff;
           
           //System.out.println(blue);
           
           return blue;
    }
   
    
    
}