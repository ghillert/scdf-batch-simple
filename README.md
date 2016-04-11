Spring Cloud Data Flow Simple Batch Sample
==========================================

In this *Hello World* example for *Spring Spring Cloud Data Flow* you will create a minimal code-based Job. This Job does no processing, only printing out `Hello Spring Cloud Data Flow!` as well as any Job parameters that were passed into the Job so as to focus only on the mechanics of the compilation and registration of artifacts into *Spring Spring Cloud Data Flow*.

## Requirements

In order for the sample to run you will need to have installed:

* *Spring Spring Cloud Data Flow* version 1.0.0 or higher ([Instructions](http://cloud.spring.io/spring-cloud-dataflow/#quick-start))

## Code Tour

The processing actions that are part of a Step in a batch Job are pluggable.  The plug-in point for a Step is known as a [Tasklet](http://static.springsource.org/spring-batch/apidocs/org/springframework/batch/core/step/tasklet/Tasklet.html).  In this example we create a tasklet by implementing the Tasklet interface.  Take a look at the [source code](https://github.com/ghillert/scdf-batch-simple/blob/master/scdf-batch-simple/src/main/java/io/spring/scdf/batch/job/HelloSimpleBatchTasklet.java).

## Building with Maven

Build the sample simply by executing:

	$ mvn clean package install

## Running the Sample Stand-Alone

Now your sample is ready to be executed. Being a pure Spring Boot application,
you can start the sample stand-alone as well:

```
$ java -jar target/java -jar target/scdf-batch-simple-1.0.0.BUILD-SNAPSHOT-exec.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v1.3.3.RELEASE)

2016-04-10 10:38:07.891  INFO 19789 --- [           main] i.s.scdf.batch.TestTaskBatchApplication  : Starting TestTaskBatchApplication v1.0.0.BUILD-SN
...
```

## Running the Sample in Spring Cloud Data Flow

Please ensure that Spring Cloud Data Flow is running.

	$ java -jar spring-cloud-dataflow-server-local-***.jar

Now start the *Spring Spring Cloud Data Flow Shell* in a separate window:

	$ java -jar spring-cloud-dataflow-shell-***.jar

```
  ____                              ____ _                __
 / ___| _ __  _ __(_)_ __   __ _   / ___| | ___  _   _  __| |
 \___ \| '_ \| '__| | '_ \ / _` | | |   | |/ _ \| | | |/ _` |
  ___) | |_) | |  | | | | | (_| | | |___| | (_) | |_| | (_| |
 |____/| .__/|_|  |_|_| |_|\__, |  \____|_|\___/ \__,_|\__,_|
  ____ |_|    _          __|___/                 __________
 |  _ \  __ _| |_ __ _  |  ___| | _____      __  \ \ \ \ \ \
 | | | |/ _` | __/ _` | | |_  | |/ _ \ \ /\ / /   \ \ \ \ \ \
 | |_| | (_| | || (_| | |  _| | | (_) \ V  V /    / / / / / /
 |____/ \__,_|\__\__,_| |_|   |_|\___/ \_/\_/    /_/_/_/_/_/

1.0.0.BUILD-SNAPSHOT

Welcome to the Spring Cloud Data Flow shell. For assistance hit TAB or type "help".
dataflow:>
```

First register the sample module using the `module register` command:

```
dataflow:>module register --name scdf-batch-simple --type task --uri maven://io.spring.cloud:scdf-batch-simple:jar:exec:1.0.0.BUILD-SNAPSHOT
Successfully registered module 'task:scdf-batch-simple'
```

You will now create a new Batch Job Task using the *Spring Spring Cloud Data Flow Shell*:

```
dataflow:>task create scdf-batch-simple --definition "scdf-batch-simple"
Created new task 'scdf-batch-simple'
```

Now we are ready to launch the task:

```
dataflow:>task launch scdf-batch-simple
Launched task 'scdf-batch-simple'
```

In the console window of the `Spring Cloud Data Flow Server`, you should see output
such as the following:

```
2016-04-10 10:52:16.075  INFO 88059 --- [nio-9393-exec-1] o.s.c.d.spi.local.LocalTaskLauncher      : launching task scdf-batch-simple
   Logs will be in /var/folders/hs/h87zy7z17qs6mcnl4hj8_dp00000gp/T/spring-cloud-dataflow-1086435732815051297/scdf-batch-simple-1460299936016/scdf-batch-simple
```

In the log directory you will find the logs for the executed task, e.g.

```
vi /var/folders/hs/h87zy7z17qs6mcnl4hj8_dp00000gp/T/spring-cloud-dataflow-1086435732815051297/scdf-batch-simple-1460299936016/scdf-batch-simple/stdout.log
```

And within the log you should see the job-relevant output such as:

```
2016-04-10 10:52:22.620  INFO 21375 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job1015519398]] launched with the following parameters: [{random=0.030360673845332475}]
2016-04-10 10:52:22.646  INFO 21375 --- [           main] o.s.c.t.b.l.TaskBatchExecutionListener   : The job execution id 2 was run within the task execution 2
2016-04-10 10:52:22.681  INFO 21375 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
Hello Spring Cloud Data Flow!
The following 1 Job Parameter(s) is/are present:
Parameter name: random; isIdentifying: true; type: STRING; value: 0.030360673845332475
2016-04-10 10:52:22.733  INFO 21375 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=job1015519398]] completed with the following parameters: [{random=0.030360673845332475}] and the following status: [COMPLETED]
```

Let's verify that the task executed successfully using the shell:

```
dataflow:>task execution list
╔═════════════════╤══╤════════════════════════════╤════════════════════════════╤═════════╗
║    Task Name    │ID│         Start Time         │          End Time          │Exit Code║
╠═════════════════╪══╪════════════════════════════╪════════════════════════════╪═════════╣
║scdf-batch-simple│2 │Sun Apr 10 10:52:21 EDT 2016│Sun Apr 10 10:52:22 EDT 2016│0        ║
╚═════════════════╧══╧════════════════════════════╧════════════════════════════╧═════════╝
```

Next, we will look how the associate Job Execution looks like:

```
dataflow:>job execution list
╔═══╤═══════╤═════════════╤════════════════════════════╤═════════════════════╤══════════════════╗
║ID │Task ID│  Job Name   │         Start Time         │Step Execution Count │Definition Status ║
╠═══╪═══════╪═════════════╪════════════════════════════╪═════════════════════╪══════════════════╣
║2  │2      │job1015519398│Sun Apr 10 10:52:22 EDT 2016│1                    │Created           ║
╚═══╧═══════╧═════════════╧════════════════════════════╧═════════════════════╧══════════════════╝
```

We can drill further in the details:

```
dataflow:>job execution view --id 2
╔═════════════════════╤════════════════════════════╗
║         Key         │           Value            ║
╠═════════════════════╪════════════════════════════╣
║Job Execution Id     │2                           ║
║Task Execution Id    │2                           ║
║Task Instance Id     │2                           ║
║Job Name             │job1015519398               ║
║Create Time          │Sun Apr 10 10:52:22 EDT 2016║
║Start Time           │Sun Apr 10 10:52:22 EDT 2016║
║End Time             │Sun Apr 10 10:52:22 EDT 2016║
║Running              │false                       ║
║Stopping             │false                       ║
║Step Execution Count │1                           ║
║Execution Status     │COMPLETED                   ║
║Exit Status          │COMPLETED                   ║
║Exit Message         │                            ║
║Definition Status    │Created                     ║
║Job Parameters       │                            ║
║random(STRING)       │0.030360673845332475        ║
╚═════════════════════╧════════════════════════════╝
```

## Custom Initialization Parameters

In order to demo various aspects of Batch Jobs, you can set the following sample-specific Boot
parameters:

* `sample.jobName` (default if not set: `job` + random numeric value)
* `sample.jobParams` (optional)
* `sample.makeParametersUnique` (defaults to `true`)

## Provide Custom Job Parameters

You can also experiment with Job parameters:

```
dataflow:>task launch scdf-batch-simple --properties sample.jobParams={"myStringParameter":"foobar","-secondParam(long)":"123456"}
```

## Throwing Exceptions

You can trigger an exception by providing a parameter named `throwError` with a String value of `true`.

```
dataflow:>task launch scdf-batch-simple --properties sample.jobParams={"throwError":"true"}
```

## Simulate Restarts that eventually succeed

This demo requires you to run Batch using a dedicated datasource.

Start the job with the following parameters:

```
dataflow:>task launch scdf-batch-simple --properties sample.jobParams={"throwError":"true"}, sample.jobName=SucceedsAfter3Times, sample.makeParametersUnique=false
```

After the 3rd run, the job will succeed.

## Adding Variables to the Step Execution Context

Any parameters that start with `context` will be added to the Step Execution Context.
E.g. if you add a parameter named `contextHello` with a String value of `World`,
the variable `contextHello` will be added to the Step Execution Context. You can
verify the context using the **Admin UI** and drilling to the Step Execution Details
via the *Executions* tab.

```
dataflow:>task launch scdf-batch-simple --properties sample.jobParams={"contextHello":"Hallo Berlin!"}
```
## Using the UI

Alternatively, you can also do all these tasks using the `Spring Cloud Data Flow UI`.
The UI is located on the machine where the ``Spring Cloud Data Flow Server` is running.
When running on `localhost`, the UI will be available at:

* **http://localhost:9393/admin-ui**

## Job Repository

In this example the state of the Job execution is stored in an HSQLDB database embedded inside the locally running `Spring Cloud Data Flow` server. Please refer to the Spring Cloud Data Flow documentation if you would like to store this data in another database.

