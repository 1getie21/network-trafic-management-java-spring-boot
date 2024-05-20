package com.insa.TeamOpsSystem.traffics;

import com.insa.TeamOpsSystem.sites.Sites;
import com.insa.TeamOpsSystem.traffics.Times;

import com.insa.TeamOpsSystem.until.Auditable;
import lombok.Data;

@Data
public class TrafficDtos extends Auditable {
    private Long id;
    private Times eightTime;
    private  String eightTimeTraffic;
    private  Times fortiethTime;
    private  String fortiethTimeTraffic;
    private  Times eighteenTime;
    private  String eighteenTimeTraffic;
    private  String remark;
    private Sites sites;
}
