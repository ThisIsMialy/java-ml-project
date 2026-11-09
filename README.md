# java-ml-project
Machine Learning to recognize handwritten digits with Support Vector Machine
[Vanilla JAVA, no Machine Learning or Mathematics Libraries]

## Description
The data is  [Optical Recognition of Handwritten Digits Data Set](https://archive.ics.uci.edu/dataset/80/optical+recognition+of+handwritten+digits "Optical Recognition of Handwritten Digits Data Set")
The goal for this machine is to recognize handwritten digits. Each digit has been
processed and generated into an input matrix of 8x8 where each element is an integer in the range 0…16. This means that each input has 64 features/ dimensions where each feature ranges from 0 to 16.
Solution:  **Multi-class Support Vector Machine** using the "one-against-all" method
Kernel: **Radial Basis Function**
Optimization: **SMO Algorithm** based on Platt, 1998

<img width="473" height="141" alt="image" src="https://github.com/user-attachments/assets/4d06d536-5385-4594-ae41-688476872a27" />

#### Kernel Trick 
The issue with RBF is it gets very complicated quickly since it tackles an infinite hyperplane. Kernel Trick calculates the similarities between 2 vectors without plotting the vectors on this infinite hyperplane. Using this idea, it is possible to calculate the similarities between the support vectors and a given input X. This will output a value 

<img width="484" height="67" alt="image" src="https://github.com/user-attachments/assets/0e55ab14-7c9e-452c-a65a-15ced6f372d6" />

Values x and y are two points of the same amounts of dimensions and ||x – y|| is their Euclidian distance. 
The value of Gamma is dependant of the number of dimensions/features each data point has. This was mentioned in Vapnik’s (Cortes and Vapnik, 1995) experiment with a hand-written digit recognition as well. When using the RBF Kernel, they set their standard deviation at 0.75, as gamma is inversely proportional to variance, gamma is calculated as 


<img width="518" height="174" alt="image" src="https://github.com/user-attachments/assets/c3d1b3db-e0ce-4f52-8a3b-4d99f2de873f" />

#### Optimization
To optimize finding the Lagrange Multiplier for the support vectors, the SMO algorithm (Platt, 1998) optimize the input data to calculate the best support vectors and their respective alpha values.
SMO requires 2 Hyperparameters:
C – Soft Margin Penalty Parameter
The higher the value, the smaller the margin of error accepted. 
Tolerance (Epsilon) – Slack variable for the accuracy

#### Multi-class SVM
The dataset has 10 different classes which are numbers 0 to 9. Each input data therefore needs to be correctly classified in either one of them. Since each SVM is only capable of computing the similarity of a data input to a class/target it was trained on, there should an SVM for each class and test the data input against each of those SVMs. This is called the “One-against-all” method where each input is tested against all trained SVMs to find the best output.


## Baseline on UCI website
Nearest Neighbour (k =1) = 98%
Support Vector Classification = 97.607% (mean) and 98.275% (max)
# Hyperparameters
Default:
**C (soft-margin penalty) = 100**
**Tolerance = 0.01**
**RBF Gamma = 0.001**
## Test results
Mean accuracy = 98.4835% in 3.08 minutes


<img width="796" height="202" alt="image" src="https://github.com/user-attachments/assets/cd5229d9-ed52-4f05-8d96-a89ca02c5ce2" />

<img width="804" height="200" alt="image" src="https://github.com/user-attachments/assets/37ea6a2a-a142-4e3f-b3f4-11b4662f3f50" />

<img width="766" height="106" alt="image" src="https://github.com/user-attachments/assets/e9177835-6314-4fc7-8697-965b54fe097c" />




# Reference list
archive.ics.uci.edu. (n.d.). UCI Machine Learning Repository. [online] Available at:
https://archive.ics.uci.edu/dataset/80/optical+recognition+of+handwritten+digits
[Accessed 9 Jan. 2024].
Cortes, C. and Vapnik, V. (1995). Supportvector networks. Machine Learning, [online]
20(3), pp.273–297. doi:https://doi.org/10.1007/BF00994018.
Cristianini, N. and ShaweTaylor, J. (2000). References. In: An Introduction to Support
Vector Machines and Other Kernelbased Learning Methods. [online] Cambridge:
Cambridge University Press, p.173186.
doi:https://doi.org/10.1017/CBO9780511801389.013.
Kowalczyk, A. (2017). Support Vector Machines Succinctly. [online] Morrisville, NC
27560 USA: Syncfusion, Inc. Available at: https://www.syncfusion.com/succinctly-free-
ebooks [Accessed 3 Feb. 2024].
Platt, J. (1998). Sequential minimal optimization: A fast algorithm for training support
vector machines. [online] Microsoft. Available at: https://www.microsoft.com/en-
us/research/publication/sequential-minimal-optimization-a-fast-algorithm-for-
training-support-vector-machines/.
scikit-learn.org. (n.d.). 1.4. Support Vector Machines — scikit-learn 0.22.1 documentation.
[online] Available at: https://scikit-learn.org/stable/modules/svm.html#classification.
Starmer, J. (2019). Support Vector Machines Part 3: The Radial (RBF) Kernel (Part 3 of 3).
YouTube. Available at: https://www.youtube.com/watch?v=Qc5IyLW_hns [Accessed 9
Feb. 2024].
Thorsten Joachims (2002). Learning to Classify Text Using Support Vector Machines.
Springer Science Business Media New York.
Vladimir Naoumovitch Vapnik (2000). The Nature of Statistical Learning Theory. 2nd ed.
New York: Springer, Cop.
