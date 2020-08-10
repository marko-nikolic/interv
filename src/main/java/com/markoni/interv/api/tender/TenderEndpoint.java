package com.markoni.interv.api.tender;

import com.markoni.interv.api.tender.command.CreateTenderCommand;
import com.markoni.interv.api.tender.model.Tender;
import com.markoni.interv.api.tender.query.TenderQuery;
import com.markoni.interv.commons.error.NotFoundException;
import com.markoni.interv.commons.page.PageResponse;
import com.markoni.interv.commons.page.PageableRequest;
import com.markoni.interv.core.tender.TenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/tenders")
@RequiredArgsConstructor
public class TenderEndpoint {

    private final TenderService tenderService;

    /**
     * Create new tender based on the provided command object
     * Tender will receive current date as creation date, and new newly generated reference number
     * @param cmd Command object containing description and deadline
     * @return Created tender object with uniquely generated reference number
     */
    @PostMapping
    public ResponseEntity<Tender> create(@RequestBody @Valid CreateTenderCommand cmd) {
        return ResponseEntity.ok(tenderService.create(cmd));
    }

    /**
     * Retrieve Tender based on it's unique reference number
     * @param refNo Tender reference number
     * @return Existing tender or empty if one doesn't exist
     */
    @GetMapping(path = "{refNo}")
    public ResponseEntity<Tender> create(@PathVariable String refNo) {
        return tenderService
            .get(refNo)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new NotFoundException("Tender with reference number %s doesn't exist", refNo));
    }

    /**
     *
     * Search existing tender (all, or just for the issuer)
     *
     * @param query Query parameter used for filtering tenders based on issuer identification number
     * @param pageRequest Query parameters used for pagination
     * @return Page response, filtered by provided parameters
     */
    @GetMapping(path = "search")
    public ResponseEntity<PageResponse<Tender>> create(TenderQuery query, PageableRequest pageRequest) {
        return ResponseEntity.ok(tenderService.search(query, pageRequest));
    }

}
