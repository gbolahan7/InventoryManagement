package com.inventory.management.controller;

import com.inventory.management.annotation.ResponseWrapper;
import com.inventory.management.auth.Privilege;
import com.inventory.management.domain.Attachment;
import com.inventory.management.repository.AttachmentRepository;
import com.inventory.management.service.AttachmentService;
import com.inventory.management.service.ProductService;
import com.inventory.management.vo.dto.ProductAuditDto;
import com.inventory.management.vo.dto.ProductDto;
import com.inventory.management.vo.dto.ProductRequestDto;
import com.inventory.management.vo.request.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("${api.base-path:/api/v1}"+"/attachment")
@ResponseWrapper
public class AttachmentController {

    private final AttachmentService attachmentService;

    @GetMapping("/{id}")
    public ResponseEntity<?>  getImageByName(@PathVariable("id") Long id){
        Attachment attachment = attachmentService.getAttachment(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(attachment.getMime()))
                .body(attachment.getData());
    }


}
