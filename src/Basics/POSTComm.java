package Basics;


import java.io.*;
import java.net.*;


// GET : url + ? + param
// POST : submit form, params are invisible 

public class POSTComm
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		try
		{
			String str = URLEncoder.encode("ÇÑ±Û", "UTF-8");
						
			URL url;
			
			url = new URL("http://localhost:8080/boardInfo/listAll");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setDoInput(true);  // for input
			conn.setDoOutput(true); // for output
			
			conn.setUseCaches(false);  // do not use cache, just get fresh data
			conn.setReadTimeout(20000); // 20 min
			
			conn.setRequestMethod("POST"); ////// request form : only data w/o header
			
			OutputStream os = conn.getOutputStream();
			OutputStreamWriter writer = new OutputStreamWriter(os);
			
			writer.write("title = " + str);
			writer.write("&subTitle = " + str + "2");
			writer.close();
			os.close();
			
			
			StringBuffer sb = new StringBuffer();
			BufferedReader bfrd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			while(true)
			{
				String line = bfrd.readLine();
				
				if(line == null)
					break;
				
				sb.append(line + "\n");
			}
			
			bfrd.close();
			conn.disconnect();
			
			String getXml = sb.toString();
			System.out.println(getXml);
			
		} 
		catch (UnsupportedEncodingException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
		
		
	}

}
