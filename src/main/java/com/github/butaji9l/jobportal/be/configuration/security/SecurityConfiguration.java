package com.github.butaji9l.jobportal.be.configuration.security;

import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;

import com.github.butaji9l.jobportal.be.configuration.properties.JobPortalApplicationProperties;
import com.github.butaji9l.jobportal.be.utils.StaticObjectFactory;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.CacheControlConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

/**
 * Configuration class for security settings of the application
 *
 * @author Vitalii Bortsov
 */
@Configuration
@EnableMethodSecurity
@Import(SecurityProblemSupport.class)
@RequiredArgsConstructor
public class SecurityConfiguration {

  private static final String[] SWAGGER_PUBLIC_ENDPOINTS = {
    "/swagger-ui.html",
    "/swagger-ui/**",
    "/v3/api-docs/**",
  };

  private static final String UUID_REGEX = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";
  private static final List<String> ALLOWED_CORS_HEADERS = List.of("x-requested-with",
    "authorization", "origin",
    "content-type", "version",
    "content-disposition", "location");
  private final JobPortalApplicationProperties applicationProperties;
  private final SecurityProblemSupport securityProblemSupport;

  @Bean
  public CorsConfigurationSource corsFilter() {
    final var cfg = new CorsConfiguration();
    cfg.addAllowedOrigin("*");
    cfg.addAllowedHeader("*");
    cfg.addAllowedMethod("*");
    cfg.setExposedHeaders(ALLOWED_CORS_HEADERS);

    final var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cfg);

    return source;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.headers(h -> h.cacheControl(CacheControlConfig::disable));

    http.cors(c -> c.configurationSource(corsFilter()));
    http.authorizeHttpRequests(ar ->
      ar.requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/auth/**", "/companies/**", "/job-categories/**",
          "/positions", "/positions/")
        .permitAll()
        .requestMatchers(
          regexMatcher(HttpMethod.GET, "/positions/" + UUID_REGEX),
          regexMatcher(HttpMethod.GET, "/users/" + UUID_REGEX + "/avatar")
        ).permitAll()
        .requestMatchers(HttpMethod.GET, SWAGGER_PUBLIC_ENDPOINTS).permitAll()
        .anyRequest()
        .authenticated()
    );

    http.oauth2ResourceServer(rs -> rs.jwt(Customizer.withDefaults()));

    http.httpBasic(Customizer.withDefaults());

    http.exceptionHandling(eh -> eh.authenticationEntryPoint(securityProblemSupport)
      .accessDeniedHandler(securityProblemSupport));

    return http.build();
  }

  @Bean
  public RSAKey rsaKey() {
    final var storeProperties = applicationProperties.getKeyStore();
    try (final var is = storeProperties.getLocation().getInputStream()) {
      final var ks = KeyStore.getInstance(KeyStore.getDefaultType());
      ks.load(is, storeProperties.getKeyStorePassword().toCharArray());

      final var instance = storeProperties.getKeyPairName();
      final var key = ks.getKey(instance, storeProperties.getKeyPairPassword().toCharArray());
      if (key instanceof PrivateKey pk) {
        final var cert = ks.getCertificate(instance);
        final var publicKey = cert.getPublicKey();
        return new RSAKey.Builder((RSAPublicKey) publicKey)
          .privateKey(pk)
          .keyID(storeProperties.getKid())
          .build();
      } else {
        throw new BeanInitializationException(
          "Failed to initialize KeyPair provider. Create key is not private");
      }
    } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException |
             IOException |
             CertificateException ex) {
      throw new BeanInitializationException(ex.getMessage(), ex);
    }
  }

  @Bean
  public JwtDecoder jwtDecoder(StaticObjectFactory staticObjectFactory) {
    try {
      final var result = NimbusJwtDecoder.withPublicKey((RSAPublicKey) rsaKey().toPublicKey())
        .build();
      final var validator = new JwtTimestampValidator();
      validator.setClock(staticObjectFactory.getClock());

      result.setJwtValidator(validator);

      return result;
    } catch (JOSEException ex) {
      throw new BeanInitializationException(ex.getMessage(), ex);
    }
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    final var source = new ImmutableJWKSet<>(new JWKSet(rsaKey()));
    return new NimbusJwtEncoder(source);
  }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration authenticationConfiguration)
    throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
