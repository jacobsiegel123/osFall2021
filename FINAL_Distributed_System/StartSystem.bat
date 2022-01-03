rem Enter the file path to YOUR project folder on the next line
cd "C:\Users\DEN - NEW\IdeaProjects\osFall2021\Distributed_System"
javac *.java
start cmd /k java Slave_A
start cmd /k java Slave_B
start cmd /k java Master
FOR /L %%x IN (0 1 3) DO start cmd /k java Client
exit
