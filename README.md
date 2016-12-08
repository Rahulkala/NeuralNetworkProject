NEURAL NETWORK PROJECT

Java based Neural Network code to train Autoassociative Network using Hebb's Rule. The input is digits from 0 to 9 in a 5*7 matrix i.e., 35 pixels for each number. Therefore, the input pattern will be 10*35 and is in the input file named TenDigitPatterns.txt

The groups identified are checked for maximum pairs which are closely and nearly orthogonal.

The groups are identified and checked for robustness by testing the network by introducing noise and error in input pattern from 1 bit to 35 bit.

Example Testing Output:

Random At: 1, 24, 5, 18, 16, 
Err Ip	Output
  ## 	 ### 
    #	#   #
#   #	#   #
## ##	#   #
#    	#   #
#   #	#   #
 ### 	 ### 
Classified to: 0

Err Ip	Output
 ##  	 ### 
##  	 ##  
# #  	#    
 ## 	  #  
  # 	  #  
  #  	  #  
#####	#####
WRONG CLASSIFICATION

Err Ip	Output
 ## 	 ### 
   #	#   #
   # 	   # 
 ## 	  #  
  # #	  #  
 #   	 #   
#####	#####
Classified to: 2

Err Ip	Output
 #  #	    #
  ##	   ##
  # #	  # #
  ##	 #  #
#### 	#####
    #	    #
    #	    #
Classified to: 4

Err Ip	Output
 ###	 ### 
     	#    
#    	#    
# #  	# ## 
#   	#   #
#   #	#   #
 ### 	 ### 
WRONG CLASSIFICATION

Err Ip	Output
####	#####
#   #	    #
   # 	   # 
		   # 
  # 	  #  
  #  	  #  
 #   	 #   
Classified to: 7
Total Classified: 4
