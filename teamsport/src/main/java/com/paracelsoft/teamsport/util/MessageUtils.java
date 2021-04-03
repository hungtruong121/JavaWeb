package com.paracelsoft.teamsport.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {

    @Autowired
    private MessageSource messageResource;

    public String getMessage(String id, String... params) {
        List<String> lstParam = new ArrayList<String>();

        if (params != null) {
            for (String param : params) {
                lstParam.add(messageResource.getMessage("label." + param, new Object[] { param },
                        LocaleContextHolder.getLocale()));
            }
        }
        return messageResource.getMessage("msg." + id, lstParam.toArray(), LocaleContextHolder.getLocale());
    }
    
    public JSONObject getListNotification(String id, String... params) throws NoSuchMessageException, JSONException {
    	JSONObject messages = new JSONObject();
    	
    	List<Locale> locales = Arrays.asList(new Locale("en"), new Locale("vi"), new Locale("jp"));
    	
    	for(Locale lang: locales) {
    		List<String> lstParam = new ArrayList<String>();
            if (params != null) {
                for (String param : params) {
                	lstParam.add(param);
                }
            }
            messages.put(lang.getLanguage(), messageResource.getMessage("ntf." + id, lstParam.toArray(), lang));
    	}
        return messages;
    }
    
    public String getNotification(String id, String... params) throws NoSuchMessageException {
    	
		List<String> lstParam = new ArrayList<String>();
        if (params != null) {
            for (String param : params) {
            	lstParam.add(param);
            }
        }
        
        return messageResource.getMessage("ntf." + id, lstParam.toArray(), LocaleContextHolder.getLocale());
        
    }

}
