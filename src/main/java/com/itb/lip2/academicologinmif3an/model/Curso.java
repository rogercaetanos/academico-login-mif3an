package com.itb.lip2.academicologinmif3an.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="cursos")
public class Curso {
 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 45, nullable =  false)
	private String nomeCurso;
	@Column(length = 255, nullable =  true) // permite nulo, se não colocar permite também 
	private String descricaoCurso;
	
	// Aqui temos uma associação - UML ( Diagrama de Classes )
	
	@ManyToOne
	@JoinColumn(name="id_usuario") // Fk
	private Usuario usuario;
	
	
	public Curso() {
	
	}	
	public Curso(String nomeCurso) {
		
		this.nomeCurso = nomeCurso;
	}

	public Curso(String nomeCurso, String descricaoCurso) {

		this.nomeCurso = nomeCurso;
		this.descricaoCurso = descricaoCurso;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public String getDescricaoCurso() {
		return descricaoCurso;
	}

	public void setDescricaoCurso(String descricaoCurso) {
		this.descricaoCurso = descricaoCurso;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		return Objects.equals(id, other.id);
	}
	
		
		
}
