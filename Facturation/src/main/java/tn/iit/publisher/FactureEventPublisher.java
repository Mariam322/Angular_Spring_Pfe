package tn.iit.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tn.iit.entites.Facture;
import tn.iit.event.FactureCreatedEvent;

@Component
@RequiredArgsConstructor
public class FactureEventPublisher {
	@Autowired
	private final StreamBridge streamBridge;
	

   
	public void publishFactureCreated(Facture facture) {
        FactureCreatedEvent event = new FactureCreatedEvent();
        event.setId(facture.getId());
        event.setNumeroFacture(facture.getNumeroFacture());
        event.setDateFacture(facture.getDateFacture());
        event.setDatePaiement(facture.getDatePaiement());
        event.setMontantHt(facture.getMontantHt());
        event.setTva(facture.getTva());
        event.setMontantTtc(facture.getMontantTtc());
        event.setRemise(facture.getRemise());
       event.setStatut(facture.getStatut().name());
        event.setTypePieceCommerciale(facture.getType());
        event.setIdCompaign(facture.getIdCompaign());
        event.setLignes(facture.getLignes());

        streamBridge.send("factureCreated-out-0", event);
    }

    // Configuration alternative si vous préférez utiliser Supplier
   /* @Bean
    public Supplier<FactureCreatedEvent> factureEventSupplier() {
        return () -> {
            // Logique pour générer des événements si nécessaire
            return null; // Retourne null si vous utilisez StreamBridge pour l'envoi manuel
        };
    }*/
}