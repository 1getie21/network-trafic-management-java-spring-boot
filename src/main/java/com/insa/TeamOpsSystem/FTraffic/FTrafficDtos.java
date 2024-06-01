package com.insa.TeamOpsSystem.FTraffic;

import com.insa.TeamOpsSystem.sites.Sites;
import com.insa.TeamOpsSystem.jwt.until.Auditable;
import lombok.Data;

@Data
public class FTrafficDtos extends Auditable {
    private Long id;
    private String trafficTimeName;
    private  String timeValues;
    private Sites sites;
   private String remark;
}
