package com.fmt.image;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.GradientPaint;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

/**
 * class to transpose an image
 * @author  Elena Loya
 */
public class ImageProc
{
    /**
     * command line access to class.
     * @param <filePath to an image file to take an image from> <filePath for file to write transposed image to>
     ***/
	public static void main( String[] args ) {
		//transform(args[0], args[1]);
		transform(new File("/home/ftaylor92/Desktop/a.jpg"), new File("/home/ftaylor92/Desktop/b.jpg"), 8);
	}
	
    public static void transform(File inFile, File outFile, double amount )
    {
	try {
	    BufferedImage orig= ImageIO.read(inFile);
	    //BufferedImage orig2= ImageIO.read(new File(inFile));
	    
	    //BufferedImage iNew= (BufferedImage)ImageProc.transpose(orig);
            for( int a= 0; a < 2; a++)
            {
                BufferedImage iNew= new BufferedImage(orig.getHeight(), orig.getWidth(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g= iNew.createGraphics();
                //Color cl= new Color(.5f, .5f, .6f, 0.5f);
                //g.setPaint(new GradientPaint(1f, 1f, cl, 1f, 1f, cl));
                //g.setPaint(new GradientPaint(0f, 1f, , 0f, 0f, new Color(.5f, .5f, .6f, 0.9f)));
                
                //at= AffineTransform.getRotateInstance(Math.PI/ 2);
                AffineTransform at;
                at= AffineTransform.getRotateInstance(-Math.PI/2, orig.getWidth()/2, iNew.getHeight()/ 2);
                AffineTransformOp rotateOp= new AffineTransformOp(at, null);

                //g.fillRect(10,10,10,10);
                g.rotate(Math.PI/amount);
                g.drawRenderedImage(orig, at);
                
                //g.rotate(Math.PI/2);
                //g.drawRenderedImage(orig, AffineTransform.getScaleInstance(orig.getWidth(), orig.getHeight()));

                ImageIO.write(iNew, "JPG", outFile);
                System.out.println("H:"+ iNew.getHeight()+ " W:"+ iNew.getWidth());
                System.out.println("H:"+ orig.getHeight()+ " W:"+ orig.getWidth());
            }
	    
    	    //ImageIO.write(BufferedImage, "TIFF", File);
	} catch(IOException ex){
	    System.err.println(ex);
	} catch(Throwable ex){
	    System.err.println(ex);
	}
    }
    
    /**
     * rotates an image 90 degrees.
     * @param orig original image.
     * @return image rotated 90 degrees.
     **/
    public static Image transpose( Image orig )
    {
	AffineTransform at;
	at= AffineTransform.getRotateInstance(Math.PI/ 6, 0, 285);
	AffineTransformOp rotateOp= new AffineTransformOp(at, null);
	/*Graphics2D g2= orig.getGraphics2D();
	g2.setTransfrom(at);
	g2.rotate(0.5);*/
	
	//BufferedImage iNew= orig.getScaledInstance(100,50,0);
	//iNew= orig;
	return orig;
    }
}
