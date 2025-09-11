package com.example.Enotes.ServiceImpl;

import com.example.Enotes.entity.User;
import com.example.Enotes.exception.JwtAuthException;
import com.example.Enotes.exception.JwtTokenExpireException;
import com.example.Enotes.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    private String secretKey="";

    public JwtServiceImpl(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String generateToken(User user) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",user.getId());
        claims.put("role",user.getRoles());
        claims.put("status",user.getStatus().getIsActive());
        String token = Jwts.builder()
                .claims().add(claims) //information that is to be stored
                .subject(user.getEmail()) //name of the field name
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .and()
                .signWith(getKey())
                .compact();
        return token;
    }

    @Override
    public String extractEmail(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public String role(String token){
        Claims claims = extractAllClaims(token);
        return (String) claims.get("role");
    }

    private Claims extractAllClaims(String token) {
        try{
            return Jwts.parser().
                    verifyWith(getKey()).
                    build().
                    parseSignedClaims(token)
                    .getPayload();
        }
        catch (ExpiredJwtException e){
            throw new JwtTokenExpireException("Token is Expired");
        }
        catch (JwtException e){
            throw new JwtAuthException("Invalid Jwt token");
        }
        catch (Exception e){
            throw e;
        }
    }

//    private SecretKey decrypt(String secretKey){
//        byte[] keyBytes =  Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        Boolean isExpired = isTokenExpired(token);
        if (email.equalsIgnoreCase(userDetails.getUsername()) && !isExpired){
            return true;
        }
        return false;
    }

    private Boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        Date expiredDate = claims.getExpiration();
        return expiredDate.before(new Date());
    }

    private SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
