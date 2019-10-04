package coursebuy.domain.usecase.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JsonWebToken {

    private static JsonWebToken jsonWebToken = null;
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static JsonWebToken getInstance() {
        if (jsonWebToken == null)
            jsonWebToken = new JsonWebToken();
        return jsonWebToken;
    }

    public String generatePrivateKey(String username, String password, String localDateTimeNow) {
        return Jwts
                .builder()
                .setSubject(username)
                .setSubject(password)
                .setSubject(localDateTimeNow)
                .signWith(key)
                .compact();
    }
}
