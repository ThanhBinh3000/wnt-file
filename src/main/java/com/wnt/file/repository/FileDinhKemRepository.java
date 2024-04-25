package com.wnt.file.repository;

import com.wnt.file.request.search.FileDinhKemSearchReq;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wnt.file.table.FileDinhKem;

import java.util.List;

@Repository
public interface FileDinhKemRepository extends BaseRepository<FileDinhKem, FileDinhKemSearchReq, Long> {

    @Query("SELECT c FROM FileDinhKem c " +
            "WHERE 1=1 "
            + " AND (:#{#param.dataType} IS NULL OR c.dataType = :#{#param.dataType}) "
            + " AND (:#{#param.dataId} IS NULL OR c.dataId = :#{#param.dataId}) "
            + " ORDER BY c.id desc"
    )
    List<FileDinhKem> searchList(@org.springframework.data.repository.query.Param("param") FileDinhKemSearchReq param);
}
