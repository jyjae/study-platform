package com.example.studyplatform.service.jwt;

import com.example.studyplatform.config.secret.Secret;
import com.example.studyplatform.exception.JwtInvalidException;
import com.example.studyplatform.exception.JwtNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final UserDetailsService userDetailsService;

    public String createJwt(Long userIdx) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userIdx", userIdx)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24 * 365)))
                .signWith(SignatureAlgorithm.HS256, Secret.ACCESS_JWT_SECRET_KEY)
                .compact();
    }

    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public Optional<String> getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return Optional.ofNullable(request.getHeader("X-ACCESS-TOKEN"));
    }

    /*
    JWT에서 userIdx 추출
    @return int
    @throws BaseException
     */
    public String getUserIdx() throws RuntimeException {
        //1. JWT 추출
        String accessToken = getJwt().orElseThrow(JwtNotFoundException::new);
        if (accessToken.length() == 0) {
            throw new JwtNotFoundException();
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Secret.ACCESS_JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new JwtInvalidException();
        }

        // 3. userIdx 추출
        return claims.getBody().get("userIdx", String.class);
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(Secret.ACCESS_JWT_SECRET_KEY).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰에서 회원 정보 추출
    public Long getUserIdx(String token) {
        return Jwts.parser().setSigningKey(Secret.ACCESS_JWT_SECRET_KEY).parseClaimsJws(token).getBody().get("userIdx", Long.class);
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserIdx(token) + "");
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


}
