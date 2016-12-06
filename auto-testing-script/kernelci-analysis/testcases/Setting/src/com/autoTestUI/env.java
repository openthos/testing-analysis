package com.autoTestUI;

public class env {
	// project name 最后生成的jar包名字 projectName.jar
	static String projectName = "otoAutoTest";
	
	// 需要根据环境修改，如：查看5.1版本对应android stdio的id（ android list target )
	static String androidTargetId = "1";
	
	static String targetIp = "192.168.0.163";
	
	// {"包名.类名", "执行的函数名"}
	static String[][] testClassFuncName = {
		{ "com.autoTestUI.setting", "testsetting" },

	};
}
