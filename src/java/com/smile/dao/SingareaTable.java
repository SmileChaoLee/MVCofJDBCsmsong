package com.smile.dao;

import com.mysql.jdbc.DatabaseMetaData;
import com.smile.model.SingareaData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SingareaTable {

    private final String dropTable = "DROP TABLE singarea ";
	private final String createTable = "CREATE TABLE singarea (" +
	    "    area_ty     VARCHAR(1)  "     +
	    "  , area_na     VARCHAR(14) "    +
        "  , area_en     VARCHAR(28) ";

	private final String insertRecord = "insert into singarea (area_ty , area_na , area_en) " +
			"values(?,?,?)";
	
	private final String basicQuery = new String("select * from singarea ");
    private final String rowsQuery = new String("select count(*) as rowCount from singarea ");
	
    private final Integer pageSize   = 20;
	
	private Integer recordsOfQuery  = 0;
	private Integer currentRecordNo = 0;
	private Integer currentPageNo   = 0;
	private Integer totalPageOfQuery = 0;
	private SingareaData singareaRecord   = null;
	private String queryCondition   = new String("");

	private Statement stat = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;
	
	private Connection dbConn = null;
	
    public SingareaTable(Connection dbConn) {
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
			// check if "singarea" table is there
			ResultSet tables = dbm.getTables(null, null, "singarea", null);

			if (tables.next()) {
				// Table exists
                result = true;
			}
			else {
				// Table does not exist
				// Create singarea Table
				// createTable();
			}
			
			singareaRecord = new SingareaData();
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

	public int insertOneRecordSingareaTable()
	{ 
		int result = -1;
		
        try
        { 
          result = 1;
		
	      pst = dbConn.prepareStatement(insertRecord);
	      
	      pst.setString(1, singareaRecord.getArea_ty());
	      pst.setString(2, singareaRecord.getArea_na());
          pst.setString(3, singareaRecord.getArea_en());

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
	public int updateOneRecordSingareaTable(String orgArea_ty)
	{ 
		int result = -1;
		
		String regex = new String("[0-9]+");
		String updatedbSQL;
		
		if ( (singareaRecord.getArea_ty()==null) || (singareaRecord.getArea_ty().isEmpty()) )
		{
			return(result);
		}
		
		if (!orgArea_ty.matches(regex)) {
			return(result);
		}

		updatedbSQL = "UPDATE singarea set area_ty="+enCloseString(singareaRecord.getArea_ty())
		             +",area_na="+enCloseString(singareaRecord.getArea_na())
                     +",area_en="+enCloseString(singareaRecord.getArea_en())
		             
		             +" where singarea.area_ty="+enCloseString(orgArea_ty);
        
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
	public int deleteOneRecordSingareaTable(String orgArea_ty)
	{
		int result = -1;
		
		String  regex = new String("[0-9]+");
		String  deletedbSQL;
		
		if ( (singareaRecord.getArea_ty()==null) || (singareaRecord.getArea_ty().isEmpty()) )
		{
			return(result);
		}
		
		if (!orgArea_ty.matches(regex)) {
			return(result);
		}
		
		deletedbSQL = "DELETE FROM singarea WHERE area_ty="+enCloseString(orgArea_ty);
        
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

	// one singer area by no
	public int getOneRecordSingareabyAreaty(String orgArea_ty)
	{ 
		String stab;
		int    result=-1;
        
		stab = basicQuery
			  +" where singarea.area_ty="+enCloseString(orgArea_ty.trim()) ;
		
	    try
	    {
	      stat = dbConn.createStatement();
	      ResultSet rrs   = stat.executeQuery(stab);
          
	      if (rrs.next()) // area_ty is primary key, so there is only one record or nothing
	      { 
	    	  result = 1 ;
	    	  singareaRecord.setArea_ty(rrs.getString("singarea.area_ty"));
	    	  singareaRecord.setArea_na(rrs.getString("singarea.area_na"));
              singareaRecord.setArea_en(rrs.getString("singarea.area_en"));
	      }
	      else
	      {
	    	  singareaRecord.initiateSingarearecord();  // empty the data of sin gareaRecord
	      }
	    }
	      
		catch(SQLException e) 
		{ 
			System.out.println("QueryDB Exception :" + e.toString()); 
	    	singareaRecord.initiateSingarearecord();  // empty the data of singareaRecord
		} 
	    finally 
	    { 
	    	Close();
	    }
	    
	    return(result);
	}
	
	// one singer area by name
	public List<SingareaData> selectRecordsSingareabyAreaname(String areana)
	{
        String temp = new String(queryCondition);
		queryCondition = " where trim(singarea.area_na)="+enCloseString(areana.trim()) + queryCondition;
        List<SingareaData> singareas = getNextPageOfSingareaTable();
        queryCondition = temp;
	    return(singareas);
	}	

    public List<SingareaData> getNextPageOfSingareaTable() {
    	List<SingareaData> singareas = new ArrayList<SingareaData>();
    	
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
            				SingareaData singarea = new SingareaData();
            				singarea.setArea_ty(rs.getString("singarea.area_ty"));
            				singarea.setArea_na(rs.getString("singarea.area_na"));
                            singarea.setArea_en(rs.getString("singarea.area_en"));

            				singareas.add(singarea);
        			
            				i++;
            			}
            			else {
            				i = pageSize;
            			}
            		}

                    if (singareas.size()>0) {
                        setSingarearecord(singareas.get(0));
                    }
            	}
        	}
        	
        } catch (SQLException e) {
            e.printStackTrace();
            return singareas;
        }
        
        return singareas;
    }


    public List<SingareaData> getFirstPageOfSingareaTable() {
        currentPageNo = 0;
        return getNextPageOfSingareaTable();
    }
    
    public List<SingareaData> getPreviousPageOfSingareaTable() {
    	currentPageNo -= 2;
    	return getNextPageOfSingareaTable();
    }

    public List<SingareaData> getLastPageOfSingareaTable() {
        currentPageNo = totalPageOfQuery--;
        return getNextPageOfSingareaTable();
    }

    public void setSingarearecord(SingareaData singareaRecord) {
  	  	this.singareaRecord.setArea_ty(singareaRecord.getArea_ty());
  	  	this.singareaRecord.setArea_na(singareaRecord.getArea_na());
        this.singareaRecord.setArea_en(singareaRecord.getArea_en());
    }
    
    public SingareaData getSingarearecord() {
    	return this.singareaRecord;
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
    	String cc = new String("select count(*) as rowCount from singarea "+queryCondition);
    	if (queryCondition.trim()=="") {
    		cc = cc + " Where area_ty<="+enCloseString(singareaRecord.getArea_ty());
    	}
    	else {
    			cc = cc + " and "+"where area_ty<="+enCloseString(singareaRecord.getArea_ty());
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

