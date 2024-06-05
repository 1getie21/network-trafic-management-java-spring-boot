package com.insa.TeamOpsSystem.request;

import com.insa.TeamOpsSystem.sites.Sites;
import com.insa.TeamOpsSystem.jwt.until.Auditable;
import lombok.Data;

@Data
public class RequestDtos extends Auditable {
    private Long id;
    private  String fname;
    private  String phone;
    private  String email;    private  String requester;
    private   String organization;
    private   String categories;
    private   String contact;
    private String description;
    private String status;
    private String descriptionFile;
    private Sites sites;

}
