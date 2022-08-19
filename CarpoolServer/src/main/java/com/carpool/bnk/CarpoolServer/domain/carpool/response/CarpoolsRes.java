package com.carpool.bnk.CarpoolServer.domain.carpool.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CarpoolsRes {

    private List<CarpoolDetailRes> carpools;

    private String msg;
}
