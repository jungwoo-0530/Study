import java.util.*;
import java.util.function.Supplier;
abstract class Base{
    int x;
    int y;

    public abstract void talk();
}

abstract class Human extends Base{

    int age;
    String name;

    public Human(int age, String name){
        this.age = age;
        this.name = name;
    }

    public Human(int age, String name, int x, int y){
        this.age = age;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public void talk(){
        System.out.println("왈왈");
    }

}



public class Main {



    public static class Post{
        String a;
        String b;

        public Post(String a, String b){
            this.a = a;
            this.b = b;
        }
    }

    public static Post func()
    {
        return null;
    }


    public static void main(String[] args) {

        System.out.println(func());
        System.out.println(1L);
        Date now = new Date();
        System.out.println(now.getTime());
        System.out.println(now);
        Long id = 1644520514000L;
        System.out.println(id);
        Date change = new Date(now.getTime() + 2000);
        System.out.println(change);



    }

}
