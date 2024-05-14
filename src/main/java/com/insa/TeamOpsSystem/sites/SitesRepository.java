package com.insa.TeamOpsSystem.sites;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface SitesRepository extends PagingAndSortingRepository<Sites, Long>, JpaSpecificationExecutor<Sites> {
    Page<Sites> findAllByOrderByCreatedAtDesc(Pageable pageable);

}
