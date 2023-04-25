package com.inventory.management.validation;

import com.inventory.management.annotation.Validate;
import com.inventory.management.vo.problem.ValidationBuilder;
import com.inventory.management.vo.problem.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

@Validate
@RequiredArgsConstructor
public class AttachmentValidation {

    public static String PICTURE_TYPE = "picture";
    List<String> supportedTypes = List.of("image/png", "image/jpeg");
    Map<String, List<String>> typedCategory = Map.of(
            PICTURE_TYPE, supportedTypes
    );

    public void validateAttachment(String value) {
        validateAttachment(value, PICTURE_TYPE);
    }

    public void validateAttachment(String value, String type) {
        ValidationBuilder builder = new ValidationBuilder();
        if (!StringUtils.hasText(value))
            builder.addError("validation.error.id.null");
        else {
            byte[] byteArray = contentBytesIsValidated(value);
            if (Objects.isNull(byteArray))
                builder.addError("validation.error.content.attachment.decode");
            String[] types = extractFileType(byteArray);
            if (builder.isClean() && Objects.isNull(types))
                builder.addError("validation.error.content.attachment.fileType");
            if (builder.isClean() && !getType(type).contains(Objects.requireNonNull(types)[0]))
                builder.addError("validation.error.content.attachment.type");
        }
        if (!CollectionUtils.isEmpty(builder.build())) throw new ValidationException(builder.build());
    }

    List<String> getType(String type) {
        if (StringUtils.hasText(type))
            return typedCategory.keySet().stream().filter(key -> key.equals(type))
                    .map(v -> typedCategory.get(v)).flatMap(Collection::stream).collect(Collectors.toList());
        return typedCategory.keySet().stream().map(v -> typedCategory.get(v)).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public byte[] contentBytesIsValidated(String base64) {
        try {
            String[] parts = base64.split(",");
            String imageString = parts[1];
            return Base64.getDecoder().decode(imageString);
        } catch (Exception ex) {
            return null;
        }
    }

    private String[] extractFileType(byte[] imageByteArray) {
        InputStream is = new ByteArrayInputStream(imageByteArray);
        String mimeType = null;
        String fileExtension = null;
        try {
            mimeType = URLConnection.guessContentTypeFromStream(is);
            String[] tokens = mimeType.split("/");
            fileExtension = tokens[1];
        } catch (Exception e) {
            return null;
        }
        return new String[]{mimeType, fileExtension};
    }

}
