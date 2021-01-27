package cc.dividebyzero.spring.foundations;

public interface IdCreator<IDTYPE> {

    IDTYPE createId();
}
