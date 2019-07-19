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
	  //��ȡ��ҵ�ʸ�֤�����룬δ�жϷ��ص�ֵ�Ƿ�Ϊ��ҵ�ʸ�֤
	  public  String  doPost(String last4Number,String realName,String path) throws IOException{  
		  	CookieManager manager = new CookieManager();
	        //����cookie���ԣ�ֻ��������Ի���������cookie����������Internet���������������͵�cookie
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
		            System.out.println("��һ������ conn.getResponseCode()="+connection.getResponseCode());  
		            cookie1=manager.getCookieStore().getCookies().get(0).toString();


	

		            
		            
		            
		            Map<String,List<String>> map=connection.getHeaderFields();  
		            Set<String> set=map.keySet();  
		            for (Iterator iterator = set.iterator(); iterator.hasNext();) {  
		                String key = (String) iterator.next();  
		                if (key!=null&&key.equals("Set-Cookie")) {  
		                    //System.out.println("key=" + key+",��ʼ��ȡcookie");  
		                    List<String> list = map.get(key);  
		                    StringBuilder builder = new StringBuilder();  
		                    for (String str : list) {  
		                        builder.append(str).toString();  
		                    }  
		                    firstCookie=builder.toString();  
		                    //System.out.println("��һ�εõ���cookie="+firstCookie);  
		                }  
		            }  
		            manager.getCookieStore().getCookies().get(0);
		            System.out.println("��һ��cookie="+cookie1); 
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
		        //File imageFile = new File("D:\\Documents\\Pictures\\4033.png");//ͼƬλ��  
	            //{16,53
		        //ImageIO.write(textImage, "png", new File("\\tess4j\\\\Pictures\\timg_temp.jpg"));
		       /* ImageIO.write(textImage, "png", new File("D:/107D/soft/java/Pictures/timg_temp.jpg"));*/
	            ITesseract instance = new Tesseract();
	            instance.setDatapath("/usr/local/share/");
	          /* instance.setDatapath("D:\\Tess4J-3.4.8-src\\Tess4J\\tessdata");*/
	            instance.setLanguage("eng");//ѡ���ֿ��ļ���ֻ��Ҫ�ļ���������Ҫ��׺����  
	            valCode = instance.doOCR(new File("/107D/soft/java/Pictures/test.jpg"));//��ʼʶ��  
	        } catch (TesseractException e) {  
	            e.printStackTrace();  
	        }  
	        
		       // URL url = new URL("http://iir.circ.gov.cn/web/baoxyx!searchInfo.html");
		    	URL url = new URL("http://iir.circ.gov.cn/ipq/insurance.do?checkcaptch");
		    	//http://iir.circ.gov.cn/ipq/insurance.do?query
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
		        connection.setDoOutput(true); //�������������Ϊtrue,Ĭ��false (post �����������ķ�ʽ��ʽ�Ĵ��ݲ���)  
		        connection.setDoInput(true); // ��������������Ϊtrue  
		        connection.setRequestMethod("POST"); // ��������ʽΪpost  
		        connection.setUseCaches(false); // post���󻺴���Ϊfalse  
		        connection.setInstanceFollowRedirects(true); //// ���ø�HttpURLConnectionʵ���Ƿ��Զ�ִ���ض���  
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
		        
		        System.out.println("�ڶ���cookie="+JSESSIONID); 
		 /*     connection.setRequestProperty("Cookie", "personid="+"PE_FIN15140421"+"; path=/; domain=.iir.circ.gov.cn; Expires=Tue, 19 Jan 2038 03:14:07 GMT;JSESSIONID="+JSESSIONID+"; path=/; domain=.iir.circ.gov.cn; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
		  */    connection.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
		      connection.setRequestProperty("Accept-Encoding", "gzip, deflate"); 
		      connection.setRequestProperty("connection", "Keep-Alive");  
		       connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36");
		        // ��������ͷ����ĸ������� (����Ϊ�������ݵ�����,����Ϊ����urlEncoded�������from����)  
		        // application/x-javascript text/xml->xml���� application/x-javascript->json���� application/x-www-form-urlencoded->������  
		   //  //multipart/form-data 
		       connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
		        connection.connect();  
		        // �������������,�����������������Я���Ĳ���,(�������Ϊ?���������)  
		        
		        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz");
		        String date = format.format(new Date())+"GMT 0800 (�й���׼ʱ��)";
		        DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());  
		        String  parm= "cardno="+last4Number.trim()+"&ualificano=&practicecode=&captcha="+valCode.trim()+"&name=" + URLEncoder.encode(realName, "UTF-8"); //URLEncoder.encode()����  Ϊ�ַ������б���             
		        //String parm = "id_card=2452&certificate_code=&valCode="+valCode+"&name=" + URLEncoder.encode("��ƽ", "UTF-8"); //URLEncoder.encode()����  Ϊ�ַ������б���             

		        System.out.println(parm);
		        dataout.writeBytes(parm);   
		        dataout.flush();   
		        dataout.close(); // ��Ҫ���׺��Բ��� (�ر���,�м�!)             
		        // ���ӷ�������,�����������Ӧ  (�����ӻ�ȡ������������װΪbufferedReader)  
		        connection.setConnectTimeout(2000000000);
		        BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));  
		        String line;  
		        StringBuilder sb = new StringBuilder(); // �����洢��Ӧ����             
		        // ѭ����ȡ��,��������β��  
		        while ((line = bf.readLine()) != null) {  
		        //	System.out.println(bf.readLine());  
		            sb.append(line);  
		        }  
		        bf.close();    // ��Ҫ���׺��Բ��� (�ر���,�м�!)  
		        connection.disconnect(); // ��������  
		        System.out.println(sb.toString()); 
		        String shtml=sb.toString();
		        InsuredBean certmsg=new InsuredBean();
		        
		        if(!shtml.contains("�ܱ�Ǹ��û���ҵ����������ļ�¼")){
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
				 if(count==5){  //�ʸ�֤��
					 insuredBean.setCertificate(val);
				 }else if(count==6){  //�ʸ�֤״̬
					 insuredBean.setCertState(val);
				 }else if(count==8){  //ִҵ֤���
					 insuredBean.setJobcertificate(val);
				 }else if(count==9){  //ִҵ֤״̬
					 insuredBean.setJobcertState(val);
				 }else if(count==10){   //��Ч��ֹ����
					 insuredBean.setExpiryTime(val);
				 }else if(count==13){   //������˾
					 insuredBean.setCompany(val);
				 }else if(count==12){   //��������
					 insuredBean.setArea(val);
				 }
			}
			
			return insuredBean;
		}
		public static void main(String[] args) throws IOException {
		
			CertificateUtil certUtil=new CertificateUtil();
			String insured=certUtil.doPost("0042","��ϼ","D:/Tess4J-3.4.8-src/Tess4J/");
		
				System.out.println(insured);
			
		}
}
