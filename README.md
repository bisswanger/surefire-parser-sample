## Sample App to demonstrate Maven Surefire Report Parser issue

* Application creates a sample report file, size=1.2 GB with a huge `system-err` info
* When run with `-Xmx256m`, the application crashes with an `OutOfMemoryError`