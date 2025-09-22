package io.sci.nnfl.model.dto;

import io.sci.nnfl.model.Material;

import java.util.Locale;

public class MaterialSearchResult {
    private final Material record;
    private final double score;

    public MaterialSearchResult(Material record, double score) {
        this.record = record;
        this.score = score;
    }

    public Material getRecord() {
        return record;
    }

    public double getScore() {
        return score;
    }

    public String getScoreDisplay() {
        return String.format(Locale.US, "%.3f", score);
    }

}