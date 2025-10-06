package tn.iit.entites;

import java.util.Date;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  private String Reference;
    private String nom;
    private String address;
    private String email;
    private String telephone;
    private String pays;
    private String ville;

    @Enumerated(EnumType.STRING)
    private TypeClientFournisseur type;

	private Date createdAt;  
	private Date UpdatedAt;
}                                                   
