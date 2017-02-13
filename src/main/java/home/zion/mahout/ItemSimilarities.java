package home.zion.mahout;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.precompute.FileSimilarItemsWriter;
import org.apache.mahout.cf.taste.impl.similarity.precompute.MultithreadedBatchItemSimilarities;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.BatchItemSimilarities;

import java.io.File;

public class ItemSimilarities {
  public static void main(String[] args) throws Exception {
    Commanders cmds = Commanders.make(args);
    String sim = cmds.targetFile("similarities.csv");

    DataModel model = new FileDataModel(new File(cmds.ratingPath()));
    ItemSimilarity similarity = new LogLikelihoodSimilarity(model);
    ItemBasedRecommender rec = new GenericItemBasedRecommender(model, similarity);

    BatchItemSimilarities batch = new MultithreadedBatchItemSimilarities(rec, cmds.count);

    int procs = Runtime.getRuntime().availableProcessors();
    int sims = batch.computeItemSimilarities(procs, 1,
        new FileSimilarItemsWriter(new File(sim)));

    System.out.println("item-sim.csv " + sims + " " + sim);

    //S.writeResult(rec, cmds.targetFile("item-result.csv"));
  }
}
