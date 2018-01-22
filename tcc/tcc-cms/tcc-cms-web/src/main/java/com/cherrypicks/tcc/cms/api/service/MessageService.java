package com.cherrypicks.tcc.cms.api.service;

import java.util.Locale;

public interface MessageService {

    String getMessage(int errorCode, Object[] args, String lang);

    String getMessage(String key, Object[] args, String lang);

    String getMessage(int errorCode, Object[] args, Locale locale);

    String getMessage(String key, Object[] args, Locale locale);
}
