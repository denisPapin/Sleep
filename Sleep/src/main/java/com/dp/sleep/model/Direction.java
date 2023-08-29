package com.dp.sleep.model;

public enum Direction {
    CALM("Успокаивающая"),
    RELAX("Расслабляющая"),
    TONUS("Тонизирующая");

    private final String genreTextDisplay;

    Direction(String text) {
        this.genreTextDisplay = text;
    }

    public String getGenreTextDisplay() {
        return this.genreTextDisplay;
    }
}
