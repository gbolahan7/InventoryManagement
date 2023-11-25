package com.inventory.management.service;

import com.inventory.management.domain.Attachment;
import com.inventory.management.vo.dto.ProductAuditDto;
import com.inventory.management.vo.dto.ProductDto;
import com.inventory.management.vo.dto.ProductRequestDto;
import com.inventory.management.vo.request.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

public interface AttachmentService {


    String saveOrUpdateFile(String url, String content);
    Attachment getAttachment(Long id);

}
