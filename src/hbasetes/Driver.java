package hbasetes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
 


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.IOUtils;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Driver {
	
	public static void recur(Iterator<?> iter, JSONObject jsonOb){
		if( iter.hasNext() ){
			recur((Iterator<?>) iter.next(), jsonOb);
		}else{
			String key = (String)iter.next();
	        System.out.println(key + ":" + jsonOb.get(key));
		}
	}
	
	static String keyFam = "tweet";
	
	public static Map<String,String> 
		parseRecur(String prevKey,String keyFam,
				JSONObject json , Map<String,String> out,int depth) throws JSONException{
		 Iterator<String> keys = json.keys();
		 while(keys.hasNext()){
			 String key = keys.next();
			 String val = null;
			 try{
				 JSONObject value = json.getJSONObject(key);
				 depth = depth + 1;
//					 System.out.println(prevKey);
				 parseRecur(prevKey.concat("_" + key),key,value, out,depth);
			 }catch(Exception e){
				val = String.valueOf(json.get(key));
			 }

			 if(val != null){
				System.out.println("_");
				System.out.println("keyFamily : " + prevKey);
				System.out.println("keyColumn : " + key);
				System.out.println("keyValue  : " + val);
				out.put(key,val);
			 }
		 }
		 return out;
		}
	
	public static String readFile(String filename) {
	    String result = "";
	    try {
	        BufferedReader br = new BufferedReader(new FileReader(filename));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	        result = sb.toString();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}


	public static void main(String[] args) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		String url = "http://freemusicarchive.org/api/get/albums.json?api_key=60BLHNQCAOUFPIBZ&limit=5";
//        String json = IOUtils.toString(new URL(url));
      String json = readFile("C://Users//byan//Documents//BigData//twitter.json");
        String jsonTwitter = new String();
        System.out.println(json);
        // use the isxxx methods to find out the type of jsonelement. In our
        // example we know that the root object is the Albums object and
        // contains an array of dataset objects
        
        
        JSONObject jsonOb = new JSONObject(json);
        
        Map<String,String> out = new HashMap<String,String>();
        System.out.println(parseRecur("tweet","tweet",jsonOb, out,-1));
	}

}
