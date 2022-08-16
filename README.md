# BNK-pjt

BNK시스템 신입사원 연수 프로젝트 (12days)

<hr/>
* IDCHECK (GET)
  ```
  /api/user/idcheck?id={checkId}
  ```
  <hr/>
* SIGNUP (POST)
  ```
  /api/user/signup
  ```
  body
  ```json
  {
    "userId" : "test",
    "userPw" : "1234",
    "userCarInfo" : "sdfs",
    "userCarNo" : "123가1223"
  }
  ```
  <hr/>
* LOGIN(POST)
  ```
  /api/user/login
  ````
  body
  ```
  {
    "userId" : "test",
    "userPw" : "1234"
  }
  ```
  <hr/>
