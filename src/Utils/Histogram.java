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
public class Histogram {
    public BufferedImage histogram(BufferedImage original){
        BufferedImage result = new BufferedImage(256*5,425*3, BufferedImage.TYPE_INT_RGB);
        Graphics g2 = result.createGraphics();
        Color white = new Color(255,255,255);
        g2.setColor(white);
        
        g2.fillRect(0, 0, 256*5, (425*5));
        int[] reds = new int[256];
        int[] normalDataRed = new int[]{100000, 0, 0};
        int[] normalDataGreen = new int[]{100000, 0, 0};
        int[] normalDataBlue = new int[]{100000, 0, 0};
        int[] greens = new int[256];
        int[] blues = new int[256];
        
        for (int i = 0; i < 256; i++){
            reds[i]=0;
            greens[i]=0;
            blues[i]=0;
        }
        
        for (int y = 0; y < original.getHeight(); y++) {
         for (int x = 0; x < original.getWidth(); x++) {
            //Retrieving contents of a pixel
            int pixel = original.getRGB(x,y);
            //Creating a Color object from pixel value
            Color color = new Color(pixel, true);
            
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            
            reds[red]++;
            greens[green]++;
            blues[blue]++;
            
            
            normalDataRed[0] = (normalDataRed[0] > reds[red]) ?  reds[red] : normalDataRed[0];
            normalDataRed[1] = (normalDataRed[1] < reds[red]) ?  reds[red] : normalDataRed[1];
            
            
            normalDataGreen[0] = (normalDataGreen[0] > greens[green]) ?  greens[green] : normalDataGreen[0];
            normalDataGreen[1] = (normalDataGreen[1] < greens[green]) ?  greens[green] : normalDataGreen[1];
            
            
            normalDataBlue[0] = (normalDataBlue[0] > blues[blue]) ?  blues[blue] : normalDataBlue[0];
            normalDataBlue[1] = (normalDataBlue[1] < blues[blue]) ?  blues[blue] : normalDataBlue[1];
          
            }
        }
        
        for(int i=0; i<256*5; i++){
                
                Color red = new Color(i/5,0,0);
                g2.setColor(red);
                int newY = (( reds[i/5] - normalDataRed[0])*400) / ( normalDataRed[1] - normalDataRed[0] ) ;
                g2.drawLine(i, 425, i,425-newY  );
                
                Color green = new Color(0,i/5,0);
                g2.setColor(green);
                newY = ((greens[i/5] - normalDataGreen[0]) * ( 400 ) )/(normalDataGreen[1] - normalDataGreen[0] );
                g2.drawLine(i, 850, i, 850-newY);
                
                Color blue = new Color(0,0,i/5);
                g2.setColor(blue);
                newY = ((blues[i/5] - normalDataBlue[0]) * ( 400 ) )/(normalDataBlue[1] - normalDataBlue[0] );
                g2.drawLine(i, 1275, i, 1275-newY);
                
                /*
                
                
                */
       
            }
        
        return result;
    }
    public BufferedImage histogramFlatten(BufferedImage original){
        BufferedImage result = new BufferedImage(256*5,425, BufferedImage.TYPE_INT_RGB);
        System.out.println(original.getWidth());
        BufferedImage histRed = original.getSubimage(0, 0, 256*5-1, 425);
        BufferedImage histGreen = original.getSubimage(0, 425, 256*5-1, 850);
        
        Graphics g2 = result.createGraphics();
        
        for(int y = 0; y< 425; y++){
            for(int x=0; x<255*5; x=x+5){
                 int pixelRed = histRed.getRGB(x,y);
                 int pixelGreen = histGreen.getRGB(x,y);
                 int pixelBlue = original.getRGB(x,y+850);
                 
                 Color colorRed = new Color(pixelRed, true);
                 Color colorGreen = new Color(pixelGreen, true);                 
                 Color colorBlue = new Color(pixelBlue, true);
                 
                 colorBlue = new Color((colorBlue.getRed() + colorGreen.getRed() + colorRed.getRed())/3,
                         (colorBlue.getGreen() + colorGreen.getGreen() + colorRed.getGreen())/3,
                         (colorBlue.getBlue() + colorGreen.getBlue() + colorRed.getBlue())/3);
                 
                 g2.setColor(colorBlue);
                 g2.fillRect(x, y, x+5, y+1);
                
               
            }
        }
        return result;
    }
}
