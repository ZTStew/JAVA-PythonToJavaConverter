# The Purpose of 'Test1.py' is to provide a senario where comments and simple
# print statements are tested and converted into java

#

"""
This is a test file for testing the functionality of PythonToJavaConvert.java
which takes any Python and converts it into functioning Java code.
"""

# Generic print statement
print("Line 1")

# Print statement using concatenation
print("Line " + "2 " + " concatenation test (plus)") #

# Print statement to test how the convertor treats casting statements
# print("Line " + str(3) + "(casting)") ###
print("Line 3") ###

print("Line 4") # Tests comment reader for if it can detect comments after code.

# Tests converter when the comment notator is found in actual code.
print("Line #5")

# Tests print statements with 'print' inside
print("Line 6: print")

# Print statement to test the presence of the start of the word 'print'
print("Line 7: pr")

# Print statement tests using commas for concatenation
print("Line", "8")

################################################################################

print("Expected Output: \nLine 1\nLine 2 concatenation test (plus)\nLine 3\nLine 4")
print("Line #5\nLine 6: print\nLine 7: pr\nLine 8")    #

# Tests if statements
if 3 > 5:
    print("3 > 5")
elif 3 < 5:
    print("3 < 5")
else:
    print("3 == 5")

if not(False):
    print("not test")