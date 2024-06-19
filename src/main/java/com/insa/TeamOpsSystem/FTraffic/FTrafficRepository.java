package com.insa.TeamOpsSystem.FTraffic;

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
public interface FTrafficRepository extends PagingAndSortingRepository<Ftraffics, Long>, JpaSpecificationExecutor<Ftraffics> {
    Page<Ftraffics> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Ftraffics> findAllByCreatedByAndSitesDeletedIsFalseOrderByCreatedAtDesc(String username, Pageable pageable);
    List<Ftraffics> findAllByCreatedAtIsGreaterThanEqualAndTrafficTimeNameAndSitesIdAndSitesDeletedIsFalse(LocalDateTime cratedAt, String ttNAme, long siteId);
    List<Ftraffics> findAll();
    Page<Ftraffics> findAllByCreatedAtBetweenAndSitesDeletedIsFalse(LocalDateTime from, LocalDateTime to,Pageable pageable);
    List<Ftraffics> findAllByCreatedAtBetweenAndSitesDeletedIsFalse(LocalDateTime from, LocalDateTime to);

    Page<Ftraffics> findAllBySitesDeletedIsFalse(Pageable pageable);
    Page<Ftraffics> findAllBySitesDeletedIsFalseAndTrafficTimeName(String trafficTime,Pageable pageable);

    List<Ftraffics> findAllByTrafficTimeName(String trafficTimeName);
}
