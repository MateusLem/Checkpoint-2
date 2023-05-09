package br.com.fiap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import br.com.fiap.model.Setup;

@Component
public interface SetupRepository extends JpaRepository<Setup, Long>{

}
