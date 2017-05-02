# Sample Java Agent and Bytecode manipulation 

Sample maven project containing a Java agent and examples of bytecode manipulation with ASM and Javassist.

See article on my blog : http://tomsquest.com/blog/2014/01/intro-java-agent-and-bytecode-manipulation/


## Build

```
$ # From the root dir
$ mvn package
```

## Run

```
$ # From the root dir
$ java -javaagent:agent/target/agent-0.1-SNAPSHOT.jar -jar other/target/other-0.1-SNAPSHOT.jar

## Sample Output
```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running other.Stuff2Test
[TEST] other.Stuff2Test : subTest
other/Stuff : 3
other/Stuff : 11
Finished .... subTest
[TEST] other.Stuff2Test : multiTest
other/Stuff : 3
other/Stuff : 11
other/Stuff : 11
other/Stuff : 11
other/Stuff : 11
other/Stuff : 11
other/Stuff : 11
other/Stuff : 8
Finished .... multiTest
[TEST] other.Stuff2Test : addTest
other/Stuff : 3
other/Stuff : 8
Finished .... addTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.053 sec - in other.Stuff2Test
Running other.StuffTest
[TEST] other.StuffTest : subTest
other/Stuff : 3
other/Stuff : 11
Finished .... subTest
[TEST] other.StuffTest : multiTest
other/Stuff : 3
other/Stuff : 11
other/Stuff : 11
other/Stuff : 11
other/Stuff : 11
other/Stuff : 11
other/Stuff : 11
other/Stuff : 8
Finished .... multiTest
[TEST] other.StuffTest : addTest
other/Stuff : 3
other/Stuff : 8
Finished .... addTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.012 sec - in other.StuffTest
Done!