from pyspark.sql import SparkSession
from pyspark.sql.functions import trim, expr

spark = SparkSession.builder.appName("SimpleDataCleaning").getOrCreate()

df = spark.read.csv("people_data.csv", header=True, inferSchema=False)

print("Original Data:")
df.show()

df_clean = (
    df.withColumn("name", trim("name"))
    .withColumn("city", trim("city"))
    .filter("name IS NOT NULL AND age IS NOT NULL")
    .withColumn("age", expr("try_cast(age as int)"))
    .filter("age IS NOT NULL")
)

print("Cleaned Data:")
df_clean.show()

spark.stop()
