# Linked Open Data (LOD)
Repository containing all my work on Linked Open Data (LOD), supervised by professor Amagasa at KDE's lab (Tsukuba University, Japan)

## Prerequisites
* This project is powered by Maven, a project management tool. ([Install Maven](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html))
* *(OPTIONAL)* IntelliJ's project files are supplied for those who want to use it. ([Install IntelliJ](https://www.jetbrains.com/idea/download/))

## Configure with Maven
About configuration, you only need to install project dependencies which are required to build the differents .jar.
This can be done by entering the following instruction when you are in the directory "LOD" :
```
mvn install
```
Now everything should be fine to compile every module.

## Compile the different modules with Maven

* In a command shell, go to "LOD/%MODULE_DIR%", where %MODULE_DIR% is the main directory of a module
* Enter the following instruction :
```
mvn package
```
The .jar should be present at "LOD/%MODULE_DIR%/target"

For example, if you want to compile the App Module :
* Go to "LOD/App"
* Enter the following instruction :
```
mvn package
```
The .jar should be present at "LOD/App/target"

## Configure with IntelliJ

### Open the project under IntelliJ
Launch IntelliJ and under "File/Open" select "LOD" directory
