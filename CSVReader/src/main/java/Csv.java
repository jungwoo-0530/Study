import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Csv {
    public static void main(String[] args) throws IOException {

        CSVReader csvReader = new CSVReader(new FileReader("/Users/jungwoo/Desktop/git/Web_Study/CSVReader/src/main/resources/User.csv"));


        List<String> name = new ArrayList<>();
        List<String[]> hobbyList = new ArrayList<>();
        List<String> memo = new ArrayList<>();

        csvReader.readNext();

        List<String []> list = csvReader.readAll();

        name = list.stream()
                .map(line -> line[0].replaceAll("\\s", ""))
                .collect(Collectors.toList());
        hobbyList = list.stream()
                .map(line -> line[1].replaceAll("\\s", ""))
                .map(line -> line.split(":"))
                .collect(Collectors.toList());
        memo = list.stream()
                .map(line -> line[2].replaceAll("\\s", ""))
                .collect(Collectors.toList());

        name.forEach(System.out::println);


//        for (String[] hobby: hobbyList) {
//            Arrays.stream(hobby).forEach(h -> System.out.print(h.));
//            System.out.println();
//        }

        memo.forEach(System.out::println);



        Map<String, Integer> result = new HashMap<>();

        String[] nextLine;
        csvReader.readNext();//헤더 스킵
        //readNext()의 반환형은 String []
        //readAll()의 반환형은 List<String[]>
//        while ((nextLine = csvReader.readNext()) != null) {
//            temp.add(nextLine[1]);
//        }
        System.out.println(list.get(1)[0]);

        //stream() stream 생성 : Stream<String[]> -> 스트링배열하나가 하나의 원소라고 생각.
        //map() : 가공 : 인자 line으로 스트링 배열에서 스트링(취미)으로 바꿈(replaceAll이 리턴값이 스트링이므로).
        //["정프로", "축구:야구", "구기종목 좋아요"](인자) -> "축구:야구"(반환)
        //flatMap사용으로 split을 먼저 사용하여 "축구:농구" -> "축구", "농구"로 변경후 2차원 String list에서 1차원 List로 변경.
       list.stream()
                .map(line -> line[1].replaceAll("\\s", ""))
                .flatMap(hobbies -> Arrays.stream(hobbies.split(":")))
                .forEach(hobby -> result.merge(hobby, 1, (oldValue, newValue) -> newValue += oldValue));
        result.keySet().forEach(v -> System.out.println(v + ": " + result.get(v)));


        System.out.println("==================================");
        Map<String, Integer> result1 = new HashMap<>();

        list.stream()
                .filter(line -> line[0].matches("정(.*)"))//line -> line[0].startsWith("정")도 가능. Stream<String[]>
                .map(line -> line[1].replaceAll("\\s", ""))//Stream<String>
                .flatMap(hobbies -> Arrays.stream(hobbies.split(":")))
                .forEach(hobby -> result1.merge(hobby, 1, (oldValue, newValue) -> newValue += oldValue));

        result1.keySet().forEach(v -> System.out.println(v + ": " + result1.get(v)));

        System.out.println("=================================");
//        Map<String, Integer> result2 = new HashMap<>();
//        Long a = list.stream()
//                .map(line -> line[1].replaceAll("\\s", ""))
////                .filter(line -> line.contains("좋아"))
//                .filter()
//                .count();
//        System.out.println(a);

        System.out.println("=====================================");
        List<String> WORDS = Arrays.asList("TONY", "a", "hULK", "B", "america", "X", "nebula", "Korea");

        //접두사가 몇개인지.
        Map<String, Integer> prefixMap = new HashMap<>();

        WORDS.stream()
                .map(line -> line.substring(0,1))
                .forEach(preFix -> prefixMap.merge(preFix, 1, (oldValue, newValue) -> newValue += oldValue));

        prefixMap.keySet().forEach(v -> System.out.println("(" + v + "," + prefixMap.get(v)+")"));


        System.out.println("=====================================");

        String result3;
        result3 = WORDS.stream()
                .filter(w -> w.length() > 2)
                .map(String::toUpperCase)
                .map(w -> w.substring(0,1))
                .collect(Collectors.joining(" "));
        System.out.println(result3);

        System.out.println("==============5번 문제=======================");

        String[] strArr = {"aaa", "bb", "c", "dddd"};

        int result4 = Arrays.stream(strArr)
                .mapToInt(String::length)
                .sum();

        //문자열 길이를 출력.
        Map<String, Integer> result5 = new HashMap<>();
        Arrays.stream(strArr)
                .forEach(w -> result5.put(w, w.length()));


        result5.keySet().forEach(v -> System.out.println( v + ": " + result5.get(v)));

        System.out.println("=====================================");
        //문자열 중 가장 긴 문자열의 길이.
        int result6 = Arrays.stream(strArr)
                .mapToInt(String::length)
                .max()
                .getAsInt();

        //문자열 중 가장 긴 문자열
        String result7 = Arrays.stream(strArr)
                .reduce("",(a,b) -> {
                    if(a.length() >= b.length()){
                        return a;
                    }
                    else{
                        return b;
                    }
                });

        System.out.println(result7);

        //로또1~45번까지 랜덤한 숫자 6개 뽑기.
        List<Integer> list2 = new Random().ints(1, 46)
                .distinct()
                .limit(6)
                .boxed()
                .collect(Collectors.toList());
        list2.forEach(System.out::println);

    }

}
