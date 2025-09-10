Series Summation Develop a program to calculate the sum of the
series: 1 - 2/3 + 3/5 - 4/7 + ... up to a specified number of terms. Allow
the user to input the number of terms in the series.
---------------------------------------------------------------------------------
series_generator <-function(terms){
  num=1
  den=1
  t=(num/den)
  if (terms==0 || terms==1){
    return (1)
  } else{
    for (i in 2:terms){
        num=num+1
        den=num*2-1
      if (i%%2 ==0){

        t=t-(num/den)
      }
      else{
        t=t+(num/den)
      }
    }
  }
  return(t)
}
t=as.numeric(readline(prompt="Enter the number of terms:"))
cat("The sum of the series: 1 - 2/3 + 3/5 - 4/7 +.... upto",t,"is:",series_generator(t))
