package com.pedrodavi.nabbreviate.controller;

import com.pedrodavi.nabbreviate.model.dto.NameResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pedrodavi.nabbreviate.utils.AbbreviateUtils.shortName;

@RestController
public class AbbreviateController {

    private static final Logger logger = LoggerFactory.getLogger(AbbreviateController.class);

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping("/")
    public ResponseEntity<NameResponseDTO> abbreviate(@RequestParam(value = "name", required = true) String name) {
        logger.info("Incoming request at {} for request /?name=", name);
        String shortedName = shortName(name);
        return ResponseEntity.ok(new NameResponseDTO(shortedName));
    }

}
