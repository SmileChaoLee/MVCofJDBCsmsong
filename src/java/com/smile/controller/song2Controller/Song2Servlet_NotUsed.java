package com.smile.controller.song2Controller;

import com.smile.dao.*;
import com.smile.model.*;
import com.smile.util.JdbcMysql;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Song2Servlet_NotUsed extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final int MYSQL_DUPLICATE_PK = 1062;
	
	private final String addStr       = "/song2Update.jsp";
	private final String updateStr    = "/song2Update.jsp";
	private final String deleteStr    = "/song2Update.jsp";
	private final String findStr      = "/song2Find.jsp";
	private final String listStr      = "/song2List.jsp";
	// private final String printStr     = "/song2print.jsp";
	private final String firstpageStr = "/song2List.jsp";
	private final String nextpageStr  = "/song2List.jsp";
	private final String prevpageStr  = "/song2List.jsp";
	private final String lastpageStr  = "/song2List.jsp";
	private final String mainStr = "/songMenu.jsp";
       
    public Song2Servlet_NotUsed() {
        super();
        System.out.println("Song2Servlet constructed !!");
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("Song2Servlet init() !!");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");     //  to display Chinese words correctly
        response.setCharacterEncoding("utf-8");    // 
        response.setContentType("text/html");      //  

        System.out.println("Song2Servlet.html->doGet() ....");

        String forwardStr = new String("");
        String song2AttributesObject = request.getParameter("song2AttributesObject");
        RequestAttributesForSong2 song2Attr = (RequestAttributesForSong2)request.getSession().getAttribute(song2AttributesObject);
        request.getSession().removeAttribute(song2AttributesObject);

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

        if (song2Attr.getActFunction().equalsIgnoreCase("ADD")) {
            forwardStr = addStr;
            Song2Data song2Tmp = new Song2Data(); 
            song2Attr.setAccessMethod("ADD");
            request.setAttribute("song2Attributes", song2Attr);
            RequestDispatcher view = request.getRequestDispatcher(forwardStr);
            view.forward(request, response);
        } else if (song2Attr.getActFunction().equalsIgnoreCase("UPDATE")) {
            forwardStr = updateStr;
            String song_no = request.getParameter("song_no");
            Song2Data song2Tmp = song2Table.getOneRecordSong2bySongno(song_no,song2Attr.getByOrder());
            request.setAttribute("song2", song2Tmp);
            song2Attr.setOrgSong_no(song_no);
            song2Attr.setAccessMethod("UPDATE");
            request.setAttribute("song2Attributes", song2Attr);
            RequestDispatcher view = request.getRequestDispatcher(forwardStr);
            view.forward(request, response);
        } else if (song2Attr.getActFunction().equalsIgnoreCase("DELETE")) {
            forwardStr = deleteStr;
            String song_no = request.getParameter("song_no");
            Song2Data song2Tmp = song2Table.getOneRecordSong2bySongno(song_no,song2Attr.getByOrder());
            request.setAttribute("song2", song2Tmp);
            song2Attr.setOrgSong_no(song_no);
            song2Attr.setAccessMethod("DELETE");
            request.setAttribute("song2Attributes", song2Attr);
            RequestDispatcher view = request.getRequestDispatcher(forwardStr);
            view.forward(request, response);
        } else if (song2Attr.getActFunction().equalsIgnoreCase("FIND")) {
            forwardStr = findStr;
            Song2Data song2Tmp = new Song2Data();
            request.setAttribute("song2",song2Tmp);
            song2Attr.setOrgSong_no("");
            song2Attr.setAccessMethod("FIND");
            request.setAttribute("song2Attributes", song2Attr);
            RequestDispatcher view = request.getRequestDispatcher(forwardStr);
            view.forward(request, response);
        } else if (song2Attr.getActFunction().equalsIgnoreCase("LIST")) {
            forwardStr = listStr;
            request.setAttribute("songs", song2Table.getOnePageOfSong2Table(song2Attr.getPageNo(),song2Attr.getQueryCondition(),song2Attr.getByOrder()));
            RequestDispatcher view = request.getRequestDispatcher(forwardStr);
            view.forward(request, response);
        } else if (song2Attr.getActFunction().equalsIgnoreCase("PREVPAGE")) {
            forwardStr = prevpageStr;
            int pageNo = song2Attr.getPageNo() - 1;
            if (pageNo<1)
            {
                pageNo = 1;
            }
            request.setAttribute("songs", song2Table.getOnePageOfSong2Table(pageNo,song2Attr.getQueryCondition(),song2Attr.getByOrder()));
            song2Attr.setPageNo(pageNo);
            request.setAttribute("song2Attributes", song2Attr);
            RequestDispatcher view = request.getRequestDispatcher(forwardStr);
            view.forward(request, response);
        } else if (song2Attr.getActFunction().equalsIgnoreCase("NEXTPAGE")) {
            forwardStr = nextpageStr;
            int pageNo = song2Attr.getPageNo() + 1;  
            int totalPage = song2Table.getTotalPageOfQuery(song2Attr.getQueryCondition());
            if (pageNo>totalPage) {
                pageNo = totalPage;
            }
            request.setAttribute("songs", song2Table.getOnePageOfSong2Table(pageNo,song2Attr.getQueryCondition(),song2Attr.getByOrder()));
            song2Attr.setPageNo(pageNo);
            request.setAttribute("song2Attributes", song2Attr);
            RequestDispatcher view = request.getRequestDispatcher(forwardStr);
            view.forward(request, response);
        } else if (song2Attr.getActFunction().equalsIgnoreCase("FIRSTPAGE")) {
            forwardStr = firstpageStr;
            int pageNo = 1;
            request.setAttribute("songs", song2Table.getOnePageOfSong2Table(pageNo,song2Attr.getQueryCondition(),song2Attr.getByOrder()));
            song2Attr.setPageNo(pageNo);
            request.setAttribute("song2Attributes", song2Attr);
            RequestDispatcher view = request.getRequestDispatcher(forwardStr);
            view.forward(request, response);
        } else if (song2Attr.getActFunction().equalsIgnoreCase("LASTPAGE")) {
            forwardStr = lastpageStr;
            int pageNo = song2Table.getTotalPageOfQuery(song2Attr.getQueryCondition());
            request.setAttribute("songs", song2Table.getOnePageOfSong2Table(pageNo,song2Attr.getQueryCondition(),song2Attr.getByOrder()));
            song2Attr.setPageNo(pageNo);
            request.setAttribute("song2Attributes", song2Attr);
            RequestDispatcher view = request.getRequestDispatcher(forwardStr);
            view.forward(request, response);
        } else if (song2Attr.getActFunction().equalsIgnoreCase("QUIT")) {
            forwardStr = mainStr;
            RequestDispatcher view = request.getRequestDispatcher(forwardStr);
            view.forward(request, response);
        } else {
            PrintWriter prtw = response.getWriter();
            // OutputStreamWriter wr = new OutputStreamWriter( response.getOutputStream());
            prtw.println("<html>");
            prtw.println("<body>");
            prtw.println("<p><h1>not one of the parameters !!</h1></p>");
            prtw.println("</body>");
            prtw.println("</html>");
            prtw.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");     //  to display Chinese words correctly
        response.setCharacterEncoding("utf-8");    // 
        response.setContentType("text/html");      //  

        System.out.println("Song2Servlet.html->doPost() ....");

        String forwardStr = "";
        RequestAttributesForSong2 song2Attr = (RequestAttributesForSong2)request.getAttribute("song2Attributes");        

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

        String submitButton = request.getParameter("submitButton");
        if (submitButton.equalsIgnoreCase("SUBMIT")) {		
            if (song2Attr.getAccessMethod().equalsIgnoreCase("ADD")) {
                int pageNo = song2Attr.getPageNo();
                Song2Data song2 = getDataFromJspView(request);
                int result = -1;
                if (song2 != null) {
                    if (song2.getSong_no().isEmpty()) {
                        request.setAttribute("error", "Song NO. can not be empty !!");
                    } else {
                        result = song2Table.insertOneRecordSong2Table(song2);
                        if (result == 0) {
                                // successfully inserted
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
                    // unsuccessfully inserted, keep the input information on screen
                    request.setAttribute("song2", song2);
                } else {
                    // successfully inserted
                    String qCon = song2Table.findQueryConditionOnSongNo(song2Attr.getQueryCondition(),song2.getSong_no());
                    pageNo = song2Table.recalculatePageNo(qCon,"song2.song_no",0,song2Attr.getByOrder(),song2Attr.getQueryCondition());
                    Song2Data song2Tmp = new Song2Data();
                    request.setAttribute("song2", song2Tmp );
                }
                forwardStr = addStr;
                song2Attr.setPageNo(pageNo);
                request.setAttribute("song2Attributes", song2Attr);
                RequestDispatcher view = request.getRequestDispatcher(forwardStr);
                view.forward(request,response);
            } else if (song2Attr.getAccessMethod().equalsIgnoreCase("UPDATE")) {
                int pageNo = song2Attr.getPageNo();
                Song2Data song2 = getDataFromJspView(request);
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
                    // unsuccessfully updated
                    forwardStr = updateStr;
                    request.setAttribute("song2", song2);
                } else {
                    // successfully updated
                    forwardStr = listStr;
                    String qCon = song2Table.findQueryConditionOnSongNo(song2Attr.getQueryCondition(),song2.getSong_no());
                    pageNo = song2Table.recalculatePageNo(qCon,"song2.song_no",0,song2Attr.getByOrder(),song2Attr.getQueryCondition());
                    request.setAttribute("songs", song2Table.getOnePageOfSong2Table(pageNo,song2Attr.getQueryCondition(),song2Attr.getByOrder()));
                }
                // request.setAttribute("orgSong_no", orgSong_no);
                song2Attr.setPageNo(pageNo);
                request.setAttribute("song2Attributes", song2Attr);
                RequestDispatcher view = request.getRequestDispatcher(forwardStr);
                view.forward(request, response);
            } else if (song2Attr.getAccessMethod().equalsIgnoreCase("DELETE")) {
                song2Table.deleteOneRecordSong2Table(song2Attr.getOrgSong_no()) ;
                forwardStr = listStr;
                String qCon = song2Table.findQueryConditionOnSongNo(song2Attr.getQueryCondition(),song2Attr.getOrgSong_no());
                int pageNo = song2Table.recalculatePageNo(qCon,"song2.song_no",1,song2Attr.getByOrder(),song2Attr.getQueryCondition());
                request.setAttribute("songs", song2Table.getOnePageOfSong2Table(pageNo,song2Attr.getQueryCondition(),song2Attr.getByOrder()));
                song2Attr.setPageNo(pageNo);
                request.setAttribute("song2Attributes", song2Attr);
                RequestDispatcher view = request.getRequestDispatcher(forwardStr);
                view.forward(request, response);
            } else if (song2Attr.getAccessMethod().equalsIgnoreCase("FIND")) {
                int pageNo = song2Attr.getPageNo();
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
                forwardStr = listStr;
                request.setAttribute("songs", song2Table.getOnePageOfSong2Table(pageNo,song2Attr.getQueryCondition(),song2Attr.getByOrder()));
                song2Attr.setPageNo(pageNo);
                request.setAttribute("song2Attributes", song2Attr);
                RequestDispatcher view = request.getRequestDispatcher(forwardStr);
                view.forward(request, response);
            } else {
                PrintWriter prtw = response.getWriter();
                prtw.println("<html>");
                prtw.println("<body>");
                prtw.println("<p><h1>  doPost ( not ADD, not UPDATE, and not DELETE !!</h1></p>");
                prtw.println("<p><h1>not one of the parameters !!</h1></p>");
                prtw.println("</body>");
                prtw.println("</html>");
                prtw.close();
            }
        } else if (submitButton.equalsIgnoreCase("BACK")) {
            // re-calculate the page no and refresh the table
            // move to last record added
            // from the page which include the last record added in "ADD" function
            // song2table.setQueryCondition("where song_no>="+'"'+song2Record.getSong_no()+'"');
            // request.setAttribute("songs", song2table.getNextPageOfSong2Table());
            // initiate the query string (All data in song2 table)
            forwardStr = listStr;
            request.setAttribute("songs", song2Table.getOnePageOfSong2Table(song2Attr.getPageNo(),song2Attr.getQueryCondition(),song2Attr.getByOrder()));
            RequestDispatcher view = request.getRequestDispatcher(forwardStr);
            view.forward(request, response);
        } else {
            PrintWriter prtw = response.getWriter();
            prtw.println("<html>");
            prtw.println("<body>");
            prtw.println("<p><h1>  doPost ( not SUBMIT and not BACK !!</h1></p>");
            prtw.println("<p><h1>not one of the parameters !!</h1></p>");
            prtw.println("</body>");
            prtw.println("</html>");
            prtw.close();
        }
    }
	
    private Song2Data getDataFromJspView(HttpServletRequest request) {

        Song2Data song2 = null;;
        String tmp = new String();

        tmp = request.getParameter("song_no");
        if (tmp != null) {
            String temp = new String("");
            song2 = new Song2Data();
            song2.setSong_no(tmp);
            song2.setSong_na(request.getParameter("song_na"));
            song2.setLang_no(request.getParameter("lang_no"));
            temp = request.getParameter("s_num_word");
            song2.setS_num_word(Integer.parseInt(temp));
            temp = request.getParameter("num_fw");
            song2.setNum_fw(Integer.parseInt(temp));
            song2.setNum_pw(request.getParameter("num_pw"));
            song2.setSing_no1(request.getParameter("sing_no1"));
            song2.setSing_no2(request.getParameter("sing_no2"));
            song2.setSele_tf(request.getParameter("sele_tf"));
            song2.setChor(request.getParameter("chor"));
            song2.setN_mpeg(request.getParameter("n_mpeg"));
            song2.setM_mpeg(request.getParameter("m_mpeg"));
            song2.setVod_yn(request.getParameter("vod_yn"));
            song2.setVod_no(request.getParameter("vod_no"));
            song2.setPathname(request.getParameter("pathname"));

            temp = request.getParameter("in_date");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dd;
            try {
                dd = df.parse(temp);
                song2.setIn_date(new java.sql.Date(dd.getTime()));
            } catch (ParseException e) {
                song2.setIn_date(null);
                e.printStackTrace();
            }
        }

        return song2;
    }

}
