Fibonacci Series with a Twist Develop a program to generate the
Fibonacci series, but with a twist. Allow the user to input the number of
terms and generate the series where each term is the sum of the last
three terms.
------------------------------------------------------------------------------------
fib <- function (terms){
  t0=0
  t1=1
  t2=1
  if (terms >= 1) cat(t0, " ")
  if (terms >= 2) cat(t1, " ")
  if (terms >= 3) cat(t2, " ")
  if (terms>3){
    for (i in 3:(terms-1)){
      
      t=t0+t1+t2
      cat(t," ")
      t0=t1
      t1=t2
      t2=t
      
    }
    }
}
terms=as.numeric(readline(prompt = "Enter the number of terms:"))
fib(terms)