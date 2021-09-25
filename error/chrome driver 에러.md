# [Linux] chrome driver 에러





### error 1

selenium.common.exceptions.WebDriverException: Message: unknown error: Chrome failed to start: exited abnormally

 (unknown error: DevToolsActivePort file doesn't exist)

 (The process started from chrome location /usr/bin/google-chrome is no longer running, so ChromeDriver is assuming that Chrome has crashed.)

 (Driver info: chromedriver=2.46.628388 (4a34a70827ac54148e092aafb70504c4ea7ae926),platform=Linux 3.10.0-327.el7.x86_64 x86_64)



-> 해결법

```
options = webdriver.ChromeOptions()
options.add_argument('--headless')
options.add_argument('--no-sandbox')
options.add_argument('--single-process')
options.add_argument('--disable-dev-shm-usage')
```

옵션 추가



