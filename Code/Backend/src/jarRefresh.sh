jar -cvf compiler-src.jar compiler/* 
jar -cvf backend-src.jar xvHandler/*
cd ../bin
jar -cvf compiler.jar compiler/*
cd ../bin
jar -cvf backend.jar xvHandler/* 
