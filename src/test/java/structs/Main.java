package structs;

/**
 * @author magicnana
 * @date 2021/6/18 11:38
 */
public class Main {

  public static void main(String[] args){

    //                            if ((e.hash & oldCap) == 0) {

    System.out.println(tableSizeFor(16));

  }

  public static final int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= (1<<30)) ? (1<<30) : n + 1;
  }

}
