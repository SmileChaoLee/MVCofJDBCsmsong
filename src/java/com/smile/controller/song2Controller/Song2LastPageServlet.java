/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smile.controller.song2Controller;

import com.smile.dao.Song2Table;
import com.smile.model.RequestAttributesForSong2;
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
@WebServlet(name = "Song2LastPageServlet", urlPatterns = {"/song2Controller/Song2LastPageServlet"})
public class Song2LastPageServlet extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        
        System.out.println("Song2LastPageServlet->doGet() ....");
        
        RequestAttributesForSong2 song2Attr = HttpServletUtil.getSong2Attributes(request);
        
        Connection dbConn = JdbcMysql.getStoredConnection(request,false);
        if (dbConn == null) {
                // go to login page (index.jsp)
                RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
                view.forward(request, response);
                return;
        }
        Song2Table song2Table = new Song2Table(dbConn);
        //
        
        // get the last page no
        int pageNo = song2Table.getTotalPageOfQuery(song2Attr.getQueryCondition());
        request.setAttribute("songs", song2Table.getOnePageOfSong2Table(pageNo,song2Attr.getQueryCondition(),song2Attr.getByOrder()));
        
        song2Attr.setPageNo(pageNo);
        HttpServletUtil.setSong2Attributes(request, song2Attr);

        RequestDispatcher view = request.getRequestDispatcher("/song2Web/song2List.jsp");
        view.forward(request, response);
    }

}
