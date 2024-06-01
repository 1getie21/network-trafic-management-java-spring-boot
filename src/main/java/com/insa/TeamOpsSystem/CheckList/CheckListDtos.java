package com.insa.TeamOpsSystem.CheckList;

import com.insa.TeamOpsSystem.sites.Sites;
import com.insa.TeamOpsSystem.jwt.until.Auditable;
import lombok.Data;

@Data
public class CheckListDtos extends Auditable {
    private Long id;
    private String linkType;
    private String download;
    private String upload;
    private String createdBy;
    private String avgNBP;
    private String npbone;
    private String npbtwo;
    private String npbthree;
    private Sites sites;
    private  float nbpTotal;
}
