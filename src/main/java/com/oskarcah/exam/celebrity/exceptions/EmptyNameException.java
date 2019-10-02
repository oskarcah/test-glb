package com.oskarcah.exam.celebrity.exceptions;

public class EmptyNameException extends CelebrityException {
    public EmptyNameException() {
        super("There are one or more people with empty or null name");
    }
}
