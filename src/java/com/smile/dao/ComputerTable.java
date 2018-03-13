package com.smile.dao;

import com.mysql.jdbc.DatabaseMetaData;
import com.smile.model.ComputerData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ComputerTable {

    private final String dropTable = "DROP TABLE computer ";
	private final String createTable = "CREATE TABLE computer (" +
	    "    computer_id     VARCHAR(5) NOT NULL"     +
	    "  , branch_id         VARCHAR(5) NOT NULL"     +
        "  , room_no          VARCHAR(3) NOT NULL"     +
        "  , song_no           VARCHAR(6) NOT NULL";

	private final String insertRecord = "insert into computer (computer_id , branch_id , room_no , song_no ) " +
			"values(?,?,?,?)";
	
	private final String basicQuery = new String("select *  from computer ");
    private final String rowsQuery = new String("select count(*) as rowCount  from computer ");
	
    private final Integer pageSize   = 20;
    
	private Integer recordsOfQuery  = 0;
	private Integer currentRecordNo = 0;
	private Integer currentPageNo   = 0;
	private Integer totalPageOfQuery = 0;
	private ComputerData currentRecord   = null;
	private String queryCondition   = new String("");
	
	private Statement stat = null;
	private ResultSet rs = null;
	private PreparedStatement pst = null;

	private Connection dbConn = null;
	
    public ComputerTable(Connection dbConn) {

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
			// check if table is there
			ResultSet tables = dbm.getTables(null, null, "computer", null);

			if (tables.next()) {
				// Table exists
                result = true;
			}
			else {
				// Table does not exist
				// Create Table
				// createTable();
			}
			
			currentRecord = new ComputerData();
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

	public int insertOneRecordTable()
	{ 
		int result = -1;
		
        try
        { 
          result = 1;
		
	      pst = dbConn.prepareStatement(insertRecord);
	      
	      pst.setString(1, currentRecord.getComputer_id());
	      pst.setString(2, currentRecord.getBranch_id());
          pst.setString(3, currentRecord.getRoom_no());
          pst.setString(4, currentRecord.getSong_no());

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
	public int updateOneRecordTable(String orgKey_id)
	{ 
		int result = -1;
		
		String regex = new String("[0-9]+");
		String updatedbSQL;
		
		if ( (currentRecord.getComputer_id()==null) || (currentRecord.getComputer_id().isEmpty()) )
		{
			return(result);
		}
		
		if (!orgKey_id.matches(regex)) {
			return(result);
		}

		updatedbSQL = "UPDATE computer set computer_id="+enCloseString(currentRecord.getComputer_id())
		             +",branch_id="+enCloseString(currentRecord.getBranch_id())
                     +",room_no="+enCloseString(currentRecord.getRoom_no())
                     +",song_no="+enCloseString(currentRecord.getSong_no())

		             +" where computer.computer_id="+enCloseString(orgKey_id);
        
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

    // update data
    public int updateSongNoOfComputerTable(String orgKey_id,String song_no)
    {
        int result = -1;

        String regex = new String("[0-9]+");
        String updatedbSQL;

        if ( (orgKey_id==null) || (orgKey_id.isEmpty()) )
        {
            return(result);
        }

        if (!orgKey_id.matches(regex)) {
            return(result);
        }

        updatedbSQL = "UPDATE computer set song_no="+enCloseString(song_no)
                +" where computer.computer_id="+enCloseString(orgKey_id);

        try
        {
            result = 1;
            pst = dbConn.prepareStatement(updatedbSQL);
            int res = pst.executeUpdate();
            if (res != 0) {
                getOneRecordByKeyId(orgKey_id);
            }
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
	public int deleteOneRecordTable(String orgKey_id)
	{
		int result = -1;
		
		String  regex = new String("[0-9]+");
		String  deletedbSQL;
		
		if ( (currentRecord.getComputer_id()==null) || (currentRecord.getComputer_id().isEmpty()) )
		{
			return(result);
		}
		
		if (!orgKey_id.matches(regex)) {
			return(result);
		}
		
		deletedbSQL = "DELETE FROM computer WHERE computer.computer_id="+enCloseString(orgKey_id);
        
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

	// one record by no
	public int getOneRecordByKeyId(String orgKey_id)
	{ 
		String stab;
		int    result=-1;
        
		stab = basicQuery
			  +" where computer.computer_id="+enCloseString(orgKey_id.trim()) ;
		
	    try
	    {
	      stat = dbConn.createStatement();
	      ResultSet rrs   = stat.executeQuery(stab);
          
	      if (rrs.next()) // Key_id is primary key, so there is only one record or nothing
	      { 
	    	  result = 1 ;
	    	  currentRecord.setComputer_id(rrs.getString("computer.computer_id"));
	    	  currentRecord.setBranch_id(rrs.getString("computer.branch_id"));
              currentRecord.setRoom_no(rrs.getString("computer.room_no"));
              currentRecord.setSong_no(rrs.getString("computer.song_no"));
	      }
	      else
	      {
	    	  currentRecord.initiateRecordData();  // empty the data of Record
	      }
	    }
	      
		catch(SQLException e) 
		{ 
			System.out.println("QueryDB Exception :" + e.toString()); 
	    	currentRecord.initiateRecordData();  // empty the data of Record
		} 
	    finally 
	    { 
	    	Close();
	    }
	    
	    return(result);
	}
	
	// one language by name
	public List<ComputerData> selectRecordsByName(String name)
	{
        String temp = new String(queryCondition);
		queryCondition = " where trim(computer.computer_na)="+enCloseString(name.trim()) + queryCondition;
        List<ComputerData> records = getNextPageOfTable();
        queryCondition = temp;
	    return(records);
	}	

    public List<ComputerData> getNextPageOfTable() {
    	List<ComputerData> records = new ArrayList<ComputerData>();
    	
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
            				ComputerData record = new ComputerData();
            				record.setComputer_id(rs.getString("computer.computer_id"));
            				record.setBranch_id(rs.getString("computer.branch_id"));
                            record.setRoom_no(rs.getString("computer.room_no"));
                            record.setSong_no(rs.getString("computer.song_no"));

            				records.add(record);
        			
            				i++;
            			}
            			else {
            				i = pageSize;
            			}
            		}

                    if (records.size()>0) {
                        setCurrentRecord(records.get(0));
                    }
            	}
        	}
        	
        } catch (SQLException e) {
            e.printStackTrace();
            return records;
        }
        
        return records;
    }


    public List<ComputerData> getFirstPageOfTable() {
        currentPageNo = 0;
        return getNextPageOfTable();
    }
    
    public List<ComputerData> getPreviousPageOfTable() {
    	currentPageNo -= 2;
    	return getNextPageOfTable();
    }

    public List<ComputerData> getLastPageOfTable() {
        currentPageNo = totalPageOfQuery--;
        return getNextPageOfTable();
    }

    public void setCurrentRecord(ComputerData record) {
  	  	this.currentRecord.setComputer_id(record.getComputer_id());
  	  	this.currentRecord.setBranch_id(record.getBranch_id());
        this.currentRecord.setRoom_no(record.getRoom_no());
        this.currentRecord.setSong_no(record.getSong_no());
    }
    
    public ComputerData getCurrentRecord() {
    	return this.currentRecord;
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
    
    public void reCalculatePageNo() {
    	String cc = new String("select count(*) as rowCount from computer "+queryCondition);
    	if (queryCondition.trim()=="") {
    		cc = cc + " Where computer.computer_id<="+enCloseString(currentRecord.getComputer_id());
    	}
    	else {
    			cc = cc + " and "+"where lang_no<="+enCloseString(currentRecord.getComputer_id());
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
	      if(dbConn!=null)
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

