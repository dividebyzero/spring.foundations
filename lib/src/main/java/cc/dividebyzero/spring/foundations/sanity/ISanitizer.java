package cc.dividebyzero.spring.foundations.sanity;

import cc.dividebyzero.spring.foundations.IBaseObject;

public interface ISanitizer<IDTYPE, T extends IBaseObject<IDTYPE>> {

    public boolean sanitizeId(IDTYPE id);

    public boolean sanitizeData(T data);

    public boolean sanitizeSearchData(T data);

    boolean sanitizeId(T data);
}
