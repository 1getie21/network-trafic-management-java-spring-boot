package com.insa.TeamOpsSystem.ftraffic;

import com.insa.TeamOpsSystem.sites.Sites;
import com.insa.TeamOpsSystem.until.Auditable;
import lombok.Data;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
public class FTrafficDtos extends Auditable {
    private Long id;
    private String trafficTimeName;
    private  String timeValues;
    private Sites sites;
   private String remark;
}
