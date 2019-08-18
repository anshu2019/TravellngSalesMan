# Travellng SalesMan Problem

Here we have implemented three different projcets to solve the Travelling Salesman Problem which is widely used in Algorithm circle.
1. Approximate approach 
Approx-TSP-Tour(G,c)
	1. Select a vertex r  V[G] to be a root vertex
	2. Compute a minimum spanning tree T for G from root r using MST-Prim(G,c,r)
	3. Let L be the list of vertices visited in a preorder tree walk of T
  4. Return the Hamiltonian cycle H that visits the vertices in the order L
  
2.Use Brute Force approach 
  One way to find an optimal tour is to simply list every possible tour and compute the length of each. Then, simply select the tour of minimum length. 
  There are |V| ! permutations of the |V| vertices. Each of these is a different Hamiltonian cycle. But half of these are the same cycle travelled in a different direction. We will not account  for the direction of travel. So, any minimal length cycle will do just fine. 

