package com.nflabs.zeppelin.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestUtil {
	
	public static File createTmpDir() throws Exception {
		File tmpDir = new File(System.getProperty("java.io.tmpdir")+"/ZeppelinLTest_"+System.currentTimeMillis());
		tmpDir.mkdir();
		return tmpDir;
		
	}
	/*
	private static final String HADOOP_DIST="http://apache.mirror.cdnetworks.com/hadoop/common/hadoop-1.2.1/hadoop-1.2.1-bin.tar.gz";
	//private static final String HADOOP_DIST="http://www.us.apache.org/dist/hadoop/common/hadoop-1.2.1/hadoop-1.2.1-bin.tar.gz";
	
	public static void getHadoop() throws MalformedURLException, IOException{
		setEnv("HADOOP_HOME", new File("./target/hadoop-1.2.1").getAbsolutePath());
		if(new File("./target/hadoop-1.2.1").isDirectory()) return;
		//System.out.println("Downloading a hadoop distribution ... it will take a while");
		//FileUtils.copyURLToFile(new URL(HADOOP_DIST), new File("/tmp/zp_test_hadoop-bin.tar.gz"));
		System.out.println("Unarchive hadoop distribution ... ");
		new File("./target").mkdir();
		Runtime.getRuntime().exec("tar -xzf /tmp/zp_test_hadoop-bin.tar.gz -C ./target");		
	}
	*/
	
	public static void delete(File file){
		if(file.isFile()) file.delete();
		else if(file.isDirectory()){
			File [] files = file.listFiles();
			if(files!=null && files.length>0){
				for(File f : files){
					delete(f);
				}
			}
			file.delete();
		}
	}
	
	public static void stringToFile(String string, File file) throws IOException{
		FileOutputStream out = new FileOutputStream(file);
		out.write(string.getBytes());
		out.close();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setEnv(String k, String v) {
		Map<String, String> newenv = new HashMap<String, String>();
		newenv.put(k, v);
	  try {
	        Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
	        Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
	        theEnvironmentField.setAccessible(true);
            Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
	        env.putAll(newenv);
	        Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
	        theCaseInsensitiveEnvironmentField.setAccessible(true);
	        Map<String, String> cienv = (Map<String, String>)     theCaseInsensitiveEnvironmentField.get(null);
	        cienv.putAll(newenv);
	  } catch (NoSuchFieldException e) {
	      try {
	        Class[] classes = Collections.class.getDeclaredClasses();
	        Map<String, String> env = System.getenv();
	        for(Class cl : classes) {
	            if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
	                Field field = cl.getDeclaredField("m");
	                field.setAccessible(true);
	                Object obj = field.get(env);
	                Map<String, String> map = (Map<String, String>) obj;
	                map.clear();
	                map.putAll(newenv);
	            }
	        }
	      } catch (Exception e2) {
	        e2.printStackTrace();
	      }
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    } 
	}
}
