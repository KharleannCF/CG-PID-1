/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Danielito
 */
public class Obsidian {
    
    Mat complexI[];
    
    public void ObsidianInit(){
        this.complexI = new Mat[3];
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
     
    public Mat unDftize(Mat origin, int i) throws IOException{
        if(origin == null){
            List<Mat> planes = new ArrayList<Mat>();
            Core.idft(this.complexI[i], complexI[i]);
            Mat restoredImage = new Mat();
            Core.split(this.complexI[i], planes);
            Core.normalize(planes.get(0), restoredImage, 0, 255, Core.NORM_MINMAX);
            
            return planes.get(0);
        }else{
            List<Mat> planes = new ArrayList<Mat>();
            Core.idft(origin, complexI[i]);
            Mat restoredImage = new Mat();
            Core.split(origin, planes);
            Core.normalize(planes.get(0), restoredImage, 0, 255, Core.NORM_MINMAX);
            
            return planes.get(0);
        }
    }
    
    public Mat Dftize(Mat origin, int i) throws IOException{
        
        Mat padded = new Mat();                     //expand input image to optimal size
        int m = Core.getOptimalDFTSize( origin.rows() );
        int n = Core.getOptimalDFTSize( origin.cols() ); // on the border add zero values
        Core.copyMakeBorder(origin, padded, 0, m - origin.rows(), 0, n - origin.cols(), Core.BORDER_CONSTANT, Scalar.all(0));
        
        List<Mat> planes = new ArrayList<Mat>();
        padded.convertTo(padded, CvType.CV_32F);
        planes.add(padded);
        planes.add(Mat.zeros(padded.size(), CvType.CV_32F));
        this.complexI[i] = new Mat();
        Core.merge(planes, this.complexI[i]);
        
        Core.dft(this.complexI[i], this.complexI[i]);
        Core.split(this.complexI[i], planes);                               // planes.get(0) = Re(DFT(I)
                                                                    // planes.get(1) = Im(DFT(I))
        Core.magnitude(planes.get(0), planes.get(1), planes.get(0));// planes.get(0) = magnitude
        Mat magI = planes.get(0);
        Mat matOfOnes = Mat.ones(magI.size(), magI.type());
        Core.add(matOfOnes, magI, magI);         // switch to logarithmic scale
        Core.log(magI, magI);
        // crop the spectrum, if it has an odd number of rows or columns
        magI = magI.submat(new Rect(0, 0, magI.cols() & -2, magI.rows() & -2));
        // rearrange the quadrants of Fourier image  so that the origin is at the image center
        int cx = magI.cols()/2;
        int cy = magI.rows()/2;
        Mat q0 = new Mat(magI, new Rect(0, 0, cx, cy));   // Top-Left - Create a ROI per quadrant
        Mat q1 = new Mat(magI, new Rect(cx, 0, cx, cy));  // Top-Right
        Mat q2 = new Mat(magI, new Rect(0, cy, cx, cy));  // Bottom-Left
        Mat q3 = new Mat(magI, new Rect(cx, cy, cx, cy)); // Bottom-Right
        Mat tmp = new Mat();               // swap quadrants (Top-Left with Bottom-Right)
        q0.copyTo(tmp);
        q3.copyTo(q0);
        tmp.copyTo(q3);
        q1.copyTo(tmp);                    // swap quadrant (Top-Right with Bottom-Left)
        q2.copyTo(q1);
        tmp.copyTo(q2);
        magI.convertTo(magI, CvType.CV_8UC1);
        Core.normalize(magI, magI, 0, 1, Core.NORM_MINMAX, CvType.CV_8UC1);
       
        /*
        Core.idft(complexI, complexI);
        Mat restoredImage = new Mat();
        Core.split(complexI, planes);
        Core.normalize(planes.get(0), restoredImage, 0, 255, Core.NORM_MINMAX);
        */

        return planes.get(1);
        
    }
    
    public BufferedImage toSpectrum(BufferedImage original) throws IOException{
        BufferedImage result = null;
        Mat origin = null;
        origin = this.matify(original);
        Mat resultMat = new Mat();
        
        List<Mat> channels = new ArrayList<Mat>();
        Core.split(origin, channels);
        //System.out.println(channels);
        
        ObsidianInit();
        
        Mat g = channels.get(0);
        Mat b = channels.get(1);
        Mat r = channels.get(2);
        
        g = Dftize(g,0);
        b = Dftize(b,1);
        r = Dftize(r,2);
        
        List<Mat> newChannels = new ArrayList<Mat>();
        
        newChannels.add(g);
        newChannels.add(b);
        newChannels.add(r);
        /*List<Mat> channels = new ArrayList<Mat>();
        List<Mat> planes = new ArrayList<Mat>();
        Core.split(origin, channels);
        System.out.println(channels);
        Mat g = channels.get(0);
        Mat b = channels.get(1);
        Mat r = channels.get(2);
        g.convertTo(g,CvType.CV_32F);
        b.convertTo(b,CvType.CV_32F);
        r.convertTo(r,CvType.CV_32F);
        //Core.dft(g, channels.get(0));
        Core.dft(b, b);
        Core.split(b, planes);*/
        /*Core.magnitude(planes.get(0), planes.get(1), planes.get(0));
        Mat magI = planes.get(0);
        Mat matOfOnes = Mat.ones(magI.size(), magI.type());
        Core.add(matOfOnes, magI, magI);         // switch to logarithmic scale
        Core.log(magI, magI);
        magI = magI.submat(new Rect(0, 0, magI.cols() & -2, magI.rows() & -2));
        int cx = magI.cols()/2;
        int cy = magI.rows()/2;
        Mat q0 = new Mat(magI, new Rect(0, 0, cx, cy));   // Top-Left - Create a ROI per quadrant
        Mat q1 = new Mat(magI, new Rect(cx, 0, cx, cy));  // Top-Right
        Mat q2 = new Mat(magI, new Rect(0, cy, cx, cy));  // Bottom-Left
        Mat q3 = new Mat(magI, new Rect(cx, cy, cx, cy)); // Bottom-Right
        Mat tmp = new Mat();               // swap quadrants (Top-Left with Bottom-Right)
        q0.copyTo(tmp);
        q3.copyTo(q0);
        tmp.copyTo(q3);
        q1.copyTo(tmp);                    // swap quadrant (Top-Right with Bottom-Left)
        q2.copyTo(q1);
        tmp.copyTo(q2);
        magI.convertTo(magI, CvType.CV_8UC1);
        Core.normalize(magI, magI, 0, 255, Core.NORM_MINMAX, CvType.CV_8UC1);
        */
        
        //Core.dft(r, channels.get(2));
        //System.out.println(channels);
        // Equalizing the histogra originm of the image
        
        
        System.out.println(g);
        Core.merge(newChannels, origin);
        //Imgproc.equalizeHist( origin, resultMat );
        result = bufferize(origin);
        
        return result;
    }

    public BufferedImage toNormal(BufferedImage original) throws IOException{
        
        BufferedImage result = null;
        Mat origin = null;
        origin = this.matify(original);
        Mat resultMat = new Mat();
        
        List<Mat> channels = new ArrayList<Mat>();
        Core.split(origin, channels);
        System.out.println(channels);
        Mat g = channels.get(0);
        Mat b = channels.get(1);
        Mat r = channels.get(2);
        if(this.complexI != null){
            g = unDftize(g,0);
            b = unDftize(b,1);
            r = unDftize(r,2);
        }else{
            g = unDftize(null,0);
            b = unDftize(null,1);
            r = unDftize(null,2);
        }
        
        
        List<Mat> newChannels = new ArrayList<Mat>();
        
        newChannels.add(g);
        newChannels.add(b);
        newChannels.add(r);
        
        //Core.merge(newChannels, origin);
        //Imgproc.equalizeHist( origin, resultMat );
        result = bufferize(b);
        
        return result;
    }
    
}
