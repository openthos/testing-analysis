package com.autoTestUI;

import java.io.IOException;

public class AutoTest {	
	public static void main(String[] args) throws IOException, InterruptedException {
	//	int i;
		int ret = 0;
		String objJarPath = "bin/";
		otoDisplayRun.port = "5555";
		String objJarName = env.projectName + ".jar";
		// 创建对象
		otoDisplayRun uiRun = new otoDisplayRun();

		if (args.length > 0) {
			if (args.length < 4) {
				System.out.println("usage: java -jar *.jar targetIp  targetPort otoAutoTest.jar logFile");
				return;
			}
			objJarPath = "";
			env.targetIp = args[0];
			otoDisplayRun.port = args[1];
			objJarName = args[2];
			otoDisplayRun.logFile = args[3];
		} else {
			//创建 build.xml 并且编译 生成projectName.jar
			uiRun.buildObjJarFile(env.projectName, env.androidTargetId);
			System.out.println("connect target ip is :" +  env.targetIp);
		}
		

		// adb connect + ip
		ret = otoDisplayRun.execCmd("adb connect " + env.targetIp);

		// 将编译生成的jar push到 目标环境
		ret = uiRun.pushTestJar(objJarName, objJarPath);
		if (ret != 0) {
			System.out.println("adb push  failed!");
			return;
		}

		System.out.println("**********************");
		System.out.println("----START " + env.testClassFuncName[0][0] + "---------");
		System.out.println("**********************");

		// 执行需要的测试 class
		uiRun.runTest(env.projectName + ".jar", env.testClassFuncName[0][0],
				env.testClassFuncName[0][1]);
		

	/*	
		System.out.println("总测试数量： " + env.testClassFuncName.length);
		for (i = 0; i < env.testClassFuncName.length; i++) {
			uiRun.runTest(env.projectName + ".jar", env.testClassFuncName[i][0],
					env.testClassFuncName[i][1], objPort);
			}
			*/

		System.out.println("**********************");
		System.out.println("----FINISH TESTING----");
		System.out.println("**********************");
	
	}
}
