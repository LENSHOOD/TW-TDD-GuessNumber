# TW-TDD-GuessNumber
TW TDD Training Lesson 3 - Guess Number

### Tasking
1. Generate random number
    - 4 digit
    - every digit are random integer from 0-9
    - no duplicate
2. Input guess number
    - take input from console
    - validate input number
        - 4 digits
        - only integer
        - no duplicate
3. Compare with real number
    - compare digit by digit
        - if equal then A++
    - merge input and secret without digital equals item, distinct
        - B equals merge length of input and secret subtract distinct length
4. Get output guess result
    - store single time result
    - output all history result
    - output xAxB, x is number of A and B
    - output "Wrong input, input again" if validate fail
5. stop program when guess correct or exceed 6 times 