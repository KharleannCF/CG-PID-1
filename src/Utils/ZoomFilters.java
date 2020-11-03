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

            boolean mask[][] = new boolean[(original.getWidth()*2)][(original.getHeight()*2)];
            int k = 0, l = 0;
            System.out.println(original.getWidth()*2);
            System.out.println(original.getHeight()*2);
            System.out.println("ENTRE A LA PUTA FUNCION");
            //Esta solucion es la mas rapida que se me ocurrio, no se si se pueda implementar de forma mas rapida

            for (int x = 0; x < original.getHeight()*2; x = x + 2) {
                for (int y = 0; y < original.getWidth()*2; y = y + 2) {
                   //Retrieving contents of a pixel
                   //System.out.println(x);
                   //System.out.println(y);
                   int pixel = original.getRGB(l,k);
                   //Creating a Color object from pixel value
                   Color color = new Color(pixel, true);
                   g2.setColor(color);
                   g2.fillRect(y,x, y+1, x+1);
                   mask[x][y] = true;
                   k++;
                }
                l++;
                k = 0;
            }

            k = 0;
            System.out.println("PRIMER CICLO DONE");
            //Esta Solucion es una porqueria, pero es la unica forma en la que se me ocurrio

            for(int i = 0; i < original.getHeight()*2; i++){
                for(int j = 0; j < original.getWidth()*2; j++){
                    int minDistance = 999999999;
                    for (int x = 0; x < original.getHeight()*2; x++) {
                        for (int y = 0; y < original.getWidth()*2; y++) {
                            if(mask[x][y]){
                                if(Math.sqrt((Math.pow((y - j),2) + Math.pow((x - i),2))) < minDistance){
                                    int pixel = result.getRGB(x,y);
                                    Color color = new Color(pixel, true);
                                    g2.setColor(color);
                                    g2.fillRect(i,j, i+1, j+1);
                                    mask[i][j] = true;
                                    System.out.println(x);
                                    System.out.println(y);
                                }
                            }
                        }
                    }
                }
            }

        System.out.println("RETORNO");
        return result;
       }
       
}