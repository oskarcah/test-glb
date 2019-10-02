package com.oskarcah.exam.celebrity.exceptions;

public class TooFewPeopleException extends CelebrityException {
    public TooFewPeopleException() {
        super("The problem definition requires at least three people");
    }
}
