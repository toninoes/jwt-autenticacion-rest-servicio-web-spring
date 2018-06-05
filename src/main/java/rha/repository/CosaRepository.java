package rha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import rha.model.Cosa;


@RepositoryRestResource(collectionResourceRel = "cosas", path = "cosas")
public interface CosaRepository extends JpaRepository<Cosa, Long>{
	
}
