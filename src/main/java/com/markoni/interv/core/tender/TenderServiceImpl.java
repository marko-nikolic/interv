package com.markoni.interv.core.tender;

import com.markoni.interv.api.tender.CreateTenderCommand;
import com.markoni.interv.api.tender.Tender;
import com.markoni.interv.commons.error.NotFoundException;
import com.markoni.interv.core.tender.issuer.IssuerEntity;
import com.markoni.interv.core.tender.issuer.IssuerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
            .orElseThrow(() -> new NotFoundException(
                "Issuer %s doesn't exist. Please register issuer before proceeding.",
                cmd.getIssuerIdNumber()));
        tenderEntity.setIssuer(issuer);

        tenderRepository.save(tenderEntity);
        return tenderMapper.map(tenderEntity);
    }

    @Override
    public Optional<Tender> get(String referenceNumber) {
        return tenderRepository.findByReferenceNumber(referenceNumber).map(tenderMapper::map);
    }
}
