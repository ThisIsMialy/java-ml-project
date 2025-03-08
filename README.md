# java-ml-project
Machine Learning to recognize handwritten digits with Support Vector Machine

## Description
The data is  [Optical Recognition of Handwritten Digits Data Set](https://archive.ics.uci.edu/dataset/80/optical+recognition+of+handwritten+digits "Optical Recognition of Handwritten Digits Data Set")
The goal for this machine is to recognize handwritten digits. Each digit has been
processed and generated into an input matrix of 8x8 where each element is an integer in the range 0…16. This means that each input has 64 features/ dimensions where each feature ranges from 0 to 16.
Solution:  **Multi-class Support Vector Machine** using the "one-against-all" method
Kernel: **Radial Basis Function**
Optimization: **SMO Algorithm** based on Platt, 1998
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
