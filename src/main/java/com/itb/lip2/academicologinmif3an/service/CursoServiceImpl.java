package com.itb.lip2.academicologinmif3an.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itb.lip2.academicologinmif3an.model.Curso;
import com.itb.lip2.academicologinmif3an.repository.CursoRepository;

@Service
public class CursoServiceImpl implements CursoService {
	
	@Autowired
	private CursoRepository cursoRepository;
	

	@Override
	public Curso save(Curso curso) {
		
		return cursoRepository.save(curso);
	}

	@Override
	public List<Curso> findAll() {
		
		return cursoRepository.findAll();
	}

	@Override
	public Curso findById(Long id) {
		
		return cursoRepository.findById(id).get();
		
	}
	
	

}
