@echo off & setlocal enabledelayedexpansion

set LIB_JARS=""
cd ..\lib
for %%i in (*) do set LIB_JARS=!LIB_JARS!;..\lib\%%i
cd ..\bin


java -Xms64m -Xmx1024m -classpath ..\conf;%LIB_JARS% com.liubaing.galaxy.core.container.Main
goto end

:end
pause