# ResponseEntity



#### 파일 리소스를 읽어오는 방법

- 스프링 ResourceLoader 사용하기

#### 파일 다운로드 응답 헤더에 설정할 내용

- Content-Disposition: 사용자가 해당 파일을 받을 때 사용할 파일 이름
- Content-Type: 어떤 파일인가
- Content-Length: 얼마나 큰 파일인가

#### 파일의 종류(미디어 타입) 알아내는 방법

- http://tika.apache.org/

#### ResponseEntity

- 응답 상태 코드 
- 응답 헤더
- 응답 본문



```java
@GetMapping("/file/{filename}")
@ResponseBody
public ResponseEntity<Resource> downloadFile(@PathVariable String filename)
throws IOException {
	Resource resource = resourceLoader.getResource("classpath:" + filename); 
  File file = resource.getFile();
	Tika tika = new Tika();
	String type = tika.detect(file);
	return ResponseEntity.ok()
    .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\"" + resource.getFilename() + "\"")
    .header(HttpHeaders.CONTENT_TYPE, type) .header(HttpHeaders.CONTENT_LENGTH,
                                                    String.valueOf(file.length()))
    .body(resource);
}
```



#### 참고

- https://spring.io/guides/gs/uploading-files/
- https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Disposition
- https://www.baeldung.com/java-file-mime-type