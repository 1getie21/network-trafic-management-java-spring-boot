package com.insa.TeamOpsSystem.failedTraffics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface FailedTrafficRepository extends PagingAndSortingRepository<FailedTraffics, Long>, JpaSpecificationExecutor<FailedTraffics> {
    Page<FailedTraffics> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<FailedTraffics> findAllByCreatedByAndSitesDeletedIsFalseOrderByCreatedAtDesc(String username, Pageable pageable);

    Page<FailedTraffics> findAllBySitesDeletedIsFalse(Pageable pageable);
}
