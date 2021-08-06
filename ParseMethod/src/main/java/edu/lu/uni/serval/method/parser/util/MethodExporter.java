package edu.lu.uni.serval.method.parser.util;

import java.util.List;

import edu.lu.uni.serval.jdt.method.Method;
import edu.lu.uni.serval.method.parser.MethodNameParser;
import edu.lu.uni.serval.utils.FileHelper;

public class MethodExporter {

	//private StringBuilder originalTokens = new StringBuilder();
	private StringBuilder onlyTokens = new StringBuilder();
	private StringBuilder sizes = new StringBuilder();
	private StringBuilder methodBodies = new StringBuilder();
	//private StringBuilder parsedMethodNames = new StringBuilder();
	private StringBuilder onlyMethodNames = new StringBuilder();
	private String outputFilePath;
	
	public MethodExporter() {
		super();
	}
	
	public MethodExporter(String outputFilePath) {
		this();
		this.outputFilePath = outputFilePath;
	}

	public int outputMethods(List<Method> methods) {
		// four folders: tokens, sizes, bodies, parsed method names.
		//String featuresFile = "tokens/originaltokens.txt";
		String featuresFile1 = "tokens/onlytokens.txt";
		String sizesFile = "sizes/sizes.csv";
		String methodBodiesFile = "method_bodies/method_bodies.txt";
		//String methodNamesFile = "ParsedMethodNames/ParsedMethodNames.txt";
		String methodNamesFile1 = "ParsedMethodNames/onlyMethodNames.csv";
		
		//int counter = outputMethods(methods, featuresFile, featuresFile1, sizesFile, methodBodiesFile, methodNamesFile,methodNamesFile1);
		int counter = outputMethods(methods,  featuresFile1, sizesFile, methodBodiesFile, methodNamesFile1);
		
		return counter;
	}

	public int outputMethods(List<Method> methods, int id) {
		// four folders: tokens, sizes, bodies, parsed method names.
		//String featuresFile = "tokens/originaltokenstokens_" + id + ".txt";
		String featuresFile1 = "tokens/onlytokens_" + id + ".txt";
		String sizesFile = "sizes/sizes_" + id + ".csv";
		String methodBodiesFile = "method_bodies/method_bodies_" + id + ".txt";
		//String methodNamesFile = "ParsedMethodNames/ParsedMethodNames_" + id + ".txt";
		String methodNamesFile1 = "ParsedMethodNames/onlydMethodNames_" + id + ".csv";
		
		//int counter = outputMethods(methods, featuresFile, featuresFile1, sizesFile, methodBodiesFile, methodNamesFile,methodNamesFile1);
		int counter = outputMethods(methods,  featuresFile1, sizesFile, methodBodiesFile, methodNamesFile1);
		
		return counter;
	}

	private int outputMethods(List<Method> methods,  String featuresFile1, String sizesFile, String methodBodiesFile, String methodNamesFile1) {
		int counter = 0;
		for (Method method : methods) {
			boolean isSuccessful = readMethodInfo(method);
			if (isSuccessful) {
				counter ++;
				if (counter % 1000 == 0) {
					//outputData(featuresFile, featuresFile1, sizesFile, methodNamesFile, methodBodiesFile, methodNamesFile1);
					outputData(featuresFile1, sizesFile,  methodBodiesFile, methodNamesFile1);
				}
			}
		}
		
		if (counter % 1000 != 0)  {
			//outputData(featuresFile, featuresFile1, sizesFile, methodNamesFile, methodBodiesFile, methodNamesFile1);
			outputData(featuresFile1, sizesFile,  methodBodiesFile, methodNamesFile1);
		}
		return 0;
	}
	
	private boolean readMethodInfo(Method method) {
		if (!"".equals(method.getBody().trim())) { // filter out the empty method bodies.
			String bodyCodeTokens = method.getBodyCodeTokens();
			String[] tokens = bodyCodeTokens.split(" ");
			int length = tokens.length;
			if (length > 0) {
//				tokens = filterOnlyMethodInvocationMethods(tokens); // FIXME
				String methodKey = method.getKey();
				String methodName = method.getName();
				// Parse method name into sub-tokens.
				String parsedMethodNameSubTokens = new MethodNameParser().parseMethodName(methodName);
				if (parsedMethodNameSubTokens == null) return false;
				
				//originalTokens.append(methodKey).append("#").append(bodyCodeTokens).append("\n");
				onlyTokens.append(bodyCodeTokens).append("\n");
				sizes.append(length).append("\n");
				//parsedMethodNames.append(methodKey).append("#").append(parsedMethodNameSubTokens).append("\n");
				onlyMethodNames.append(methodName).append("\n");
				methodBodies.append("#METHOD_BODY#========================\n").append(methodKey).append("\n").append(method.getBody()).append("\n");
				return true;
			}
		}
		return false;
	}
	
	private void outputData(String featuresFile1, String sizesFile, String methodBodiesFile,String methodNames1) {
		//FileHelper.outputToFile(outputFilePath + featuresFile, originalTokens, true);
		FileHelper.outputToFile(outputFilePath + featuresFile1, onlyTokens, true);
		FileHelper.outputToFile(outputFilePath + sizesFile, sizes, true);
		//FileHelper.outputToFile(outputFilePath + methodNames, parsedMethodNames, true);
		FileHelper.outputToFile(outputFilePath + methodNames1, onlyMethodNames, true);
		FileHelper.outputToFile(outputFilePath + methodBodiesFile, methodBodies, true);
		//parsedMethodNames.setLength(0);
		methodBodies.setLength(0);
		//originalTokens.setLength(0);
		sizes.setLength(0);
		onlyTokens.setLength(0);
		onlyMethodNames.setLength(0);
	}

}
