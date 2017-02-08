package home.zion.mahout;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

class Commanders {

  @Parameter(names = "-rating")
  String rating = "/e/ml/ml-latest-small/ratings-headless.csv";

  static Commanders make(String[] args){
    Commanders cmds = new Commanders();
    new JCommander(cmds, args);
    return cmds;
  }
}
