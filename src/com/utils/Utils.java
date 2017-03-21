package com.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

/**
 * 这个是遍历问价你的小程序
 * 
 * @author hdy
 *
 */
public class Utils {

	// 这个是用来判断循环什么时候结束用的
	// 因为我使用了while.如果我想退出程序必须要设定一个条件.让它退出循环
	// 我们先默认设置为true
	private static boolean flag = true;

	// 记录文件夹的数量
	private static long folder = 0l;

	// 记录文件的数量
	private static long fileNum = 0l;

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(System.in);
		while (flag) {
			System.out.println("下面请输入您的文件夹地址(注意是文件夹地址.而不是文件地址)");
			System.out.println("请输入您的地址(输入q退出):");
			// 获取到用户输入的内容
			String line = scanner.nextLine();
			// 判断一下是否为空,或者是空的输入.
			if (line == null || line.isEmpty()) {
				// continue的意思就是让循环跳过下面的代码.直接让当前的循环从头开始
				// 重新运行一遍
				continue;
			}
			if("q".equals(line)){
				//判断是否要退出
				flag=false;
				continue;
			}
			// 如果不是就开始调用下面写的方法了
			getComputerNum(line);
		}
	}

	/**
	 * 这个函数的作用就是获取当前电脑中某一个位置下的文件夹和文件的数量
	 * 
	 * @param path
	 *            就是你要文件夹地址
	 * @throws FileNotFoundException
	 */
	public static void getComputerNum(String path) throws FileNotFoundException {
		// 声明两个变量.记录查询文件数量的开始时间和结束时间.记录运行的额时间
		long start = 0l;
		long end = -0l;
		// 通过这个路径地址找到得到File类对象
		// 这是第一步.我们先把这个地址变成一个文件类.然后在对文件类进行一个处理
		File file = new File(path);
		// 我们先判断这个路径是否存在.调用file类当中的函数.判断是否存在
		if (file.exists()) {
			// 如果存在,在判断是否为文件夹.因为只有文件夹才能进行遍历.
			if (file.isDirectory()) {
				// 这里面就是遍历的主代码了
				// 这里记录开始的时间
				start = System.currentTimeMillis();
				// 这里就是遍历文件和文件夹的主函数的调用了
				find(file);
				// 这里记录结束的时间
				end = System.currentTimeMillis();
				System.out.println("一共存在文件:" + fileNum + "个");
				System.out.println("一共存在文件夹:" + folder + "个");
				System.out.println("运行时间为:" + (end - start)+"豪秒");
				System.out.println("遍历结束!");
			} else {
				// 如果文件不是文件夹而是文件.同样的我们也抛出一个异常
				throw new RuntimeErrorException(null, "您输入的文件地址是一个文件而不是文件夹!");
			}
		}else{
			// 如果文件不存在,抛出一个异常给外界.告诉他文件不存在
			// 我这里抛出的java中自带的异常.是文件不存在异常
			throw new FileNotFoundException("您输入的文件不存在");
		}

	}

	/**
	 * 遍历文件夹和文件的函数
	 * 
	 * @param path
	 *            代表当前的文件夹地址
	 */
	public static void find(File path) {
		//获取当前文件夹下所有的文件和文件夹集合
		File[] files = path.listFiles();
		//进行遍历
		for (int i = 0; i < files.length; i++) {
			//获取对象
			File temp = files[i];
			//判断是否为文件夹
			if (temp.isDirectory()) {
				//如果是文件夹数量+1
				folder++;
				//并且递归自己本身
				find(temp);
			} else {
				//如果是文件,那么文件数量+1
				fileNum++;
			}
		}
	}

}
