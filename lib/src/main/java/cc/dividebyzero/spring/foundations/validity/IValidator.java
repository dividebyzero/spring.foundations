package cc.dividebyzero.spring.foundations.validity;

public interface IValidator<T> {

    boolean isValid(T data);

}
