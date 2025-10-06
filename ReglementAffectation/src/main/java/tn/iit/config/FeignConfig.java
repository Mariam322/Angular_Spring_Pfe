package tn.iit.config;


import feign.RequestInterceptor;
import feign .RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Configuration
public class FeignConfig {

    private static final Logger logger = LoggerFactory.getLogger(FeignConfig.class);

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            try {
                // Récupération du contexte de la requête
                ServletRequestAttributes attributes = (ServletRequestAttributes) 
                    RequestContextHolder.getRequestAttributes();
                
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();
                    String token = request.getHeader("Authorization");
                    
                    if (token != null && !token.isEmpty()) {
                        // Propagation du token
                        template.header("Authorization", token);
                        logger.debug("Token JWT propagé avec succès vers le service cible");
                    } else {
                        logger.warn("Aucun token JWT trouvé dans la requête entrante");
                    }
                } else {
                    logger.warn("Aucun contexte de requête disponible - appel hors contexte HTTP");
                }
            } catch (Exception e) {
                logger.error("Erreur lors de la propagation du token JWT", e);
                throw new FeignTokenPropagationException("Échec de la propagation du token JWT", e);
            }
        };
    }

    // Classe d'exception personnalisée
    public static class FeignTokenPropagationException extends RuntimeException {
        public FeignTokenPropagationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
