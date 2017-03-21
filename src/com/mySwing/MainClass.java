package com.mySwing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.management.RuntimeErrorException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 窗体化的文件和文件夹数量遍历器
 * 
 * @author hdy
 *
 */
public class MainClass {
	private static JFrame frame;
	// 记录文件夹的数量
	private static long folder = 0l;
	// 记录文件的数量
	private static long fileNum = 0l;
	private static JLabel jLabel;

	public static void main(String[] args) {
		createSwing();
	}

	public static void createSwing() {
		//设置默认界面外观
		JFrame.setDefaultLookAndFeelDecorated(true);
		//新建一个窗体
		frame = new JFrame("恶搞大王制文件和文件夹数量查询器");
		//设置窗体大小
		frame.setBounds(0, 0, 300, 300);
		// frame.setResizable(false);
		JButton jButton = new JButton("选择文件夹");
		//居中窗体
		frame.setLocationRelativeTo(null);
		// 设置按钮的监听器
		jButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
				// 这是选择格式,这里选择只能选择文件夹
				jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				//设置标签
				jFileChooser.showDialog(new JLabel(), "确认");
				//获取返回的file对象
				File file = jFileChooser.getSelectedFile();
				//判断是否为文件夹
				if (file != null && file.isDirectory()) {
					jLabel.setText("开始搜索,请耐心等待!");
					String absolutePath = file.getAbsolutePath();
					try {
						getComputerNum(absolutePath);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				} else {
					jLabel.setText("您选择的不是一个文件夹!请重新选择");
				}
			}
		});
		jButton.setBounds(0, 0, 100, 100);
		jLabel = new JLabel();
		jLabel.setText("请点击按钮选择一个文件夹!");
		//添加按钮到窗体
		frame.add(jButton);
		frame.add(jLabel);
		// 设置界面关闭程序进程自动结束
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置窗体可视.默认为false
		frame.setVisible(true);
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
				StringBuilder sb = new StringBuilder();
				sb.append("<html>").append("一共存在文件:" + fileNum + "个").append("<br/>").append("一共存在文件夹:" + folder + "个")
						.append("<br/>").append("运行时间为:" + (end - start) + "毫秒").append("<br/>").append("遍历结束!")
						.append("</html>");
				jLabel.setText(sb.toString());
			} else {
				// 如果文件不是文件夹而是文件.同样的我们也抛出一个异常
				throw new RuntimeErrorException(null, "您输入的文件地址是一个文件而不是文件夹!");
			}
		} else {
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
		// 获取当前文件夹下所有的文件和文件夹集合
		File[] files = path.listFiles();
		// 进行遍历
		for (int i = 0; i < files.length; i++) {
			// 获取对象
			File temp = files[i];
			// 判断是否为文件夹
			if (temp.isDirectory()) {
				// 如果是文件夹数量+1
				folder++;
				// 并且递归自己本身
				find(temp);
			} else {
				// 如果是文件,那么文件数量+1
				fileNum++;
			}
		}
	}
}
