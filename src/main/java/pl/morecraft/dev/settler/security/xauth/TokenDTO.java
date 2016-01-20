package pl.morecraft.dev.settler.security.xauth;

/**
 * The security token.
 */
public class TokenDTO {

    //przekazywane do frontendu DataTransferToObject

    String token;
    long expires;

    public TokenDTO(String token, long expires) {
        this.token = token;
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public long getExpires() {
        return expires;
    }
}
