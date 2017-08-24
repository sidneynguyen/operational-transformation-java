# operational-transformation-java

A simple library for building real-time collaboration applications.

Supports only insert, delete, and retain operations.

# Design
Uses a custom graph data structure to store and transform operations. Notable algorithms used are a modified version
of Levenshtein Distance to determine a user's edits and Operational Transformation to enable consistency of documents.
The transform function uses an algorithm I created and has a runtime of O(n). Apparently, a O(log(n)) transform function 
is possible.

# Usage
Coming Soon. Requires some more testing and bug fixing to ensure reliabilty.

# Resources
http://www.codecommit.com/blog/java/understanding-and-applying-operational-transformation

http://fitzgeraldnick.com/2011/04/05/operational-transformation-operations.html
