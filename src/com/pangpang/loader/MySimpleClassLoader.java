package com.pangpang.loader;

import com.pangpang.util.CommonFindClass;

/**
 * CustomClassLoader
 * 演示--自定义类加载器
 * @author pangpang
 *
 */
public class MySimpleClassLoader extends ClassLoader{
	private String byteCode_Path;

	public MySimpleClassLoader(String byteCode_Path) {
		this.byteCode_Path = byteCode_Path;
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {

		byte[] value = CommonFindClass.commonFindClass(byteCode_Path, name);
		System.out.println("正在调用自定义类加载器");

		//将byte数组转化为一个类的Class对象实例
		return defineClass(null, value, 0, value.length);

	}
	//测试
	public static void main(String[] args) throws ClassNotFoundException {

		System.out.println(System.getProperty("sun.boot.class.path"));
		System.out.println(System.getProperty("java.ext.dirs"));

		MySimpleClassLoader loader = new MySimpleClassLoader("E:\\H2\\gitTest\\");
		System.out.println("当前类的父类加载器"+loader.getParent().getClass().getName());
		System.out.println("加载目标类的类加载器"+loader.loadClass("LoggerAdvice").getClassLoader().getClass().getName());

	}
}
