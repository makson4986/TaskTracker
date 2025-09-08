package com.makson.tasktracker.services;

import com.makson.tasktracker.exceptions.InvalidJwtException;
import com.makson.tasktracker.exceptions.JwtClaimsException;
import com.makson.tasktracker.exceptions.JwtExtractionException;
import com.makson.tasktracker.exceptions.JwtGenerationException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {
    @Value("${jwt.expiration-sec}")
    private Long jwtExpiration;
    private final SecretKey secretKey;
    private final UserService userService;


    public JwtService(@Value("${jwt.secret-key}") String secretKey, UserService userService) {
        this.userService = userService;
        this.secretKey = decodeKeyFromString(secretKey);
    }

    public String generateToken(UserDetails userDetails) {
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(userDetails.getUsername())
                    .issueTime(Date.from(Instant.now()))
                    .expirationTime(Date.from(Instant.now().plusSeconds(jwtExpiration)))
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(new MACSigner(secretKey));

            JWEObject jwe = new JWEObject(new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A192GCM)
                    .contentType("JWT")
                    .build(),
                    new Payload(signedJWT.serialize())
            );

            jwe.encrypt(new DirectEncrypter(secretKey));
            return jwe.serialize();
        } catch (JOSEException e) {
            throw new JwtGenerationException("Error generating token", e);
        }
    }

    public Optional<String> getUsernameFromToken(String token) {
        SignedJWT jwt = extractJWSFromJWE(token);
        try {
            return Optional.ofNullable(jwt.getJWTClaimsSet().getSubject());
        } catch (ParseException e) {
            throw new JwtClaimsException("Error getting subject from token", e);
        }
    }

    public UserDetails verifyToken(String token) {
        if (!verifySignature(token)) {
            throw new InvalidJwtException("The integrity of the token has been compromised");
        }

        var usernameFromToken = getUsernameFromToken(token).orElseThrow(() -> new InvalidJwtException("Subject is missing in token"));
        UserDetails userDetails = userService.loadUserByUsername(usernameFromToken);

        if (usernameFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token)) {
            return userDetails;
        }

        throw new InvalidJwtException("Token is not valid");
    }

    public boolean isTokenExpired(String token) {
        SignedJWT jwt = extractJWSFromJWE(token);
        try {
            return jwt.getJWTClaimsSet().getExpirationTime().before(Date.from(Instant.now()));
        } catch (ParseException e) {
            throw new JwtClaimsException("Error getting expiration time from token", e);
        }
    }

    private boolean verifySignature(String jwe) {
        try {
            SignedJWT jwt = extractJWSFromJWE(jwe);
            MACVerifier macVerifier = new MACVerifier(secretKey);
            return jwt.verify(macVerifier);
        } catch (JOSEException e) {
            throw new InvalidJwtException("Error verifying token signature", e);
        }

    }

    private SignedJWT extractJWSFromJWE(String jweString) {
        try {
            JWEObject jwe = JWEObject.parse(jweString);
            jwe.decrypt(new DirectDecrypter(secretKey));

            String jwsString = jwe.getPayload().toString();
            return SignedJWT.parse(jwsString);
        } catch (ParseException | JOSEException e) {
            throw new JwtExtractionException("Exception when extracting jwt from jwe", e);
        }
    }

    private SecretKey decodeKeyFromString(String secretKey) {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(decodedKey, "AES");
    }
}
