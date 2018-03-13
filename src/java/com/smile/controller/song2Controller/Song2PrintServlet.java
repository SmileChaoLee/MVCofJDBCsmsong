/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smile.controller.song2Controller;

import com.smile.dao.Song2Table;
import com.smile.model.RequestAttributesForSong2;
import com.smile.model.Song2Data;
import com.smile.util.HttpServletUtil;
import com.smile.util.JdbcMysql;
import java.io.IOException;
import java.sql.Connection;
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
@WebServlet(name = "Song2PrintServlet", urlPatterns = {"/song2Controller/Song2PrintServlet"})
public class Song2PrintServlet extends HttpServlet {
    
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
        
        System.out.println("Song2PrintServlet->doGet() ....");
     
        RequestAttributesForSong2 song2Attr = HttpServletUtil.getSong2Attributes(request);
        
        Song2Data song2Tmp = new Song2Data();
        request.setAttribute("song2",song2Tmp);
        
        HttpServletUtil.setSong2Attributes(request, song2Attr);
        
        RequestDispatcher view = request.getRequestDispatcher("/song2Web/song2Print.jsp");
        view.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        /*
        PrintWriter prtw = response.getWriter();
        prtw.println("<!DOCTYPE html>");
        prtw.println("<html>");
        prtw.println("<body>");
        prtw.println("<p><h1>Song2PrintServlet (POST method) !!</h1></p>");
        prtw.println("</body>");
        prtw.println("</html>");
        prtw.close();
        */
        
        System.out.println("Song2PrintServlet->doPost() ....");

        RequestAttributesForSong2 song2Attr = HttpServletUtil.getSong2Attributes(request);

        // do not create a new database connection if connection does not exist for this session
        Connection dbConn = JdbcMysql.getStoredConnection(request,false);
        if (dbConn == null) {
            // go to login page (index.jsp)
            RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
            view.forward(request, response);
            return;
        }
        Song2Table song2Table = new Song2Table(dbConn);
        //

        request.setAttribute("songs", song2Table.getOnePageOfSong2Table(song2Attr.getPageNo(),song2Attr.getQueryCondition(),song2Attr.getByOrder()));

        HttpServletUtil.setSong2Attributes(request, song2Attr);

        RequestDispatcher view = request.getRequestDispatcher("/song2Web/song2List.jsp");
        view.forward(request, response);
    }

}
