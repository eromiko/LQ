package lq.util;

import java.sql.Blob;
import java.sql.SQLException;

import org.apache.axis.encoding.Base64;

/**
 * @descript BLOB/BASE64相互转化工具类
 * @author ***
 *
 */
public class BlobAndBase64Util {
    // BLOB 转 BASE64
    /*
     * @Description ：将BLOB类型数据转化成BASE64类型
     * @param : blobDate ———— blob类型的数据，直接扔进去就好
     */
	public static String getBase64InBlob(Object objDate) {
        String result=new String();
	    Blob blob = (Blob)objDate;
	    try{
	      byte[] valueArr=null;
	    if(null!=blob){
		       valueArr=blob.getBytes(1L,(int)blob.length());
		    }
      result=Base64.encode(valueArr);
	   }catch (SQLException e){
			e.printStackTrace();
	    }
        return result;
    }

}