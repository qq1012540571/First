package com.xiaoqiang.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;




public class CertificateUtil {
	  //获取从业资格证件号码，未判断返回的值是否为从业资格证
	  public  String  doPost(String last4Number,String realName,String path) throws IOException{  
		  	CookieManager manager = new CookieManager();
	        //设置cookie策略，只接受与你对话服务器的cookie，而不接收Internet上其它服务器发送的cookie
	        manager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
	        CookieHandler.setDefault(manager);
	        URL url1 = null;
	        int imageNumber = 0;
	        String valCode = null;
	        String cookie1=null;
			//map.put("valCode", 123);
			 try {
				 	String firstCookie=null;
		            url1 = new URL("http://iir.circ.gov.cn/ipq/captchacn.svl?time=1529552347868");
		            HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
		            String basic = Base64.getEncoder().encodeToString("infcn:123456".getBytes());
		            connection.setRequestProperty("Proxy-authorization", "Basic " + basic);        
		            System.out.println("第一次请求 conn.getResponseCode()="+connection.getResponseCode());  
		            cookie1=manager.getCookieStore().getCookies().get(0).toString();


	

		            
		            
		            
		            Map<String,List<String>> map=connection.getHeaderFields();  
		            Set<String> set=map.keySet();  
		            for (Iterator iterator = set.iterator(); iterator.hasNext();) {  
		                String key = (String) iterator.next();  
		                if (key!=null&&key.equals("Set-Cookie")) {  
		                    //System.out.println("key=" + key+",开始获取cookie");  
		                    List<String> list = map.get(key);  
		                    StringBuilder builder = new StringBuilder();  
		                    for (String str : list) {  
		                        builder.append(str).toString();  
		                    }  
		                    firstCookie=builder.toString();  
		                    //System.out.println("第一次得到的cookie="+firstCookie);  
		                }  
		            }  
		            manager.getCookieStore().getCookies().get(0);
		            System.out.println("第一次cookie="+cookie1); 
		            //DataInputStream dataInputStream = new DataInputStream(url.openStream());
		            DataInputStream dataInputStream = new DataInputStream(connection.getInputStream());
		            File picture = new File("/107D/soft/java/Pictures");
					if (!picture.exists()) {
						picture.mkdirs();
					}
					String imageName =  "/107D/soft/java/Pictures/test.jpg";
		            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
		            ByteArrayOutputStream output = new ByteArrayOutputStream();
		            byte[] buffer = new byte[1024];
		            int length;
		            while ((length = dataInputStream.read(buffer)) > 0) {
		                output.write(buffer, 0, length);
		            }
		            byte[] context=output.toByteArray();
		            fileOutputStream.write(output.toByteArray());
		            dataInputStream.close();
		            fileOutputStream.close();
		        } catch (MalformedURLException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			try { 
				//convert("D:\\Documents\\Pictures\\test.jpg", "png", "D:\\Documents\\Pictures\\test.png");
				//File picture = new File("\\tess4j\\Pictures");
				//BufferedImage image = ImageIO.read(new File("\\tess4j\\Pictures\\test.jpg"));
			/*	BufferedImage image = ImageIO.read(new File("D:/107D/soft/java/Pictures/test.jpg"));
		        BufferedImage textImage = ImageHelper.convertImageToGrayscale(ImageHelper.getSubImage(image, 2, 2, 56, 16));
		        textImage = ImageHelper.convertImageToBinary(textImage);
		        textImage = ImageHelper.getScaledInstance(textImage, textImage.getWidth() * 1, textImage.getHeight() * 1);*/
		        //File imageFile = new File("D:\\Documents\\Pictures\\4033.png");//图片位置  
	            //{16,53
		        //ImageIO.write(textImage, "png", new File("\\tess4j\\\\Pictures\\timg_temp.jpg"));
		       /* ImageIO.write(textImage, "png", new File("D:/107D/soft/java/Pictures/timg_temp.jpg"));*/
	            ITesseract instance = new Tesseract();
	            instance.setDatapath("/usr/local/share/");
	          /* instance.setDatapath("D:\\Tess4J-3.4.8-src\\Tess4J\\tessdata");*/
	            instance.setLanguage("eng");//选择字库文件（只需要文件名，不需要后缀名）  
	            valCode = instance.doOCR(new File("/107D/soft/java/Pictures/test.jpg"));//开始识别  
	        } catch (TesseractException e) {  
	            e.printStackTrace();  
	        }  
	        
		       // URL url = new URL("http://iir.circ.gov.cn/web/baoxyx!searchInfo.html");
		    	URL url = new URL("http://iir.circ.gov.cn/ipq/insurance.do?checkcaptch");
		    	//http://iir.circ.gov.cn/ipq/insurance.do?query
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
		        connection.setDoOutput(true); //设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)  
		        connection.setDoInput(true); // 设置连接输入流为true  
		        connection.setRequestMethod("POST"); // 设置请求方式为post  
		        connection.setUseCaches(false); // post请求缓存设为false  
		        connection.setInstanceFollowRedirects(true); //// 设置该HttpURLConnection实例是否自动执行重定向  
		        StringBuffer cookieInfo=null;
		        List<HttpCookie> cookies = manager.getCookieStore().getCookies();
		        String personid="";
		        String JSESSIONID="";
		        for (HttpCookie cookie : cookies) {
		        if("personid".equals(cookie.getName()))
		        {
		        		personid=cookie.getValue();
		        		break;
		        }
		        if("JSESSIONID".equals(cookie.getName()))
		        {
		        	JSESSIONID=cookie.getValue();
	        		break;
		        }
		        
				}
		        
		        System.out.println("第二次cookie="+JSESSIONID); 
		 /*     connection.setRequestProperty("Cookie", "personid="+"PE_FIN15140421"+"; path=/; domain=.iir.circ.gov.cn; Expires=Tue, 19 Jan 2038 03:14:07 GMT;JSESSIONID="+JSESSIONID+"; path=/; domain=.iir.circ.gov.cn; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
		  */    connection.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
		      connection.setRequestProperty("Accept-Encoding", "gzip, deflate"); 
		      connection.setRequestProperty("connection", "Keep-Alive");  
		       connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36");
		        // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)  
		        // application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据  
		   //  //multipart/form-data 
		       connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
		        connection.connect();  
		        // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)  
		        
		        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz");
		        String date = format.format(new Date())+"GMT 0800 (中国标准时间)";
		        DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
		        String  parm= "cardno="+last4Number.trim()+"&ualificano=&practicecode=&captcha="+valCode.trim()+"&name=" + URLEncoder.encode(realName, "UTF-8"); //URLEncoder.encode()方法  为字符串进行编码             
		        //String parm = "id_card=2452&certificate_code=&valCode="+valCode+"&name=" + URLEncoder.encode("吴平", "UTF-8"); //URLEncoder.encode()方法  为字符串进行编码             

		        System.out.println(parm);
		        dataout.writeBytes(parm);   
		        dataout.flush();   
		        dataout.close(); // 重要且易忽略步骤 (关闭流,切记!)             
		        // 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)  
		        connection.setConnectTimeout(2000000000);
		        BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));  
		        String line;  
		        StringBuilder sb = new StringBuilder(); // 用来存储响应数据             
		        // 循环读取流,若不到结尾处  
		        while ((line = bf.readLine()) != null) {  
		        //	System.out.println(bf.readLine());  
		            sb.append(line);  
		        }  
		        bf.close();    // 重要且易忽略步骤 (关闭流,切记!)  
		        connection.disconnect(); // 销毁连接  
		        System.out.println(sb.toString()); 
		        String shtml=sb.toString();
		        InsuredBean certmsg=new InsuredBean();
		        
		        if(!shtml.contains("很抱歉，没有找到符合条件的记录")){
			        certmsg=getCertCard(shtml);
		        }
		        
		        return shtml;
		    } 
	  
	    public class InsuredBean{
	    	
	    	private String name;
	    	
	    	private String certificate;
	    	
	    	private String certState;
	    	
	    	private String jobcertificate;
	    	
	    	private String jobcertState;
	    	
	    	private String expiryTime;
	    	
	    	private String area;
	    	
	    	private String company;

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getCertificate() {
				return certificate;
			}

			public void setCertificate(String certificate) {
				this.certificate = certificate;
			}

			public String getCertState() {
				return certState;
			}

			public void setCertState(String certState) {
				this.certState = certState;
			}

			public String getJobcertificate() {
				return jobcertificate;
			}

			public void setJobcertificate(String jobcertificate) {
				this.jobcertificate = jobcertificate;
			}

			public String getJobcertState() {
				return jobcertState;
			}

			public void setJobcertState(String jobcertState) {
				this.jobcertState = jobcertState;
			}

			public String getExpiryTime() {
				return expiryTime;
			}

			public void setExpiryTime(String expiryTime) {
				this.expiryTime = expiryTime;
			}

			public String getCompany() {
				return company;
			}

			public void setCompany(String company) {
				this.company = company;
			}

			public String getArea() {
				return area;
			}

			public void setArea(String area) {
				this.area = area;
			}
	    	
	    	
	    	
	    	
	    }
	  
	  public  InsuredBean getCertCard(String htmlStr) throws FileNotFoundException, IOException{
			
		    InsuredBean insuredBean=new InsuredBean();
			Pattern pattern =Pattern.compile("<td[^<>]*>[^<>]*</td>");
			Matcher matcher=pattern.matcher(htmlStr);
			int count=0;
			String card="";
			while(matcher.find()){
				 ++count;
				 String val=matcher.group().replace("<td>", "").replace("</td>", "");
				 if(count==5){  //资格证书
					 insuredBean.setCertificate(val);
				 }else if(count==6){  //资格证状态
					 insuredBean.setCertState(val);
				 }else if(count==8){  //执业证编号
					 insuredBean.setJobcertificate(val);
				 }else if(count==9){  //执业证状态
					 insuredBean.setJobcertState(val);
				 }else if(count==10){   //有效截止日期
					 insuredBean.setExpiryTime(val);
				 }else if(count==13){   //所属公司
					 insuredBean.setCompany(val);
				 }else if(count==12){   //所属地区
					 insuredBean.setArea(val);
				 }
			}
			
			return insuredBean;
		}
		public static void main(String[] args) throws IOException {
		
			CertificateUtil certUtil=new CertificateUtil();
			String insured=certUtil.doPost("0042","范霞","D:/Tess4J-3.4.8-src/Tess4J/");
		
				System.out.println(insured);
			
		}
}
