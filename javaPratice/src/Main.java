import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;


public class Main {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("hi", "hello", "hithere" );
        long a = list.stream()
                .filter(s -> s.startsWith("h"))
                .map(String::toUpperCase)
                .sorted()
                .count();

        System.out.println(a);


        Supplier<String> supplier = () -> "Hello";
        System.out.println(supplier.get());


        System.out.println("main");
    }
}
