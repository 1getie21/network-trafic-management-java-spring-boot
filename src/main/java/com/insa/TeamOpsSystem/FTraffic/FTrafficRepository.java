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

    Page<Ftraffics> findAllByCreatedByAndSitesDeletedIsFalseOrderByCreatedAtDesc(String username, Pageable pageable);
    List<Ftraffics> findAllByCreatedAtIsGreaterThanEqualAndTrafficTimeNameAndSitesIdAndSitesDeletedIsFalse(LocalDateTime cratedAt, String ttNAme, long siteId);
    List<Ftraffics> findAll();
    Page<Ftraffics> findAllByCreatedAtBetweenAndSitesDeletedIsFalseAndCreatedBy(LocalDateTime from, LocalDateTime to,String createdBy,Pageable pageable);

    Page<Ftraffics> findAllBySitesDeletedIsFalse(Pageable pageable);
    List<Ftraffics> findAllBySitesDeletedIsFalse(   );

    List<Ftraffics> findAllBySitesDeletedIsFalseAndCreatedBy(String createdBy);
    Page<Ftraffics> findAllByCreatedAtBetweenAndSitesDeletedIsFalse(LocalDateTime from, LocalDateTime to,Pageable pageable);
    List<Ftraffics> findAllByCreatedAtBetweenAndSitesDeletedIsFalse(LocalDateTime from, LocalDateTime to);
    List<Ftraffics> findAllByCreatedAtBetweenAndSitesDeletedIsFalseAndCreatedBy(LocalDateTime from, LocalDateTime to, String username);

    Page<Ftraffics> findAllByTrafficTimeNameAndCreatedByAndSitesDeletedIsFalse(
            String trafficTimeName,
            String createdBy,
            boolean sitesDeleted,
            Pageable pageable);
    List<Ftraffics> findAllByTrafficTimeNameAndCreatedByAndSitesDeletedIsFalse(String trafficTimeName, String userName);

    Page<Ftraffics> findAllByTrafficTimeNameAndSitesDeletedIsFalse(String trafficTime, Pageable pageable);

    Page<Ftraffics> findAllByTrafficTimeNameAndCreatedByAndSitesDeletedIsFalse(String trafficTime, String createdBy, Pageable pageable);
}
