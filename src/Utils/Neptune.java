/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Danielito
 */
public class Neptune {
       
    
    public Mat bufferedImage2Mat(BufferedImage bi) throws IOException{
        
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        byte[] data; 
                try{
                    data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
                    mat.put(0,0,data);
                }catch(Exception exp){
                    int[] dataInt = ((DataBufferInt)bi.getRaster().getDataBuffer()).getData();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(dataInt.length * 4); 
                    IntBuffer intBuffer = byteBuffer.asIntBuffer();
                    intBuffer.put(dataInt);
                    mat.put(0, 0, byteBuffer.array());
                }
                
        
        return mat;
    }
    
    public Mat matify(BufferedImage sourceImg) {

    long millis = System.currentTimeMillis();

    DataBuffer dataBuffer = sourceImg.getRaster().getDataBuffer();
    byte[] imgPixels = null;
    Mat imgMat = null;

    int width = sourceImg.getWidth();
    int height = sourceImg.getHeight();

    if(dataBuffer instanceof DataBufferByte) {      
            imgPixels = ((DataBufferByte)dataBuffer).getData();
    }

    if(dataBuffer instanceof DataBufferInt) {

        int byteSize = width * height;      
        imgPixels = new byte[byteSize*3];

        int[] imgIntegerPixels = ((DataBufferInt)dataBuffer).getData();

        for(int p = 0; p < byteSize; p++) {         
            imgPixels[p*3 + 2] = (byte) ((imgIntegerPixels[p] & 0x00FF0000) >> 16);
            imgPixels[p*3 + 1] = (byte) ((imgIntegerPixels[p] & 0x0000FF00) >> 8);
            imgPixels[p*3 + 0] = (byte) (imgIntegerPixels[p] & 0x000000FF);
        }
    }

    if(imgPixels != null) {
        imgMat = new Mat(height, width, CvType.CV_8UC3);
        imgMat.put(0, 0, imgPixels);
    }

    System.out.println("matify exec millis: " + (System.currentTimeMillis() - millis));

    return imgMat;
}
    public BufferedImage bufferize(Mat ori) throws IOException{
        MatOfByte mOB = new MatOfByte();
        Imgcodecs.imencode(".png", ori, mOB);
        byte [] ba = mOB.toArray();
        BufferedImage result = ImageIO.read(new ByteArrayInputStream(ba));
        return result;
    }
    
     public BufferedImage FreeRotation(BufferedImage original, int grades) throws IOException{
        BufferedImage result =null;
        Mat origin = null;
        origin = this.matify(original);
        Mat resultMat = new Mat();
        
        Mat rotationMatrix = Imgproc.getRotationMatrix2D(new Point(original.getWidth()/2, original.getHeight()/2), grades, 1 );
        int maxsize = origin.rows() > origin.cols() ? origin.rows():origin.cols();
        Imgproc.warpAffine(origin, resultMat, rotationMatrix, new Size(maxsize, maxsize));
        result = bufferize(resultMat);
        
        return result;
    }
     
         public BufferedImage freePanning(BufferedImage original, int panX, int panY) throws IOException{
        BufferedImage result =null;
        Mat origin = null;
        origin = this.matify(original);
        Mat resultMat = new Mat();
        Point p1 = new Point( 0,0 );
        Point p2 = new Point( origin.cols() - 1, 0 );
        Point p3 = new Point( 0, origin.rows() - 1 );
        Point p4 = new Point( panX, panY );
        Point p5 = new Point(  origin.cols() - 1 + panX, panY );
        Point p6 = new Point( panX, origin.rows()-1 + panY  );
      MatOfPoint2f ma1 = new MatOfPoint2f(p1,p2,p3);
      MatOfPoint2f ma2 = new MatOfPoint2f(p4,p5,p6);
      Mat tranformMatrix = Imgproc.getAffineTransform(ma1,ma2);   
      int maxsize = origin.rows() > origin.cols() ? origin.rows():origin.cols();
      Imgproc.warpAffine(origin, resultMat, tranformMatrix, new Size(maxsize, maxsize));
        result = bufferize(resultMat);
        
        return result;
    }
     
}
