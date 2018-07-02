package viewers.utils;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Test extends Application {
    @Override
    public void start(Stage stage) {
        //Drawing a Rectangle
        Rectangle rectangle = new Rectangle(50, 50, 50, 50);

        Circle circle = new Circle(50, 50, 3, Color.RED);

        //Setting the color of the rectangle
        rectangle.setFill(Color.BURLYWOOD);

        //Setting the stroke color of the rectangle
        rectangle.setStroke(Color.BLACK);

        //creating the rotation transformation
        Rotate rotate = new Rotate();

        //Setting the angle for the rotation
        rotate.setAngle(0);

        //Setting pivot points for the rotation
        rotate.setPivotX(150);
        rotate.setPivotY(225);

        //Creating the scale transformation
        Scale scale = new Scale();

        //Setting the dimensions for the transformation
        scale.setX(2);
        scale.setY(2);

        //Setting the pivot point for the transformation
        scale.setPivotX(-50);
        scale.setPivotY(-50);

        //Creating the translation transformation
        Translate translate = new Translate();

        //Setting the X,Y,Z coordinates to apply the translation
        translate.setX(50);
        translate.setY(50);
        translate.setZ(0);

        //Adding all the transformations to the rectangle
        rectangle.getTransforms().addAll(rotate, scale, translate);

        //Creating a Group object
        Group root = new Group(rectangle, circle);

        //Creating a scene object
        Scene scene = new Scene(root, 600, 300);

        //Setting title to the Stage
        stage.setTitle("Multiple transformations");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}
