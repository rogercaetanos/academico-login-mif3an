package com.itb.lip2.academicologinmif3an.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Usuario {
	
	@Id                                                  // Chave- Primária
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto- Incremento
	private Long id;
	private String nome;
	private String sobrenome;
	private String email;
	private String password;
	private String tipoPrincipalUsuario;
	private LocalDate dataNascimento;
	private boolean codStatusUsuario;
	private String logradouro;
	private String bairro;
	private String cep;
	private String numero;
	private String uf;
	private String cidade;
	private String pais;
	
	
	// Relacionamento  M:N
	
	// FetchType.EAGER => Busca também os relacionados
	// FetchType.Lazy => Traz somente o referido
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			    name="usuarios_papeis",
			    joinColumns = @JoinColumn(name="usuario_id", referencedColumnName = "id"),
			    inverseJoinColumns = @JoinColumn(name="papel_id", referencedColumnName = "id")
			)
	
	private Collection<Papel> papeis;
	
	
	@OneToMany
	@JoinColumn(name="id_usuario")
	@JsonIgnore
	private List<Curso> cursos;
	
	
	public Usuario() {
		
	}
	
	public Usuario(String nome, String sobrenome, String email, String password, Collection<Papel> papeis) {
		
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.password = password;
		this.papeis = papeis;
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTipoPrincipalUsuario() {
		return tipoPrincipalUsuario;
	}
	public void setTipoPrincipalUsuario(String tipoPrincipalUsuario) {
		this.tipoPrincipalUsuario = tipoPrincipalUsuario;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public boolean isCodStatusUsuario() {
		return codStatusUsuario;
	}
	public void setCodStatusUsuario(boolean codStatusUsuario) {
		this.codStatusUsuario = codStatusUsuario;
	}
	public Collection<Papel> getPapeis() {
		return papeis;
	}
	public void setPapeis(Collection<Papel> papeis) {
		this.papeis = papeis;
	}
	
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	
	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
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
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
