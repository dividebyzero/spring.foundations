package cc.dividebyzero.spring.foundations;

import java.util.UUID;

public class StringIdCreator implements IdCreator<String>{
    @Override
    public String createId() {
        return UUID.randomUUID().toString();
    }
}
