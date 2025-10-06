package tn.iit.entites;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class CompteBancaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long idCompte;

    private String numeroCompte;

    private String nomBanque;
    @OneToMany (mappedBy = "compteBancaire")
     @JsonProperty(access = Access.WRITE_ONLY)
    private List<OperationBancaire> operationBancaire;
    
    
}
