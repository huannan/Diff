package com.nan.diff.utils;

public class BsPatch {

	//APK合并
	public native static void patch(String oldFile, String newFile, String patchFile);

	static{
		System.loadLibrary("bspatch");
	}
	
}
