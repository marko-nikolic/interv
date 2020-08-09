package com.markoni.interv.core.tender;

import com.markoni.interv.api.tender.CreateTenderCommand;
import com.markoni.interv.api.tender.Tender;

public interface TenderService {
    Tender create(CreateTenderCommand cmd);
}
