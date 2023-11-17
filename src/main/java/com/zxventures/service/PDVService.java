package com.zxventures.service;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.zxventures.model.PDV;
import com.zxventures.repository.PDVRepository;
import com.zxventures.vo.PDVvo;

@Service
public class PDVService {
    private static Logger LOGGER = LoggerFactory.getLogger(PDVService.class);

    @Autowired
    private PDVRepository repository;

    public PDVvo findById(Long id) {
        LOGGER.debug("calling findById method in PDVService:");
        try {
            //TODO: REFAZER
            PDV searchedPdv = null;

            if (searchedPdv == null) {
                throw new EmptyResultDataAccessException("no register found!", 1);
            }

            PDVvo pdvVO = new PDVvo();
            return this.buildVO(searchedPdv, pdvVO);
        } catch (HttpClientErrorException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
        }
    }

    public PDVvo save(PDV pdv) {
        LOGGER.debug("calling save method in CategoryService:");
        try {
            this.verifyDocument(pdv.getDocument());

            PDVvo pdvVO = new PDVvo();

            return this.buildVO(repository.save(pdv), pdvVO);
        } catch (HttpClientErrorException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
        }
    }

    public PDVvo searchClosestPartner(PDV pdv) {
        LOGGER.debug("calling searchClosestPartner method in CategoryService:");
        try {
            List<PDV> searchedPdv = this.findAll();

            Double pdvDistance = 0.0;
            Double lessDistant = Double.MAX_VALUE;

            PDV closestPDV = null;

            for (PDV pdvItem : searchedPdv) {
                if (!(pdv.getAddress().equalsExact(pdvItem.getAddress()))) {
                    pdvDistance = pdvItem.getDistance(pdv.getAddress().getY(), pdv.getAddress().getX());
                    if (pdvDistance < lessDistant) {
                        lessDistant = pdvDistance;
                        closestPDV = pdvItem;
                    }
                }
            }

            if (closestPDV == null) {
                throw new RuntimeException("no nearby PDV was found.");
            }
            PDVvo pdvVO = new PDVvo();

            return this.buildVO(closestPDV, pdvVO);
        } catch (HttpClientErrorException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
        } catch (HttpServerErrorException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            LOGGER.error(ExceptionUtils.getRootCauseMessage(e));
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
        }

    }

    private List<PDV> findAll() {
        LOGGER.debug("calling findAll method in PDVService:");
        List<PDV> searchedPdv = repository.findAll();

        if (searchedPdv == null) {
            throw new EmptyResultDataAccessException("no register found!", 1);
        }

        return searchedPdv;
    }

    private void verifyDocument(String document) {
        LOGGER.debug("calling verifyDocument method in PDVService:");
        PDV pdvExists = repository.findByDocument(document);

        if (pdvExists != null) {
            throw new RuntimeException("document has already been registered.");
        }

    }

    private PDVvo buildVO(PDV pdv, PDVvo pdvVO) {
        BeanUtils.copyProperties(pdv, pdvVO);

        return pdvVO;
    }

}
