package cc.dividebyzero.spring.foundations.sanity;


import cc.dividebyzero.spring.foundations.IBaseObject;
import org.springframework.util.StringUtils;

public abstract class AbstractStringIdSanitizer<String, T extends IBaseObject<String>> extends AbstractSanitizer<String, T> implements ISanitizer<String, T> {

    public boolean sanitizeId(final String id) {
        try {
            if (StringUtils.isEmpty(id)) return false;

            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

}
