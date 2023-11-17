package com.zxventures.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zxventures.model.PDV;

@Repository
public interface PDVRepository extends JpaRepository<PDV, Long> {
	PDV findByDocument(String document);
}