Andrew Nguyen

**SURLY0 Submission**

**Compiling instructions:**
1) first, open up a terminal and cd to the src directory.
2) then, in the src directory, execute the following:
"javac -d <output directory> *.java"

3) You can specify an output directory using the -d flag.
"javac -d ../output *.java"

which will output all the compiled classes to the same parent folder
under the output directory.

If you simply use "javac *.java" then it will output all compiled class files
into the same directory as the java files.
I've already pre-compiled into the /out directory as well.

**How to run**
cd into the output folder, and execute "java Main input.txt"
Make sure the input.txt file is in the same output folder, or manually write the filepath to it.
