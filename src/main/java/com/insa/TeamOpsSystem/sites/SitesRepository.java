package com.insa.TeamOpsSystem.sites;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface SitesRepository extends PagingAndSortingRepository<Sites, Long>, JpaSpecificationExecutor<Sites> {
    @Query("SELECT s FROM Sites s ORDER BY s.name ASC")
    Page<Sites> findAllByOrderByNameAsc(Pageable pageable);


}


//    look me
//    how to deploye backend
//        1 click m from ritht top side which is maven
//                2 make visible dropdown of lifecycle
//3 double click clean,
//when it is finished, "target folder" from left side which is below srs folder
//is removed.   look me
//4 click install. if it is finished, "target" folder is created again. look me
// 5 copy war file  look me.   copy yaderekutn ayeshiw?ewo
//this means deploye yeminadergew file build sihon yichin file create yaderglinal
//so we have to deploye it
//6 u have to paste it on disk "D:\apache-tomcat-9.0.89-windows-x64\apache-tomcat-9.0.89\webapps"
// ....... follow me on window 10.  eyeteketelshign new?ew
//paste kaderegishiw erasu redeploy yadergewal. anch menekakat aytebekbshim
//
//endet reinitialize endemiyadergew temelkech
//bey te engidih anch nesh yemtadergiw eshi
