mkdir bin
del /f /s /q bin\*.class

cd src
javac -d ../bin -classpath "." fr/cnam/grp4/poker/*.java

pause