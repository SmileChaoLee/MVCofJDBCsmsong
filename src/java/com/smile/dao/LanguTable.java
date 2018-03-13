package com.smile.dao;

import com.mysql.jdbc.DatabaseMetaData;
import com.smile.model.LanguData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LanguTable {

    private final String dropTable = "DROP TABLE langu ";
	private final String createTable = "CREATE TABLE langu (" +
	    "    lang_no     VARCHAR(1)  "     +
	    "  , lang_na     VARCHAR(8) "     +
        "  , lang_en     VARCHAR(20) " ;

	private final String insertRecord = "insert into langu (lang_no , lang_na , lang_en ) " +
			"values(?,?,?)";
	
    private final String basicQuery = new String("select *  from langu ");
    private final String rowsQuery = new String("select count(*) as rowCount  from langu ");
    
	private final Integer pageSize   = 20;
	
	private Integer recordsOfQuery  = 0;
	private Integer currentRecordNo = 0;
	private Integer currentPageNo   = 0;
	private Integer totalPageOfQuery = 0;
	private LanguData languRecord   = null;
	private String queryCondition   = new String("");

	private Statement stat = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;

	private Connection dbConn = null;
	
    public LanguTable(Connection dbConn) {
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

		try {
			dbm = (DatabaseMetaData)dbConn.getMetaData();
			// check if "langu" table is there
			ResultSet tables = dbm.getTables(null, null, "langu", null);

			if (tables.next()) {
				// Table exists
                result = true;
			}
			else {
				// Table does not exist
				// Create langu Table
				// createTable();
			}
			
			languRecord = new LanguData();
		}
		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return result;
	}
	
	private int createTable()
	{ 
		int result = -1;
	    try 
	    { 
	    	result = 1;
	    	stat = dbConn.createStatement();
	    	stat.executeUpdate(createTable);
	    } 
	    catch(SQLException e) 
	    { 
	    	result = -1;
	    	System.out.println("CreateDB Exception :" + e.toString()); 
	    } 
	    finally 
	    { 
	      Close(); 
	    } 
	    
	    return(result);
	} 
	
	private int dropTable()
	{ 
		int result = -1;
	    try 
	    { 
	    	result = 1;
	    	stat = dbConn.createStatement();
	    	stat.executeUpdate(dropTable);
	    } 
	    catch(SQLException e) 
	    { 
	    	result = -1;
	    	System.out.println("DropDB Exception :" + e.toString()); 
	    } 
	    finally 
	    { 
	      Close(); 
	    } 
	    
	    return(result);
	} 

	public int insertOneRecordLanguTable()
	{ 
		int result = -1;
		
        try
        { 
          result = 1;
		
	      pst = dbConn.prepareStatement(insertRecord);
	      
	      pst.setString(1, languRecord.getLang_no());
	      pst.setString(2, languRecord.getLang_na());
          pst.setString(3, languRecord.getLang_en());

	      pst.executeUpdate();
	    }
	  
	    catch(SQLException e) 
	      { 
	    	result = -1 ;
	    	System.out.println("InsertDB Exception :" + e.toString()); 
	      } 
	    finally 
	    { 
	    Close(); 
	    } 
	    
	    return(result);
	  }
	
	// update data 
	public int updateOneRecordLanguTable(String orgLang_no)
	{ 
		int result = -1;
		
		String regex = new String("[0-9]+");
		String updatedbSQL;
		
		if ( (languRecord.getLang_no()==null) || (languRecord.getLang_no().isEmpty()) )
		{
			return(result);
		}
		
		if (!orgLang_no.matches(regex)) {
			return(result);
		}

		updatedbSQL = "UPDATE langu set lang_no="+enCloseString(languRecord.getLang_no())
		             +",lang_na="+enCloseString(languRecord.getLang_na())
                     +",lang_en="+enCloseString(languRecord.getLang_en())

		             +" where langu.lang_no="+enCloseString(orgLang_no);
        
		try
        { 
			result = 1;
			pst = dbConn.prepareStatement(updatedbSQL);
			pst.executeUpdate();
	    }
	  
	    catch(SQLException e) 
	      { 
	    	result = -1;
	    	System.out.println("UpdateDB Exception :" + e.toString()); 
	      } 
	    finally 
	    { 
	    	Close(); 
	    } 
	    
	    return(result);
	  } 		
	
	// delete data 
	public int deleteOneRecordLanguTable(String orgLang_no)
	{
		int result = -1;
		
		String  regex = new String("[0-9]+");
		String  deletedbSQL;
		
		if ( (languRecord.getLang_no()==null) || (languRecord.getLang_no().isEmpty()) )
		{
			return(result);
		}
		
		if (!orgLang_no.matches(regex)) {
			return(result);
		}
		
		deletedbSQL = "DELETE FROM langu WHERE lang_no="+enCloseString(orgLang_no);
        
		try
        { 
			result = 1;
			pst = dbConn.prepareStatement(deletedbSQL);
			pst.executeUpdate(); 
	    }
	  
	    catch(SQLException e) 
	      { 
	    	result = -1;
	    	System.out.println("DeleteDB Exception :" + e.toString()); 
	      } 
	    finally 
	    { 
	    Close(); 
	    } 
        
	    return(result);
	  } 

	// one language by no
	public int getOneRecordLangubyLangno(String orgLang_no)
	{ 
		String stab;
		int    result=-1;
        
		stab = basicQuery
			  +" where langu.lang_no="+enCloseString(orgLang_no.trim()) ;
		
	    try
	    {
	      stat = dbConn.createStatement();
	      ResultSet rrs   = stat.executeQuery(stab);
          
	      if (rrs.next()) // lang_no is primary key, so there is only one record or nothing
	      { 
	    	  result = 1 ;
	    	  languRecord.setLang_no(rrs.getString("langu.lang_no"));
	    	  languRecord.setLang_na(rrs.getString("langu.lang_na"));
              languRecord.setLang_en(rrs.getString("langu.lang_en"));
	      }
	      else
	      {
	    	  languRecord.initiateLangurecord();  // empty the data of languRecord
	      }
	    }
	      
		catch(SQLException e) 
		{ 
			System.out.println("QueryDB Exception :" + e.toString()); 
	    	languRecord.initiateLangurecord();  // empty the data of languRecord
		} 
	    finally 
	    { 
	    	Close();
	    }
	    
	    return(result);
	}
	
	// one language by name
	public List<LanguData> selectRecordsLangubyLangname(String langna)
	{
        String temp = new String(queryCondition);
		queryCondition = " where trim(langu.lang_na)="+enCloseString(langna.trim()) + queryCondition;
        List<LanguData> langs = getNextPageOfLanguTable();
        queryCondition = temp;
	    return(langs);
	}	

    public List<LanguData> getNextPageOfLanguTable() {
    	List<LanguData> langs = new ArrayList<LanguData>();
    	
		currentPageNo++;
		if (currentPageNo<=0) {
            currentPageNo = 1;
		}
		currentRecordNo = (currentPageNo-1) * pageSize;
		
        try {
    		stat = dbConn.createStatement();
    		ResultSet rrs = stat.executeQuery(rowsQuery + queryCondition);
    		if (rrs.next()) {
    			recordsOfQuery = rrs.getInt("rowCount");
    			totalPageOfQuery = recordsOfQuery / pageSize;
    			if ((totalPageOfQuery*pageSize)<recordsOfQuery) {
    				totalPageOfQuery++;
    			}
    			if (currentPageNo>totalPageOfQuery) {
    				currentPageNo = totalPageOfQuery;
    				currentRecordNo = (currentPageNo-1) * pageSize;
    			}

            	String stab = new String("");
            	stab = stab+basicQuery+queryCondition+" limit "
            	           +currentRecordNo.toString()+","
            			   +pageSize.toString();
            	
            	stat = dbConn.createStatement();
            	rs   = stat.executeQuery(stab);
            	
            	if (rs != null) {
            		int i = 0;
            		while (i<pageSize) {
            			if (rs.next()) {
            				LanguData langu = new LanguData();
            				langu.setLang_no(rs.getString("langu.lang_no"));
            				langu.setLang_na(rs.getString("langu.lang_na"));
                            langu.setLang_en(rs.getString("langu.lang_en"));

            				langs.add(langu);
        			
            				i++;
            			}
            			else {
            				i = pageSize;
            			}
            		}

                    if (langs.size()>0) {
                        setLangurecord(langs.get(0));
                    }
            	}
        	}
        	
        } catch (SQLException e) {
            e.printStackTrace();
            return langs;
        }
        
        return langs;
    }


    public List<LanguData> getFirstPageOfLanguTable() {
        currentPageNo = 0;
        return getNextPageOfLanguTable();
    }
    
    public List<LanguData> getPreviousPageOfLanguTable() {
    	currentPageNo -= 2;
    	return getNextPageOfLanguTable();
    }

    public List<LanguData> getLastPageOfLanguTable() {
        currentPageNo = totalPageOfQuery--;
        return getNextPageOfLanguTable();
    }

    public void setLangurecord(LanguData languRecord) {
  	  	this.languRecord.setLang_no(languRecord.getLang_no());
  	  	this.languRecord.setLang_na(languRecord.getLang_na());
        this.languRecord.setLang_en(languRecord.getLang_en());
    }
    
    public LanguData getLangurecord() {
    	return this.languRecord;
    }
    
    public void setQueryCondition(String condition) {
    	if (condition==null) {
    		condition = new String("");
    	}
    	this.queryCondition = condition;
    }
    
    public void setCurrentPageNo(Integer currentPageNo) {
    	this.currentPageNo = currentPageNo;
    }
    
    public Integer getCurrentPageNo() {
    	return this.currentPageNo;
    }
    
    public void recalculatePageNo() {
    	String cc = new String("select count(*) as rowCount from langu "+queryCondition);
    	if (queryCondition.trim()=="") {
    		cc = cc + " Where lang_no<="+enCloseString(languRecord.getLang_no());
    	}
    	else {
    			cc = cc + " and "+"where lang_no<="+enCloseString(languRecord.getLang_no());
    	}

    	try {
    		stat = dbConn.createStatement();
    		ResultSet rrs = stat.executeQuery(cc); 
    		if (rrs.next()) {
    			Integer rQuery = rrs.getInt("rowCount");
    			currentPageNo = rQuery/pageSize;
    	    	
    			if ((currentPageNo*pageSize)==rQuery) {
    				currentPageNo--;
    			}
    		}

    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public void setCurrentRecordNo(Integer currentRecordNo) {
    	this.currentRecordNo = currentRecordNo;
    }
    
    public Integer getCurrentRecordNo() {
    	return this.currentRecordNo;
    } 

	private void Close() 
	  { 
	    try 
	    { 
	      if(rs!=null) 
	      { 
	        rs.close(); 
	        rs = null;
	      } 
	      if(stat!=null) 
	      { 
	        stat.close(); 
	        stat = null;
	      } 
	      if(pst!=null) 
	      { 
	        pst.close(); 
	        pst = null;
	      } 
	    } 
	    catch(SQLException e) 
	    { 
	      System.out.println("Close Exception :" + e.toString()); 
	    }  
	  } 
		
	public int closeConnection()
	{
		int result=-1;
		
	    try 
	    { 
	      if(dbConn != null)
	      { 
	    	  result = 1;
	    	  dbConn.close();
	    	  dbConn = null;
	      } 
	    } 
	    catch(SQLException e) 
	    { 
	      System.out.println("Close Exception :" + e.toString()); 
	    } 
	    
	    return(result);
	}

	    
	private String enCloseString(String sString) {

		String tString = new String("");
		
		tString = sString.replace("\\", "\\\\");
		tString = tString.replace('"', '\"');
		tString = tString.replace("'", "\'");
		tString = tString.replace("\n", "\\n");
		
	 	tString = '"'+tString+'"';
	 	
	  	return tString;
	  }

}

