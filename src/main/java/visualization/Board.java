package visualization;

import animal.Animal;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import map.Field;
import simulation.DNAStatistics;
import simulation.Engine;
import vector2d.Vector2d;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Board extends GridPane {
  private final Engine engine;
  private final int width;
  private final int height;
  private final Vector2d jungleLeftBottomCorner;
  private final Vector2d jungleRightTopCorner;
  private final Simulation simulation;
  private final DNAStatistics dnaStatistics;
  private final List<Pane> tmpObjects = new LinkedList<>();

  public Board(Engine engine, int width, int height, Vector2d jungleLeftBottomCorner, Vector2d jungleRightTopCorner, Simulation simulation, DNAStatistics dnaStatistics)
  {
    this.dnaStatistics = dnaStatistics;
    this.engine = engine;
    this.width = width;
    this.height = height;
    this.jungleLeftBottomCorner = jungleLeftBottomCorner;
    this.jungleRightTopCorner = jungleRightTopCorner;
    this.simulation = simulation;
    this.prepareFields();
    prepareBoard();
  }

  public void updateBoard(int dominantAnimalState)
  {
    this.getChildren().removeAll(tmpObjects);
    addTmpObjects(dominantAnimalState);
  }

  private void prepareFields()
  {
    for(int i = 0; i <  width; i++)
    {
      this.getColumnConstraints().add(createCol(100*(1/ (double) width)));
    }
    for (int i = 0; i < height; i++)
    {
      this.getRowConstraints().add(createRow(100*(1/ (double) height)));
    }
  }

  private void addTmpObjects(int dominantAnimalState)
  {
    tmpObjects.clear();
    Collection<Field> colOfFields = engine.getFields().values();
    for (Field current : colOfFields)
    {
      if (current.containsAnimals())
      {
        Pane circle = new Pane();
        circle.setShape(new Circle(0.05));
        circle.setStyle(setColour(current, dominantAnimalState));
        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            if(simulation.getAnimationStatus() == 0) {
              simulation.setAnimalSelected(current.getStrongestAnimal());
            }
          }
        });
        this.add(circle, current.getPosition().x,current.getPosition().y);
        tmpObjects.add(circle);
      }
      else
      {
        if(current.getContainsGrass())
        {
          Pane square = new Pane();
          square.setStyle("-fx-background-color: #00B500");
          this.add(square, current.getPosition().x, current.getPosition().y);
          tmpObjects.add(square);
        }
      }
    }
  }

  private void prepareBoard()
  {
    for (int row = 0; row < height; row++)
    {
      for (int col = 0; col < width; col++)
      {
        StackPane square = new StackPane();
        String color;
        Vector2d vec = new Vector2d(col, row);
        if (vec.precedes(jungleRightTopCorner) && vec.follows(jungleLeftBottomCorner))
        {
          color = "#008000";
        }
        else
        {
          color = "#00FF00";
        }
        square.setStyle("-fx-background-color: " + color + ";");
        this.add(square, col, row);
      }
    }
  }

  private ColumnConstraints createCol(double percent)
  {
    ColumnConstraints col = new ColumnConstraints();
    col.setPercentWidth(percent);
    return col;
  }

  private RowConstraints createRow(double percent)
  {
    RowConstraints row = new RowConstraints();
    row.setPercentHeight(percent);
    return row;
  }
  private String setColour(Field field, int dominantAnimalState)
  {
    Animal strongestAnimal = field.getStrongestAnimal();
    if(dominantAnimalState == 1 && strongestAnimal.getDNA().equals(dnaStatistics.getDominatingDNA()))
    {
      return "-fx-background-color: #0F00FE;";
    }
    else
    {
      double lvl = (double)Animal.getInitialEnergy()/4;
      if(strongestAnimal.getEnergy() < lvl)
        return "-fx-background-color: #FBEEE6;";
      if(strongestAnimal.getEnergy() < 2*lvl)
        return "-fx-background-color: #F6DDCC;";
      if(strongestAnimal.getEnergy() < 3*lvl)
        return "-fx-background-color: #EDBB99;";
      if(strongestAnimal.getEnergy() < 4*lvl)
        return "-fx-background-color: #E59866;";
      if(strongestAnimal.getEnergy() < 5*lvl)
        return "-fx-background-color: #DC7633;";
      if(strongestAnimal.getEnergy() < 6*lvl)
        return "-fx-background-color: #D35400;";
      if(strongestAnimal.getEnergy() < 7*lvl)
        return "-fx-background-color: #BA4A00;";
      if(strongestAnimal.getEnergy() < 8*lvl)
        return "-fx-background-color: #A04000;";
      if(strongestAnimal.getEnergy() < 9*lvl)
        return "-fx-background-color: #873600;";
      return "-fx-background-color: #6E2C00;";
    }
  }
}
