package com.insa.TeamOpsSystem.failedTraffics;

import com.insa.TeamOpsSystem.sites.Sites;
import com.insa.TeamOpsSystem.jwt.until.Auditable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FailedTrafficDtos extends Auditable {
    private Long id;
    private  String failedLinkType;
    private  String failedReason;
    private  String reportedTo;
    private LocalDateTime fixedAt;
    private LocalDateTime disConnectedAt;
    private String failureLength;
    private Sites sites;
}
