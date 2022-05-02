java



모의고사

```java
import java.util.ArrayList;
import java.util.List;
//모의고사
public class Solution {


    public int[] solution(int[] answers) {
        int[] answer = {};

       int[] a = {1, 2, 3, 4, 5};
       int[] b = {2, 1, 2, 3, 2, 4, 2, 5};
       int[] c = {3, 3, 1, 1, 2, 2, 4, 4, 5, 5};

       int[] score = new int[3];

        for(int i = 0; i<answers.length; i++)
        {
            if(a[i%(a.length)] == answers[i])
                score[0]++;
            if(b[i%(b.length)] == answers[i])
                score[1]++;
            if(c[i%(c.length)] == answers[i])
                score[2]++;
        }

        int max = Math.max(Math.max(score[0], score[1]), score[2]);
        List<Integer> list = new ArrayList<>();



        for(int i = 0; i<3; i++)
        {
            if(score[i] == max){
                list.add(i+1);
            }
        }

        answer = new int[list.size()];
        for(int i = 0; i<list.size(); i++)
        {
            answer[i] = list.get(i);
        }


        return answer;
    }
}

```



타겟 넘버

```java
class Solution {

    static int answer;


    public void dfs(int[] numbers, int target, int sum, int idx)
    {
        if(idx == numbers.length)
        {
            if(sum == target)
                answer++;
            return;
        }

        sum += numbers[idx];
        dfs(numbers,target,sum,idx+1);

        sum -= numbers[idx];
        sum -= numbers[idx];
        dfs(numbers,target,sum,idx+1);
    }

    public int solution(int[] numbers, int target) {
        answer = 0;


        dfs(numbers, target, 0, 0);

        return answer;
    }
}
```





네트워크

```java
class Solution {

    static int answer;
    static boolean [] visit;
    public void dfs(int startNode, int n, int[][] computers){

        visit[startNode] = true;

        for(int i = 0; i < computers[startNode].length; i++)
        {
            if(visit[i] == false && computers[startNode][i] == 1)
                dfs(i,n,computers);
        }


    }



    public int solution(int n, int[][] computers) {
        answer = 0;
        visit = new boolean[n];
        System.out.println(visit[1]);
        for(int i = 0; i< n; i++)
        {
            if(visit[i] == false)
            {
                dfs(i, n, computers);
                answer++;
            }


        }

        return answer;
    }
}
```

기능개발

- 효율성이 매우 떨어짐.

```java
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        int[] answer = {};

        Queue<Integer> q = new LinkedList<>();
        List<Integer> list = new ArrayList<>();


        for(int i = 0; i<progresses.length; i++)
        {
            q.offer(progresses[i]);
        }
        int day = 0;
        int cnt = 0;
        int idx = 0;
        while(idx < speeds.length)
        {
            day++;

            while(!q.isEmpty()) {
                int temp = q.peek();

                int a = temp + (day * speeds[idx]);
                System.out.println(a);
                if (a >= 100) {
                    cnt++;
                    idx++;
                    q.poll();
                    if(q.isEmpty())
                        list.add(cnt);

                }
                else if(cnt == 0)
                    break;
                else
                {
                    list.add(cnt);
                    break;
                }

            }

            cnt = 0;


        }

        answer = new int[list.size()];

        for(int i = 0; i < list.size(); i++)
        {
            answer[i] = list.get(i);
        }

        return answer;
    }
}
```



신고 결과 받기

```java
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Solution {

    public int[] solution(String[] id_list, String[] report, int k) {
        int[] answer = {};

        //key = 이름 value = 신고당한 수.
        HashMap<String, Integer> reportNum = new HashMap<>();
        //key = 이름, value = 신고한 사람.
        HashMap<String, Set<String>> map = new HashMap<>();

        for (String a : id_list) {
            Set<String> s = new HashSet<>();
            map.put(a, s);
        }

        for(int i = 0; i<report.length; i++) {
            String[] temp = report[i].split(" ");

            //동일한 사람이 신고한 것이면.
            if(map.get(temp[0]).contains(temp[1]))
                continue;
            reportNum.put(temp[1], reportNum.getOrDefault(temp[1], 0) + 1);

            Set<String> s = map.get(temp[0]);
            s.add(temp[1]);

        }


        answer = new int[id_list.length];

        for(int i = 0; i<id_list.length; i++)
        {
            for(String a : map.get(id_list[i]))
            {
                if(reportNum.get(a) >= k)
                {
                    answer[i]++;
                }
            }
        }

        return answer;
    }

}
///////////////////////////////////////////////////////////////////////
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

    public int[] solution(String[] id_list, String[] report, int k) {
        
        List<String> list = Arrays.stream(report).distinct().collect(Collectors.toList());
        HashMap<String, Integer> count = new HashMap<>();
        for (String s : list) {
            String target = s.split(" ")[1];
            count.put(target, count.getOrDefault(target, 0) + 1);
        }
        

        return Arrays.stream(id_list).map(member -> {
            List<String> reportList = list.stream().filter(s -> s.startsWith(member + " ")).collect(Collectors.toList());
            return reportList.stream().filter(s -> count.getOrDefault(s.split(" ")[1], 0) >= k).count();
        }).mapToInt(Long::intValue).toArray();
    }

}

```



----------------------------------

Queue

- Offer(), poll(), peek() 사용 할것.

```java
Queue<Integer>q = new LinkedList<>();

//삽입 , add(value), offer(value)
//add는 삽입 할 수 없을 경우 예외(illegalStateException) 발생
//offer는 삽입할 수 없을 시 false발생.
q.offer(1);
q.offer(2);
q.offer(3);
q.offer(4);

//empty()는 비어있을 시 true.
while(!q.isEmpty())
{
  
}

//삭제, poll(), remove()
//remove()은 삭제하고 반환. 꺼낼게 없다면 예외발생.
//poll()은 반환. 꺼낼게 없다면 null반환.

//헤드 조회, element(), peek()
//element()는 조회및 반환, 비어있다면 예외.
//peek()는 조회 및 반환, 비어있다면 null.

int temp = q.peek();
q.

public void offer(Element data);//순차보관
public Element poll();//가장 먼저 보관한 값 꺼내고 반환
public Element peek();//가장 먼저 보관한 값 단순 참조, 꺼내지 않음
public boolean empty(); //비어있는지 판별



```



주차요금 계산

```java
import java.util.*;


class Solution {
    public int[] solution(int[] fees, String[] records) {
        int[] answer = {};

        //key - 차량 번호, value - 입장시간.
        Map<Integer, Integer> carTime = new HashMap<>();
        //key - 차량 번호, vlaue - 사용요금
        Map<Integer, Integer> cost = new TreeMap<>();

        for(int i = 0; i < records.length; i++) {

            String[] temp = records[i].split(" ");

            int time = cal_time(temp[0]);
            int carNum = Integer.parseInt(temp[1]);
            String state = temp[2];

            if(state.equals("OUT"))
            {
                int inTime = carTime.get(carNum);
                int totalTime = time - inTime;

                if(cost.containsKey(carNum))
                {
                    int a = cost.get(carNum);
                    totalTime += a;
                }
                cost.put(carNum, totalTime);
                carTime.remove(carNum);
                continue;
            }
            carTime.put(carNum, time);
        }
        //여기까지 진행헀다면 아직 안나간 차량들이 carTime에 남아있음.

        //아직 안나간 차량.
        int outTime = cal_time("23:59");
        for(int num : carTime.keySet())
        {
            int in = carTime.get(num);
            int totalTime = outTime - in;
            if(cost.containsKey(num))
            {
                int a = cost.get(num);
                totalTime += a;
            }
            cost.put(num, totalTime);
        }

        //요금 계산.
        for(int num : cost.keySet())
        {
            int totalCost = 0;
            totalCost += fees[1];
            int totalTime = cost.get(num);
            if(totalTime > fees[0])
            {
                totalTime -= fees[0];
                int b = totalTime/fees[2];
                if(totalTime%fees[2] == 0)
                    totalCost += b*fees[3];
                else
                    totalCost += (b+1)*fees[3];
                cost.put(num, totalCost);
            }

            else
                cost.put(num, totalCost);

        }

        int i = 0;
        answer = new int[cost.size()];
        for(int num : cost.keySet())
        {
            answer[i++] = cost.get(num);
        }

        return answer;
    }

    //시간: 파라미터, 리턴(분)
    public int cal_time(String a)
    {
        String[] temp = a.split(":");
        return Integer.parseInt(temp[0])*60 + Integer.parseInt(temp[1]);
    }

}
```

다른 사람 풀이

```java

```



-------------------------------------------

Map

- HashMap은 순서를 보장하지 않는다
- TreeMap은 Key 값으로 사용된 클래스의 비교 연산을 활용하여 순서를 보장한다. ( 이 때, Key 값으로 사용한 클래스가 Comparator 인터페이스를 구현하게 한다면 원하는대로 순서를 조정할 수 있다)
- LinkedHashMap은 입력된 순서를 보장한다
- 시간 복잡도 Hash = LinkedHashmap(O(1)) < TreeMap(O(logn))l



```java

String[] a = {"05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT", "07:59 5961 OUT", "07:59 0148 IN", "18:59 0000 IN", "19:09 0148 OUT", "22:59 5961 IN", "23:00 5961 OUT"};
Map<Integer, String[]>map = new HashMap<Integer, String[]>();

        for(int i = 0; i<a.length; i++)
        {
            String[]temp = a[i].split(" ");
            map.put(i, temp);
        }


//출력 ; 05:34
        System.out.println(map.get(0)[0]);

    }
```











-----------------------------------------

### 문자열



1. String to int

   - 숫자가 아닌 값이 들어가면 에러.

   ```java
   String str = "1234";
   int a = Integer.parseInt(str);
   ```

2. 문자열 분할.

   - split, subString

   ```java
   String str = "hi jungwoo";
   //공백기준으로 분할.
   String []temp = str.split(" ");
   //j기준으로 자름. j는 없어짐.
   String []temp1 = str.split("j");
   
   //인덱스 기준으로 자르기
   //public String substring(int beginIndex)
   //public String substring(int beginIndex, int endIndex)
   //begIndex만 인자로 전달할 경우, 인덱스가 포함된 문자부터 마지막.
   //endIndex까지 넣을 경우 begin이 포함된 문자부터 endIndex이전까지.
   //! 인덱스는 0부터시작.
   //jungwoo 출력
   String str1 = str.substring(3, 10);
   System.out.println(str1);
   
   ```



3. 문자열 배열 전체 출력

   ```java
   String [] strings = {"hi", "jungwoo"};
   System.out.println(Array.toString(strings));
   ```

   







```java

        //key = 이름, value = 신고한 사람.
        HashMap<String, Set<String>> map = new HashMap<>();
//            Set<String> s = map.get(temp[0]);
//            if(s == null)
//            {
//                s = new HashSet<String>();
//                map.put(temp[0], s);
//            }
//            s.add(temp[1]);
            map.computeIfAbsent(temp[0],v -> new HashSet<String>()).add(temp[1]);

```



