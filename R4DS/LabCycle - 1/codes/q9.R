Data Compression Design a program to read a string and compress it
using run-length encoding. In run-length encoding, consecutive
repeated characters are replaced with a single character followed by
the count of occurrences.
---------------------------------------------------------------------------
compression <- function(string){
  
  occurence_vector <-c()
  string_vector <-unlist(strsplit(string,""))
  for (i in (1:length(string_vector))){
    occurrence <-0
    char <-string_vector[i]
    if(char %in% occurence_vector){
      next
    }
    for (j in (1:length(string_vector))){
      if (char==string_vector[j]){
        occurrence=occurrence+1
      }
    }
    occurence_vector <-c(occurence_vector," ",char,":",occurrence)
  }
  return(occurence_vector)
}
cat(compression("She speaks four languages"))
