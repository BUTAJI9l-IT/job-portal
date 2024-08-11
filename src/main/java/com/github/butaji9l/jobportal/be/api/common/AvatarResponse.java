package com.github.butaji9l.jobportal.be.api.common;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

public record AvatarResponse(Resource resource, MediaType mediaType) {

}
