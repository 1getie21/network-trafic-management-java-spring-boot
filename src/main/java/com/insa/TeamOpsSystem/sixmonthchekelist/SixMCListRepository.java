package com.insa.TeamOpsSystem.sixmonthchekelist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RepositoryRestResource
public interface SixMCListRepository extends PagingAndSortingRepository<SixMCList, Long>, JpaSpecificationExecutor<SixMCList> {
    Page<SixMCList> findAllBySitesDeletedIsFalse(Pageable pageable);

    Page<SixMCList> findAllByCreatedByAndSitesDeletedIsFalseOrderByCreatedAtDesc(String username, Pageable pageable);

    List<SixMCList> findAllByCreatedByAndSitesDeletedIsFalse(String createdBy);

    Page<SixMCList> findAllByCreatedAtBetweenAndSitesDeletedIsFalse(LocalDateTime atStartOfDay, LocalDateTime atStartOfDay1, Pageable pageable);

    List<SixMCList> findAllByCreatedAtBetweenAndCreatedByAndSitesDeletedIsFalse(LocalDateTime atStartOfDay, LocalDateTime atStartOfDay1, String username);
}
