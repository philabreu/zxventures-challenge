package com.zxventures.endpoint;

import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.zxventures.model.PDV;
import com.zxventures.service.PDVService;
import com.zxventures.vo.PDVvo;

@RestController
@RequestMapping("/pdv")
public class PDVEndpoint {

	private static Logger LOGGER = LoggerFactory.getLogger(PDVEndpoint.class);

	@Autowired
	private PDVService service;

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> findById(@PathVariable Long id) {
		LOGGER.debug("calling findById method in PDVEndpoint:");
		try {
			PDVvo pdvSearched = service.findById(id);

			return ResponseEntity.ok().body(pdvSearched);
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

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> save(@Valid @RequestBody PDV pdv) {
		LOGGER.debug("calling save method in PDVEndpoint:");
		try {
			PDVvo pdvCreated = service.save(pdv);

			return ResponseEntity.status(HttpStatus.CREATED).body(pdvCreated);
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

	@GetMapping("/partner")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> searchClosestPartner(@Valid @RequestBody PDV pdv) {
		LOGGER.debug("calling searchClosestPartner method in PDVEndpoint:");
		try {
			PDVvo partner = service.searchClosestPartner(pdv);

			return ResponseEntity.ok().body(partner);
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

}
