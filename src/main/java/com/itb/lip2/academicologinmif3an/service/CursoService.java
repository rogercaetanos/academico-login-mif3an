package com.itb.lip2.academicologinmif3an.service;

import java.util.List;

import com.itb.lip2.academicologinmif3an.model.Curso;

public interface CursoService {
	
	Curso save(Curso curso);
	List<Curso> findAll();
	Curso findById(Long id);

}
