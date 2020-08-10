package com.markoni.interv.api.tender;

import com.markoni.interv.api.tender.command.CreateOfferCommand;
import com.markoni.interv.api.tender.command.UpdateOfferCommand;
import com.markoni.interv.api.tender.model.Offer;
import com.markoni.interv.api.tender.query.OfferQuery;
import com.markoni.interv.commons.page.PageResponse;
import com.markoni.interv.commons.page.PageableRequest;
import com.markoni.interv.core.tender.offer.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/offers")
@RequiredArgsConstructor
public class OfferEndpoint {

    private final OfferService offerService;

    /**
     * Service for creating new offer. Tender and bidder must already be created
     *
     * @param cmd Command object for creating new offer
     * @return newly created offer with SUBMITTED STATUS
     */
    @PostMapping
    public ResponseEntity<Offer> create(@RequestBody CreateOfferCommand cmd) {
        return ResponseEntity.ok(offerService.create(cmd));
    }

    /**
     * Accept/Reject offer based on offer reference number. Already accepted/rejected offers cannot change status
     * @param refNumber Unique reference number of the offer
     * @param cmd Command object containing status
     * @return Accepted/Rejected offer
     */
    @PutMapping("{refNumber}")
    public ResponseEntity<Offer> update(@PathVariable String refNumber, @RequestBody UpdateOfferCommand cmd) {
        return ResponseEntity.ok(offerService.update(refNumber, cmd));
    }

    /**
     *
     * Search existing offers (all, for tender, for bidder, and for both)
     *
     * @param query Query parameters used for filtering offers based on tender number or bidder identification number
     * @param pageRequest Query parameters used for pagination
     * @return Page response filtered by provided parameters
     */
    @GetMapping(path = "search")
    public ResponseEntity<PageResponse<List<Offer>>> create(OfferQuery query, PageableRequest pageRequest) {
        return ResponseEntity.ok(offerService.search(query, pageRequest));
    }
}
