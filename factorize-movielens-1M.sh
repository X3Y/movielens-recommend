#!/usr/bin/env bash
# To run:  change into the mahout directory and type:
#  export MAHOUT_LOCAL=true
# Then:
#  examples/bin/factorize-movielens-1M.sh /path/to/ratings.dat

if [ "$1" = "--help" ] || [ "$1" = "--?" ]; then
  echo "This script runs the Alternating Least Squares Recommender on the Grouplens data set (size 1M)."
  echo "Syntax: $0 /path/to/ratings.dat\n"
  exit
fi


export MAHOUT_LOCAL=true
export MAHOUT_WORK_DIR=.

MAHOUT="$MAHOUT_HOME/bin/mahout"

if [[ -z "$MAHOUT_WORK_DIR" ]]; then
  WORK_DIR=/tmp/mahout-work-${USER}
else
  WORK_DIR=$MAHOUT_WORK_DIR
fi

echo "creating work directory at ${WORK_DIR}"
mkdir -p ${WORK_DIR}/movielens

echo "Converting ratings..."
cat $1 |sed -e s/::/,/g| cut -d, -f1,2,3 > ${WORK_DIR}/movielens/ratings.csv

# create a 90% percent training set and a 10% probe set
$MAHOUT splitDataset --input ${WORK_DIR}/movielens/ratings.csv \
    --output ${WORK_DIR}/dataset \
    --trainingPercentage 0.9 \
    --probePercentage 0.1 \
    --tempDir ${WORK_DIR}/dataset/tmp

# run distributed ALS-WR to factorize the rating matrix defined by the training set
$MAHOUT parallelALS --input ${WORK_DIR}/dataset/trainingSet/ \
    --output ${WORK_DIR}/als/out \
    --tempDir ${WORK_DIR}/als/tmp \
    --numFeatures 20 \
    --numIterations 10 \
    --lambda 0.065 \
    --numThreadsPerSolver 2

# compute predictions against the probe set, measure the error
$MAHOUT evaluateFactorization --input ${WORK_DIR}/dataset/probeSet/ \
    --output ${WORK_DIR}/als/rmse/ \
    --userFeatures ${WORK_DIR}/als/out/U/ \
    --itemFeatures ${WORK_DIR}/als/out/M/ \
    --tempDir ${WORK_DIR}/als/tmp

# compute recommendations
$MAHOUT recommendfactorized --input ${WORK_DIR}/als/out/userRatings/ \
    --output ${WORK_DIR}/recommendations/ \
    --userFeatures ${WORK_DIR}/als/out/U/ \
    --itemFeatures ${WORK_DIR}/als/out/M/ \
    --numRecommendations 6 \
    --maxRating 5 \
    --numThreads 2

# print the error
echo -e "\nRMSE is:\n"
cat ${WORK_DIR}/als/rmse/rmse.txt
echo -e "\n"

echo -e "\nSample recommendations:\n"
shuf ${WORK_DIR}/recommendations/part-m-00000 |head
echo -e "\n\n"

