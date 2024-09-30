
/**
 * This class contains class (static) methods
 * that will help you test the Picture class
 * methods. Uncomment the methods and the code
 * in the main to test.
 * 
 * @author Barbara Ericson
 */
import java.awt.*;
import javax.swing.*;

public class PictureTester {
  /** Method to test zeroBlue */
  public static void testZeroBlue() {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }

  /** Method to test mirrorVertical */
  public static void testMirrorVertical() {
    Picture caterpillar = new Picture("caterpillar.jpg");
    caterpillar.explore();
    caterpillar.mirrorVertical();
    caterpillar.explore();
  }

  /** Method to test mirrorTemple */
  public static void testMirrorTemple() {
    Picture temple = new Picture("temple.jpg");
    temple.explore();
    temple.mirrorTemple();
    temple.explore();
  }

  /** Method to test the collage method */
  public static void testCollage() {
    Picture canvas = new Picture("640x480.jpg");
    canvas.createCollage();
    canvas.explore();
  }

  /** Method to test edgeDetection */
  public static void testEdgeDetection() {
    Picture swan = new Picture("swan.jpg");
    swan.edgeDetection(10);
    swan.explore();
  }

  public static void testrealEdgeDetection() {
    Picture flutter = new Picture("IMG_8895.jpeg");
    // Picture flutter = new Picture("wall.jpg");
    // flutter.colorOR();
    // flutter.grayscale();
    flutter.explore();
    int[][] greys = flutter.greyArray();
    int[][] converted = flutter.sobelGreyscale(greys);
    Picture convertedPicture = new Picture(flutter.convertGreyscaleRGB(converted));
    convertedPicture.explore();
    // flutter.seeing4();
    flutter.explore();
  }

  public static void testrectangle() {
    Picture picture = new Picture("swan.jpg");

    JFrame frame = new JFrame();

    frame.setSize(picture.getWidth(), picture.getHeight());

    // Create a PicturePanel that can display the picture and allow drawing
    DrawRectangle picturePanel = new DrawRectangle(picture);

    // // Create a JFrame to hold the PicturePanel
    frame.getContentPane().add(picturePanel); // Add the PicturePanel to the
    // frame

    // Set the frame size and make it visible

    frame.setVisible(true);
  }

  /**
   * Main method for testing. Every class can have a main
   * method in Java
   */
  public static void main(String[] args) {
    // uncomment a call here to run a test
    // and comment out the ones you don't want
    // to run
    // testZeroBlue();
    testrectangle();
    testrealEdgeDetection();
    // testKeepOnlyBlue();
    // testKeepOnlyRed();
    // testKeepOnlyGreen();
    // testNegate();
    // testGrayscale();
    // testFixUnderwater();
    // testMirrorVertical();
    // testMirrorTemple();
    // testMirrorArms();
    // testMirrorGull();
    // testMirrorDiagonal();
    // testCollage();
    // testCopy();
    // testEdgeDetection();
    // testEdgeDetection2();
    // testChromakey();
    // testEncodeAndDecode();
    // testGetCountRedOverValue(250);
    // testSetRedToHalfValueInTopHalf();
    // testClearBlueOverValue(200);
    // testGetAverageForColumn(0);
  }
}