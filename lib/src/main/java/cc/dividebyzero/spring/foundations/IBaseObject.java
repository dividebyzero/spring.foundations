package cc.dividebyzero.spring.foundations;


/**
* A Simple base class for all Objects requiring an ID.
 * IDTYPE usually is something like String or UUID.
*/
public interface IBaseObject<IDTYPE>{

    IDTYPE getId();

    void setId(IDTYPE id);

}
