package com.api.invoice.services;
import com.api.invoice.models.Invoice;
import com.api.invoice.dto.request.InvoiceDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
import java.io.IOException;

public interface InvoiceService {
    public Invoice getInvoice(String id, String token);
    public Page<Invoice> getInvoices(Pageable pageable, String token);
    public Invoice uploadInvoice(MultipartFile image, String lang, String token) throws IOException;
    public Invoice updateInvoice(String id, InvoiceDTO invoice, String token);
    public void deleteInvoice(String id, String token);
    public Invoice getInvoiceBySearchNumber(String number, String token);
    public Resource getImage(String token, String filename);
    public Page<Invoice> getInvoicesOnCategory(Pageable pageable, String token, String name);
    public Page<Invoice> getInvoicesOnUsername(Pageable pageable, String token, String username);
}
