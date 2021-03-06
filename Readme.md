_**Some of our core missions to use a functional style in our designs**_

1. Remove code duplication. E.g. timing slow operations by wrapping existing
code with System.currentTimeInMillis is a very common pattern. Instead of 
surrounding every slow operation with such code, we can extract this piece out
in an utility, thus avoiding code duplication. Refer to the classes: 
    - SlowWorkingProgram.java
    - Timing.java
    - TimingTest.java
    
2. Separation of concerns. We should be able to clearly call out the following when
 we are dealing with an aggregate of elements:
    - How we get the elements
    - What we do with each element
    - How we limit processing beyond a number a elements, as in, when do we stop.
 Refer to the code examples below:
    - GuavaSummarizer
    - LambdaSummarizer
    
 3. Execute around - It is a pattern which helps with cross-cutting concerns like transactions.
 Refer to the code examples below:
 
    - Timing.java

4. Functional approach to common problems

Problem | Imperative approach | Functional approach | Examples to refer
--------| ------------------- | ------------------- | -----------------
Validate/assert input to a function | Throw unchecked exception if input is invalid | In functional programming, you don’t throw exceptions; <br> you use special contexts such as `Result` for error handling | TestAssertions.java
    
 _**References**_
 
 Github link|Author
 -----------|------
 https://github.com/fpinjava/fpinjava/blob/master/fpinjava-parent | Pierre-Yves Saumont
