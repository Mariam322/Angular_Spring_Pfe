package tn.iit.request;

import java.util.Date;

public class CreateOperationBancaireRequest {
	private Date dateOperation ;
	private String numCheque;
	private Long debit;
	private Long credit;
	private Long solde;
	
	public CreateOperationBancaireRequest(Date dateOperation, String numCheque, Long debit, Long credit, Long solde) {
		super();
		this.dateOperation = dateOperation;
		this.numCheque = numCheque;
		this.debit = debit;
		this.credit = credit;
		this.solde = solde;
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
		return solde;
	}
	public void setSolde(Long solde) {
		this.solde = solde;
	}
	
	
}
