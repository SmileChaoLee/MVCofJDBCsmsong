package com.smile.dao;

import com.smile.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.DatabaseMetaData;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;

public class Song2Table {
	
    public final String songNoOrder     = "song2.song_no";
    public final String vodNoOrder      = "song2.vod_no";
    public final String songNaOrder     = "song2.song_na";
    public final String langSongNaOrder = "CONCAT(song2.lang_no,song2.song_na)";
    public final String langSwordSongNaOrder = 
                    "CONCAT(song2.lang_no, LPAD(CONVERT(song2.s_num_word,CHAR(2)),2,'0'), song2.song_na)";
    public final String langSwordSongNoOrder = 
                    "CONCAT(song2.lang_no, LPAD(CONVERT(song2.s_num_word,CHAR(2)),2,'0'), song2.song_no)";
    public final String singerOrder1 = "sing1.sing_na";
    public final String singerOrder2 = "sing2.sing_na";
	
    private final String dropTable = "DROP TABLE song2 "; 
    private final String createTable = "CREATE TABLE song2 (" + 
        "    song_no     VARCHAR(6)  "     + 
        "  , song_na     VARCHAR(45) "     + 
        "  , lang_no     VARCHAR(1)  "     +
        "  , s_num_word  INT(2)      "     +
        "  , num_fw      INT(2)      "     +
        "  , num_pw      VARCHAR(1)  "     +
        "  , sing_no1    VARCHAR(5)  "     +
        "  , sing_no2    VARCHAR(5)  "     +
        "  , sele_tf     VARCHAR(1)  "     +
        "  , chor        VARCHAR(1)  "     +
        "  , n_mpeg      VARCHAR(2)  "     +
        "  , m_mpeg      VARCHAR(2)  "     +
        "  , vod_yn      VARCHAR(1)  "     +
        "  , vod_no      VARCHAR(6)  "     +
        "  , pathname    VARCHAR(6)  "     +
        "  , ord_no      INT(6)      "     +
        "  , order_num   INT(6)      "     +
        "  , ord_old_n   INT(6)      "     +
        "  , in_date     DATE       )" ;

    private final String insertsong2 = "INSERT INTO song2 (song_no,song_na,lang_no,s_num_word" +
                    ",num_fw,num_pw,sing_no1,sing_no2,sele_tf,chor,n_mpeg,m_mpeg,vod_yn,vod_no,pathname" +
                    ",ord_no,order_num,ord_old_n,in_date) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private final String basicQuery = "SELECT song2.*,langu.lang_na ,sing1.sing_na as sing_na1,sing2.sing_na as sing_na2" +
            "    FROM song2" +
            "    LEFT JOIN langu on song2.lang_no=langu.lang_no" +
            "    LEFT JOIN singer as sing1 on song2.sing_no1=sing1.sing_no" +
            "    LEFT JOIN singer as sing2 on song2.sing_no2=sing2.sing_no";
    
    private final String rowsQuery = "SELECT count(*) AS rowCount FROM song2 ";

    private final Integer pageSize   = 20;

    private Connection dbConn = null;
	
    public Song2Table(Connection dbConn) {
    	if (dbConn != null) {
            this.dbConn = dbConn;
            existTable();
    	}
    } 
    
    public Connection getConnection() {
    	return dbConn;
    }

    private boolean existTable() {
        boolean result = false;
        DatabaseMetaData dbm;
        ResultSet rs = null;
        try {
            dbm = (DatabaseMetaData)dbConn.getMetaData();
            // check if "song2" table is there
            rs = dbm.getTables(null, null, "song2", null);

            if (rs.next()) {
                // Table exists
                result = true;
            } else {
                // Table does not exist
                // Create song2 Table
                // createTable();
            }
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.toString());
        } finally {
            closeResultSetStatementPreparedStatement(rs,null,null);
        }

        return result;
    }
	
    private int createTable()
    { 
        int result = -1;
        Statement stat = null;
        try {
            result = 0;
            stat = dbConn.createStatement(); 
            stat.executeUpdate(createTable); 
        } 
        catch(SQLException e) {
            result = e.getErrorCode() ;
            System.out.println("CreateDB Exception :" + e.toString()); 
        } 
        finally {
            closeResultSetStatementPreparedStatement(null,stat,null); 
        } 

        return(result);
    } 
	
    private int dropTable()
    { 
        int result = -1;
        Statement stat = null;
        try {
            result = 0;
            stat = dbConn.createStatement(); 
            stat.executeUpdate(dropTable); 
        } 
        catch(SQLException e) {
            result = e.getErrorCode() ;
            System.out.println("DropDB Exception :" + e.toString()); 
        } 
        finally {
            closeResultSetStatementPreparedStatement(null,stat,null);
        } 

        return(result);
    } 

    public int insertOneRecordSong2Table(Song2Data song2) {
        int result = -1;
        PreparedStatement pst = null;
        try {
            pst = dbConn.prepareStatement(insertsong2); 
            pst.setString(1, song2.getSong_no()); 
            pst.setString(2, song2.getSong_na());
            pst.setString(3, song2.getLang_no());
            pst.setInt(4,song2.getS_num_word());
            pst.setInt(5,song2.getNum_fw());
            pst.setString(6,song2.getNum_pw());
            pst.setString(7,song2.getSing_no1());
            pst.setString(8,song2.getSing_no2());
            pst.setString(9,song2.getSele_tf());
            pst.setString(10,song2.getChor());
            pst.setString(11,song2.getN_mpeg());
            pst.setString(12,song2.getM_mpeg());
            pst.setString(13,song2.getVod_yn());
            pst.setString(14,song2.getVod_no());
            pst.setString(15,song2.getPathname());
            pst.setInt(16,song2.getOrd_no());
            pst.setInt(17,song2.getOrder_num());
            pst.setInt(18,song2.getOrd_old_n());
            pst.setDate(19, song2.getIn_date());
            pst.executeUpdate();
            result = 0;
        }
        catch(SQLException e) {
            result = e.getErrorCode() ;
            System.out.println("InsertDB Exception :" + e.toString()); 
        } 
        finally {
            closeResultSetStatementPreparedStatement(null,null,pst);
        } 

        return(result);
    }
	
    // update data 
    public int updateOneRecordSong2Table(String orgSong_no,Song2Data song2) {
        int result = -1;
        String regex = "[0-9]+";
        String updatedbSQL;
        if ( (song2.getSong_no() == null) || (song2.getSong_no().isEmpty()) ) {
            return(result);
        }
        if (!orgSong_no.matches(regex)) {
            return(result);
        }
        updatedbSQL = "UPDATE song2 set song_no="+enCloseString(song2.getSong_no())
                 +",song_na="+enCloseString(song2.getSong_na())
                 +",lang_no="+enCloseString(song2.getLang_no())
                 +",s_num_word="+song2.getS_num_word().toString().trim()
                 +",num_fw="+song2.getNum_fw().toString().trim()
                 +",num_pw="+enCloseString(song2.getNum_pw())
                 +",sing_no1="+enCloseString(song2.getSing_no1())
                 +",sing_no2="+enCloseString(song2.getSing_no2())
                 +",sele_tf="+enCloseString(song2.getSele_tf().trim())
                 +",chor="+enCloseString(song2.getChor())
                 +",n_mpeg="+enCloseString(song2.getN_mpeg())
                 +",m_mpeg="+enCloseString(song2.getM_mpeg())
                 +",vod_yn="+enCloseString(song2.getVod_yn())
                 +",vod_no="+enCloseString(song2.getVod_no())
                 +",pathname="+enCloseString(song2.getPathname())
                 +",ord_no="+song2.getOrd_no().toString().trim()
                 +",order_num="+song2.getOrder_num().toString().trim()
                 +",ord_old_n="+song2.getOrd_old_n().toString().trim()
                 +",in_date="+enCloseString(song2.getIn_date().toString().trim())

                 +" WHERE song2.song_no="+enCloseString(orgSong_no);
        PreparedStatement pst = null;
        try {
            pst = dbConn.prepareStatement(updatedbSQL); 
            pst.executeUpdate();
            result = 0;
        }
        catch(SQLException e) {
            result = e.getErrorCode() ;
            System.out.println("UpdateDB Exception :" + e.toString()); 
        } 
        finally {
            closeResultSetStatementPreparedStatement(null,null,pst);
        } 

        return(result);
    } 		
	
    // delete data 
    public int deleteOneRecordSong2Table(String orgSong_no) {
        int result = -1;
        String  regex = "[0-9]+";
        String  deletedbSQL;
        if ( (orgSong_no == null) || (orgSong_no.isEmpty()) ) {
            return(result);
        }
        if (!orgSong_no.matches(regex)) {
            return(result);
        }
        deletedbSQL = "DELETE FROM song2 WHERE song_no=" + enCloseString(orgSong_no);
        PreparedStatement pst = null;
        try {
            pst = dbConn.prepareStatement(deletedbSQL); 
            pst.executeUpdate(); 
            result = 0;
        }
        catch(SQLException e) {
            result = e.getErrorCode() ;
            System.out.println("DeleteDB Exception :" + e.toString()); 
        } 
        finally {
            closeResultSetStatementPreparedStatement(null,null,pst);
        } 

        return(result);
    } 

    // one song by no
    public Song2Data getOneRecordSong2bySongno(String orgSong_no, String byOrder) {
        String stab;
        stab = basicQuery
            +" WHERE song2.song_no="+enCloseString(orgSong_no.trim())+" ORDER BY "+byOrder;
        Song2Data song2 = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = dbConn.createStatement(); 
            rs   = stat.executeQuery(stab);
            if (rs.next()) { // song_no is primary key, so there is only one record or nothing
                song2 = new Song2Data();
                song2.setSong_no(rs.getString("song2.song_no"));
                song2.setSong_na(rs.getString("song2.song_na"));
                song2.setLang_no(rs.getString("song2.lang_no"));
                song2.setLang_na(rs.getString("langu.lang_na"));
                song2.setS_num_word(rs.getInt("song2.s_num_word"));
                song2.setNum_fw(rs.getInt("song2.num_fw"));
                song2.setNum_pw(rs.getString("song2.num_pw"));
                song2.setSing_no1(rs.getString("song2.sing_no1"));
                song2.setSing_na1(rs.getString("sing_na1"));
                song2.setSing_no2(rs.getString("song2.sing_no2"));
                song2.setSing_na2(rs.getString("sing_na2"));
                song2.setSele_tf(rs.getString("song2.sele_tf"));
                song2.setChor(rs.getString("song2.chor"));
                song2.setN_mpeg(rs.getString("song2.n_mpeg"));
                song2.setM_mpeg(rs.getString("song2.m_mpeg"));
                song2.setVod_yn(rs.getString("song2.vod_yn"));
                song2.setVod_no(rs.getString("song2.vod_no"));
                song2.setPathname(rs.getString("song2.pathname"));
                song2.setOrd_no(rs.getInt("song2.ord_no"));
                song2.setOrder_num(rs.getInt("song2.order_num"));
                song2.setOrd_old_n(rs.getInt("song2.ord_old_n"));
                song2.setIn_date(rs.getDate("song2.in_date"));	    	  
            }
        } catch(SQLException e) { 
            System.out.println("QueryDB Exception :" + e.toString()); 
        } finally { 
            closeResultSetStatementPreparedStatement(rs,stat,null);
        }

        return(song2);
    }
	
    // one song by name
    public List<Song2Data> selectRecordsSong2bySongno(String songno,String queryCondition,String byOrder)
    {
        String temp = queryCondition+" trim(song2.song_no)=" + enCloseString(songno.trim());
        List<Song2Data> songs = getOnePageOfSong2Table(1, temp, byOrder); // from 1st page
        return(songs);
    }

    // one song by name
    public List<Song2Data> selectRecordsSong2bySongname(String songna, String queryCondition,String byOrder)
    {
        String temp = queryCondition+" trim(song2.song_na)=" + enCloseString(songna.trim());
        List<Song2Data> songs = getOnePageOfSong2Table(1, temp, byOrder); // from 1st page
        return(songs);
    }
	
    public Integer getTotalRecordsOfQuery(String queryCondition) {
        Integer totalRecordsOfQuery = 0;
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = dbConn.createStatement();
            rs = stat.executeQuery(rowsQuery + " "+queryCondition);
            if (rs.next()) {
                totalRecordsOfQuery = rs.getInt("rowCount");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResultSetStatementPreparedStatement(rs,stat,null);
        }
        return totalRecordsOfQuery;
    }
	
    public Integer getTotalPageOfQuery(String queryCondition) {
        Integer totalPageOfQuery = 0;
        Statement stat = null;
        ResultSet rs = null;
        try {
            stat = dbConn.createStatement();
            rs = stat.executeQuery(rowsQuery + " "+queryCondition);
            if (rs.next()) {
                Integer recordsOfQuery = rs.getInt("rowCount");
                totalPageOfQuery = recordsOfQuery / pageSize;
                if ((totalPageOfQuery*pageSize)<recordsOfQuery) {
                        totalPageOfQuery++;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResultSetStatementPreparedStatement(rs,stat,null);
        }
        return totalPageOfQuery;
    }

    public List<Song2Data> getOnePageOfSong2Table(Integer currentPageNo, String queryCondition,String byOrder) {
    	
    	if (currentPageNo <= 0) {
            currentPageNo = 1;
    	}
    	if (queryCondition == null) {
            queryCondition = "";
    	}
    	if ( (byOrder == null) || (byOrder.isEmpty()) ) {
            byOrder = "song2.song_no";
    	}
    	
    	List<Song2Data> songs = null;
        Integer totalPageOfQuery = getTotalPageOfQuery(queryCondition);

        if (currentPageNo>totalPageOfQuery) {
            currentPageNo = totalPageOfQuery;
        }
        if (currentPageNo<=0) {
            currentPageNo = 1;
        }
		
        Integer currentRecordNo = (currentPageNo-1) * pageSize;
		
    	String stab = "";
    	stab = stab+basicQuery+" "+queryCondition+" ORDER BY "+byOrder+" LIMIT "
                +currentRecordNo.toString()+","
                +pageSize.toString(); 	
    	Statement stat = null;
    	ResultSet rs = null;
    	try
    	{
            stat = dbConn.createStatement();
            rs   = stat.executeQuery(stab);
            	
            if (rs != null) {
                int i = 0;
                // songs = new ArrayList<Song2Data>();
                songs = new ArrayList<>();
                while (i<pageSize) {
                    if (rs.next()) {
                        Song2Data song2 = new Song2Data();
                        song2.setSong_no(rs.getString("song2.song_no"));
                        song2.setSong_na(rs.getString("song2.song_na"));
                        song2.setLang_no(rs.getString("song2.lang_no"));
                        song2.setLang_na(rs.getString("langu.lang_na"));
                        song2.setS_num_word(rs.getInt("song2.s_num_word"));
                        song2.setNum_fw(rs.getInt("song2.num_fw"));
                        song2.setNum_pw(rs.getString("song2.num_pw"));
                        song2.setSing_no1(rs.getString("song2.sing_no1"));
                        song2.setSing_na1(rs.getString("sing_na1"));
                        song2.setSing_no2(rs.getString("song2.sing_no2"));
                        song2.setSing_na2(rs.getString("sing_na2"));
                        song2.setSele_tf(rs.getString("song2.sele_tf"));
                        song2.setChor(rs.getString("song2.chor"));
                        song2.setN_mpeg(rs.getString("song2.n_mpeg"));
                        song2.setM_mpeg(rs.getString("song2.m_mpeg"));
                        song2.setVod_yn(rs.getString("song2.vod_yn"));
                        song2.setVod_no(rs.getString("song2.vod_no"));
                        song2.setPathname(rs.getString("song2.pathname"));
                        song2.setOrd_no(rs.getInt("song2.ord_no"));
                        song2.setOrder_num(rs.getInt("song2.order_num"));
                        song2.setOrd_old_n(rs.getInt("song2.ord_old_n"));
                        song2.setIn_date(rs.getDate("song2.in_date"));
                        songs.add(song2);
                        i++;
                    } else {
                        i = pageSize;
                    }
                }
            }	
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            closeResultSetStatementPreparedStatement(rs,stat,null);
        }
        
        return songs;
    }

    public List<Song2Data> getLastPageOfSong2Table(String queryCondition,String byOrder) {
    	Integer totalPageOfQuery = getTotalPageOfQuery(queryCondition);
        Integer currentPageNo = totalPageOfQuery;
        if (currentPageNo<=0) {
            currentPageNo = 1;
        }
        return getOnePageOfSong2Table(currentPageNo, queryCondition,byOrder);
    }
    
    public Integer recalculatePageNo(String queryCondition,String conOrder,int offset,String byOrder,String orgQueryCondition) {

    	Statement stat = null;
    	ResultSet rs = null;
    	Integer currentPageNo = 0;
    	
    	System.out.println("conOrder , byOrder = "+conOrder+" "+byOrder);
    	
    	String cc = basicQuery + " " + queryCondition + " ORDER BY " + conOrder + " ASC LIMIT 1";
    	System.out.println("First query= "+cc);
    	// find the first record of the query in ascending order (eg. song2.song_no>"12001")
    	String tempCondition = "";
    	try {
            stat = dbConn.createStatement();
            rs = stat.executeQuery(cc);
            if (rs.next()) {
                if (byOrder.equalsIgnoreCase(songNoOrder)) {
                    String temp = '"'+rs.getString("song2.song_no")+'"';
                    tempCondition = " WHERE "+byOrder+" > " + temp;
                } else if (byOrder.equalsIgnoreCase(vodNoOrder)) {
                    String temp = '"'+rs.getString("song2.vod_no")+'"';
                    tempCondition = " WHERE "+byOrder+" > " + temp;
                } else if (byOrder.equalsIgnoreCase(songNaOrder)) {
                    String temp = '"'+rs.getString("song2.song_na")+'"';
                    tempCondition = " WHERE "+byOrder+" > " + temp;
                } else if (byOrder.equalsIgnoreCase(langSongNaOrder)) {
                    String lang = '"'+rs.getString("song2.lang_no")+'"';
                    String temp = '"'+rs.getString("song2.song_na")+'"';
                    tempCondition = " WHERE "+byOrder+" > CONCAT("+lang+","+temp+")";
                } else if (byOrder.equalsIgnoreCase(langSwordSongNaOrder)) {
                    String lang = '"'+rs.getString("song2.lang_no")+'"';
                    String temp = '"'+rs.getString("song2.song_na")+'"';
                    String tmp1 = '"'+String.format("%02d",rs.getInt("song2.s_num_word"))+'"';
                    tempCondition = " WHERE "+byOrder+" > CONCAT("+lang+","+tmp1+","+temp+")";
                } else if (byOrder.equalsIgnoreCase(langSwordSongNoOrder)) {
                    String lang = '"'+rs.getString("song2.lang_no")+'"';
                    String temp = '"'+rs.getString("song2.song_no")+'"';
                    String tmp1 = '"'+String.format("%02d",rs.getInt("song2.s_num_word"))+'"';
                    tempCondition = " WHERE "+byOrder+" > CONCAT("+lang+","+tmp1+","+temp+")";
                } else if (byOrder.equalsIgnoreCase(singerOrder1)) {
                    String temp = '"'+rs.getString("sing1.sing_na")+'"';
                    tempCondition = " WHERE sing1.sing_na > " + temp;
                } else if (byOrder.equalsIgnoreCase(singerOrder2)) {
                    String temp = '"'+rs.getString("sing2.sing_na")+'"';
                    tempCondition = " WHERE sing2.sing_na > " + temp;
                }
           }
    	} catch (SQLException e) {
            System.out.println(e.toString());
    	} finally {
            closeResultSetStatementPreparedStatement(rs,stat,null);
    	}
    	
    	cc = rowsQuery + " " + tempCondition;
    	Integer totalRecords = getTotalRecordsOfQuery(orgQueryCondition);
    	try {
            stat = dbConn.createStatement();
            rs = stat.executeQuery(cc); 
            if (rs.next()) {
                Integer rQuery = rs.getInt("rowCount")+offset;
                // because we use song2.song_no>"12001" as a query for song_no="12001"
                // and we want to find song_no="12001" exactly or just grater than "12001"
                // if "12001" did not exist
                currentPageNo = (totalRecords - rQuery)/pageSize;
                if ((currentPageNo*pageSize)<(totalRecords-rQuery) ) {
                    currentPageNo++;
                }
            }
    	} catch (SQLException e) {
            System.out.println(e.toString());
    	} finally {
            closeResultSetStatementPreparedStatement(rs,stat,null);
    	}
    	
    	return currentPageNo;
    }

    private void closeResultSetStatementPreparedStatement(ResultSet rs,Statement stat,PreparedStatement pst ) {
        try {
            if(rs != null) {
                rs.close(); 
            } 
            if(stat != null) {
                stat.close(); 
            } 
            if(pst != null) {
                pst.close(); 
            } 
        } catch(SQLException e) {
            System.out.println(e.toString());
        }  
    } 
		
    public int closeConnection() {
        int result=-1;
        try {
            if(dbConn != null) {
                result = 0;
                dbConn.close(); 
                dbConn = null;
            } 
        } catch(SQLException e) {
            result = e.getErrorCode() ;
            System.out.println(e.toString());
        } 

        return(result);
    }
	    
    private String enCloseString(String sString) {
        String tString = sString.replace("\\", "\\\\");
        tString = tString.replace('"', '\"');
        tString = tString.replace("'", "\'");
        tString = tString.replace("\n", "\\n");
        tString = '"'+tString+'"';

        return tString;
    }

    // added on 2017-08-14
    public String findQueryConditionOnSongNo(String orgQuery, String song_no) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        song_no = '"' + song_no + '"';
        // to find the first record of the query (especially the exactly one)
        String temp = songNoOrder + ">=" + song_no;
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }
	
    public String findQueryConditionOnVodNo(String orgQuery, String vod_no) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        vod_no = '"' + vod_no + '"';
        // to find the first record of the query (especially the exactly one)
        String temp = vodNoOrder + ">=" + vod_no;
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }

    public String findQueryConditionOnSongNa(String orgQuery, String song_na) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        song_na = '"'+song_na+'"';
        // to find the first record of the query (especially the exactly one)
        String temp = songNaOrder+">="+song_na;
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }
	
    public String findQueryConditionOnLangSongNa(String orgQuery, String lang_no,String song_na) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        // to find the first record of the query (especially the exactly one)
        lang_no = '"'+lang_no+'"';
        song_na = '"'+song_na+'"';
        String temp = langSongNaOrder+">= CONCAT("+lang_no+","+song_na+")";
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }
	
    public String findQueryConditionOnLangSwordSongNa(String orgQuery, String lang_no,String sword,String song_na) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        // to find the first record of the query (especially the exactly one)
        lang_no = '"'+lang_no+'"';
        song_na = '"'+song_na+'"';
        sword = '"'+String.format("%02d", Integer.valueOf(sword))+'"';
        String temp = langSwordSongNaOrder+">=CONCAT("+lang_no+","+sword+","+song_na+")";
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }
	
    public String findQueryConditionOnLangSwordSongNo(String orgQuery, String lang_no,String sword,String song_no) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        // to find the first record of the query (especially the exactly one)
        lang_no = '"'+lang_no+'"';
        song_no = '"'+song_no+'"';
        sword = '"'+String.format("%02d", Integer.valueOf(sword))+'"';
        String temp = langSwordSongNoOrder+">=CONCAT("+lang_no+","+sword+","+song_no+")";
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }
	
    public String findQueryConditionOnSingNa1(String orgQuery, String sing_na1) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        sing_na1 = '"'+sing_na1+'"';
        // to find the first record of the query (especially the exactly one)
        String temp = singerOrder1 + ">=" + sing_na1;
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }
	
    public String findQueryConditionOnSingNa2(String orgQuery, String sing_na2) {
        String qCon = "";
        if (orgQuery != null) {
            qCon = orgQuery;
        }
        sing_na2 = '"'+sing_na2+'"';
        // to find the first record of the query (especially the exactly one)
        String temp = singerOrder2 + ">=" + sing_na2;
        if (!qCon.isEmpty()) {
            qCon = " WHERE " + qCon + " AND " + temp;
        } else {
            qCon = " WHERE " + temp;
        }
        return qCon;
    }
    //
    
    // added on 2017-08-25
    public Song2Data getDataFromJspView(HttpServletRequest request) {
        Song2Data song2 = null;
        String tmp = request.getParameter("song_no");
        if (tmp != null) {
            song2 = new Song2Data();
            song2.setSong_no(tmp);
            song2.setSong_na(request.getParameter("song_na"));
            song2.setLang_no(request.getParameter("lang_no"));
            String temp = request.getParameter("s_num_word");
            if ( (temp == null) || temp.isEmpty()) {
                song2.setS_num_word(0);
            }
            else {
                song2.setS_num_word(Integer.parseInt(temp));
            }
            temp = request.getParameter("num_fw");
            if ( (temp == null) || temp.isEmpty()) {
                song2.setNum_fw(0);
            } else {
                song2.setNum_fw(Integer.parseInt(temp));
            }
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
                System.out.println(e.toString());
                song2.setIn_date(null);
            }
        }
        return song2;
    }
    //
}

