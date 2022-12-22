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

   




## 2. key값으로 value찾기

1. `find()`

   - ```c++
     map<string, string> m;
     
     m["kim"] = "jungwoo";
     
     cout << m.find("kim")->second;
     ```

     

# 





# unordered_map(hash_map)



## 1. Insert

<img src="img/STL/Screenshot of Safari (2022-10-31 11-44-40 AM).png" alt="Screenshot of Safari (2022-10-31 11-44-40 AM)" style="zoom:50%;" />

<img src="img/STL/Screenshot of Safari (2022-10-31 11-45-20 AM).png" alt="Screenshot of Safari (2022-10-31 11-45-20 AM)" style="zoom:50%;" />



## 2. Find, Count로 exist 체크

<img src="img/STL/Screenshot of Safari (2022-10-31 11-45-25 AM).png" alt="Screenshot of Safari (2022-10-31 11-45-25 AM)" style="zoom:50%;" />





- 반환값
  - `count(key)` : key에 해당하는 갯수 리턴(즉, 맵은 0, 1만 나온다.)
  - `find(key)` 
    - 해당 key의 값이 있다면 key에 해당하는 iterator 반환 
    - 해당 key의 값이 없다면 **end() 반환**

## 3. Map과 Unordered_map 차이

<img src="img/STL/Screenshot of Safari (2022-10-31 11-47-44 AM).png" alt="Screenshot of Safari (2022-10-31 11-47-44 AM)" style="zoom:50%;" />





## 4. 출력



```c++
unordered_map<int,char>m = {{1, 'a'}, {2, 'b'}, {3, 'c'}};

for(auto it = m.begin(); it != end(); it++){
  cout << it->first << it->second << endl;
  cout << (*it).first << (*it).second << endl;
}

for(auto i : m){
  cout << i.first << i.second << endl;
}

for(auto &c : m){
  cout << c.first << c.second << endl;
}

for(auto const &c : m){
  cout << c.first << c.second << endl;
}
```



## 5. begin(), end()

- `end()` : end iterator를 반환.(타입의 자료의 마지막값 **다음 메모리 주소**, 이는 곧 **비어있음**을 의미)
- `begin()` : 시작 iterator를 반환 (타입은 자료의 시작값의 메모리 주소이다.)



# Set

- key값으로 이루어진 이진균형트리

- key값은 중복될 수 없다.

- key값 정렬되어진다.(완전정렬)

- Map과 다른 점은 operator[]를 사용할 수 없습니다.

  





# Vector



## 1.초기화

```c++
//4사이즈 1로 초기화
vector<int> v(4, 1);

//빈 값 넣기
vector<int> v;
vector<int> v1 = {};

//array처럼
vector<int> v{10,20,30};

//array로부터의 초기화
int arr[] = {10,20,30};
int n = sizeof(arr)/sizeof(arr[0]);
vector<int> v(arr, arr+n);

//다른 벡터로부터의 초기화
vector<int> v1(v.begin(), v.end());

//2차원

```





## 2. 2차원 벡터에 빈 벡터 넣기

```c++
void dfs(int deep, TreeNode* cur){

    if(cur == nullptr){
        return;
    }
    // result.resize(deep+1);
    if(result.size() == deep)result.push_back(vector<int>());//빈 벡터 넣기.
    result[deep].push_back(cur->val);
    dfs(deep+1, cur->left);
    dfs(deep+1, cur->right);

}
```



# fill() & memset()

- bool 형을 채울떄는 memset() 사용
- 그 외는 fill() 사용



```c++
//배열
int arr[5];
fill(&arr, &arr+5, 5);

int arr[5][5];
fill(&arr[0][0], &arr[4][5], 5);

bool visit[5][5];
memset(visit, true, sizeof(visit));

//vector
vector<int>v(5);//사이즈 5로 초기화.
fill(v.begin(), v.end(), 10);

vector<vector<int>> v(5, vector<int>(5));
fill(v.begin(), v.end(), vector<int>(5, 5));



```

