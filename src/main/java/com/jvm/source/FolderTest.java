package com.jvm.source;

import java.io.File;

/**
 * @功能说明:
 * @创建者: Pom
 * @创建时间: 2020-01-17 14:31:21
 * @公司名称: 180830.com
 * @版本:V1.0
 */
public class FolderTest {
	public static void main(String[] args) {
		File file = new File("d:/ms143014");
		
		BreakPointX.breakOn(file != null, true);
		
		System.out.println(file);
//		if(file.isDirectory()) {
//			travelFolder(file, 0, false);
//		}else {
//			System.out.println(file.getAbsolutePath() + "\t" + file.length());
//		}
	}
	public static String blank(int depth, boolean last) {
		if(depth <= 0) {
			return "";
		}else if(depth == 1){
			return (last?"└":"├")+"---";
		}else {
			return String.format("%"+(depth-1)*4+"s%s---", "", (last?"└":"├"));
		}
	}
	public static void travelFolder(File file, int depth, boolean laset) {
		System.out.printf("%s%s\n", blank(depth, laset), file.getName()); //这个必定是文件夹
		File[] childs = file.listFiles();
		for(int i=0; i < childs.length; i++) {
			File child = childs[i];
			if(child.isDirectory()) {
				travelFolder(child, depth + 1, i == childs.length -1);
			}else {
				System.out.printf("%s%s\n", blank(depth + 1, i == childs.length -1), child.getName());
			}
		}
	}
}
