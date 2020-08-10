package com.markoni.interv.core.tender.offer;

import com.markoni.interv.api.tender.command.CreateOfferCommand;
import com.markoni.interv.api.tender.command.UpdateOfferCommand;
import com.markoni.interv.api.tender.model.Offer;
import com.markoni.interv.api.tender.model.OfferStatus;
import com.markoni.interv.api.tender.query.OfferQuery;
import com.markoni.interv.commons.error.NotFoundException;
import com.markoni.interv.commons.page.PageResponse;
import com.markoni.interv.commons.page.PageableRequest;
import com.markoni.interv.core.tender.TenderEntity;
import com.markoni.interv.core.tender.TenderRepository;
import com.markoni.interv.core.tender.bidder.BidderEntity;
import com.markoni.interv.core.tender.bidder.BidderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class OfferServiceImpl implements OfferService {

    private final OfferMapper offerMapper;
    private final OfferRepository offerRepository;
    private final TenderRepository tenderRepository;
    private final BidderRepository bidderRepository;

    @Override
    public Offer create(CreateOfferCommand cmd) {
        OfferEntity offer = offerMapper.map(cmd);

        // Generate unique offer reference number
        offer.setReferenceNumber(UUID.randomUUID().toString());

        TenderEntity tender = tenderRepository
            .findByReferenceNumber(cmd.getTenderReferenceNumber())
            .orElseThrow(() -> new NotFoundException("Tender %s doesn't exist.", cmd.getTenderReferenceNumber()));
        offer.setTender(tender);

        // Retrieve existing bidder. Issuer must be already registered(in DB) in order to create tender offer
        BidderEntity bidder = bidderRepository
            .findByIdentificationNumber(cmd.getBidderIdNumber())
            .orElseThrow(() -> new NotFoundException("Bidder %s doesn't exist", cmd.getBidderIdNumber()));
        offer.setBidder(bidder);

        offerRepository.save(offer);
        return offerMapper.map(offer);
    }

    @Override
    public Offer update(String refNumber, UpdateOfferCommand cmd) {
        OfferEntity offer = offerRepository
            .findByReferenceNumber(refNumber)
            .orElseThrow(() -> new NotFoundException("Offer %s doesn't exist", refNumber));

        OfferValidator.validateUpdate(cmd.getStatus(), offer);

        offer.setStatus(cmd.getStatus());
        offerRepository.save(offer);

        if (cmd.getStatus() == OfferStatus.ACCEPTED) {
            offerRepository.rejectOffersExcluding(offer.getTender().getId(), offer.getId());
        }
        return offerMapper.map(offer);
    }

    @Override
    public PageResponse<List<Offer>> search(OfferQuery query, PageableRequest pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
        Page<OfferEntity> searchResult = offerRepository.search(query, pageRequest);
        List<Offer> content = searchResult.getContent().stream().map(offerMapper::map).collect(Collectors.toList());
        return new PageResponse<>(
            content,
            searchResult.getNumber() + 1,
            searchResult.getTotalPages(),
            searchResult.getNumberOfElements());
    }
}
