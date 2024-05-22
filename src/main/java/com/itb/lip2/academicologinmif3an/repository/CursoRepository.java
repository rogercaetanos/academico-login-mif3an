package com.itb.lip2.academicologinmif3an.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itb.lip2.academicologinmif3an.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

}
