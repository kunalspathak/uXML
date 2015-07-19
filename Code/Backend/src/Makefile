all : ./xvHandler/Executor.java ./xvHandler/CodeAnalysis.java
./xvHandler/Executor.java : ./xvHandler/xv/Executor.xv
	java -jar xv.jar < ./xvHandler/xv/Executor.xv > ./xvHandler/Executor.java
./xvHandler/CodeAnalysis.java : ./xvHandler/xv/CodeAnalysis.xv
	java -jar xv.jar < ./xvHandler/xv/CodeAnalysis.xv > ./xvHandler/CodeAnalysis.java

