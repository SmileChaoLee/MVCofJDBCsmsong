package com.smile.controller.song2Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;

import com.smile.dao.Song2Table;
import com.smile.model.RequestAttributesForSong2;
import com.smile.util.HttpServletUtil;
import com.smile.util.JdbcMysql;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "Song2ListServlet", urlPatterns = {"/song2Controller/Song2ListServlet"})
public class Song2ListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // response.getWriter().append("Served at: ").append(request.getContextPath());

        System.out.println("Song2ListServlet->doGet() ....");

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
