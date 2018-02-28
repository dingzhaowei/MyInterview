# MyInterview

## Run Test

The **com.zuora.test.exam.RunTests** is a JUnit test suite including all test cases. You can run it to trigger all tests.

The test cases all passed on MacOS 10.13.1. There is accidentally socket timeout according to the network conditions. This can be improved, but not yet implement.

## Key Points

#### com.zuora.test.exam.@Mandatory

This annotation can be added to model class fields to mark that this field value is required and cannot be null.

#### com.zuora.test.exam.@NumRange

This annotation can be added to model class fields to mark that this field is a number of which the value falls between specified range.

#### com.zuora.test.exam.model.BaseModel

This abstract class is the parent of the other models like Deal. It has common assertion methods like **assertFieldsValid** and **assertEqualsWith**, which use Reflection API and Annotations to do handy field value checks.

#### com.zuora.test.exam.TestBase

This abstract class is the parent of the other test classes like DealTest. It has common http methods: get, post, put, delete, and connect, which can be used by all child tests. The response status is checked within these methods too.

#### Data Setup

The test data is stored in the **testdata** folder as JSON files. They are created using multi-threads mode, and cleaned after all tests. The setup is now written in each test class, but can be extracted to be a shared and customizable method in further development.
