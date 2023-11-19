package com.zxventures.controller;

import com.zxventures.dto.PDVDto;
import com.zxventures.model.PDV;
import com.zxventures.service.PDVService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/pdv")
public class PDVController {

    @Autowired
    private PDVService service;

    @GetMapping("/{id}")
    public ResponseEntity<PDVDto> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(mapToDto(
                            service.findById(id)
                    ));
        } catch (HttpServerErrorException e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<PDVDto> save(@Valid @RequestBody PDV pdv) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(mapToDto(
                            service.save(pdv)
                    ));
        } catch (HttpServerErrorException e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/partner")
    public ResponseEntity<PDVDto> searchClosestPartner(@Valid @RequestBody PDV pdv) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(mapToDto(
                            service.searchClosestPartner(pdv)
                    ));
        } catch (HttpServerErrorException e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getCause().getMessage());
        }
    }

    private static PDVDto mapToDto(PDV pdv) {
        PDVDto dto = new PDVDto();
        BeanUtils.copyProperties(pdv, dto, "id");

        return dto;
    }
}
