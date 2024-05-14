package com.insa.TeamOpsSystem.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface RequestRepository extends PagingAndSortingRepository<Request, Long>, JpaSpecificationExecutor<Request> {
    Page<Request> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Request> findAllByCreatedByOrderByCreatedAtDesc(String username, Pageable pageable);
}
