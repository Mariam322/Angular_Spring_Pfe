package tn.iit.interceptor;



import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

// Remplace javax.servlet par jakarta.servlet
import jakarta.servlet.http.HttpServletRequest;


@Component // rend ce composant accessible à Spring
public class AuthFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // Récupère les attributs de la requête HTTP actuelle
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        // Vérifie que la requête HTTP existe (sinon cela plante dans les appels asynchrones)
        if (requestAttributes != null) {
            // On cast les attributs en ServletRequestAttributes pour accéder à la requête HTTP
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

            // Récupère le token JWT dans l’en-tête "Authorization"
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null) {
                // Ajoute le token à la requête Feign automatiquement
                template.header("Authorization", authorizationHeader);
            }
        }
    }
}
