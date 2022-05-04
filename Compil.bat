chcp 65001
mkdir bin
del /f /s /q bin\*.class
cd src
javac -d ../bin -classpath "." fr\cnam\grp4\poker\server\PokerApplication.java
javac -d ../bin -classpath "." fr\cnam\grp4\poker\client\IHMClientApplication.java
pause