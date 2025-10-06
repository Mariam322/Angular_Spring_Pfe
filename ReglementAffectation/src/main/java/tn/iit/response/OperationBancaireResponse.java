package tn.iit.response;

import java.util.Date;

public class OperationBancaireResponse {
	private Long idOperationBancaire;
	private Date dateOperation ;
	private String numCheque;
	private Long debit;
	private Long credit;
	public Long getIdOperationBancaire() {
		return idOperationBancaire;
	}
	public void setIdOperationBancaire(Long idOperationBancaire) {
		this.idOperationBancaire = idOperationBancaire;
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
	
}
