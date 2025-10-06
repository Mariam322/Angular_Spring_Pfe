package tn.iit.entites;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OperationBancaire implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //autoIncrement
private Long idOperation;
private Date dateOperation ;
private String numCheque;
private Long debit;
private Long credit;
private  Long Solde;
@ManyToOne

private CompteBancaire compteBancaire;

public Long getIdOperation() {
	return idOperation;
}
public void setIdOperation(Long idOperation) {
	this.idOperation = idOperation;
}
public Date getDateOperation() {
	return dateOperation;
}
public void setDateOperation(Date dateOperation) {
	this.dateOperation = dateOperation;
}
public String getNumCheque() {
	return numCheque;
}
public void setNumCheque(String numCheque) {
	this.numCheque = numCheque;
}
public Long getDebit() {
	return debit;
}
public void setDebit(Long debit) {
	this.debit = debit;
}
public Long getCredit() {
	return credit;
}
public void setCredit(Long credit) {
	this.credit = credit;
}
public Long getSolde() {
	return Solde;
}
public void setSolde(Long solde) {
	Solde = solde;
}
public CompteBancaire getCompteBancaire() {
	return compteBancaire;
}
public void setCompteBancaire(CompteBancaire compteBancaire) {
	this.compteBancaire = compteBancaire;
}


}
