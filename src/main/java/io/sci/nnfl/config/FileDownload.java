package io.sci.nnfl.config;

import java.io.InputStream;

public record FileDownload(InputStream stream, long size, String contentType, String filename) {}
