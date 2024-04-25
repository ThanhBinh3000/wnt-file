package com.wnt.file.repository;

import java.io.Serializable;
import java.util.List;

import feign.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<E, R, PK extends Serializable> extends CrudRepository<E, PK>
{
    List<E> searchList(@Param("param") R param);
}