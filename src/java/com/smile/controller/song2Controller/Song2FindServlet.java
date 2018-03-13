package com.smile.controller.song2Controller;

import com.smile.dao.Song2Table;
import com.smile.model.RequestAttributesForSong2;
import com.smile.model.Song2Data;
import com.smile.util.*;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Song2FindServlet", urlPatterns = {"/song2Controller/Song2FindServlet"})
public class Song2FindServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public Song2FindServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        System.out.println("Song2FindServlet->doGet() ....");
     
        RequestAttributesForSong2 song2Attr = HttpServletUtil.getSong2Attributes(request);
        
        Song2Data song2Tmp = new Song2Data();
        request.setAttribute("song2",song2Tmp);
        
        HttpServletUtil.setSong2Attributes(request, song2Attr);
        
        RequestDispatcher view = request.getRequestDispatcher("/song2Web/song2Find.jsp");
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // response.getWriter().append("Served at: ").append(request.getContextPath());
        
        System.out.println("Song2FindServlet->doPost() ....");
        
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
            String sType = request.getParameter("search_type");
            System.out.println("search_type= "+sType);
            if (sType != null) {
                if (sType.equalsIgnoreCase("song_no")) {
                    String song_no = request.getParameter("song_no");
                    String qCon = song2Table.findQueryConditionOnSongNo(song2Attr.getQueryCondition(),song_no);
                    pageNo = song2Table.recalculatePageNo(qCon,song2Table.songNoOrder,0,song2Attr.getByOrder(),song2Attr.getQueryCondition());
                } else if (sType.equalsIgnoreCase("vod_no")) {
                    String vod_no = request.getParameter("vod_no");
                    String qCon = song2Table.findQueryConditionOnVodNo(song2Attr.getQueryCondition(),vod_no);
                    pageNo = song2Table.recalculatePageNo(qCon,song2Table.vodNoOrder,0,song2Attr.getByOrder(),song2Attr.getQueryCondition());
                } else if (sType.equalsIgnoreCase("song_na")) {
                    String song_na  = request.getParameter("song_na");
                    String qCon = song2Table.findQueryConditionOnSongNa(song2Attr.getQueryCondition(),song_na);
                    pageNo = song2Table.recalculatePageNo(qCon,song2Table.songNaOrder,0,song2Attr.getByOrder(),song2Attr.getQueryCondition());
                } else if (sType.equalsIgnoreCase("lang_songname")) {
                    String lang_no  = request.getParameter("lang_no");
                    String song_na  = request.getParameter("song_na");
                    String qCon = song2Table.findQueryConditionOnLangSongNa(song2Attr.getQueryCondition(),lang_no,song_na);
                    pageNo = song2Table.recalculatePageNo(qCon,song2Table.langSongNaOrder,0,song2Attr.getByOrder(),song2Attr.getQueryCondition());
                } else if (sType.equalsIgnoreCase("lang_sword_songname")) {
                    String lang_no = request.getParameter("lang_no");
                    String sword   = request.getParameter("sword");
                    String song_na = request.getParameter("song_na");
                    String qCon = song2Table.findQueryConditionOnLangSwordSongNa(song2Attr.getQueryCondition(),lang_no,sword,song_na);
                    pageNo = song2Table.recalculatePageNo(qCon,song2Table.langSwordSongNaOrder,0,song2Attr.getByOrder(),song2Attr.getQueryCondition());
                } else if (sType.equalsIgnoreCase("lang_sword_songno")) {
                    System.out.println(sType);
                    String lang_no  = request.getParameter("lang_no");
                    String sword     = request.getParameter("sword");
                    String song_no   = request.getParameter("song_no");
                    String qCon = song2Table.findQueryConditionOnLangSwordSongNo(song2Attr.getQueryCondition(),lang_no,sword,song_no);
                    pageNo = song2Table.recalculatePageNo(qCon,song2Table.langSwordSongNoOrder,0,song2Attr.getByOrder(),song2Attr.getQueryCondition());
                } else if (sType.equalsIgnoreCase("singer1_name")) {
                    String sing_na1 = request.getParameter("sing_na1");
                    String qCon = song2Table.findQueryConditionOnSingNa1(song2Attr.getQueryCondition(),sing_na1);
                    pageNo = song2Table.recalculatePageNo(qCon,song2Table.singerOrder1,0,song2Attr.getByOrder(),song2Attr.getQueryCondition());
                } else if (sType.equalsIgnoreCase("singer2_name")) {
                    String sing_na2 = request.getParameter("sing_na2");
                    String qCon = song2Table.findQueryConditionOnSingNa2(song2Attr.getQueryCondition(),sing_na2);
                    pageNo = song2Table.recalculatePageNo(qCon,song2Table.singerOrder2,0,song2Attr.getByOrder(),song2Attr.getQueryCondition());
                }
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
