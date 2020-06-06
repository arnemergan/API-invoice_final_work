package com.api.invoice.external;

import com.api.invoice.dto.external.InvoiceDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class OcrandNlpServiceImpl implements OcrandNlpService{
    private final String uri = "https://final-work-python.herokuapp.com/api/v1/ocr?language=";

    @Override
    public InvoiceDTO getInvoice(MultipartFile file, String lang) {
        InvoiceDTO invoiceDTO;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", new FileSystemResource(convert(file)));
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity response = new RestTemplate().postForEntity(uri + lang, entity, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            invoiceDTO = objectMapper.readValue(response.getBody().toString(), InvoiceDTO.class);
        }catch (Exception e){
            return null;
        }
        return invoiceDTO;
    }

    private static File convert(MultipartFile file)
    {
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convFile;
    }
}
