all : C.java

jtb.out.jj : C.jj  
	java -jar jtb132.jar -tk -np parserOfC.syntaxTree -vp parserOfC.visitor C.jj

C.java : jtb.out.jj
	java -classpath "C:/Workspace/Eclipse3.4 Full/plugins/sf.eclipse.javacc_1.5.14/javacc.jar" javacc -JDK_VERSION=1.6 jtb.out.jj