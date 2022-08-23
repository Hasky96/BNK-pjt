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
* INFO(GET)
  ```
  /api/user/info
  ````
  Header
  ```
  {
    "Authorization" : "Bearer JWT"
  }
  ```
 
  ---
* USERUPDATE(PUT)
  ```
  /api/user/update
  ````
  Header
  ```
  {
    "Authorization" : "Bearer JWT"
  }
  ```
  body
  ```
  {
    "userCarInfo" : "changed info",
    "userCarNo" : "changed number"
  }
  ```
* USERPWUPDATE(PUT)
  ```
  /api/user/pwupdate
  ````
  Header
  ```
  {
    "Authorization" : "Bearer JWT"
  }
  ```
  body
  ```
  {
    "oldPw" : "1234",
    "newPw" : "12345"
  }
  ```
  
---
CARPOOL
---
* CREATE(POST)
  ```
  api/carpool/create
  ```
  Header
  ```
  {
    "Authorization" : "Bearer JWT"
  }
  ```
  body
  ```json{
    "carpoolWriter":1,
    "carpoolDriver":1,
    "carpoolType":"false",
    "carpoolLocation":"location",
    "carpoolQuota":5,
    "carpoolInfo":"Info",
    "carpoolFee":3000,
    "carpoolTime": "2022-08-15T12:13:14"
  }
  ```
  ---
  * CARPOOLDONE(POST)
  ```
  /api/carpool/{carpoolNo}/done
  ````
  Header
  ```
  {
    "Authorization" : "Bearer JWT"
  }
  ```
    * CARPOOLJOIN(POST)
  ```
  /api/carpool/join/{carpoolNo}
  ````
  Header
  ```
  {
    "Authorization" : "Bearer JWT"
  }
  ```
  ---
* CARPOOLUPDATE(PUT)
  ```
  api/carpool/{carpoolNo}
  ```
  Header
  ```
  {
    "Authorization" : "Bearer JWT"
  }
  ```
  body
  ```json
  {
    "carpoolType":true,
    "carpoolLocation":"location",
    "carpoolQuota":5,
    "carpoolInfo":"Info",
    "carpoolTime": "2022-08-15T12:13:14"
  }
  ```
  ---
    * CARPOOLLEAVE(POST)
  ```
  /api/carpool/leave/{carpoolNo}
  ```
  Header
  ```
  {
    "Authorization" : "Bearer JWT"
  }
  ```
  ---
    * CARPOOLDETAIL(GET)
  ```
  /api/carpool/{carpoolNo}
  ````
  Header
  ```
  {
    "Authorization" : "Bearer JWT"
  }
  ```
  
  ---
    * GETROUTE(POST)
  ```
  /api/map/route
  ```
  Header
  ```
  {
    "Authorization" : "Bearer JWT"
  }
  ```
  body
  ```json
  {
    "latitude" : "129.0653633",
    "longitude" : "35.1528347"
  }
  ```
  ---
  * GETADDRESS(GET)
  ```
  api/map/loca
  ```
  Header
  ```
  {
    "Authorization" : "Bearer JWT"
  }
  ```
  PARAM
  ```
    query={검색어} ex) 문현역, 사상역, 미음산단로 127번길 21
  ```
  
