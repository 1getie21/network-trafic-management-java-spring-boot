package com.insa.TeamOpsSystem.until;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class Auditable implements Serializable {

    @Schema(hidden = true)
    private LocalDateTime deletedAt;
    private boolean deleted = Boolean.FALSE;
    @Version()
    @Column(columnDefinition = "integer default 0")
    private long version;
    @Schema(hidden = true)
    @Column(name = "created_by")
    @CreatedBy
    private String created_by;
    @Schema(hidden = true)
    @Column(name = "updated_by")
    @LastModifiedBy
    private String updated_by;
    @Schema(hidden = true)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();
    @Schema(hidden = true)
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private String remark;
}
