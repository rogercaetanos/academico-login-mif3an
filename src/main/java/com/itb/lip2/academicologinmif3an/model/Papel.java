package com.itb.lip2.academicologinmif3an.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "papeis")
public class Papel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomePapel;
	private String descricaoPapel;
	private boolean codStatusPapel;
	
	// Construtor padrão
	
	public Papel() {
		
	}
	
	// Construtor com o nomePapel 
	
	public Papel(String nomePapel) {
		
		this.nomePapel = nomePapel;
	}
	

	// Construtor com o id e o nomePapel 
	
	public Papel(Long id, String nomePapel) {
		this.id = id;
		this.nomePapel = nomePapel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomePapel() {
		return nomePapel;
	}

	public void setNomePapel(String nomePapel) {
		this.nomePapel = nomePapel;
	}

	public String getDescricaoPapel() {
		return descricaoPapel;
	}

	public void setDescricaoPapel(String descricaoPapel) {
		this.descricaoPapel = descricaoPapel;
	}

	public boolean isCodStatusPapel() {
		return codStatusPapel;
	}

	public void setCodStatusPapel(boolean codStatusPapel) {
		this.codStatusPapel = codStatusPapel;
	}
	
   
}
