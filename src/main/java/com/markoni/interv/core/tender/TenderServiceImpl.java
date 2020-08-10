package com.markoni.interv.core.tender;

import com.markoni.interv.api.tender.command.CreateTenderCommand;
import com.markoni.interv.api.tender.model.Tender;
import com.markoni.interv.api.tender.query.TenderQuery;
import com.markoni.interv.commons.error.NotFoundException;
import com.markoni.interv.commons.page.PageResponse;
import com.markoni.interv.commons.page.PageableRequest;
import com.markoni.interv.core.tender.issuer.IssuerEntity;
import com.markoni.interv.core.tender.issuer.IssuerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class TenderServiceImpl implements TenderService {

    private final TenderMapper tenderMapper;
    private final TenderRepository tenderRepository;
    private final IssuerRepository issuerRepository;

    @Override
    public Tender create(CreateTenderCommand cmd) {
        TenderEntity tenderEntity = tenderMapper.map(cmd);

        // Generate unique tender reference based on db sequence
        Long referenceSequenceNumber = tenderRepository.getReferenceSequenceNumber();
        tenderEntity.setReferenceNumber(String.format("%07d", referenceSequenceNumber));

        // Retrieve existing issuer. Issuer must be already registered(in DB) in order to create Tender
        IssuerEntity issuer = issuerRepository
            .findByIdentificationNumber(cmd.getIssuerIdNumber())
            .orElseThrow(() -> new NotFoundException("Issuer %s doesn't exist.", cmd.getIssuerIdNumber()));
        tenderEntity.setIssuer(issuer);

        tenderRepository.save(tenderEntity);
        return tenderMapper.map(tenderEntity);
    }

    @Override
    public Optional<Tender> get(String referenceNumber) {
        return tenderRepository.findByReferenceNumber(referenceNumber).map(tenderMapper::map);
    }

    @Override
    public PageResponse<Tender> search(TenderQuery query, PageableRequest pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());
        Page<TenderEntity> searchResult = tenderRepository.search(query, pageRequest);
        List<Tender> content = searchResult.getContent().stream().map(tenderMapper::map).collect(Collectors.toList());
        return new PageResponse<>(
            content,
            searchResult.getNumber() + 1,
            searchResult.getTotalPages(),
            searchResult.getNumberOfElements());
    }
}
