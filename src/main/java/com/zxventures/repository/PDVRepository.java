package com.zxventures.repository;

import com.zxventures.model.PDV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PDVRepository extends JpaRepository<PDV, Long> {
    PDV findByDocument(String document);
}
