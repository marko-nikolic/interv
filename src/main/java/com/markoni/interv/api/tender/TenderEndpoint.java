package com.markoni.interv.api.tender;

import com.markoni.interv.core.tender.TenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "tenders")
@RequiredArgsConstructor
public class TenderEndpoint {

    private final TenderService tenderService;

    @PostMapping
    public ResponseEntity<Tender> create(@RequestBody @Valid CreateTenderCommand cmd) {
        return ResponseEntity.ok(tenderService.create(cmd));
    }

}
