
/**
 * Write a description of class EnhancedPicture here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnhancedPicture extends Picture
{
    public EnhancedPicture(String fileName)
    {
        super(fileName);
    }
    
    public void zeroBlue() // override zeroBlue
    {
        // super.zeroBlue();
        Pixel[][] pixels = this.getPixels2D();
        for (int row=0; row < pixels.length; row++)
        {
            for (int col = 0; col<pixels[0].length; col++)
            {
                Pixel pixelObj = pixels[row][col];
                pixelObj.setBlue(0);
                int red = pixelObj.getRed();
                int green = pixelObj.getGreen();
                int changeRed = (255-red)/2;
                int changeGreen = (255-green)/2;
                pixelObj.setRed(red+changeRed);
                pixelObj.setGreen(green+changeGreen);
            }
        }
    }
}
