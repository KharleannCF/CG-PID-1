/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import Utils.Neptune;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

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
    
    public BufferedImage contrast(BufferedImage original, int min, int max){
        BufferedImage result = new BufferedImage(original.getWidth(),original.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g2 = result.createGraphics();
        
        Color minColor = new Color(min);
        Color maxColor = new Color(max);
        System.out.println(minColor.getGreen());
        System.out.println(maxColor.getGreen());
        System.out.println(minColor.getBlue());
        System.out.println(maxColor.getBlue());
        for (int y = 0; y < original.getHeight(); y++) {
         for (int x = 0; x < original.getWidth(); x++) {
            //Retrieving contents of a pixel
            int pixel = original.getRGB(x,y);
            //Creating a Color object from pixel value
            Color color = new Color(pixel, true);
            
           int red = (int) (((color.getRed() - minColor.getRed())*255)/(maxColor.getRed() - minColor.getRed()) );
            int green = (int) (((color.getGreen() - minColor.getGreen())*255)/(maxColor.getGreen() - minColor.getGreen()) );
            int blue = (int) (((color.getBlue() - minColor.getBlue())*255)/(maxColor.getBlue() - minColor.getBlue()) );;
            
            red = red < 0 ? 0 : red;
            green = green < 0 ? 0 : green;
            blue = blue < 0 ? 0 : blue;
            red = red > 255 ? 255 : red;
            green = green > 255 ? 255 : green;
            blue = blue > 255 ? 255 : blue;
            
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
    
    public BufferedImage otsu(BufferedImage original){
        
        BufferedImage result;
        Neptune a = new Neptune();
        Mat origin = a.matify(original);
        Mat dst = new Mat(origin.rows(), origin.cols(), origin.type());
        Imgproc.cvtColor(origin, dst, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(dst, dst, 50, 255, Imgproc.THRESH_OTSU);
        dst.convertTo(dst, CvType.CV_8UC3);
        origin = dst;
        
        try{
            
            result = bufferize(origin);
            
        }catch(IOException ex){
            
            result = original;
            
        }
        
        return result;
    }
    
    public BufferedImage bufferize(Mat ori) throws IOException{
        MatOfByte mOB = new MatOfByte();
        Imgcodecs.imencode(".png", ori, mOB);
        byte [] ba = mOB.toArray();
        BufferedImage result = ImageIO.read(new ByteArrayInputStream(ba));
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
