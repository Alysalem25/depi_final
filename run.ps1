$env:Path = "C:\Program Files\Java\jdk-17\bin;" + $env:Path # adjust if needed
$ErrorActionPreference = "Stop"

# Compile
Write-Host "Compiling..."
& "C:\Program Files\Java\jdk-17\bin\javac.exe" -cp "C:\Users\Ali\.m2\repository\org\seleniumhq\selenium\selenium-java\4.20.0\selenium-java-4.20.0.jar;C:\Users\Ali\.m2\repository\org\seleniumhq\selenium\selenium-api\4.20.0\selenium-api-4.20.0.jar;C:\Users\Ali\.m2\repository\org\seleniumhq\selenium\selenium-chrome-driver\4.20.0\selenium-chrome-driver-4.20.0.jar;C:\Users\Ali\.m2\repository\org\seleniumhq\selenium\selenium-remote-driver\4.20.0\selenium-remote-driver-4.20.0.jar" src\main\java\com\orangehrm\Main.java

Write-Host "Done compiling."
