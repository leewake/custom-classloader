package com.pangpang.loader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.pangpang.security.Use3DES;
import com.pangpang.util.CommonFindClass;

/**
 * 自定义类加载器
 * 对加密后的字节码进行解密
 * @author pangpang
 *
 */
public class MyClassLoader extends ClassLoader {

	/**
	 * 原 字节码路径
	 */
	private String byteCode_Path;
	
	/**
	 * 密钥
	 */
	private byte[] key;
	
	public MyClassLoader(String byteCode_Path, byte[] key) {
		this.byteCode_Path = byteCode_Path;
		this.key = key;
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {

		byte[] value = CommonFindClass.commonFindClass(byteCode_Path, name);

		//将加密后的字节码--解密
		value = Use3DES.decrypt(key, value);

		//将byte数组转化为一个类的Class对象实例
		Class<?> tmp = defineClass( null, value, 0, value.length);

		System.out.println(tmp.toString());
		
		return tmp;
	}
	
	public static void main(String[] args) {
		BufferedInputStream in = null;
		try {
			//把原字节码文件读到src字节数组
			in = new BufferedInputStream(new FileInputStream("E:\\H2\\gitTest\\SimpleTest.class"));
			byte[] src = new byte[in.available()];
			in.read(src);
			in.close();
			
			byte[] key = "01234567899876543210abcd".getBytes();//密钥24位
			
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("E:/H2/SimpleTest.class"));
			
			//将字节码  加密后，写到"E:\\GitCode"
			out.write(Use3DES.encrypt(key, src));
			out.close();
			
			//创建自定义类加载器，加载目标字节码
			MyClassLoader loader = new MyClassLoader("E:/H2/", key);
			System.out.println(loader.loadClass("SimpleTest").getClassLoader().getClass().getName());
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
