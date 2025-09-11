package io.sci.nnfl.config;

import java.net.URI;

public record StoredFile(String key, URI url, long size, String contentType) {}
