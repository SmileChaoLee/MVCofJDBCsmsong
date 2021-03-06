/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smile.controller.song2Controller;

import static com.smile.controller.song2Controller.Song2Servlet_NotUsed.MYSQL_DUPLICATE_PK;
import com.smile.dao.Song2Table;
import com.smile.model.RequestAttributesForSong2;
import com.smile.model.Song2Data;
import com.smile.util.HttpServletUtil;
import com.smile.util.JdbcMysql;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "Song2UpdateServlet", urlPatterns = {"/song2Controller/Song2UpdateServlet"})
public class Song2UpdateServlet extends HttpServlet {

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
        System.out.println("Song2UpdateServlet->doGe"
                + "t() ....");
        
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
        
        String song_no = request.getParameter("song_no");
        Song2Data song2Tmp = song2Table.getOneRecordSong2bySongno(song_no,song2Attr.getByOrder());
        
        request.setAttribute("song2", song2Tmp);
        
        song2Attr.setOrgSong_no(song_no);
        HttpServletUtil.setSong2Attributes(request, song2Attr);
        
        RequestDispatcher view = request.getRequestDispatcher("/song2Web/song2Update.jsp");
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
        
        System.out.println("Song2UpdateServlet->doPost() ....");
        
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
        
        int pageNo = song2Attr.getPageNo();
        String submitButton = request.getParameter("submitButton");
        if (submitButton.equalsIgnoreCase("SUBMIT")) {
            Song2Data song2 = song2Table.getDataFromJspView(request);
            int result = -1;
            if (song2 != null) {
                if (song2.getSong_no().isEmpty()) {
                    request.setAttribute("error", "Song NO. can not be empty !!");
                } else {
                    result = song2Table.updateOneRecordSong2Table(song2Attr.getOrgSong_no(),song2) ;
                    if (result == 0) {
                        // successfully updated
                        request.setAttribute("error", "");
                    } else {
                        // result != 1, failed to inserted
                        if (result == MYSQL_DUPLICATE_PK) {
                            request.setAttribute("error","Song No. can not be duplicated !!");
                        } else {
                            request.setAttribute("error","May be some problem with database !!");
                        }
                    }
                }
            } else {
                request.setAttribute("error","Song No. can not be NULL !!");
            }
            if (result != 0) {
                // failed to update
                System.out.println("Failed to update.");
                request.setAttribute("song2", song2);
                // keep modifying it
                
                HttpServletUtil.setSong2Attributes(request, song2Attr);
                
                RequestDispatcher view = request.getRequestDispatcher("/song2Web/song2Update.jsp");
                view.forward(request, response);
                
                return;
            } else {
                // succeeded to update
                System.out.println("Succeeded to Update.");
                String qCon = song2Table.findQueryConditionOnSongNo(song2Attr.getQueryCondition(),song2.getSong_no());
                pageNo = song2Table.recalculatePageNo(qCon,"song2.song_no",0,song2Attr.getByOrder(),song2Attr.getQueryCondition());
            }
        }
        //else if (submitButton.equalsIgnoreCase("BACK")) {}
        
        request.setAttribute("songs", song2Table.getOnePageOfSong2Table(pageNo,song2Attr.getQueryCondition(),song2Attr.getByOrder()));
        
        song2Attr.setPageNo(pageNo);
        HttpServletUtil.setSong2Attributes(request, song2Attr);
        
        RequestDispatcher view = request.getRequestDispatcher("/song2Web/song2List.jsp");
        view.forward(request, response);
    }

}
