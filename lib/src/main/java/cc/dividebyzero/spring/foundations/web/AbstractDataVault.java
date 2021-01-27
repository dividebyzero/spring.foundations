package cc.dividebyzero.spring.foundations.web;

import cc.dividebyzero.spring.foundations.IBaseObject;
import cc.dividebyzero.spring.foundations.IdCreator;
import cc.dividebyzero.spring.foundations.repository.IRepository;
import cc.dividebyzero.spring.foundations.sanity.AbstractSanitizer;
import cc.dividebyzero.spring.foundations.sanity.ISanitizer;
import cc.dividebyzero.spring.foundations.validity.IValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


public abstract class AbstractDataVault<IDTYPE, MODEL extends IBaseObject<IDTYPE>> implements IDataVault<IDTYPE,MODEL> {

    static Logger logger = LoggerFactory.getLogger(AbstractDataVault.class);

    @Autowired
    IRepository<MODEL> repository;

    @Autowired
    AbstractSanitizer<IDTYPE,MODEL> sanitizer;

    @Autowired
    IdCreator<IDTYPE> idCreator;

    @Autowired
    IDataVaultEventListener<MODEL> eventListener;

    @Autowired
    IValidator<MODEL> validator;

    @Override
    public ISanitizer<IDTYPE,MODEL> getSanitizer() {
        return sanitizer;
    }

    @Override
    public IRepository<MODEL> getRepository() {
        return repository;
    }

    @Override
    public IValidator<MODEL> getValidator() {
        return validator;
    }

    @Override
    public IDataVaultEventListener<MODEL> getEventListener() {
        return eventListener;
    }

    public ResponseEntity<MODEL> create(@RequestBody MODEL data){
        if(!getSanitizer().sanitizeId(data)){
            data.setId(idCreator.createId());
        }
        if(getSanitizer().sanitizeData(data)){
            if(getValidator().isValid(data)) {
                Optional<MODEL> result = getRepository().create(data);
                if (result.isPresent()) {
                    getEventListener().onCreated(result.get());
                    return ResponseEntity.status(HttpStatus.CREATED).body(result.get());
                }
            }else {
                logger.error("Data Validation failed");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(data);
            }
        }else{
            logger.error("Data Sanitation failed");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(data);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    public ResponseEntity<MODEL> update(@RequestBody MODEL data){
        if(getSanitizer().sanitizeData(data)) {
            if (getValidator().isValid(data)) {
                Optional<MODEL> result = getRepository().update(data);
                if (result.isPresent()) {
                    getEventListener().onUpdated(result.get());
                    return ResponseEntity.ok().body(result.get());
                }
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(data);
            } else {
                logger.error("Data Validation failed");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(data);
            }
        }
        logger.error("Data Sanitation failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


    public ResponseEntity<MODEL> delete(@RequestBody MODEL data){
        if(getSanitizer().sanitizeData(data)){
            if(getValidator().isValid(data)) {
                Optional<MODEL> result = getRepository().delete(data);
                if (result.isPresent()) {
                    getEventListener().onDeleted(result.get());
                    return ResponseEntity.ok().body(result.get());
                }
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(data);
            }else {
                logger.error("Data Validation failed");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(data);
            }
        }
        logger.error("Data Sanitation failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    public ResponseEntity<MODEL> findOneById(@PathVariable final IDTYPE id){
        if(getSanitizer().sanitizeId(id)) {
            final Optional<MODEL> result = getRepository().findOneById(id.toString());

            return ResponseEntity.ok().body(result.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


    public ResponseEntity<Page<MODEL>> findMany(@RequestParam final MODEL search, @RequestParam(required = false) final Pageable page){
        if(getSanitizer().sanitizeSearchData(search)) {
            final Page<MODEL> result = getRepository().findMany(search,page);

            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
