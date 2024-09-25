import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; 
import java.util.ArrayList;
import java.util.Random; // resolves problem with java.awt.List and java.util.List



/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
    
private Pixel[][] pixels;

  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
    pixels = this.getPixels2D();    
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
    pixels = this.getPixels2D();
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
    pixels = this.getPixels2D();
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
    pixels = this.getPixels2D();
  }
  
  
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
    pixels = this.getPixels2D();
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for(int row = 0; row < pixels.length; row ++) 
    {
        for (int col = 0; col < pixels[0].length; col ++)  
        {
            Pixel pixelObj = pixels [row][col];
            
            int red = pixelObj.getRed();
            int green = pixelObj.getGreen();
            int blue = pixelObj.getBlue();
//            pixelObj.setBlue(0);
            pixelObj.setRed(green & blue);
            pixelObj.setGreen(green & blue);
            pixelObj.setBlue(red | green);
        }  
    }
  }
  
   public void zeroRed()
  {
    Pixel[][] pixels = this.getPixels2D();
    for(Pixel[] pix : pixels) 
    {
        for (Pixel p : pix)  
        {
            p.setRed(0);
            p.setBlue(0);
        }  
    }
  }
  
  public void crazyColor()
  {
    Pixel[][] pixels = this.getPixels2D();
    for(Pixel[] pix : pixels) 
    {
        for (Pixel p : pix)  
        {
            int r = p.getRed();
            int g = p.getGreen();
            int b = p.getBlue();
            p.setRed( r | g );
            p.setGreen( g & b );
            p.setBlue( b ^ r );
        }  
    }
  }
  
  public void colorOR()
  {
    Pixel[][] pixels = this.getPixels2D();
    for(Pixel[] pix : pixels) 
    {
        for (Pixel p : pix)  
        {
            int colNum = p.getX();
            int r = p.getRed();
            int g = p.getGreen();
            int b = p.getBlue();
            int avg = (r + b + g) / 3;
            if (colNum % 2 == 0)
            {
              p.setRed( avg );
              p.setGreen( avg );
              p.setBlue( avg );
            }
            else
            {
              p.setRed( r | g );
              p.setGreen( g | b );
              p.setBlue( b | r );
            }
        }  
    }
  }
  
    public int [] tiff(int x, int y)
  {
//      Pixel[][] pixels = this.getPixels2D();
      int [] colors = new int [3];
      
      if (x < pixels.length && y < pixels[0].length)
      {
          colors [0] = pixels[x][y].getRed();
          colors [1] = pixels[x][y].getGreen();
          colors [2] = pixels[x][y].getBlue();
      }
      else
      {
          colors [0] = 0;
          colors [1] = 0;
          colors [2] = 0;
      }
      return colors;
  }

public void seeing4()
  {
//    Pixel[][] pixels = this.getPixels2D();
    int row2 = 0;
    int col2 = 0;
    for (int row = 0; row < pixels.length / 2; row++)
    {
        for (int col = 0; col < pixels[0].length / 2; col++)
        {
           Pixel pixelObj = pixels [row][col];
           Pixel pixelObj2 = pixels [row2][col2];
           
           int red = pixelObj2.getRed();
           int blue = pixelObj2.getBlue();
           int green = pixelObj2.getGreen();
              
           pixelObj.setRed(red);
           pixelObj.setGreen(green);
           pixelObj.setBlue(blue);
           
           col2 += 2;
        }
        row2 += 2;
        col2 = 0;
    }
    
    for (int row3 = 0; row3 < pixels.length / 2; row3++)
    {
        for (int col3 = 0; col3 < pixels[0].length / 2; col3++)
        {
           Pixel pixelObj = pixels [row3][col3];
           Pixel quad1 = pixels [row3 + (pixels.length / 2)][col3];
           Pixel quad3 = pixels [row3][col3 + (pixels[0].length / 2)];
           Pixel quad4 = pixels [row3 + (pixels.length / 2)][col3 + (pixels[0].length / 2)];
           
           int red = pixelObj.getRed();
           int blue = pixelObj.getBlue();
           int green = pixelObj.getGreen();
              
           quad1.setRed(blue ^ green);
           quad1.setGreen(red ^ blue);
           quad1.setBlue(green ^ red);
           
           quad3.setRed(blue & green);
           quad3.setGreen(red & blue);
           quad3.setBlue(green & red);
           
           quad4.setRed(blue | green);
           quad4.setGreen(red | blue);
           quad4.setBlue(green | red);
        }
    }
  }  
  
  public void brighten()
  {
    Pixel[][] pixels = this.getPixels2D();
    for(int row = 0; row < pixels.length; row ++) 
    {
        for (int col = 0; col < pixels[0].length; col ++)  
        {
            Pixel pixelObj = pixels [row][col];
            
            int red = pixelObj.getRed() + 20;
            int blue = pixelObj.getBlue() + 20;
            int green = pixelObj.getGreen() + 20;
            
            pixelObj.setRed(red);
            pixelObj.setBlue(blue);
            pixelObj.setGreen(green);
        }  
    } 
  }
  
  
   /** Method to see how many pixels are mostly red */
  public int mostlyRed()
  {
    Pixel[][] pixels = this.getPixels2D();
    int counter = 0;
    for(int row = 0; row < pixels.length; row ++) 
    {
        for (int col = 0; col < pixels[0].length; col ++)  
        {
            Pixel pixelObj = pixels [row][col];
            
            int red = pixelObj.getRed();
            int blue = pixelObj.getBlue();
            int green = pixelObj.getGreen();
            
            if(red > blue && red > green)
            {
                counter++;
                pixelObj.setRed(0);
                pixelObj.setGreen(0);
                pixelObj.setBlue(0);
            }
        }  
    }
    return counter;
  }
  
  
  public void faceComposite()
  {
    Pixel[][] pixels = this.getPixels2D();
    int counter = 0;
    for(int row = 0; row < pixels.length; row ++) 
    {
        if (counter%2==0)
        { 
            for (int col = 0; col < pixels[0].length; col += 2)  
            {
                Pixel pixelObj = pixels [row][col];
                Pixel pixelObj2 = pixels[row] [pixels[0].length - col - 1];
                int red = pixelObj.getRed();
                int blue = pixelObj.getBlue();
                int green = pixelObj.getGreen();
              
                pixelObj2.setRed(red);
                pixelObj2.setGreen(green);
                pixelObj2.setBlue(blue);
            } 
        }
        else if (counter%2==1)
        { 
            for (int col = 1; col < pixels[0].length; col += 2)  
            {
                Pixel pixelObj = pixels [row][col];
                Pixel pixelObj2 = pixels[row] [pixels[0].length - col - 1];
                int red = pixelObj.getRed();
                int blue = pixelObj.getBlue();
                int green = pixelObj.getGreen();
              
                pixelObj2.setRed(red);
                pixelObj2.setGreen(green);
                pixelObj2.setBlue(blue);
            } 
        }
        counter++;        
    }
  }
  
  public void colorize()
  {
      Pixel[][] pixels = this.getPixels2D();
      for (int col = 0; col < pixels[0].length; col++)  
      {
          for(int row = 0; row < pixels.length; row++)
          {
              Pixel pixelObj = pixels [row][col];
                Pixel pixelObj2 = pixels[row] [pixels[0].length - col - 1];
                int red = pixelObj.getRed();
                int blue = pixelObj.getBlue();
                int green = pixelObj.getGreen();
                if(red >= 200 && blue >= 200 && green >= 200)
                {
                      int START = 0;
                      int END = 255;
                      Random rand = new Random();
                      int random = rand.nextInt((END - START) + 1) + START;
                      int random2 = rand.nextInt((END - START) + 1) + START;
                      int random3 = rand.nextInt((END - START) + 1) + START;
                      
                      pixelObj.setRed(random);
                      pixelObj.setGreen(random2);
                      pixelObj.setBlue(random3);
                }
          }
      }
  } 
  
  public void grayscaleWithLuminance()
  {
        Pixel[] pixelArray = this.getPixels();
        Pixel pixel = null;
        int luminance = 0;
        double redValue = 0;
        double greenValue = 0;
        double blueValue = 0;
    
        // loop through all the pixels
        for (int i = 0; i < pixelArray.length; i++)
        {
          // get the current pixel
          pixel = pixelArray[i];
    
          // get the corrected red, green, and blue values
          redValue = pixel.getRed() * 0.299;
          greenValue = pixel.getGreen() * 0.587;
          blueValue = pixel.getBlue() * 0.114;
    
          // compute the intensity of the pixel (average value)
          luminance = (int) (redValue + greenValue + blueValue);
    
          // set the pixel color to the new color
          pixel.setColor(new Color(luminance,luminance,luminance));
    
        }
  }
  
  public void grayscale()
  {
    Pixel[][] pixels = this.getPixels2D();
    
        for (Pixel[] rowArray : pixels)
        {
          for (Pixel pixelObj : rowArray)
          {
              int grey = (pixelObj.getRed() + pixelObj.getBlue() + pixelObj.getGreen())/3;
              pixelObj.setBlue(grey);
              pixelObj.setRed(grey);
              pixelObj.setGreen(grey);
          }
        }
  }

  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical2()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row += 1)
    {
      for (int col = 0; col < width / 2; col += 1)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture flutter = new Picture("butterfly1.jpg");
//    Picture flutter = new Picture("wall.jpg");
//    flutter.colorOR();
//    flutter.grayscale();
//    flutter.explore();
    flutter.zeroBlue();
//    flutter.seeing4();
    flutter.explore();
  }
  
} // this } is the end of class Picture, put all new methods before this
