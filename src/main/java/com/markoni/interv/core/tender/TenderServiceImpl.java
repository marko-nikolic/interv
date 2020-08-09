package com.markoni.interv.core.tender;

import com.markoni.interv.api.tender.CreateTenderCommand;
import com.markoni.interv.api.tender.Tender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class TenderServiceImpl implements TenderService {

    private final TenderRepository tenderRepository;
    private final TenderMapper tenderMapper;

    @Override
    public Tender create(CreateTenderCommand cmd) {
        TenderEntity tenderEntity = tenderMapper.map(cmd);
        Long referenceSequenceNumber = tenderRepository.getReferenceSequenceNumber();
        tenderEntity.setReferenceNumber(String.format("%07d", referenceSequenceNumber));
        tenderRepository.save(tenderEntity);
        return tenderMapper.map(tenderEntity);
    }
}
