String Encryption Write a program that reads a sentence from the user
and encrypts it using a simple Caesar cipher. The user can specify the
shift value. Implement the encryption such that only alphabetic
characters are shifted, while maintaining their case.
----------------------------------------------------------------------------------------------------------------------------------------------------

caesar_cipher <- function(text, shift) {
  
  chars <- strsplit(text, NULL)[[1]]
  
  shift_char <- function(ch, shift) {
    if (grepl("[A-Z]", ch)) {
      return(intToUtf8(((utf8ToInt(ch) - utf8ToInt("A") + shift) %% 26) + utf8ToInt("A")))
    } else if (grepl("[a-z]", ch)) {
      return(intToUtf8(((utf8ToInt(ch) - utf8ToInt("a") + shift) %% 26) + utf8ToInt("a")))
    } else {
      return(ch)
    }
  }
  
  encrypted <- sapply(chars, shift_char, shift = shift)
  
  return(paste(encrypted, collapse = ""))
}

sentence <- readline(prompt = "Enter a sentence: ")

shift_value <- as.integer(readline(prompt = "Enter shift value: "))

encrypted_sentence <- caesar_cipher(sentence, shift_value)

cat("\nOriginal Sentence: ", sentence, "\n")
cat("Encrypted Sentence:", encrypted_sentence, "\n")
