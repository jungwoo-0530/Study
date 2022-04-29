import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * fileName     : InputStreamTest
 * author       : jungwoo
 * description  :
 */
public class InputStreamTest {

  public static void main(String[] args) throws IOException {


//    int a = inputStream.read();
//
//    System.out.println((char)a);
//
//    System.out.println(a);

    InputStream inputStream = System.in;
    InputStreamReader sr = new InputStreamReader(inputStream);

    char[] c = new char[10];
    sr.read(c);

    for (char val : c) {
      System.out.println(val + " : " + (int) val);
    }




  }
}
