package ru.bgcrm.plugin.bgbilling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import ru.bgcrm.model.BGException;
import ru.bgcrm.util.ParameterMap;
import ru.bgcrm.util.Setup;

public class DBInfoManager
{
	private static final Logger log = Logger.getLogger( DBInfoManager.class );
	
    private static DBInfoManager instance;
    
	private Map<String, DBInfo> dbInfoMap = new HashMap<String, DBInfo>();
	private List<DBInfo> dbInfoList = new ArrayList<DBInfo>();
	
	private static final Set<String> SUPPORTED_VERSIONS = new HashSet<String>( Arrays.asList( new String[]{ "5.1", "5.2", "6.0", "6.1", "6.2", "7.0", "7.1", "7.2", "8.0"} ) ); 

    private DBInfoManager( Setup setupData )
    {
        final String prefix = "bgbilling:server.";
		final String prefixOld = "bgbilling.";
		
		for( Map.Entry<Integer, ParameterMap> me : setupData.subIndexed( prefix, prefixOld ).entrySet() )
        {        
        	ParameterMap params = me.getValue();
        	
        	//TODO: Исторически сложилось, что биллинги обозначаются строками 
        	try
            {
        		DBInfo dbInfo = new DBInfo( params.get( "id" ) );
    	        dbInfo.setUrl( params.get( "url" ) );
    	        dbInfo.setTitle( params.get( "title" ) );
    	        dbInfo.setVersion( params.get( "version", "" ) );
    	        dbInfo.setSetup( setupData.sub( prefix + me.getKey() + ".", prefixOld + me.getKey() + "." ) );
    	        
    	        if( !SUPPORTED_VERSIONS.contains( dbInfo.getVersion() ) )
    	        {
    	        	throw new BGException( "Unsupported billing version: " + dbInfo.getVersion() );
    	        }
    	        
    	        dbInfoMap.put( dbInfo.getId(), dbInfo );
    	        dbInfoList.add( dbInfo );
    	        
            }
            catch( Exception e )
            {
	            log.error( e.getMessage() );
            }	        
        }
    }

    public static void flush()
    {
    	// закрытие соединений к БД
    	if( instance != null )
    	{
    		for( DBInfo dbInfo : instance.getDbInfoList() )
    		{
    			dbInfo.getConnectionPool().close();
    		}
    	}
    	instance = null;
    }
    
    public static DBInfoManager getInstance()
    {
        if ( instance == null )
        {
            instance = new DBInfoManager( Setup.getSetup() );
        }
        return instance;
    }
    
    public static DBInfo getDbInfo( String billingId )
    	throws BGException
    {
    	DBInfo dbInfo = DBInfoManager.getInstance().getDbInfoMap().get( billingId );
    	if( dbInfo == null )
    	{
    		throw new BGException( "Не найден биллинг: " + billingId );
    	}
    	return dbInfo;
    }
   
	public List<DBInfo> getDbInfoList()
    {
    	return dbInfoList;
    }

	public Map<String, DBInfo> getDbInfoMap()
    {
    	return dbInfoMap;
    }
}