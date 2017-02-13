package home.zion.mahout;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.nio.file.Paths;

class Commanders {

  @Parameter(names = "-rating")
  String rating = "ratings-headless.csv";

  @Parameter(names = "-working")
  String working = "/e/ml/ml-latest-small";

  @Parameter(names = "-target")
  String target = ".";

  @Parameter(names = "-count")
  int count = 5;

  @Parameter(names = "-neibors")
  int neibors = 10;

  @Parameter(names = "--neibor-min-sim")
  double neibor_min = 0.2;

  @Parameter(names = "--sampling-rate")
  double sampling_rate = 0.2;

  @Parameter(names = "-features")
  int features = 10;

  @Parameter(names = "-iterations")
  int iterations = 30;

  static Commanders make(String[] args) {
    Commanders cmds = new Commanders();
    new JCommander(cmds, args);
    return cmds;
  }

  String ratingPath() {
    return Paths.get(working).resolve(rating).toString();
  }

  String targetFile(String fn) {
    return Paths.get(working).resolve(target).resolve(fn).toString();
  }
}
