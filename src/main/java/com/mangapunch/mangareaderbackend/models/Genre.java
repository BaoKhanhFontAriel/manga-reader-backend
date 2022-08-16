package com.mangapunch.mangareaderbackend.models;

public enum Genre {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    TRAGEDY("Tragedy"),
    COMEDY("Comedy"),
    FANTASY("Fantasy"),
    HORROR("Horror"),
    MYSTERY("Mystery"),
    SHOUNEN("Shounen"),
    SHOUJO("Shoujo"),
    HISTORICAL("Historical"),
    SLICE_OF_LIFE("Slice of Life");

    private Genre(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

}
