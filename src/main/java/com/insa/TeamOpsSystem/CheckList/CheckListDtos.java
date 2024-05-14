package com.insa.TeamOpsSystem.CheckList;

import com.insa.TeamOpsSystem.sites.Sites;
import com.insa.TeamOpsSystem.until.Auditable;
import lombok.Data;

@Data
public class CheckListDtos extends Auditable {
    private Long id;
    private String LinkType;
    private String Download;
    private String Upload;
    private String createdBy;
    private String NPB;
    private String AvgNBP;
    private Sites sites;
}