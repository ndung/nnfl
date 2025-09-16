package io.sci.nnfl.model.dto;

import io.sci.nnfl.model.MaterialRecord;

import java.util.Locale;

public class MaterialSearchResult {
    private final MaterialRecord record;
    private final double score;

    public MaterialSearchResult(MaterialRecord record, double score) {
        this.record = record;
        this.score = score;
    }

    public MaterialRecord getRecord() {
        return record;
    }

    public double getScore() {
        return score;
    }

    public String getScoreDisplay() {
        return String.format(Locale.US, "%.3f", score);
    }

}