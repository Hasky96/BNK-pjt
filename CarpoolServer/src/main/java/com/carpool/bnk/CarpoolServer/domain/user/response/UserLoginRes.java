package com.carpool.bnk.CarpoolServer.domain.user.response;

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

    public static UserLoginRes of(Integer statusCode, String message, String accessToken, String userId, int userNo) {
        UserLoginRes res = new UserLoginRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setAccessToken(accessToken);
        res.setUserId(userId);
        res.setUserNo(userNo);
        return res;
    }
}