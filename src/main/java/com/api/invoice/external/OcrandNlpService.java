package com.api.invoice.external;

import com.api.invoice.dto.external.InvoiceDTO;
import org.springframework.web.multipart.MultipartFile;

public interface OcrandNlpService {
    public InvoiceDTO getInvoice(MultipartFile file, String lang);
}
