package com.inventory.management.service.impl;

import com.inventory.management.domain.Attachment;
import com.inventory.management.repository.AttachmentRepository;
import com.inventory.management.service.AttachmentService;
import com.inventory.management.vo.problem.CustomApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Base64;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    @Value("${api.base-path:/api/v1}")
    private String baseUrl;

    @Override
    public String saveOrUpdateFile(String url, String content) {
        Long id = extractIdFromUrl(url);
        byte[] bytes = getContentBytes(content);
        String[] type = extractFileType(bytes);
        Attachment attachment = new Attachment();
        attachment.setMime(type[0]);
        attachment.setType(type[1]);
        attachment.setData(compressImage(bytes));
        if(id != null) {
            attachment.setId(id);
            attachment.setName(attachmentRepository.findAttachmentById(id));
        } else attachment.setName(UUID.randomUUID().toString());
        id = attachmentRepository.save(attachment).getId();
        return decodeIdToUrl(id);
    }

    @Override
    public Attachment getAttachment(Long id) {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new CustomApiException("custom.error.entity.not.exist", new Object[0]));
        attachment.setData( decompressImage(attachment.getData()));
        return attachment;
    }

    Long extractIdFromUrl(String url) {
        if(!StringUtils.hasText(url)) return null;
        String factor = baseUrl+"/attachment/";
        int beg = url.indexOf(factor) + factor.length();
        return Long.parseLong(url.substring(beg));
    }

    String decodeIdToUrl(Long id) {
        return baseUrl+"/attachment/"+id;
    }

    private String[] extractFileType(byte[] imageByteArray) {
        InputStream is = new ByteArrayInputStream(imageByteArray);
        String mimeType = null;
        String fileExtension = null;
        try {
            mimeType = URLConnection.guessContentTypeFromStream(is);
            String[] tokens = mimeType.split("/");
            fileExtension = tokens[1];
        } catch (IOException e){
            log.error("An error occurred", e);
        }
        return new String[]{mimeType, fileExtension};
    }

    public byte[] getContentBytes(String base64) {
        String[] parts = base64.split(",");
        String imageString = parts[1];
        return Base64.getDecoder().decode(imageString);
    }

    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception e) {
            log.error("An error occurred", e);
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception exception) {
            log.error("An error occurred", exception);
        }
        return outputStream.toByteArray();
    }

}
