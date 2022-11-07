package com.mangapunch.mangareaderbackend.utils;

import com.mangapunch.mangareaderbackend.models.GenreEnum;

public class GenreUtil {
    public static GenreEnum convertStringToGenreEnum(String value) {
        for (GenreEnum genreEnum : GenreEnum.values()) {
            if (genreEnum.getValue().equals(value)) {
                return genreEnum;
            }
        }
        // switch (value) {
        // case "Action":
        // return GenreEnum.ACTION;
        // case "Slice of Life":
        // return GenreEnum.SLICE_OF_LIFE;
        // case "Historical":
        // return GenreEnum.HISTORICAL;
        // case "Shoujo":
        // return GenreEnum.SHOUJO;
        // case "Shounen":
        // return GenreEnum.SHOUNEN;
        // case "Mystery":
        // return GenreEnum.MYSTERY;
        // case "Horror":
        // return GenreEnum.HORROR;
        // case "Fantasy":
        // return GenreEnum.FANTASY;
        // case "Comedy":
        // return GenreEnum.COMEDY;
        // case "Tragedy":
        // return GenreEnum.TRAGEDY;
        // case "Adventure":
        // return GenreEnum.ADVENTURE;
        // default:
        // break;
        // }
        return null;
    }
}
