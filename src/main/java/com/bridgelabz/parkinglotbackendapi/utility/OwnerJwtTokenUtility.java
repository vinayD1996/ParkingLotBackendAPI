package com.bridgelabz.parkinglotbackendapi.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.stereotype.Service;

@Service
public class OwnerJwtTokenUtility {

    private static final String TOKEN_SECRET ="sd5745FAHFW" ;

    public String createToken(Long id) {
        try {
            // to set algorithm
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // payload
            String token = JWT.create().withClaim("ownerId", id).sign(algorithm);
            return token;
        } catch (JWTCreationException | IllegalArgumentException exception) {
            exception.printStackTrace();
            // log Token Signing Failed
        }
        return null;
    }

    public Long decodeToken(String token) {
        Long ownerid;
        // for verification algorithm
        Verification verification = null;
        try {
            verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException e) {

            e.printStackTrace();
        }
        JWTVerifier jwtverifier = verification.build();
        // to decode token
        DecodedJWT decodedjwt = jwtverifier.verify(token);

        Claim claim = decodedjwt.getClaim("ownerId");
        ownerid = claim.asLong();
        return ownerid;
    }
}
