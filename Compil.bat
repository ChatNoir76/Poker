chcp 65001
mkdir bin
del /f /s /q bin\*.class
xcopy "Ressources" "bin/Ressources" /e /i
cd src
javac -d ../bin -classpath "." fr/cnam/grp4/poker/*.java

pause