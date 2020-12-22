package utilities;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class FileParser {
  public int width;
  public int height;
  public double jungleRatio;
  public int numberOfAnimals;
  public int dayCost;
  public int grassIncome;
  public int animalStartEnergy;
  public int numberOfSimulations;

  public FileParser() throws Exception
  {
    String root = System.getProperty("user.dir");
    String FileName="source.json";
    String filePath = root+ File.separator+"src"+File.separator+"main" +File.separator+"resources" + File.separator + FileName;
    JSONParser jsonParser = new JSONParser();
    Object object = jsonParser.parse(new FileReader(filePath));
    JSONObject file = (JSONObject) object;
    width = ((Long) file.get("width")).intValue();
    height = ((Long) file.get("height")).intValue();
    jungleRatio = (Double) file.get("jungleRatio");
    numberOfAnimals = ((Long) file.get("numberOfAnimals")).intValue();
    dayCost = ((Long) file.get("dayCost")).intValue();
    grassIncome = ((Long) file.get("energyFromGrass")).intValue();
    animalStartEnergy = ((Long) file.get("animalStartEnergy")).intValue();
    numberOfSimulations = ((Long) file.get("numberOfMaps")).intValue();
    if (width <= 0 || height <= 0 || jungleRatio <= 0)
      throw new RuntimeException("Wrong Data! Width, height and jungleRatio must be positive values");
    if (numberOfSimulations != 1 && numberOfSimulations !=2)
      throw new RuntimeException("Wrong number of Simulations! Correct number of simulations is 1 or 2");
  }
}
