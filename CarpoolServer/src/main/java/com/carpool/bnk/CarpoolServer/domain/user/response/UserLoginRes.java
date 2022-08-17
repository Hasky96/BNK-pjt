package com.carpool.bnk.CarpoolServer.domain.user.response;

import com.carpool.bnk.CarpoolServer.domain.user.db.entity.User;
import com.carpool.bnk.CarpoolServer.global.model.reponse.BaseResponseBody;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserLoginRes extends BaseResponseBody {
    String accessToken;

    String userId;

    int userNo;

    String userCarInfo;

    String userCarNo;

    public static UserLoginRes of(Integer statusCode, String message, String accessToken, User userInfo) {
        UserLoginRes res = new UserLoginRes();
        if(userInfo != null){
            res.setUserId(userInfo.getUserId());
            res.setUserNo(userInfo.getUserNo());
            res.setUserCarInfo(userInfo.getUserCarInfo());
            res.setUserCarNo(userInfo.getUserCarNo());
        }
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setAccessToken(accessToken);

        return res;
    }
}