package com.insa.TeamOpsSystem.FTraffic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface FTrafficRepository extends PagingAndSortingRepository<Ftraffics, Long>, JpaSpecificationExecutor<Ftraffics> {
    Page<Ftraffics> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Ftraffics> findAllByCreatedByAndSitesDeletedIsFalseOrderByCreatedAtDesc(String username, Pageable pageable);

    Page<Ftraffics> findAllBySitesDeletedIsFalse(Pageable pageable);
    Page<Ftraffics> findAllBySitesDeletedIsFalseAndTrafficTimeName(String trafficTime,Pageable pageable);

}
