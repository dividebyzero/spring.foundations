package cc.dividebyzero.spring.foundations.sanity;


import cc.dividebyzero.spring.foundations.IBaseObject;

public abstract class AbstractSanitizer<IDTYPE, T extends IBaseObject<IDTYPE>> implements ISanitizer<IDTYPE, T> {

    @Override
    public boolean sanitizeId(T data) {
        if(data==null){
            return false;
        }
        return sanitizeId(data.getId());
    }

    public boolean sanitizeData(T data){
        if(data==null) return false;
        return sanitizeId(data.getId());
    }
}
