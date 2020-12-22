package visualization;

import animal.Animal;
import javafx.application.Platform;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import simulation.DNAStatistics;
import simulation.Engine;
import utilities.JungleBorderCalculator;
import utilities.rowColUtilities;
import vector2d.Vector2d;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class Simulation extends BorderPane {
  private final Engine engine;
  private Animal animalSelected = null;
  private int animationStatus = 0;
  private int simulationSpeed = 100;
  private final Board board;
  private final Statistics statistics;
  private final DNAStatistics dnaStatistics;
  private int selectedAnimalCounter = 0;
  private final Button dominantDNA = new Button("Select Dominant DNA");
  private int dominantDNAState = 0;
  private BorderPane speedValue = new BorderPane();

  public Simulation(int width, int height, double jungleRatio, int numberOfAnimals, int animalStartingEnergy, int energyFromGrass, int dayCost) {
    ArrayList<Vector2d> list = JungleBorderCalculator.calculate(width, height, jungleRatio);
    engine = new Engine(width, height, jungleRatio, numberOfAnimals, animalStartingEnergy, dayCost, energyFromGrass);
    dnaStatistics = new DNAStatistics(this.engine);
    statistics = new Statistics(engine, animalSelected, dnaStatistics);
    board = new Board(engine, width, height, list.get(2), list.get(3), this, dnaStatistics);
    this.setLeft(leftSide());
    Thread thread = createThread();
    thread.setDaemon(true);
    thread.start();
    generatePane();
  }

  public int getAnimationStatus() {
    return animationStatus;
  }


  private void updateEngine()
  {
    engine.nextDay();
    generatePane();
  }

  public void generatePane() {
    this.setCenter(null);
    dnaStatistics.update();
    statistics.updateStatistics(animalSelected, selectedAnimalCounter);
    board.updateBoard(0);
    this.setCenter(board);
  }

  protected void setAnimalSelected(Animal animalSelected) {
    selectedAnimalCounter++;
    this.animalSelected = animalSelected;
    this.animalSelected.setAncestryFactor(selectedAnimalCounter);
    statistics.updateStatistics(animalSelected, selectedAnimalCounter);
  }

  private GridPane leftSide() {
    GridPane left = new GridPane();
    left.getRowConstraints().add(rowColUtilities.createRow(20));
    left.getRowConstraints().add(rowColUtilities.createRow(80));
    left.getColumnConstraints().add(rowColUtilities.createCol(100));
    left.setMinWidth(150);
    left.add(buttons(), 0, 0);
    statistics.updateStatistics(null, selectedAnimalCounter);
    left.add(statistics, 0, 1);
    return left;
  }

  private GridPane buttons() {
    GridPane buttonArea = new GridPane();
    GridPane upperButtonArea = new GridPane();
    BorderPane lowerButtonArea = new BorderPane();
    Button slower = new Button("slower");
    Button start = new Button("start");
    Button stop = new Button("stop");
    Button faster = new Button("faster");
    Button animal = new Button("Unselect");
    upperButtonArea.add(slower, 0, 0);
    upperButtonArea.add(start, 1, 0);
    upperButtonArea.add(stop, 2, 0);
    upperButtonArea.add(faster, 3, 0);
    lowerButtonArea.setLeft(animal);
    lowerButtonArea.setRight(dominantDNA);
    speedValue.setLeft(new Text("Current Refresh Time: " + simulationSpeed+ "ms"));
    buttonArea.add(upperButtonArea, 0, 0);
    buttonArea.add(lowerButtonArea, 0, 1);
    buttonArea.add(speedValue, 0, 2);
    slower.setOnAction(e -> slowSimulation());
    start.setOnAction(e-> startSimulation());
    stop.setOnAction(e->stopSimulation());
    faster.setOnAction(e->accelerateSimulation());
    animal.setOnAction(e->resetSelectedAnimal());
    dominantDNA.setOnAction(e->dominantDNAAction());
    return buttonArea;
  }

  private void startSimulation() {
    if(dominantDNAState == 1)
      dominantDNAAction();
    animationStatus = 1;
  }

  private void stopSimulation() {
    animationStatus = 0;
  }

  private void accelerateSimulation() {
    if (simulationSpeed > 40)
      simulationSpeed -= 20;
    speedValue.setLeft(new Text("Current Refresh Time: " + simulationSpeed+ "ms"));
  }

  private void slowSimulation() {
    if (simulationSpeed < 500)
      simulationSpeed += 20;
    speedValue.setLeft(new Text("Current Refresh Time: " + simulationSpeed+ "ms"));
  }

  private void resetSelectedAnimal() {
    animalSelected = null;
    statistics.updateStatistics(null, selectedAnimalCounter);
  }

  private void dominantDNAAction()
  {
    if(animationStatus == 0) {
      if (dominantDNAState != 0)
      {
        dominantDNA.setText("Select dominant DNA");
        dominantDNAState = 0;
      } else
      {
        dominantDNA.setText("Unselect dominant DNA");
        dominantDNAState = 1;
      }
      board.updateBoard(dominantDNAState);
    }
  }

  private Thread createThread()
  {
    return new Thread(() -> {
      Runnable runnable = this::updateEngine;
      while (true) {
        try {
          Thread.sleep(simulationSpeed);
        } catch (InterruptedException ignore) {
        }
        if (animationStatus == 1) {
          Platform.runLater(runnable);
        }
      }
    });
  }
}