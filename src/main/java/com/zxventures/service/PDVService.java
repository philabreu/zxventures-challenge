package com.zxventures.service;

import com.zxventures.model.PDV;
import com.zxventures.repository.PDVRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class PDVService {

    @Autowired
    private PDVRepository repository;

    public PDV findById(long id) {
        try {
            return repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No register found."));
        } catch (HttpServerErrorException e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
        }
    }

    public PDV save(PDV pdv) {
        try {
            verifyDocument(pdv.getDocument());

            return repository.save(pdv);
        } catch (HttpServerErrorException e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
        }

    }

    public PDV searchClosestPartner(PDV pdv) {
        try {
            List<PDV> pdvList = repository.findAll();

            Double pdvDistance = 0.0;
            Double lessDistant = Double.MAX_VALUE;

            PDV closestPDV = null;

            for (PDV eachPdv : pdvList) {
                if (!(pdv.getAddress().equalsExact(eachPdv.getAddress()))) {
                    pdvDistance = eachPdv.getDistance(pdv.getAddress().getY(), pdv.getAddress().getX());
                    if (pdvDistance < lessDistant) {
                        lessDistant = pdvDistance;
                        closestPDV = eachPdv;
                    }
                }
            }

            if (isNull(closestPDV)) {
                throw new RuntimeException("no nearby PDV was found.");
            }

            return closestPDV;
        } catch (HttpServerErrorException e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
        }
    }

    private void verifyDocument(String document) {
        PDV exists = repository.findByDocument(document);

        if (nonNull(exists)) {
            throw new RuntimeException("document has already been registered.");
        }
    }
}