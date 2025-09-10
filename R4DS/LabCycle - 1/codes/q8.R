Palindrome Checker Write a program that reads a string and checks if
its a palindrome. A palindrome is a string that reads the same forwards
and backwards, ignoring spaces and punctuation.
---------------------------------------------------------------------------
palindrome_checker <- function(string){
  string_new <- tolower(gsub("[[:punct:] [:space:]]","",string))
  string_vector <- unlist(strsplit(string_new,""))
  palindrome <- rev(string_vector)
  palindrome_string <-paste(palindrome, collapse = "")
  if (string_new==palindrome_string){
    cat("The entered string is a palindrome")
  } else{
    cat("The entered string is not a palindrome")
  }
}
palindrome_checker("malayalam")
