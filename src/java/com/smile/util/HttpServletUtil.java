/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smile.util;

import com.smile.model.RequestAttributesForSong2;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author chaolee
 */
public class HttpServletUtil {
    
    public static RequestAttributesForSong2 getSong2Attributes(HttpServletRequest request) {

        String song2AttributesObject = request.getParameter("song2AttributesObject");
        RequestAttributesForSong2 song2Attr = (RequestAttributesForSong2)request.getSession().getAttribute(song2AttributesObject);
        request.getSession().removeAttribute(song2AttributesObject);
        
        return song2Attr;
    }
    
    public static void setSong2Attributes(HttpServletRequest request, RequestAttributesForSong2 song2Attr) {
        
        String song2AttributesObject = UUID.randomUUID().toString();	// make it unique
        request.getSession().setAttribute(song2AttributesObject, song2Attr);
        request.setAttribute("song2AttributesObject", song2AttributesObject);
    }
}
