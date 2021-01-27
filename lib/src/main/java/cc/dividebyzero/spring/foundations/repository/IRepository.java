package cc.dividebyzero.spring.foundations.repository;

import cc.dividebyzero.spring.foundations.IBaseObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IRepository<Model extends IBaseObject<?>> {


        Optional<Model> create(Model data);

        Optional<Model> update(Model data);

        Optional<Model> delete(Model data);

        Optional<Model> findOne(Model searchData);

        Optional<Model> findOneById(String id);

        Page<Model> findMany(Model searchData, Pageable pageInfo);

}
