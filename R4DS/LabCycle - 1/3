Data Validation and User Input Develop a program to read student
records with their names, ages, and grades. Implement validation
checks: a. Ensure age is a positive integer. b. Ensure grade is a valid
letter grade (A, B, C, D, F). c. Calculate and display the average age
of students with valid records.
---------------------------------------------------------------------------------------------------------------------------
is_positive_integer <- function(x) {
  !is.na(as.integer(x)) && as.integer(x) > 0 && x == as.character(as.integer(x))
}

is_valid_grade <- function(grade) {
  toupper(grade) %in% c("A", "B", "C", "D", "F")
}

repeat {
  num_students <- suppressWarnings(as.integer(readline(prompt = "Enter number of students: ")))
  if (!is.na(num_students) && num_students > 0) break
  cat("Please enter a valid positive integer.\n")
}

students <- data.frame(Name = character(),
                       Age = integer(),
                       Grade = character(),
                       stringsAsFactors = FALSE)

for (i in 1:num_students) {
  cat("\n--- Enter details for Student", i, "---\n")
  
  name <- readline(prompt = "Enter name: ")
  age <- readline(prompt = "Enter age: ")
  grade <- readline(prompt = "Enter grade (A/B/C/D/F): ")
  
  if (!is_positive_integer(age)) {
    cat("Invalid age. Record skipped.\n")
    next
  }
  
  if (!is_valid_grade(grade)) {
    cat("Invalid grade. Record skipped.\n")
    next
  }
  
  students <- rbind(students,
                    data.frame(Name = name,
                               Age = as.integer(age),
                               Grade = toupper(grade),
                               stringsAsFactors = FALSE))
  cat("Record saved.\n")
}

cat("\nValid Student Records:\n")
print(students)

if (nrow(students) > 0) {
  avg_age <- mean(students$Age)
  cat("\nAverage Age of Valid Students:", round(avg_age, 2), "\n")
} else {
  cat("\nNo valid student records found.\n")
}
