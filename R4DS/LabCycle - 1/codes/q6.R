Prime Number Checker Write a program to check whether a given
number is prime or not. Implement this using both loops and functions.
Additionally, allow the user to input a range and identify all prime
numbers within that range.
------------------------------------------------------------------------
check_prime <- function(num){
  count=0
  if (num<=1){
      return (FALSE)
    }
  for( i in 1:num){

    if (num%%i==0){
      count=count+1
    }
  }
  if (count<=2){
    return (TRUE)
  } else{
    return(FALSE)
  }

}
n=as.numeric(readline(prompt ="Enter the number to check whether its prime or not:" ))
cat(check_prime(n),"\n")

no = as.numeric(readline(prompt="Enter a number: "))

if (no <= 1) {
  cat("Not a prime number\n")
} else {
  is_prime <- TRUE
  for (i in 2:(no-1)) {
    if (no %% i == 0) {
      cat("The number is NOT prime\n")
      is_prime <- FALSE
      break
    }
  }
  if (is_prime) {
    cat("The number is PRIME\n")
  }
}

range_prime <- function (num1,num2){
  count=0
  if (num1<=1  && num2<=1){
    cat("There are no prime numbers in the given range")
  }else{
    count = 0
    for (i in num1:num2){
      if (check_prime(i)){
        cat(i," ")
      }
    }
 }
 cat("\n")
}
up=as.numeric(readline(prompt="Enter the upper limit:"))
low=as.numeric(readline(prompt = "Enter the lower limit:"))
range_prime(up,low)
