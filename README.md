### Console Canvas Drawing App
Simple app to draw lines and rectangles and paint in console.

### Dependencies
* You need Java 8 or higher as well as maven installed to build the app
* To run the app, you only need Java JRE

### Building App
Open a terminal and go the project directory

* Build the program

        mvn clean package

### Executing App
Open a terminal and go the project directory

* To run App on Windows command prompt, execute below script

        .\run.bat

* To run App on Linux bash, execute below command

        ./run.sh


### Commands to draw and paint on canvas
* Creates a new canvas of width w and height h

        C w h

* Creates a new line from (x1,y1) to (x2,y2). Currently only horizontal or vertical lines are supported. Horizontal and vertical lines will be drawn using the 'x' character

        L x1 y1 x2 y2

* Creates a new rectangle, whose upper left corner is (x1,y1) and lower right corner is (x2,y2). Horizontal and vertical lines will be drawn using the 'x' character.

        R x1 y1 x2 y2

* Fills the entire area connected to (x,y) with "colour" c. The behaviour of this is the same as that of the "bucket fill" tool in paint programs.

        B x y c

* Quits the program

        Q