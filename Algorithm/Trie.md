

### Trie 노드의 기본 모습

```java
class Trie{
  Trie[] child = new Trie[26];
  
  //현재 노드에서 자식 노드가 몇 개 있는지 count
  int count;

  String data;
  
  ...
    
    
}

Trie rootNode = new Trie();
```

Trie의 기본 형태는 하나의 루트 노드를 가지고 해당 노드마다 알파벳을 인덱스로하는 Trie 노드를 가지고 있다.

즉, 알파벳만 할 것이면 알파벳은 26개 이므로 `Trie[] child = new Trie[26];`로 필드를 정의하면 되고

그 이상의 공간이 필요하다고하면 필요한 공간만큼 할당해주면 된다.



또한 팁으로 루트 노드를 여러 개 만들어도 된다. 즉, 하나의 루트 노드에 모든 문자열을 넣고 싶다면 위와 같이하면 되고

삽입하는 문자열의 길이마다 루트 노드를 할당하고 싶다면

`Trie[] rootNode = new Trie[11] `로 루트 노드 배열을 만든 후, 삽입하려는 문자열의 길이를 인덱스로하여 삽입해주면 된다. 위 코드는 10자리까지의 길이를 나타내는 rootNode 배열이다.

### 문자열 삽입

```java
void insert(String str) {

  //항상 루트 노드부터 시작하므로 커서를 this부터 시작.
  Trie curr = this;
  curr.count++;

  for (char ch : str.toCharArray()) {

    int idx = Character.getNumericValue(ch) - Character.getNumericValue('a');

    if (curr.child[idx] == null) {
      curr.child[idx] = new Trie();
    }

    curr = curr.child[idx];
    curr.count++;
  }

  curr.data = str;
}
```









```java
//50개의 Trie class 배열을 만든다. 지금은 다 null
Trie[] rootTrie = new Trie[50];

rootTrie[0] = new Trie();//0번째 인덱스에 인스턴스를 참조시킨다. null이 아님.
```





<img src="/img/Trie/image-20220527171912291.png" alt="image-20220527171912291" style="zoom:50%;" />

하나의 루트 노드에 account, app, apple, cap, capture 5개 문자열을 추가한 상태.





### 단어 삭제

여기서 apple라는 단어를 삭제한다면

<img src="/Users/jungwoo/Desktop/dev/git/Coding-Test/JAVAStudy/img/Trie/image-20220527172604117.png" alt="image-20220527172604117" style="zoom:50%;" />

위와 같이 된다. 위에서 root(4)로 변경.

우선 해당 삭제하는 단어가 있는지 확인 후, delete를 시작한다.

여기서 delete하는 포인트는 해당 단어로 curr를 curr.count--해주면서 이동하고 count가 1인 노드를 삭제(null)하면 된다.

```java
boolean search(String target) {

  Trie curr = this;

  for (char ch : target.toCharArray()) {

    int idx = Character.getNumericValue(ch) - Character.getNumericValue('a');

    if (curr.child[idx] == null) {
      return false;
    }

    curr = curr.child[idx];
    if (curr.data == target) {
      return true;
    }
  }

  return false;
}

void delete(String target) {

  //우선 해당 단어가 존재하는 지 확인.
  if (!this.search(target)) {
    System.out.println("해당 단어가 존재하지 않아 삭제하지 못합니다.");
    return;
  }

  Trie curr = this;
  curr.count--;

  for (char ch : target.toCharArray()) {

    int idx = Character.getNumericValue(ch) - Character.getNumericValue('a');

    if (curr.child[idx].count == 1) {
      curr.child[idx] = null;
      return;
    }

    curr = curr.child[idx];
    curr.count--;
  }


}
```



### 접두어에 해당하는 단어 삭제

접두어(prefix) ap를 가지고 있는 단어 모두를 삭제하고 싶다면

```java
int getCount(String str) {
  Trie curr = this;

  for (char ch : str.toCharArray()) {

    int idx = Character.getNumericValue(ch) - Character.getNumericValue('a');

    if (curr.child[idx] == null) {
      return 0;
    }

    curr = curr.child[idx];

  }

  return curr.count;
}

void deletePrefix(String prefix) {

  Trie curr = this;

  int wordCnt = curr.getCount(prefix);

  if (wordCnt == 0) {
    System.out.println("해당 prefix를 가지고 있는 단어가 없습니다.");
  }

  curr.count -= wordCnt;

  for (char ch : prefix.toCharArray()) {
    int idx = Character.getNumericValue(ch) - Character.getNumericValue('a');

    if ((curr.child[idx].count - wordCnt) < 1) {
      curr.child[idx] = null;
      return;
    }

    curr = curr.child[idx];
    curr.count -= wordCnt;
  }

}
```

a(3) p(2) p(2) l(1) e(1)

​        c(1) c(1) ....

삭제 후,

a(1) p(0) p(0) l(-1) e(-1)

​       c(1) c(1)

1보다 작아지면 null하여 가지치기한다.



















B(4) A(4) P(4) C(4) .(3) i(1)(BACP.in)

​										o(1)(BACP.out)

​										t(1)(BACP.tex)

f(4) i(4) l(4) e(3) n(2) a(2) m(2) e(2) s(1)

​							s(1)

​                     t(1)

c(7) l(7) e(7) a(7) n(7) u(5) p(5) .(4)

​                                      i(1)



p(1)