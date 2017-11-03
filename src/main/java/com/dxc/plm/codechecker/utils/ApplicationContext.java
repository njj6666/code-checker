package com.dxc.plm.codechecker.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dxc.plm.codechecker.model.Result;

public class ApplicationContext {
	public static File targetFile;
	public static int lineNumber;
	public static List<Result> results = new ArrayList<Result>();
}
