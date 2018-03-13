/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smile.controller.song2Controller;

import com.smile.model.RequestAttributesForSong2;
import com.smile.util.HttpServletUtil;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author chaolee
 */
@WebServlet(name = "Song2QuitServlet", urlPatterns = {"/song2Controller/Song2QuitServlet"})
public class Song2QuitServlet extends HttpServlet {
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Song2QuitServlet->doGet() ....");
        
        // get attributes
        RequestAttributesForSong2 song2Attr = HttpServletUtil.getSong2Attributes(request);
        
        // set attributes
        HttpServletUtil.setSong2Attributes(request, song2Attr);
        
        RequestDispatcher view = request.getRequestDispatcher("/songMenu.jsp");
        view.forward(request, response);
    }

}
