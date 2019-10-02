package com.oskarcah.exam.celebrity.exceptions;

public class RepeatedPersonException extends CelebrityException {
    public RepeatedPersonException(String name) {
        super("The names " + name + " are repeated in problem definition");
    }
}
