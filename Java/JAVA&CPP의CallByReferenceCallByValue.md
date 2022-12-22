# C++ & Java Call By Value and Call By reference



## 매개변수(Parameter) and 인자(Arguments)

```CPP
int main(){
  int target = 10;
  int result = function(target)//target은 parameter 매개변수
  
}

//a는 arguments, 인자
int function(int a){
  //...
}
```









# CPP



## Call by value(Pass by value)

- 값을 인자로 전달하는 함수의 호출 방식

- 파라미터가 인자로 복사되는 형식

- ```cpp
  int main(){
    int num1 = 1;
    int num2 = 2;
    
    cout << num1 << endl;//1
    cout << num2 << endl;//2
    
    swap(num1, num2);
    
    cout << num1 << endl;//1
    cout << num2 << endl;//2
  }
  
  void swap(int a, int b){
    int temp = a;
    a = b;
    b = temp;
    
  }
  ```

- a에 num1이 복사되고 b에 num2가 복사된다.

- 그러므로 값의 변화는 없다. 왜냐하면 복사한 값이기에 연관이 없다.





## Call by reference

- Call by reference의 두가지

  1. 주소 값을 이용한 Call by reference(call by address)
  2. 참조자를 이용한 Call by reference

- 주소 값을 인자로 전달하는 함수 호출.

- ```cpp
  //주소 값을 이용한 call by reference
  int main(){
    int num1 = 1;
    int num2 = 2;
    
    cout << num1 << endl;//1
    cout << num2 << endl;//2
    
    swap(&num1, &num2);
    
    cout << num1 << endl;//2
    cout << num2 << endl;//1
  }
  
  void swap(int *a, int *b){
    int temp = *a;
    *a = *b;
    *b = temp;
    
  }
  
  //참조자를 이용한 call by reference
  int main(){
    int num1 = 1;
    int num2 = 2;
    
    cout << num1 << endl;//1
    cout << num2 << endl;//2
    
    swap(num1, num2);
    
    cout << num1 << endl;//2
    cout << num2 << endl;//1
  }
  
  void swap(int &a, int &b){
    int temp = a;
    a = b;
    b = temp;
    
  }
  
  ```

- a와 b는 num1, num2의 주소값을 받아와서
- a와 b는 num1, num2의 메모리 주소를 가르키는 포인터다.



### Call by reference Array

- ```cpp
  int main(){
    
    int arr[2] = {1, 2};
  }
  ```

  



### Call by reference STL

- ```cpp
  #include <iostream>
  #include <vector>
  using namespace std;
   
  void modifyVector(vector<int> &vec);
   
   int main(void) {
  	 	
  	vector<int> vec = { 1, 2, 3 };
  	modifyVector(vec);
   
  	for (unsigned int i = 0; i < vec.size(); i++) {
  		cout << vec.at(i) << " ";//1234
  	}
  	cout << endl;
   
   
  	return 0;
  }
   
   void modifyVector(vector<int> &vec) {
  	 vec.push_back(4);
  	 
  	 for (unsigned int i = 0; i < vec.size(); i++) {
  		 cout << vec.at(i) << " ";//1234
  	 }
  	 cout << endl;
   }
  ```

  



----------------------------

## Array 배열을 함수의 매개변수로 사용 시 주의점

- 배열을 함수의 매개변수로 사용시 STL과 원시타입과 다르게 자동으로 포인터로 받는다.

- 즉, *, &를 사용하지 않아도 call by reference가 된다.

- ```cpp
  void function(int score[], int size){
    score[0] = 1;
    for(int i = 0; i<size; i++){
      cout << i ; //11234
    }
  }
  
  int main(){
    int arr[5] = {0,1,2,3,4};
    
    int size = sizeof(arr) / sizeof(int);
    
    function(arr, size);
    
    for(int i = 0; i<5; i++){
      cout << i; // 11234
    }
  }
  ```

- 주의할 것

  - `void function(int score[], int size)`
  - `void function(int score[5], int size)`
  - 위 두개는 같다. 컴파일러가 자동으로 괄호 안에 숫자를 무시한다. 포인터기 떄문에.

- 주의할 것 2

  - 배열의 사이즈를 구하는 방법은 : `int size = sizeof(arr) / sizeof(int);`
  - 그러나 함수에서는 포인터기 때문에 : `int size = sizeof(score) / sizeof(int);`가 안된다. 그러기에 사이즈를 넘겨줘야한다.





# JAVA

- Java는 Primitive Type(원시 타입), reference type(참조 타입)이 존재한다.

- JAVA에서는 변수를 선언하면 Stack 영역 저장된다.

  - 원시 타입 :  변수와 값과 함께 스택영역에 저장된다.
  - 참조 타입  : **변수와 인스턴스의 참조값(주소값)이 스택 영역이 저장되고 인스턴스는 힙영역에 저장된다.**

  - <img src="img/JAVA&CPP의CallByReferenceCallByValue/Screenshot of Typora (2022-11-30 12-35-05 AM).png" alt="Screenshot of Typora (2022-11-30 12-35-05 AM)" style="zoom:50%;" />

- 자바는 기본적으로 포인터라는 개념이 없다.

- 자바는 기본적으로 Call by Value로 스택에 저장되어있는 값들이 복사된다.

  <img src="img/JAVA&CPP의CallByReferenceCallByValue/Screenshot of Safari (2022-11-30 1-38-46 AM).png" alt="Screenshot of Safari (2022-11-30 1-38-46 AM)" style="zoom:50%;" />

## Java는 call by reference가 없다.

