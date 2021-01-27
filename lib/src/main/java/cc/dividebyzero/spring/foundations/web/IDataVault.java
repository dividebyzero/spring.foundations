package cc.dividebyzero.spring.foundations.web;

import cc.dividebyzero.spring.foundations.IBaseObject;
import cc.dividebyzero.spring.foundations.repository.IRepository;
import cc.dividebyzero.spring.foundations.sanity.ISanitizer;
import cc.dividebyzero.spring.foundations.validity.IValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A DataVault is a CRUD Endpoint for Objects.
 * A Transaction has three stages:
 *  1) Sanitize the data (encodings etc)
 *  2) Validate the data
 *  3) Store the data in Repository
 *  After a successful transaction an Event is fired with the latest Version of the Data attached.
 * @param <IDTYPE>
 * @param <MODEL>
 */
public interface IDataVault<IDTYPE, MODEL extends IBaseObject<IDTYPE>> {


    ISanitizer<IDTYPE,MODEL> getSanitizer();

    IValidator<MODEL> getValidator();
    IRepository<MODEL> getRepository();
    IDataVaultEventListener<MODEL> getEventListener();

    public ResponseEntity<MODEL> create(MODEL data);
    public ResponseEntity<MODEL> update(MODEL data);
    public ResponseEntity<MODEL> delete(MODEL data);
    public ResponseEntity<MODEL> findOneById(@PathVariable final IDTYPE id);
    public ResponseEntity<Page<MODEL>> findMany(@RequestParam final MODEL search, @RequestParam(required = false) final Pageable page);
}
