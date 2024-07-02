package com.insa.TeamOpsSystem.CheckList;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RepositoryRestResource
public interface CheckListRepository extends JpaRepository<CheckList, Long>, JpaSpecificationExecutor<CheckList> {

    Page<CheckList> findAllBySitesDeletedIsFalse(Pageable Pageable);
    List<CheckList> findAllBySitesDeletedIsFalse(   );
    Page<CheckList> findAllByCreatedByAndSitesDeletedIsFalseOrderByCreatedAtDesc(String username, Pageable pageable);

    Page<CheckList> findAllByCreatedAtBetween(LocalDateTime atStartOfDay, LocalDateTime atStartOfDay1, Pageable pageable);
    List<CheckList> findAllByCreatedAtBetweenAndCreatedByAndSitesDeletedIsFalse(LocalDateTime from, LocalDateTime to, String username);
   // List<CheckList> findAllByCreatedAtBetweenAndCreatedByAndSitesDeletedIsFalse(LocalDateTime atStartOfDay, LocalDateTime atStartOfDay1, String username);
    List<CheckList> findAllByCreatedByAndSitesDeletedIsFalse(String createdBy);

    List<CheckList> findAllByCreatedAtBetween(LocalDateTime atStartOfDay, LocalDateTime atStartOfDay1);


    Page<CheckList> findAllByCreatedAtBetweenAndSitesDeletedIsFalse(LocalDateTime atStartOfDay, LocalDateTime atStartOfDay1, Pageable pageable);

    Page<CheckList> findAllByCreatedAtBetweenAndSitesDeletedIsFalseAndCreatedBy(LocalDateTime atStartOfDay, LocalDateTime atStartOfDay1, String createdBy, Pageable pageable);
}
