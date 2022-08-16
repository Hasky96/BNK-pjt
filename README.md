# BNK-pjt

BNK시스템 신입사원 연수 프로젝트 (12days)

---
USER
---
* IDCHECK(GET)
  ```
  /api/user/idcheck?id={checkId}
  ```
---
* SIGNUP(POST)
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
---
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
---
CARPOOL
---
* CREATE(POST)
  ```
  api/carpool/create
  ```
  ```json{
    "carpoolWriter":1,
    "carpoolDriver":1,
    "carpoolType":"false",
    "carpoolLocation":"location",
    "carpoolQuota":5,
    "carpoolInfo":"Info",
    "carpoolFee":3000
  }
  ```
