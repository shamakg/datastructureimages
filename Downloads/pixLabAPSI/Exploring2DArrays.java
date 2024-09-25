
/**
 * Write a description of class Exploring2DArrays here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Exploring2DArrays
{
  public static int[][] initialize(int rows, int cols)    
  {
    int[][] m = new int[rows][cols];
    for (int r = 0; r < m.length; r++)
    {
      for (int c = 0; c < m[0].length; c++)
      {
        m[r][c] = r*3 + c + 1;
      }
    }
    return m;
  }
  
  public static void main(String[] args) 
  {
//    int[][] mat = {{1,2,3},{4,5,6},{7,8,9},{10,11,12}};
    int[][] mat = initialize(4,3);
    
    for (int[] r: mat)
    {
      for (int num: r)
      {
        System.out.print(num + " ");
      }
      System.out.println();
    }
  }
}
