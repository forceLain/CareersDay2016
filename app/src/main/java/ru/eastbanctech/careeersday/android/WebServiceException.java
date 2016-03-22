package ru.eastbanctech.careeersday.android;

public class WebServiceException extends RuntimeException {
    public WebServiceException(Throwable e) {
        super(e);
    }


    public WebServiceException(String message) {
        super(message);
    }
}
