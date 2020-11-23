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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.TermCriteria;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.accumulate;

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
   
    public BufferedImage kmeans(BufferedImage original, int colorNumber){
        
        BufferedImage result;
        Neptune a = new Neptune();
        Mat origin = a.matify(original);
        int n = origin.rows()*origin.cols();
        Mat dst = origin.reshape(1,n);
        int k = colorNumber;
        
        if(colorNumber < 3){
            k = 3;
        }
        
        dst.convertTo(dst, CvType.CV_32F);
        Mat labels = new Mat();
	TermCriteria criteria = new TermCriteria(TermCriteria.COUNT, 100, 1);
	Mat centers = new Mat();
	Core.kmeans(dst, k, labels, criteria, 1, Core.KMEANS_PP_CENTERS, centers);
        
	
        System.out.println(dst);
        System.out.println(labels);
        System.out.println(centers);
            
        for(int i = 0; i < n; i++){
            
            int index = findTag(centers, labels.get(i, 0));
            
            dst.put(i,0, centers.get(index,0));
            dst.put(i,1, centers.get(index,1));
            dst.put(i,2, centers.get(index,2));
        }
        
        int rows = 0;
        
        
  /*      
        for (int i = 0; i < n; ++i)
        {
            dst.set(i, 0) = colors(labels[i], 0);
            data.at<float>(i, 1) = colors(labels[i], 1);
            data.at<float>(i, 2) = colors(labels[i], 2);
        }
*/
        Mat reduced = dst.reshape(3, origin.rows());
        dst.convertTo(dst, CvType.CV_8U);
        reduced.convertTo(reduced, CvType.CV_8U);
        System.out.println(reduced);
        origin = reduced;
        
        try{
            
            result = bufferize(origin);
            
        }catch(IOException ex){
            
            result = original;
            
        }
        
        return result;
        
    }
    
    public int findTag(Mat colors, double[] label){
        
        return (int) label[0];
        
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
    
    public BufferedImage bht(BufferedImage original){
        
        BufferedImage result;
        Neptune a = new Neptune();
        Mat origin = a.matify(original);
        Mat dst = new Mat(origin.rows(), origin.cols(), origin.type());
        Imgproc.cvtColor(origin, dst, Imgproc.COLOR_BGR2GRAY);
        int histSize = 256;
        boolean accumulate = false;

        float[] range = {0, 256}; //the upper boundary is exclusive
        MatOfFloat histRange = new MatOfFloat(range);
        
        List<Mat> hist = new ArrayList<>();
        hist.add(dst);
        
        Mat bHist = new Mat();
        Imgproc.calcHist(hist, new MatOfInt(0), new Mat(), bHist, new MatOfInt(histSize), histRange, accumulate);       
        float[] histogram = new float[(int) (bHist.total() * bHist.channels())];
        bHist.get(0, 0, histogram);

        int i_s = 0, i_e = 255;
        int i_m = (int)((i_s + i_e) / 2.0f); // center of the weighing scale I_m
        float w_l = get_weight(i_s, i_m + 1, histogram); // weight on the left W_l
        float w_r = get_weight(i_m + 1, i_e + 1, histogram); // weight on the right W_r
        while (i_s <= i_e) {
            if (w_r > w_l) { // right side is heavier
                w_r -= histogram[i_e--];
                if (((i_s + i_e) / 2) < i_m) {
                    w_r += histogram[i_m];
                    w_l -= histogram[i_m--];
                }
            } else if (w_l >= w_r) { // left side is heavier
                w_l -= histogram[i_s++]; 
                if (((i_s + i_e) / 2) >= i_m) {
                    w_l += histogram[i_m + 1];
                    w_r -= histogram[i_m + 1];
                    i_m++;
                }
            }
        }
    
        
        Imgproc.threshold(dst, dst, i_m, 255, Imgproc.THRESH_BINARY);
        dst.convertTo(dst, CvType.CV_8UC3);
        origin = dst;
        
        try{
            
            result = bufferize(origin);
            
        }catch(IOException ex){
            
            result = original;
            
        }
        
        return result;
    }
    
    public float get_weight(int inf, int sup, float[] histogram){
        
        float weight = (float) 0.0;
        
        for(int i = inf; i < sup; i++){
            weight += histogram[i];
        }
        
        return weight;
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
