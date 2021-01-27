package cc.dividebyzero.spring.foundations.web;

public interface IDataVaultEventListener<MODEL> {

    void onCreated(MODEL data);
    void onUpdated(MODEL data);
    void onDeleted(MODEL data);

}
