# Map

| 멤버     | 리턴                                                         |
| -------- | ------------------------------------------------------------ |
| begin()  | 첫번째 원소의 iterator                                       |
| insert() | void<br />pair<iterator, bool> - 중복시 second 값 false, 중복아닐시 true |
|          |                                                              |
|          |                                                              |
|          |                                                              |
|          |                                                              |
|          |                                                              |
|          |                                                              |
|          |                                                              |
|          |                                                              |
|          |                                                              |
|          |                                                              |
|          |                                                              |



# 1. 원소 추가

1. operator([])사용

   - ```c++
     map<string, string> m;
     
     m["kim"] = "jungwoo";
     ```

     

2. `insert`

   - 

   ```c++
   map<string, string> m;
   
   m.insert(make_pair("kim", "jungwoo"));
   
   m.insert(kim)
   ```

   




### 2. key값으로 value찾기

1. `find()`

   - ```c++
     map<string, string> m;
     
     m["kim"] = "jungwoo";
     
     cout << m.find("kim")->second;
     ```

     


# 



