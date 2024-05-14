package com.insa.TeamOpsSystem;

import com.insa.TeamOpsSystem.traffics.Times;
import lombok.Data;

@Data
public class TrafficDtos {
    private Long id;
    private String sites;
    private Times eightTime;
    private  String eightTimeTraffic;
    private  Times fortiethTime;
    private  String fortiethTimeTraffic;
    private  Times eighteenTime;
    private  String eighteenTimeTraffic;
    private  String description;
}
