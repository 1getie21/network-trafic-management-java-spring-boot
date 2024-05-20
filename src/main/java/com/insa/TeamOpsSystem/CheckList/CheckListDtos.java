package com.insa.TeamOpsSystem.CheckList;

import com.insa.TeamOpsSystem.sites.Sites;
import com.insa.TeamOpsSystem.until.Auditable;
import lombok.Data;

import javax.persistence.Column;

@Data
public class CheckListDtos extends Auditable {
    private Long id;
    private String linkType;
    private String download;
    private String upload;
    private String createdBy;
    private String npb;
    private String avgNBP;
    private Sites sites;
}
