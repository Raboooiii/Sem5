Develop a program to read a paragraph of text and perform the following tasks: 
  a. Count the total number of words. 
  b. Calculate the average word length. 
  c. Identify and print the longest word. 
  d. Replace all occurrences of a specific word with another word of your choice.
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
process_paragraph <- function(paragraph, target_word, replacement_word) {
  
  words <- unlist(strsplit(paragraph, "[[:space:][:punct:]]+"))
  words <- words[words != ""]   # remove empty strings
  
  total_words <- length(words)
  
  avg_length <- mean(nchar(words))
  
  longest_word <- words[which.max(nchar(words))]
  
  modified_paragraph <- gsub(paste0("\\b", target_word, "\\b"), replacement_word, paragraph, ignore.case = TRUE)
  
  cat("Original Paragraph:\n", paragraph, "\n\n")
  cat("Total number of words:", total_words, "\n")
  cat("Average word length:", round(avg_length, 2), "\n")
  cat("Longest word:", longest_word, "\n\n")
  cat("Modified Paragraph:\n", modified_paragraph, "\n")
}

paragraph <- "R programming is very powerful and versatile. R can handle data analysis, visualization, and machine learning."
target_word <- "R"
replacement_word <- "Python"

process_paragraph(paragraph, target_word, replacement_word)
