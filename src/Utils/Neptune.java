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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Danielito
 */
public class Neptune {
    private static List<Mat> planes = new ArrayList<Mat>();
    private static List<Mat> allPlanes = new ArrayList<Mat>();
    
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
        public BufferedImage equalization(BufferedImage original) throws IOException{
        BufferedImage result =null;
        Mat origin = null;
        origin = this.matify(original);
        Mat resultMat = new Mat();
        Imgproc.cvtColor(origin, origin, Imgproc.COLOR_BGR2YCrCb);
         List<Mat> channels = new ArrayList<Mat>();
         Core.split(origin, channels);

      // Equalizing the histogram of the image
      Imgproc.equalizeHist(channels.get(0), channels.get(0));
      Core.merge(channels, origin);
      Imgproc.cvtColor(origin, origin, Imgproc.COLOR_YCrCb2BGR);
        //Imgproc.equalizeHist( origin, resultMat );
        result = bufferize(origin);
        
        return result;
    }
         
         public BufferedImage histogram(BufferedImage original) throws IOException{
        BufferedImage result =null;
        Mat origin = null;
        origin = this.matify(original);
        Mat resultMat = new Mat();
        
        List<Mat> bgrPlanes = new ArrayList <>();
        Core.split(origin, bgrPlanes);
        int histSize = 256;
        float[] range = {0, 256}; //the upper boundary is exclusive
        MatOfFloat histRange = new MatOfFloat(range);
        boolean accumulate = false;
        Mat bHist = new Mat(), gHist = new Mat(), rHist = new Mat();
        Imgproc.calcHist(bgrPlanes, new MatOfInt(0), new Mat(), bHist, new MatOfInt(histSize), histRange, accumulate);
        Imgproc.calcHist(bgrPlanes, new MatOfInt(1), new Mat(), gHist, new MatOfInt(histSize), histRange, accumulate);
        Imgproc.calcHist(bgrPlanes, new MatOfInt(2), new Mat(), rHist, new MatOfInt(histSize), histRange, accumulate);
        int histW = 512, histH = 400;
        int binW = (int) Math.round((double) histW / histSize);
        Mat histImage = new Mat( histH, histW, CvType.CV_8UC3, new Scalar( 0,0,0) );
        Core.normalize(bHist, bHist, 0, histImage.rows(), Core.NORM_MINMAX);
        Core.normalize(gHist, gHist, 0, histImage.rows(), Core.NORM_MINMAX);
        Core.normalize(rHist, rHist, 0, histImage.rows(), Core.NORM_MINMAX);
        float[] bHistData = new float[(int) (bHist.total() * bHist.channels())];
        bHist.get(0, 0, bHistData);
        float[] gHistData = new float[(int) (gHist.total() * gHist.channels())];
        gHist.get(0, 0, gHistData);
        float[] rHistData = new float[(int) (rHist.total() * rHist.channels())];
        rHist.get(0, 0, rHistData);
        for( int i = 1; i < histSize; i++ ) {
            Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(bHistData[i - 1])),
                    new Point(binW * (i), histH - Math.round(bHistData[i])), new Scalar(255, 0, 0), 2);
            Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(gHistData[i - 1])),
                    new Point(binW * (i), histH - Math.round(gHistData[i])), new Scalar(0, 255, 0), 2);
            Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(rHistData[i - 1])),
                    new Point(binW * (i), histH - Math.round(rHistData[i])), new Scalar(0, 0, 255), 2);
        }
        result = bufferize(histImage);
        
        return result;
    }
         
        public BufferedImage erosion(BufferedImage original, char type) throws IOException{
        BufferedImage result =null;
        Mat origin = null;
        origin = this.matify(original);
        Mat resultMat = new Mat();
        //Imgproc.cvtColor(origin, origin, Imgproc.COLOR_BGR2YCrCb);
         List<Mat> channels = new ArrayList<Mat>();
         Core.split(origin, channels);
         int typeMorph;
        switch (type) {
            case 'O':
                typeMorph = Imgproc.MORPH_ELLIPSE;
                break;
            case 'B':
                typeMorph = Imgproc.MORPH_RECT;
                break;
            default:
                typeMorph = Imgproc.MORPH_CROSS;
                break;
        }
         
         Mat kernel = Imgproc.getStructuringElement(typeMorph, new Size(3,3));
      // Equalizing the histogram of the image
        //Imgproc.erode(channels.get(0), channels.get(0), kernel);
        //Imgproc.erode(channels.get(1), channels.get(1), kernel);
        //Imgproc.erode(channels.get(2), channels.get(2), kernel);
        //Core.merge(channels, origin);
        Imgproc.erode(origin, origin, kernel);
      //Imgproc.cvtColor(origin, origin, Imgproc.COLOR_YCrCb2BGR);
        //Imgproc.equalizeHist( origin, resultMat );
        result = bufferize(origin);
        
        return result;
    }
        public BufferedImage dilatacion(BufferedImage original, char type) throws IOException{
        BufferedImage result =null;
        Mat origin = null;
        origin = this.matify(original);
        Mat resultMat = new Mat();
        //Imgproc.cvtColor(origin, origin, Imgproc.COLOR_BGR2YCrCb);
         List<Mat> channels = new ArrayList<Mat>();
         Core.split(origin, channels);
         int typeMorph;
        switch (type) {
            case 'O':
                typeMorph = Imgproc.MORPH_ELLIPSE;
                break;
            case 'B':
                typeMorph = Imgproc.MORPH_RECT;
                break;
            default:
                typeMorph = Imgproc.MORPH_CROSS;
                break;
        }
         
         
         Mat kernel = Imgproc.getStructuringElement(typeMorph, new Size(3,3));
      // Equalizing the histogram of the image
        //Imgproc.erode(channels.get(0), channels.get(0), kernel);
        //Imgproc.erode(channels.get(1), channels.get(1), kernel);
        //Imgproc.erode(channels.get(2), channels.get(2), kernel);
        //Core.merge(channels, origin);
        Imgproc.dilate(origin, origin, kernel);
      //Imgproc.cvtColor(origin, origin, Imgproc.COLOR_YCrCb2BGR);
        //Imgproc.equalizeHist( origin, resultMat );
        result = bufferize(origin);
        
        return result;
    }
        public BufferedImage openKernel(BufferedImage original, char type) throws IOException{
        BufferedImage result =null;
        Mat origin = null;
        origin = this.matify(original);
        Mat resultMat = new Mat();
        //Imgproc.cvtColor(origin, origin, Imgproc.COLOR_BGR2YCrCb);
         List<Mat> channels = new ArrayList<Mat>();
         Core.split(origin, channels);
         int typeMorph;
        switch (type) {
            case 'O':
                typeMorph = Imgproc.MORPH_ELLIPSE;
                break;
            case 'B':
                typeMorph = Imgproc.MORPH_RECT;
                break;
            default:
                typeMorph = Imgproc.MORPH_CROSS;
                break;
        }
         
         Mat kernel = Imgproc.getStructuringElement(typeMorph, new Size(3,3));
      // Equalizing the histogram of the image
        //Imgproc.erode(channels.get(0), channels.get(0), kernel);
        //Imgproc.erode(channels.get(1), channels.get(1), kernel);
        //Imgproc.erode(channels.get(2), channels.get(2), kernel);
        //Core.merge(channels, origin);
        Imgproc.morphologyEx(origin, origin, Imgproc.MORPH_OPEN, kernel);
      //Imgproc.cvtColor(origin, origin, Imgproc.COLOR_YCrCb2BGR);
        //Imgproc.equalizeHist( origin, resultMat );
        result = bufferize(origin);
        
        return result;
    }
        public BufferedImage closeKernel(BufferedImage original, char type) throws IOException{
        BufferedImage result =null;
        Mat origin = null;
        origin = this.matify(original);
        Mat resultMat = new Mat();
        //Imgproc.cvtColor(origin, origin, Imgproc.COLOR_BGR2YCrCb);
         List<Mat> channels = new ArrayList<Mat>();
         Core.split(origin, channels);
         int typeMorph;
        switch (type) {
            case 'O':
                typeMorph = Imgproc.MORPH_ELLIPSE;
                break;
            case 'B':
                typeMorph = Imgproc.MORPH_RECT;
                break;
            default:
                typeMorph = Imgproc.MORPH_CROSS;
                break;
        }
         
         Mat kernel = Imgproc.getStructuringElement(typeMorph, new Size(3,3));
      // Equalizing the histogram of the image
        //Imgproc.erode(channels.get(0), channels.get(0), kernel);
        //Imgproc.erode(channels.get(1), channels.get(1), kernel);
        //Imgproc.erode(channels.get(2), channels.get(2), kernel);
        //Core.merge(channels, origin);
        Imgproc.morphologyEx(origin, origin, Imgproc.MORPH_CLOSE, kernel);
      //Imgproc.cvtColor(origin, origin, Imgproc.COLOR_YCrCb2BGR);
        //Imgproc.equalizeHist( origin, resultMat );
        result = bufferize(origin);
        
        return result;
    }

        public BufferedImage floodFill(BufferedImage original, int vecindad) throws IOException{
        BufferedImage result =null;
        Mat origin = null;
        origin = this.matify(original);
        Mat resultMat = new Mat();
     
         //List<Mat> channels = new ArrayList<Mat>();
        Mat flooded=new Mat();
        Point flood=new Point(original.getWidth()-1,original.getHeight()-1);
        Scalar lowerDiff = new Scalar(10, 10, 10);
        Scalar color = new Scalar(255,0,0);
        Scalar upperDiff = new Scalar(100, 100, 100);
        //int flags = vecindad + (ffillMode == 1 ? Imgproc.FLOODFILL_FIXED_RANGE : 0);
        int flags = vecindad ;
        
        Imgproc.floodFill(origin, flooded , flood, color, null, lowerDiff, upperDiff, 8);
      
         
         
     
        result = bufferize(origin);
        
        return result;
    }
         
        
        
         
         private static Mat optimizeImageDim(Mat image) {
        Mat padded = new Mat();
        int addPixelRows = Core.getOptimalDFTSize(image.rows());
        int addPixelCols = Core.getOptimalDFTSize(image.cols());
        Core.copyMakeBorder(image, padded, 0, addPixelRows - image.rows(), 0, addPixelCols - image.cols(), 
        Core.BORDER_CONSTANT, Scalar.all(0));

        return padded;
         }
         
         private static Mat splitSrc(Mat mat, ImageCustom ourImage) {
        mat = optimizeImageDim(mat);
        Core.split(mat, allPlanes);
        ourImage.addPlanes(allPlanes);
        Mat padded = new Mat();
        if (allPlanes.size() > 1) {
            for (int i = 0; i < allPlanes.size(); i++) {
                if (i == 0) {
                    padded = allPlanes.get(i);
                    break;
                }
            }
        } else {
            padded = mat;
        }
        return padded;

         }
         
         private static Mat splitSrc2(Mat mat) {
        mat = optimizeImageDim(mat);
        Core.split(mat, allPlanes);
        Mat padded = new Mat();
        if (allPlanes.size() > 1) {
            for (int i = 0; i < allPlanes.size(); i++) {
                if (i == 0) {
                    padded = allPlanes.get(i);
                    break;
                }
            }
        } else {
            padded = mat;
        }
        return padded;

         }
         
         private static Mat createOptimizedMagnitude(Mat complexImage) {
        List<Mat> newPlanes = new ArrayList<Mat>();
        Mat mag = new Mat();
        Core.split(complexImage, newPlanes);
        Core.magnitude(newPlanes.get(0), newPlanes.get(1), mag);
        Core.add(Mat.ones(mag.size(), CvType.CV_32F), mag, mag);
        Core.log(mag, mag);
        shiftDFT(mag);
        mag.convertTo(mag, CvType.CV_8UC1);
        Core.normalize(mag, mag, 0, 255, Core.NORM_MINMAX, CvType.CV_8UC1);
        return mag;
    }
         private static void shiftDFT(Mat image) {
        image = image.submat(new Rect(0, 0, image.cols() & -2, image.rows() & -2));
        int cx = image.cols() / 2;
        int cy = image.rows() / 2;
 
        Mat q0 = new Mat(image, new Rect(0, 0, cx, cy));
        Mat q1 = new Mat(image, new Rect(cx, 0, cx, cy));
        Mat q2 = new Mat(image, new Rect(0, cy, cx, cy));
        Mat q3 = new Mat(image, new Rect(cx, cy, cx, cy));
        Mat tmp = new Mat();
        q0.copyTo(tmp);
        q3.copyTo(q0);
        tmp.copyTo(q3);
        q1.copyTo(tmp);
        q2.copyTo(q1);
        tmp.copyTo(q2);
    }
         
         public static Mat getImageWatermarkWithText(Mat image, ImageCustom ourImage){
        List<Mat> planes = new ArrayList<Mat>();
        Mat complexImage = new Mat();
        Mat padded = splitSrc(image, ourImage);
        padded.convertTo(padded, CvType.CV_32F);
        planes.add(padded);
        planes.add(Mat.zeros(padded.size(), CvType.CV_32F));
        Core.merge(planes, complexImage);
        // dft
        Core.dft(complexImage, complexImage);
        ourImage.addImage(complexImage);
        Mat magnitude = createOptimizedMagnitude(complexImage);
        planes.clear();
        return magnitude;
    }
         
       public BufferedImage fourier(BufferedImage original, ImageCustom ourImage) throws IOException{
        BufferedImage result =null;
        Mat origin = null;
        origin = this.matify(original);
        Mat resultMat = new Mat();
        resultMat = getImageWatermarkWithText(origin, ourImage);
       
        result = bufferize(resultMat);
        
        return result;
    }
       public BufferedImage invFourier(BufferedImage original, ImageCustom ourImage) throws IOException{
        BufferedImage result =null;
        Mat origin = null;
        origin = this.matify(original);
        Mat resultMat = new Mat();
        resultMat = inverseDFT(origin, ourImage);
           
       
        result = bufferize(resultMat);
        
        return result;
    }
       
        public static Mat inverseDFT(Mat image, ImageCustom ourImage){
        List<Mat> planes = new ArrayList<Mat>();
        //Mat complexImage = ourImage.complexImage;
        //Mat padded = splitSrc2(image);
        //padded.convertTo(padded, CvType.CV_32F);
        //planes.add(padded);
        //planes.add(Mat.zeros(padded.size(), CvType.CV_32F));
        //Core.merge(planes, complexImage);
        // dft
            System.out.println(ourImage.getImage());
        return antitransformImage(ourImage.getImage(), ourImage.getPlanes());
    }
           
           private static Mat antitransformImage(Mat complexImage, List<Mat> allPlanes) {
        Mat invDFT = new Mat();
        Core.idft(complexImage, invDFT, Core.DFT_SCALE | Core.DFT_REAL_OUTPUT, 0);
        Mat restoredImage = new Mat();
        invDFT.convertTo(restoredImage, CvType.CV_8U);
        if (allPlanes.size() == 0) {
            allPlanes.add(restoredImage);
        } else {
            allPlanes.set(0, restoredImage);
        }
        Mat lastImage = new Mat();
        Core.merge(allPlanes, lastImage);
        return lastImage;
    }
           
}
