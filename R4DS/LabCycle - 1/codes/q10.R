Data Reversal Write a program to reverse the order of elements
in a given list. Implement this without using any built-in functions or
loops
--------------------------------------------------------------------------
reverse_list<-function(l){
  if (length(l)==1 || length(l)==0){
    return(l)
  }
  remaining_reversed <- reverse_list(l[-1])
  return (c(remaining_reversed,l[1]))

}
cat("Reversed list:",unlist(reverse_list(list(1,2,3,4))))
