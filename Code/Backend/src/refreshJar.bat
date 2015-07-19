#!/bin/bash
# @echo off
# This bat file regenerates the jar file to update the jar files
makecl
# Updates the *.java jar
jar -cvf compiler-src.jar compiler/*
jar -cvf backend-src.jar xvHandler/*
# Goto bin folder to update the *.class jar
cd ../bin
jar -cvf compiler.jar compiler/*
jar -cvf backend.jar xvHandler/*
cd ../src
